package com.stackexchange.codereview;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class Q113448
{
    public static void main(@Nonnull final String[] args)
    {
        final Path root = Paths.get("/Users/jhr/Documents/git/Stack-Overflow");
        final PathCollector pc = PathCollector.root(root).pathMatcher(new PathMatcher() {
            @Override public boolean matches(final Path path)
            {
                return path.getFileName().toString().endsWith(".java");
            }
        }).build();

        class Logger
        {
            @Subscribe
            public void log(@Nonnull final Path path) {System.out.println(path.toAbsolutePath().toString());}
        }

        final ImmutableSortedSet.Builder<Path> issb = ImmutableSortedSet.naturalOrder();
        class SetBuilder
        {
            @Subscribe
            public void add(@Nonnull final Path path) { issb.add(path); }
        }

        /* This is here as an example on how to plug in actions. It is not used in the example for obvious reasons. */
        class CopyToDirectory
        {
            private final Path destination;

            public CopyToDirectory(@Nonnull final Path destination)
            {
                checkArgument(destination.toFile().isDirectory(), "%s must be a Directory!");
                this.destination = destination;
            }

            @Subscribe
            public void copy(@Nonnull final Path path)
            {
                try { Files.copy(path, destination, StandardCopyOption.REPLACE_EXISTING); }
                catch (final IOException e) { throw new RuntimeException(e); }
            }

        }

        pc.register(new Logger());
        pc.register(new SetBuilder());
        /* commented out intentionally
        pc.register(new CopyToDirectory(Paths.get("/some/where/useful")));
        */
        System.out.println("In the order they are encountered in:");
        pc.run();
        System.out.println("===============================");
        System.out.println("In sorted order from a SortedSet");
        final Set<Path> paths = issb.build();
        final Iterator<Path> iterator = paths.iterator();
        for (int i = 0; iterator.hasNext(); i++)
        {
            System.out.format("%d : %s", i, iterator.next());
            System.out.println();
        }
    }

    public static class PathCollector implements Runnable
    {
        private enum Fields
        {
            ROOT, PATH_MATCHER
        }

        private static PathMatcher ALL_PATH_MATCHER;

        static
        {
            ALL_PATH_MATCHER = new PathMatcher()
            {
                @Override public boolean matches(final Path path)
                {
                    return true;
                }
            };

        }

        private final EventBus eventBus;
        private final Path root;
        private final Map<Fields, Object> config;

        private PathCollector(@Nonnull final Path root, @Nonnull final Map<Fields, Object> config)
        {
            this.eventBus = new EventBus("Path Collector");
            this.root = root.toFile().isDirectory() ? root : root.getParent();
            this.config = config;
        }

        public void register(@Nonnull final Object object)
        {
            this.eventBus.register(object);
        }

        @Override
        public void run()
        {
            try
            {
                Files.walkFileTree(this.root, new FileVisitor<Path>() {
                    @Override public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException
                    {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException
                    {
                        final PathMatcher pm = (PathMatcher) config.get(Fields.PATH_MATCHER);
                        if (pm.matches(file)) { eventBus.post(file); }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException
                    {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException
                    {
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        public static Builder root(@Nonnull final Path root)
        {
            final Map<Fields, Object> defaults = ImmutableMap.<Fields, Object>builder()
                                                         .put(Fields.PATH_MATCHER, ALL_PATH_MATCHER)
                                                         .put(Fields.ROOT, root)
                                                         .build();
            return new Builder()
            {
                final ImmutableMap.Builder<Fields, Object> imb = ImmutableMap.builder();

                @Override public Builder pathMatcher(@Nonnull final PathMatcher pathMatcher)
                {
                    imb.put(Fields.PATH_MATCHER, pathMatcher);
                    return this;
                }

                @Override public PathCollector build()
                {
                    final Map<Fields,Object> merged = Maps.newHashMap(defaults);
                    merged.putAll(imb.build());
                    return new PathCollector(root, ImmutableMap.copyOf(merged));
                }
            };
        }

        public interface Builder
        {
            public Builder pathMatcher(@Nonnull final PathMatcher pathMatcher);

            public PathCollector build();

        }
    }
}

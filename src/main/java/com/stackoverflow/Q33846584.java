package com.stackoverflow;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;

import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class Q33846584
{
    public static void main(final String[] args) throws Exception
    {
        checkArgument(args.length > 2, "You must supply at least 3 file paths as argumens dest, src, src, ...");
        final List<Path> paths = Lists.transform(Arrays.asList(args), new Function<String, Path>()
        {
            @Nullable @Override public Path apply(@Nullable String input)
            {
                return Paths.get(Preconditions.checkNotNull(input));
            }
        });

        final Path destination = paths.get(0);
        try (OutputStream fos = new FileOutputStream(destination.toFile()))
        {
            for (final Path p : paths.subList(1, paths.size() - 1))
            {
                System.out.format("Reading %s", p.toAbsolutePath());
                final FileInputStream fis = new FileInputStream(p.toFile());
                System.out.format(" writing to %s", destination.toAbsolutePath());
                ByteStreams.copy(fis,fos);
                System.out.println();
            }
        }
    }
}

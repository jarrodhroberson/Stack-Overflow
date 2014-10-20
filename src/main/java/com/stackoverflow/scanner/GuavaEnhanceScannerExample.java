package com.stackoverflow.scanner;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.io.Closer;
import com.google.common.primitives.Ints;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class GuavaEnhanceScannerExample
{
    private static final Set<Command> COMMANDS;
    private static final Pattern DATE_PATTERN;
    private static final String HELP_MESSAGE;

    static
    {
        DATE_PATTERN = Pattern.compile("\\d{4}([-\\/])\\d{2}\\1\\d{2}"); // http://regex101.com/r/xB8dR3/1
        final ImmutableSet.Builder<Command> cb = ImmutableSet.builder();
        final ImmutableSet<String> exits = ImmutableSet.copyOf(new String[]{"exit", "done", "quit", "end", "fino"});
        cb.add(new Command<Void>(new Function<Scanner, Void>()
        {
            @Nullable
            @Override
            public Void apply(@Nullable final Scanner scanner)
            {
                output("Exit command issued, exiting!");
                System.exit(0);
                return null;
            }
        }, exits.toArray(new String[exits.size()])));
        cb.add(new Command<Void>(new Function<Scanner, Void>()
        {
            @Nullable
            @Override
            public Void apply(@Nullable final Scanner scanner)
            {
                output(HELP_MESSAGE);
                return null;
            }
        }, "help", "helpi", "?"));
        HELP_MESSAGE = format("Please enter some data or enter one of the following commands to exit %s", exits);
        cb.add(new Command<List<Integer>>(new Function<Scanner, List<Integer>>()
        {
            @Nullable
            @Override
            public List<Integer> apply(@Nullable final Scanner scanner)
            {
                final ImmutableList.Builder<Integer> lb = ImmutableList.builder();
                while (scanner.hasNextInt())
                {
                    lb.add(scanner.nextInt());
                }
                return lb.build();
            }
        }, "/ints"));
        cb.add(new Command<String>(new Function<Scanner, String>()
        {
            @Nullable
            @Override
            public String apply(@Nullable final Scanner scanner)
            {
                return scanner.next();
            }
        }, "?"));
        COMMANDS = cb.build();
    }

    /**
     * Using exceptions to control execution flow is always bad.
     * That is why this is encapsulated in a method, this is done this
     * way specifically so as not to introduce any external libraries
     * so that this is a completely self contained example.
     * @param s possible url
     * @return true if s represents a valid url, otherwise false
     */
    private static boolean isValidURL(@Nonnull final String s)
    {
        try { new URL(s); return true; }
        catch (final MalformedURLException e) { return false; }
    }

    private static void output(@Nonnull final String format, @Nonnull final Object... args)
    {
        System.out.println(format(format, args));
    }

    public static void main(final String[] args)
    {
        final Closer closer = Closer.create();
        final ModernScanner sis = new ModernScanner(System.in);
        closer.register(sis);
        output(HELP_MESSAGE);
        final ModernScanner.PredicateIterator<String> iter = sis.predicateIterator();
        while (iter.hasNext())
        {

        }
        /*
           This will close the underlying Readable, in this case System.in, and free those resources.
           You will not be to read from System.in anymore after this you call .close().
           If you wanted to use System.in for something else, then don't close the Scanner.
        */
        try { closer.close(); } catch (final IOException e) { System.err.print(e.getMessage()); }
        finally { System.exit(0); }
    }
}

package com.stackoverflow.scanner;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class ModernScanner implements Iterable<String>, Closeable
{
    private final InputStream is;
    private final PredicateIterator<String> it;
    private final AtomicReference<Character> as;

    public ModernScanner(@Nonnull final CharSequence s)
    {
        this(new ByteArrayInputStream(s.toString().getBytes(Charsets.UTF_8)));
    }

    public ModernScanner(@Nonnull final InputStream source)
    {
        this.is = source;
        this.as = new AtomicReference<Character>('\n');
        this.it = new PredicateIterator<String>()
        {
            final StringBuilder sb = new StringBuilder(1024);

            @Override
            public boolean hasNext()
            {
                if (sb.length() > 0)
                {
                    return true;
                }
                else
                {
                    try
                    {
                        int i;
                        while ((i = is.read()) != ModernScanner.this.as.get())
                        {
                            sb.append((char) i);
                        }
                        return sb.length() > 0;
                    }
                    catch (final IOException e)
                    {
                        sb.delete(0, sb.length());
                        try
                        {
                            is.close();
                            return false;
                        }
                        catch (final IOException ce) { return false; }
                    }
                }
            }

            @Nonnull
            @Override
            public String next()
            {
                final String next = sb.toString();
                sb.delete(0, sb.length());
                return next;
            }

            @Override
            public void remove() { throw new UnsupportedOperationException(); }

            @Override
            public boolean hasNext(@Nonnull final Predicate<String> p)
            {
                return this.hasNext() && p.apply(sb.toString());
            }

            @Nonnull
            @Override
            public <N> N next(@Nonnull final Function<String, N> function)
            {
                return function.apply(this.next());
            }
        };
    }

    @Nonnull
    public Character getDelimiter() { return this.as.get(); }

    public void setDelimiter(@Nonnull final Character c) { this.as.set(c); }

    @Override
    public void close() throws IOException { is.close(); }

    @Nonnull
    public PredicateIterator<String> predicateIterator()
    {
        return this.it;
    }

    @Override
    public Iterator<String> iterator() { return this.it; }

    public static interface PredicateIterator<String> extends Iterator<String>
    {
        public boolean hasNext(@Nonnull final Predicate<String> p);

        public <N> N next(@Nonnull final Function<String, N> function);
    }

    public class NextStrategy<T>
    {
        private final Predicate<String> p;
        private final Function<String,T> f;

        public NextStrategy(@Nonnull final Predicate<String> p, @Nonnull final Function<String, T> f)
        {
            this.p = p;
            this.f = f;
        }

        private boolean isNext() { return ModernScanner.this.it.hasNext(this.p);}

        private T next() { return ModernScanner.this.it.next(this.f); }
    }
}

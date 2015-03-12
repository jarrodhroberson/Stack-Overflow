package com.stackoverflow.scanner;

import java.io.*;
import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nonnull;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.PeekingIterator;
import com.vertigrated.FormattedRuntimeException;

public class ModernScanner implements Iterable<String>, Closeable
{
    private final boolean isSystemIn;
    private final Reader br;
    private final PredicateIterator<String> pit;
    private final AtomicReference<Character> arc;

    public ModernScanner(@Nonnull final InputStream source)
    {
        this.isSystemIn = source == System.in;
        this.br = new BufferedReader(new InputStreamReader(source, Charsets.UTF_8));
        this.arc = new AtomicReference<Character>('\n');
        this.pit = new PredicateIterator<String>()
        {
            private final StringBuilder sb = new StringBuilder(1024);
            private final Reader br = ModernScanner.this.br;

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
                        final CharBuffer cb = CharBuffer.allocate(1024);
                        find:
                        while (true)
                        {
                            this.br.mark(0);
                            while (this.br.read(cb) != -1)
                            {
                                for (int i = 0; i < cb.length(); i++)
                                {
                                    final char c = cb.charAt(i);
                                    if (c == ModernScanner.this.arc.get())
                                    {
                                        this.sb.append(cb.flip());
                                        this.br.reset();
                                        this.br.skip(i + 1);
                                        break find;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                    catch (final IOException e)
                    {
                        sb.delete(0, sb.length());
                        try
                        {
                            this.br.close();
                            return false;
                        }
                        catch (final IOException ce) { return false; }
                    }
                }
            }

            @Override
            public String peek()
            {
                return sb.toString();
            }

            @Nonnull
            @Override
            public String next()
            {
                final String next = this.sb.toString();
                this.sb.delete(0, next.length());
                sb.ensureCapacity(1024);
                return next;
            }

            @Override
            public void remove() { throw new UnsupportedOperationException(); }

            @Override
            public <N> boolean hasNext(@Nonnull final NextStrategy<N> s) { return this.hasNext() && s.is(sb.toString()); }

            @Override
            public <N> N next(@Nonnull final NextStrategy<N> s) { return s.of(this.next()); }
        };
    }

    @Nonnull
    public Character getDelimiter() { return this.arc.get(); }

    public void setDelimiter(@Nonnull final Character c) { this.arc.set(c); }

    @Override
    public void close() throws IOException
    {
        if (this.isSystemIn)
        {
            throw new FormattedRuntimeException("Backing InputStream is 'System.in'.\nClosing 'System.in' is a bad idea!\nIf this is what you really want to do you should catch this exception and deal with it.");
        }
        else
        {
            br.close();
        }
    }

    @Nonnull
    public PredicateIterator<String> predicateIterator()
    {
        return this.pit;
    }

    @Override
    public Iterator<String> iterator() { return this.pit; }

    public interface PredicateIterator<String> extends Iterator<String>, PeekingIterator<String>
    {
        public <N> boolean hasNext(@Nonnull final NextStrategy<N> s);

        public <N> N next(@Nonnull final NextStrategy<N> s);

        @Override
        public String peek();
    }

    public class NextStrategy<T>
    {
        private final Predicate<String> p;
        private final Function<String, T> f;

        public NextStrategy(@Nonnull final Predicate<java.lang.String> p, @Nonnull final Function<java.lang.String, T> f)
        {
            this.p = p;
            this.f = f;
        }

        public boolean is(@Nonnull final String s) { return this.p.apply(s); }

        public T of(@Nonnull final String s) { return this.f.apply(s); }
    }
}

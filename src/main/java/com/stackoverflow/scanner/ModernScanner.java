package com.stackoverflow.scanner;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.Atomics;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public class ModernScanner implements Iterable<String>, Closeable
{
    private final InputStream is;
    private final Iterator<String> it;
    private final AtomicReference<Character> as;

    public ModernScanner(@Nonnull final CharSequence s)
    {
        this(new ByteArrayInputStream(s.toString().getBytes(Charsets.UTF_8)));
    }

    public ModernScanner(@Nonnull final InputStream source)
    {
        this.is = source;
        this.as = new AtomicReference<Character>('\n');
        this.it = new Iterator<String>()
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

            @Override
            public String next()
            {
                final String next = sb.toString();
                sb.delete(0, sb.length());
                return next;
            }

            @Override
            public void remove() { throw new UnsupportedOperationException(); }
        };
    }

    public void setDelimiter(@Nonnull final Character c) { this.as.set(c); }

    public Character getDelimiter() { return this.as.get(); }

    @Override
    public void close() throws IOException { is.close(); }

    @Override
    public Iterator<String> iterator() { return this.it; }
}

package com.vertigrated;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nonnull;

public class BomProofInputStream extends InputStream
{
    private final InputStream is;

    public BomProofInputStream(@Nonnull final InputStream is)
    {
        this.is = is;
    }

    private boolean isFirstByte = true;

    @Override
    public int read() throws IOException
    {
        final int b = is.read();
        if (this.isFirstByte && "\uFEFF".charAt(0) == b)
        {
            this.isFirstByte = false;
            return is.read();
        }
        else
        {
            return b;
        }
    }
}

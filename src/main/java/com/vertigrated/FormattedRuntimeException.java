package com.vertigrated;


import javax.annotation.Nonnull;

/**
 * This is a convenience facade class to enable simple message creation with the String.format()
 * facilities instead of manually building strings to pass in as a message.
 * <p/>
 */
public class FormattedRuntimeException extends RuntimeException
{
    public FormattedRuntimeException(@Nonnull final String format, @Nonnull Object... args)
    {
        super(String.format(format, args));
    }

    public FormattedRuntimeException(@Nonnull final Throwable cause, @Nonnull String format, @Nonnull Object... args)
    {
        super(String.format(format, args), cause);
    }

    /**
     * This no-arg constructor is hidden specifically to keep people from using it
     */
    private FormattedRuntimeException()
    { /* keep people from using "anonymous" instances */ }

    /**
     * This Exception only constructor is hidden specifically to keep people from using it
     */
    private FormattedRuntimeException(final Exception e)
    { /* keep people from using "anonymous" instances */ }
}

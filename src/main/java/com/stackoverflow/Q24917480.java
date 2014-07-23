package com.stackoverflow;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q24917480
{
    public static void main(final String[] args)
    {
        final Pattern p = Pattern.compile("(\\d+)([hmsd])");
        final Matcher m = p.matcher("5d3h6m");
        long totalMillis = 0;
        while (m.find())
        {
            final int duration = Integer.parseInt(m.group(1));
            final TimeUnit interval = toTimeUnit(m.group(2));
            final long l = interval.toMillis(duration);
            totalMillis = totalMillis + l;
            System.out.format("TimeUnit.%s.toMillis(%s) = %d\n", interval.name(), duration, l);
        }
        System.out.println("TimeUnit.MILLISECONDS.toSeconds(totalMillis) = " + TimeUnit.MILLISECONDS.toSeconds(totalMillis));
        System.out.println("TimeUnit.MILLISECONDS.toMinutes(totalMillis) = " + TimeUnit.MILLISECONDS.toMinutes(totalMillis));
        System.out.println("TimeUnit.MILLISECONDS.toDays(totalMillis) = " + TimeUnit.MILLISECONDS.toDays(totalMillis));
    }

    public static TimeUnit toTimeUnit(@Nonnull final String c)
    {
        switch (c)
        {
            case "s": return TimeUnit.SECONDS;
            case "m": return TimeUnit.MINUTES;
            case "h": return TimeUnit.HOURS;
            case "d": return TimeUnit.DAYS;
            default: throw new IllegalArgumentException(String.format("%s is not a valid code [smhd]", c));
        }
    }
}

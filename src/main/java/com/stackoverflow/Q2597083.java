package com.stackoverflow;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Q2597083
{
    /**
     * All Dates are normalized to UTC, it is up the client code to convert to the appropriate TimeZone.
     */
    public static final TimeZone UTC;

    /**
     * @see <a href="http://en.wikipedia.org/wiki/ISO_8601#Combined_date_and_time_representations">Combined Date and Time Representations</a>
     */
    public static final String ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * 0001-01-01T00:00:00.000Z
     */
    public static final Date BEGINNING_OF_TIME;

    /**
     * 292278994-08-17T07:12:55.807Z
     */
    public static final Date END_OF_TIME;

    static
    {
        UTC = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(UTC);
        final Calendar c = new GregorianCalendar(UTC);
        c.set(1, 0, 1, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        BEGINNING_OF_TIME = c.getTime();
        c.setTime(new Date(Long.MAX_VALUE));
        END_OF_TIME = c.getTime();
    }

    public static void main(String[] args) throws Exception
    {

        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_24H_FULL_FORMAT);
        sdf.setTimeZone(UTC);
        System.out.println("sdf.format(Dates.BEGINNING_OF_TIME) = " + sdf.format(BEGINNING_OF_TIME));
        System.out.println("sdf.format(Dates.END_OF_TIME) = " + sdf.format(END_OF_TIME));
        System.out.println("sdf.format(new Date()) = " + sdf.format(new Date()));
        System.out.println("sdf.parse(\"2015-04-28T14:23:38.521Z\") = " + sdf.parse("2015-04-28T14:23:38.521Z"));
        System.out.println("sdf.parse(\"0001-01-01T00:00:00.000Z\") = " + sdf.parse("0001-01-01T00:00:00.000Z"));
        System.out.println("sdf.parse(\"292278994-08-17T07:12:55.807Z\") = " + sdf.parse("292278994-08-17T07:12:55.807Z"));
    }
}

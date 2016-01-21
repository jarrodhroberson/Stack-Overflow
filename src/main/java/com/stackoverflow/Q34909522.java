package com.stackoverflow;

import com.google.common.base.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class Q34909522
{
    private static enum Month implements Predicate<Integer>
    {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(28),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        public final int daysInMonth;

        private Month(final int daysInMonth)
        {
            this.daysInMonth = daysInMonth;
        }

        @Override
        public boolean apply(@Nullable final Integer day)
        {
            return day != null && day >= 1 && day <= this.daysInMonth;
        }
    }

    public static void main(@Nonnull final String[] args)
    {
        /** Scanner excluded for brevity */
        for (final Month m : Month.values())
        {
            for (int i = 0; i <= 33; i++)
            {
                if (!m.apply(i))
                {
                    System.out.format("%d is not a valid day of the month for %s (%d)", i, m, m.daysInMonth);
                    System.out.println();
                }
            }
        }
    }

    /**
     * Leap Year Example
     */
    private static class YearMonth implements Predicate<Integer>
    {
        private final int year;
        private final Month month;

        public YearMonth(final int year, @Nonnull final Month month)
        {
            this.year = year;
            this.month = month;
        }

        private boolean isLeapYear()
        {
            return this.year % 4 == 0 && (this.year % 100 != 0 || this.year % 400 == 0);
        }

        @Override
        public boolean apply(@Nullable final Integer integer)
        {
            if (this.isLeapYear() && Month.FEBRUARY.equals(this.month))
            {
                return this.month.apply(checkNotNull(integer) - 1);
            }
            else
            {
                return this.month.apply(integer);
            }
        }
    }
}

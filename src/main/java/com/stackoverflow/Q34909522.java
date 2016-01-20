package com.stackoverflow;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;

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
}

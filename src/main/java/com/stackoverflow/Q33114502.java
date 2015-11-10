package com.stackoverflow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Q33114502
{
    private static int LINE_LENGTH = 50;
    private static int NOT_HAPPY = -1;

    private static List<Integer> toDigits(int i)
    {
        final String n = Integer.toString(i);
        final char[] chars = n.toCharArray();
        final List<Integer> digits = new ArrayList<>();
        for (final char c : chars) { digits.add(Integer.parseInt(String.valueOf(c))); }
        return digits;
    }

    private static List<Integer> square(final List<Integer> li)
    {
        final List<Integer> squares = new ArrayList<>();
        for (final Integer i : li) { squares.add(i * i); }
        return squares;
    }

    private static int sum(final List<Integer> li)
    {
        int sum = 0;
        for (final Integer i : li) { sum = sum + i; }
        return sum;
    }

    private static boolean isHappy(final int i)
    {
        return i == 1 || i != 4 && isHappy(sum(square(toDigits(i))));
    }

    public static void main(final String[] args)
    {
        final int start = Integer.parseInt(args[0]);
        final int end = Integer.parseInt(args[1]);

        final List<Integer> happy = new ArrayList<Integer>();
        for (int i = start; i <= end; i++)
        {
            if (isHappy(i)) { happy.add(i); }
            else { happy.add(NOT_HAPPY); }
        }
        final StringBuilder sb = new StringBuilder(60);
        final Iterator<Integer> ii = happy.iterator();
        while (ii.hasNext())
        {
            final int i = ii.next();
            if (i == NOT_HAPPY) { sb.append("."); }
            else { sb.append(String.format("%d", i)); }
            if (sb.length() >= LINE_LENGTH)
            {
                System.out.println(sb.toString());
                sb.setLength(0);
                sb.ensureCapacity(60);
            }
            else if (!ii.hasNext())
            {
                System.out.println(sb.toString());
            }
        }
    }
}
package com.stackoverflow;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q25004732
{
    private static final Pattern P = Pattern.compile("(\\d+)-(\\d+)");

    public static void main(final String[] args)
    {
        final int input = 13;
        final String[] sa = "1,3,10-15,17".split(",");
        final Set<Integer> is = new HashSet<Integer>();
        for (final String s : sa)
        {
            final Matcher m = P.matcher(s);
            if (m.find())
            {
                final int start = Integer.parseInt(m.group(1));
                final int end = Integer.parseInt(m.group(2));
                for (int i = start; i <= end; i++)
                {
                    is.add(i);
                }
            }
            else
            {
                is.add(Integer.parseInt(s));
            }
        }

        if (is.contains(input))
        {
            System.out.format("Found %d in the set %s", input, is);
        }
        else
        {
            System.out.format("Didn't find %d in set %s", input, is);
        }
        System.out.println();
    }
}

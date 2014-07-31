package com.stackoverflow;

import javax.annotation.Nonnull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

public class Q25004732
{
    private static final Pattern RANGE = Pattern.compile("(?i)^(?=[a-z]+-[a-z]+$|\\d+-\\d+$)([a-z\\d]+)-([a-z\\d]+)$");

    public static void main(final String[] args)
    {
        final String[] sa = "1,3,10-15,17,A,b,XX-ZZ,z".split(",");
        final RangeSet<String> is = TreeRangeSet.create();
        for (final String s : sa)
        {
            System.out.println("s = " + s);
            final Matcher m = RANGE.matcher(s);
            if (m.find())
            {
                is.add(Range.closed(m.group(1), m.group(2)));
            }
            else
            {
                is.add(Range.closed(s, s));
            }
        }
        report("13", is);
        report("A", is);
        report("XY", is);
    }

    private static void report(@Nonnull final String input, @Nonnull final RangeSet<String> rs)
    {
        if (rs.contains(input))
        {
            System.out.format("Found %s in the set %s", input, rs);
        }
        else
        {
            System.out.format("Didn't find %s in set %s", input, rs);
        }
        System.out.println();
    }
}

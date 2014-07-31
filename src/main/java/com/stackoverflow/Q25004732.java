package com.stackoverflow;

import javax.annotation.Nonnull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

public class Q25004732
{
    // regex demo and explanation http://regex101.com/r/yU2eT4/2
    private static final Pattern RANGE = Pattern.compile("(?i)^(?=[a-z]+-[a-z]+$|\\d+-\\d+$)([a-z\\d]+)-([a-z\\d]+)$");

    public static void main(final String[] args)
    {
        final String[] sa = "1,3,10-15,17,A,b,XX-ZZ,z".split(",");
        final RangeSet<String> rs = TreeRangeSet.create();
        for (final String s : sa)
        {
            final Matcher m = RANGE.matcher(s);
            if (m.find())
            {
                rs.add(Range.closed(m.group(1), m.group(2)));
            }
            else
            {
                rs.add(Range.closed(s, s));
            }
        }
        report("13", rs);
        report("A", rs);
        report("XY", rs);
        report("c", rs);
        report("42", rs);
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

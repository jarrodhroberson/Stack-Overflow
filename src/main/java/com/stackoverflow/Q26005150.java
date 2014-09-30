package com.stackoverflow;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

public class Q26005150
{
    private static final Pattern P = Pattern.compile("(<\\/?(\\w+)>|(\\d+))");

    public static void main(String[] args)
    {
        final String s1 = "1234 <CIRCLE> 12 12 12 </CIRCLE>";
        final String s2 = "1234 <RECTANGLE> 12 12 12 12 </RECTANGLE>";

        final List<String> l1 = getAllMatches(s1);
        final List<String> l2 = getAllMatches(s2);

        System.out.println("l1 = " + l1);
        System.out.println("l2 = " + l2);
    }

    private static List<String> getAllMatches(@Nonnull final String s)
    {
        final Matcher m = P.matcher(s);
        final List<String> matches = new ArrayList<String>();
        while(m.find())
        {
            final String match = m.group(1);
            matches.add(match);
        }
        return matches;
    }
}

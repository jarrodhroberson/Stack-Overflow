package com.stackoverflow;

import java.util.Random;

/**
 * This is a very naive proof of concept for creating a "rolling checksum" type identifier.
 * The first two characters are subtracted to create the third character which is the distance between the first two.
 * This is fast but error prone, in that incorrect characters are can be detected and corrected but
 * transpositions of the first two characters are not detected.
 *
 * This just a straw man on how one might go about creating a "rolling checksum" self correcting string.
 * Being able to detect transpositions would create a fairly robust error correcting code, at the expense of
 * inflating the data 33%. But the checksum is part of the data at that point, so it isn't really a negative.
 */
public class Q25001549
{
    private static final Random RND;

    static
    {
        RND = new Random();
    }

    private static char randomCharacter()
    {
        return (char)(RND.nextInt(26) + 'a');
    }

    private static char checksum(char a, char b)
    {
        final int d = a - b;
        return (char) (Math.abs(d) + (d < 0 ? 'a' : 'A'));
    }

    public static void main(final String[] args)
    {
        final StringBuilder sb = new StringBuilder(9);
        for (int i=0; i < 3; i++)
        {
            final char a = randomCharacter();
            final char b = randomCharacter();
            final char c = checksum(a, b);
            System.out.println("a = " + (int)a);
            System.out.println("b = " + (int)b);
            System.out.println("c = " + (int)c);
            sb.append(a).append(b).append(c);
            System.out.println();
        }
        System.out.println("sb.toString() = " + sb.toString());
        final String s = sb.toString();
        verify(sb);
        sb.setCharAt(1,'x');
        verify(sb);
    }

    private static void verify(final StringBuilder sb)
    {
        for (int i=0; i < sb.length(); i += 3)
        {
            final char a = sb.charAt(i);
            final char b = sb.charAt(i+1);
            final char c = sb.charAt(i+2);
            final char x = checksum(a, b);
            if (x==c)
            {
                System.out.format("%s%s%s", a, b, c);
            }
            else
            {
                System.out.format("\n%s != %s", x, sb.charAt(i));
            }
        }
    }
}

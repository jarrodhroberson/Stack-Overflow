package com.stackoverflow;

import java.util.Random;

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

    public static void main(final String[] args)
    {
        final StringBuilder sb = new StringBuilder(9);
        for (int i=0; i < 3; i++)
        {
            final char a = randomCharacter();
            final char b = randomCharacter();
            final char c = (char)(Math.abs(a - b) + 'a');
            System.out.println("a = " + (int)a);
            System.out.println("b = " + (int)b);
            System.out.println("c = " + (int)c);
            sb.append(a).append(b).append(c);
            System.out.println();
        }
        System.out.println("sb.toString() = " + sb.toString());
        for (int i=0; i < sb.length(); i += 3)
        {
            final char a = sb.charAt(i);
            final char b = sb.charAt(i+1);
            final char c = sb.charAt(i+2);
            final char x = (char)(Math.abs(a - b) + 'a');
            if (x==c)
            {
                System.out.format("%s%s%s:", a, b, c);
            }
            else
            {
                System.out.format("\n%s != %s", x, c);
            }
        }
    }
}

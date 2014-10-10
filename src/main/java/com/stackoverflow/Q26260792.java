package com.stackoverflow;

import javax.annotation.Nonnull;

public class Q26260792
{
    private double a = 42.0;

    public static void main(final String[] args)
    {
        final Q26260792 q = new Q26260792();
        System.out.println(q.add(new Short((short)1)));
        System.out.println(q.add(new Integer(2)));
        System.out.println(q.add(new Long(1000L)));
        System.out.println(q.add(new Double(1.5)));
        System.out.println(q.add(new Float(1.3)));
    }

    public <T extends Number> double add(@Nonnull final T n)
    {
        return a + n.doubleValue();
    }
}

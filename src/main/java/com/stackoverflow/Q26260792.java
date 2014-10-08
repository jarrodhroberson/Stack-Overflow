package com.stackoverflow;

import javax.annotation.Nonnull;

import com.google.common.reflect.TypeToken;

public class Q26260792
{
    private Double a = 42.0;

    public static void main(final String[] args)
    {
        final Q26260792 q = new Q26260792();
        System.out.println(q.add(new Short((short)1)));
        System.out.println(q.add(new Integer(2)));
        System.out.println(q.add(new Long(1000L)));
        System.out.println(q.add(new Double(1.5)));
        System.out.println(q.add(new Float(1.3)));
    }

    @SuppressWarnings("unchecked")
    public <T extends Number> T add(@Nonnull final T n)
    {
        final Class<T> entityType = (Class<T>) new TypeToken<T>(getClass()) {}.getRawType();
        return entityType.cast(a + n.doubleValue());
    }
}

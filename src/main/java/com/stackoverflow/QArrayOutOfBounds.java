package com.stackoverflow;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;

public class QArrayOutOfBounds
{
    private static <T> T get(@Nonnull final Iterable<T> iterable, final int index, @Nonnull final T missing)
    {
        if (index < 0) { return missing; }
        if (iterable instanceof List)
        {
            final List<T> l = List.class.cast(iterable);
            return l.size() <= index ? l.get(index) : missing;
        }
        else
        {
            final Iterator<T> iterator = iterable.iterator();
            for (int i = 0; iterator.hasNext(); i++)
            {
                final T o = iterator.next();
                if (i == index) { return o; }
            }
            return missing;
        }
    }

    public static void main(final String[] args)
    {
        /* this example uses a raw array of primitaives
        *  because that is what is the first construct
        *  taught to new students.
        *  In common usage it is always better to use
        *  ArrayList<T> where T is the type you need.
         */
        final int ints[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        final List<Integer> toTen = ImmutableList.copyOf(Ints.asList(ints));
        System.out.println(Iterables.get(toTen, 0));
        System.out.println(Iterables.get(toTen, 100, -1));


        /* traditional idiomatic for/next loop
        *  susceptible to one off errors and is the most
        *  common cause of java.lang.ArrayIndexOutOfBoundsException
        *  */
        for (int i = 0; i < ints.length; i++)
        {
            System.out.format("index %d = %d", i, ints[i]);
            System.out.println();
        }

        /* idomatic "enhanced" for/each loop can't cause
        *  java.lang.ArrayIndexOutOfBoundsException as long
        *  as no other threads modify it during traversal.
        *  That would cause a ConcurrentModificationException
        * */
        for (final int i : ints)
        {
            System.out.println(i);
        }

        /* This uses Guava to easily conver the int[] to
        *  something Iterable every project should include it
        */
        final Iterator<Integer> it = Ints.asList(ints).iterator();
        for (int i = 0; it.hasNext(); i++)
        {
            System.out.format("index %d = %d", i, it.next());
            System.out.println();
        }

        /* Even better is alway use ImmutableLists/Set/Maps */
        final ImmutableList<Integer> ili = ImmutableList.copyOf(Ints.asList(ints));
        final Iterator<Integer> iit = ili.iterator();
        for (int i = 0; iit.hasNext(); i++)
        {
            System.out.format("index %d = %d", i, it.next());
            System.out.println();
        }
    }
}

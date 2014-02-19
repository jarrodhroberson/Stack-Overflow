package com.stackoverflow;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Q21871053
{
    public static void main(final String[] args)
    {
        final Random rnd = new Random();

        final Map<Integer, AtomicInteger> counter = new HashMap<Integer, AtomicInteger>()
        {
            @Override
            public AtomicInteger get(final Object key)
            {
                if (!super.containsKey(key)) { super.put((Integer) key, new AtomicInteger(rnd.nextInt(100))); }
                return super.get(key);
            }
        };

        final AtomicInteger nextInt = new AtomicInteger(rnd.nextInt(100));
        while (nextInt.get() != 0)
        {
            counter.get(nextInt.get()).incrementAndGet();
            if (counter.size() > 1000) { nextInt.set(0); } // limit the run to 1000 numbers
            else { nextInt.set(rnd.nextInt(100)); }
        }

        for (final Integer i : counter.keySet())
        {
            System.out.format("%d occurs %s time\n", i, counter.get(i));
        }
    }
}

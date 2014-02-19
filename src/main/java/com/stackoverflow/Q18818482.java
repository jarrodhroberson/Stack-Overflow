package com.stackoverflow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Q18818482
{
    public static Random RND;

    static
    {
        RND = new Random();
    }

    public static void main(final String[] args)
    {
        try
        {
            final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            final List<Future<String>> results = new ArrayList<>(10);
            for (int i = 0; i < 10; i++)
            {
                results.add(es.submit(new TimeSliceTask(RND.nextInt(10), TimeUnit.SECONDS)));
            }
            es.shutdown();
            while(!results.isEmpty())
            {
                final Iterator<Future<String>> i = results.iterator();
                while (i.hasNext())
                {
                    final Future<String> f = i.next();
                    if (f.isDone())
                    {
                        System.out.println(f.get());
                        i.remove();
                    }
                }
            }
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static class TimeSliceTask implements Callable<String>
    {
        private final long timeToLive;
        private final long duration;


        public TimeSliceTask(final long timeToLive, final TimeUnit timeUnit)
        {
            this.timeToLive = System.nanoTime() + timeUnit.toNanos(timeToLive);
            this.duration = timeUnit.toMillis(timeToLive);
        }

        @Override
        public String call() throws Exception
        {
            while( timeToLive <= System.nanoTime() )
            {
                // simulate work here
                Thread.sleep(500);
            }
            final long end = System.nanoTime();
            return String.format("Finished Elapsed Time = %d, scheduled for %d", TimeUnit.NANOSECONDS.toMillis(timeToLive - end), this.duration );
        }
    }
}
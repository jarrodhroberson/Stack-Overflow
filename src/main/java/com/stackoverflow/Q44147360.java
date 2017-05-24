package com.stackoverflow;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static java.lang.String.format;

public class Q44147360
{
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new NamedThreadFactory(new Function<String, String>()
    {
        private final AtomicInteger ordinal = new AtomicInteger(0);

        @Override
        public String apply(String s)
        {
            return format("A%03d", this.ordinal.incrementAndGet());
        }
    }));

    private static int SHARE_COUNT = 0;

    private static final AtomicInteger SYNC_COUNT = new AtomicInteger(0);

    private static class NamedThreadFactory implements ThreadFactory
    {
        private final ThreadFactory threadFactory = Executors.defaultThreadFactory();
        private final Function<String, String> namingStrategy;

        NamedThreadFactory(@Nonnull final Function<String, String> namingStrategy)
        {
            this.namingStrategy = namingStrategy;
        }

        @Nonnull
        @Override
        public Thread newThread(@Nonnull final Runnable r)
        {
            final Thread thread = this.threadFactory.newThread(r);
            thread.setName(this.namingStrategy.apply(null));
            return thread;
        }
    }

    private static class Counter implements Runnable
    {
        public void run()
        {
            for (int ii = 1; ii <= 500; ii++)
            {
                SYNC_COUNT.incrementAndGet();
                System.out.println(format("%s : is at %03d with syncCount = %04d",Thread.currentThread().getName(), ii, SYNC_COUNT.get()));
                try
                {
                    Thread.sleep(1);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                SHARE_COUNT++;
            }
        }
    }

    public static void main(final String[] args)
    {
        final int numberOfCounters = Integer.parseInt(args[0]);
        for (int ii = 0; ii < numberOfCounters; ii++)
        {
            EXECUTOR.execute(new Counter());
        }
        EXECUTOR.shutdown();
        try
        {
            EXECUTOR.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace(System.err);
        }
        System.out.println(format("Share Count = %03d", SHARE_COUNT));
        System.out.println(format("Sync Count  = %03d", SYNC_COUNT.get()));
    }
}
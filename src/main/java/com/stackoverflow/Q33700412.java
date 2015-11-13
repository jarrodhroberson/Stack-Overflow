package com.stackoverflow;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.ThreadSafe;

import com.vertigrated.FormattedRuntimeException;

public class Q33700412
{
    public static void main(final String[] args)
    {
        final Storage s = new Storage(100);
        final int ap = Runtime.getRuntime().availableProcessors();
        final ExecutorService es = Executors.newFixedThreadPool(ap);
        for (int i = 0; i < ap; i++)
        {
            es.execute(new Runnable()
            {
                final Random r = new Random();

                @Override
                public void run()
                {
                    while (!es.isShutdown())
                    {
                        /* this if/else block is NOT thread safe, I did this on purpose
                           the state can change between s.remainingCapacity() and
                           the call to s.increase/s.decrease.
                           This is ok, because the Storage object is internally consistent.
                           This thread might fail if this happens, but that is all.
                        */
                        if (s.remainingCapacity() > 0)
                        {
                            if (r.nextBoolean()) { s.increase(r.nextInt(10)); }
                            else { s.decrease(10); }
                            System.out.format("Current Capacity is %d", s.getCurrentCapacity());
                            System.out.println();
                        }
                        else
                        {
                            System.out.format("Max Capacity %d Reached", s.getMaxCapacity());
                            System.out.println();
                        }
                        try { Thread.sleep(r.nextInt(5000)); }
                        catch (InterruptedException e) { System.out.println("Done!"); }
                    }
                }
            });
        }
        try
        {
            Thread.sleep(TimeUnit.MINUTES.toMillis(1));
            es.shutdown();
        }
        catch (InterruptedException e) { throw new RuntimeException(e); }
    }

    @ThreadSafe
    public static final class Storage
    {
        /* AtomicInteger is used so that it can be marked mutable and final at the same time */
        private final AtomicInteger currentCapacity;
        private final int maxCapacity;

        public Storage(final int maxCapacity) { this(0, maxCapacity); }

        public Storage(final int currentCapacity, final int maxCapacity)
        {
            this.currentCapacity = new AtomicInteger(currentCapacity);
            this.maxCapacity = maxCapacity;
        }

        public int remainingCapacity() { return this.maxCapacity - this.currentCapacity.get(); }

        public int getCurrentCapacity() { return this.currentCapacity.get(); }

        public void increase(final int q)
        {
            synchronized (this.currentCapacity)
            {
                if (this.currentCapacity.get() < this.maxCapacity)
                {
                    this.currentCapacity.addAndGet(q);
                }
                else
                {
                    throw new FormattedRuntimeException("Max Capacity %d Exceeded!", this.maxCapacity);
                }
            }
        }

        public int getMaxCapacity() { return this.maxCapacity; }

        public void decrease(final int q)
        {
            synchronized (this.currentCapacity)
            {
                if (this.currentCapacity.get() - q >= 0)
                {
                    this.currentCapacity.addAndGet(q * -1);
                }
                else
                {
                    this.currentCapacity.set(0);
                }
            }
        }
    }
}

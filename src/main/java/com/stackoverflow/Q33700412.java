package com.stackoverflow;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
                    while (true)
                    {
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
                        catch (InterruptedException e) { throw new RuntimeException(e); }
                    }
                }
            });
        }
        es.shutdown();
    }

    public static final class Storage
    {
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

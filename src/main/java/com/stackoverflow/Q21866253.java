package com.stackoverflow;

import com.google.common.util.concurrent.RateLimiter;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.System.currentTimeMillis;

public class Q21866253
{
    public static void main(final String[] args)
    {
        runJDKRateLimiter();
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        runGuavaRateLimiter();
    }

    private static void runJDKRateLimiter()
    {
        final FunctionToBeSpammed ftbs = new JDKFunctionToBeSpammed();
        final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 4; i++)
        {
            final String name = String.format("Thread-%d", i);
            es.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    for (int k = 0; k < 10000; k++)
                    {
                        ftbs.functionToBeRateLimited(name);
                    }
                    System.out.println(String.format("%s called the function %d times", name, ftbs.getTotalTimesCalled(name)));
                }
            });
        }
        es.shutdown();
        while (!es.isTerminated())
        {
            try { Thread.sleep(1000); } catch (InterruptedException e) { throw new RuntimeException(e); }
        }
    }

    private static void runGuavaRateLimiter()
    {
        final FunctionToBeSpammed ftbs = new GuavaFunctionToBeSpammedGuava();
        final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 4; i++)
        {
            final String name = String.format("Thread-%d", i);
            es.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    for (int k = 0; k < 10000; k++)
                    {
                        ftbs.functionToBeRateLimited(name);
                    }
                    System.out.println(String.format("%s called the function %d times", name, ftbs.getTotalTimesCalled(name)));
                }
            });
        }
        es.shutdown();
        while (!es.isTerminated())
        {
            try { Thread.sleep(1000); } catch (InterruptedException e) { throw new RuntimeException(e); }
        }
    }

    public static interface FunctionToBeSpammed
    {
        public Integer getTotalTimesCalled(@Nonnull final String caller);

        public void functionToBeRateLimited(@Nonnull final String caller);
    }

    private static abstract class AbstractFunctionToBeSpammed implements FunctionToBeSpammed
    {
        protected final Map<String, AtomicInteger> timesCalled = new HashMap<String, AtomicInteger>() {
            // handle missing keys automagically!
            @Override
            public AtomicInteger get(final Object key)
            {
                if (!this.containsKey(key)) { this.put((String)key, new AtomicInteger(1)); }
                return super.get(key);
            }
        };

        public Integer getTotalTimesCalled(@Nonnull final String caller) { return this.timesCalled.get(caller).get(); }

        public abstract void functionToBeRateLimited(@Nonnull final String caller);
    }

    private static class JDKFunctionToBeSpammed extends AbstractFunctionToBeSpammed
    {
        private static final Integer DELTA;
        private static final Map<String, AtomicLong> LASTED_CALLED_BY_AT;

        static
        {
            DELTA = 500; // in milliseconds
            LASTED_CALLED_BY_AT = new HashMap<String, AtomicLong>() {
                @Override
                public AtomicLong get(final Object key)
                {
                    if (!this.containsKey(key)) { this.put((String)key, new AtomicLong(1)); }
                    return super.get(key);
                }
            };
        }

        private Boolean shouldRun(@Nonnull final String caller)
        {
            return currentTimeMillis() - LASTED_CALLED_BY_AT.get(caller).get() >= DELTA;
        }

        @Override
        public void functionToBeRateLimited(@Nonnull final String caller)
        {
            // do some stuff every time here
            super.timesCalled.get(caller).incrementAndGet();

            // do some stuff only after a certain time has elapsed since the last time it was done
            if (shouldRun(caller))
            {
                System.out.println(String.format("%s Called Rate Limited Logic every %d ms", caller, DELTA));
            }
        }
    }

    private static class GuavaFunctionToBeSpammedGuava extends AbstractFunctionToBeSpammed
    {
        private static final Map<String, RateLimiter> LIMITER;
        static
        {
            LIMITER = new HashMap<String, RateLimiter>() {
                @Override
                public RateLimiter get(final Object key)
                {
                    if (!this.containsKey(key)) { this.put((String) key, RateLimiter.create(2)); }
                    return super.get(key);
                }
            };
        }

        @Override
        public void functionToBeRateLimited(@Nonnull final String caller)
        {
            // do some stuff every time here
            super.timesCalled.get(caller).incrementAndGet();

            // do some stuff only after a certain time has elapsed since the last time it was done
            if (LIMITER.get(caller).tryAcquire())
            {
                System.out.println(String.format("%s Called Rate Limited Logic up to 2 times a second ( 500 ms )", caller));
            }
        }
    }
}

package com.stackoverflow;

import javax.annotation.Nonnull;
import java.text.BreakIterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class Q23794090
{
    private static final Timer TIMER = new Timer();
    private static AtomicLong DELAY = new AtomicLong(0L);

    private static void say(final long delay, @Nonnull final String value, @Nonnull final BreakIterator bi)
    {
        bi.setText(value);
        int start = bi.first();
        int end = bi.next();

        while (end != BreakIterator.DONE)
        {
            final String s = value.substring(start, end);
            TIMER.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    System.out.print(s);
                }
            }, DELAY.addAndGet(delay));
            start = end;
            end = bi.next();
        }
        TIMER.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println();
                System.out.println("* * * * * * * * * * * * *");
            }
        }, DELAY.addAndGet(0));
    }

    public static void main(final String[] args)
    {
        final String x = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vitae molestie leo, sed molestie turpis.";
        say(250, x, BreakIterator.getCharacterInstance());
        say(500, x, BreakIterator.getWordInstance());
        say(1000, x, BreakIterator.getSentenceInstance());
        TIMER.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                TIMER.cancel();
            }
        }, DELAY.addAndGet(1000));
        try { Thread.currentThread().join(); }
        catch (InterruptedException e) { throw new RuntimeException(e); }
    }
}

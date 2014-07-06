package com.stackoverflow;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Q24591731
{
    private static final ExecutorService EXECUTOR_SERVICE;

    static
    {
        EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
    }

    public static void main(final String[] args)
    {
        final Download d1;
        try { d1 = new Download(new URL("www.someurl.to.download.com"), new File("dest/file/name")); }
        catch (MalformedURLException e) { throw new RuntimeException(e); }
        // create as many downloads as you need how ever you want.
        EXECUTOR_SERVICE.submit(d1);
        // when all are submitted
        EXECUTOR_SERVICE.shutdown();
        try
        {
            EXECUTOR_SERVICE.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch (InterruptedException e)
        {
            System.exit(1);
        }

    }

    public static class Download implements Runnable
    {
        private final URL url;
        private final File dest;

        public Download(@Nonnull final URL url, @Nonnull final File dest)
        {
            this.url = url;
            this.dest = dest;
        }

        @Override
        public void run()
        {
            // download the file and write it to disk
        }
    }
}

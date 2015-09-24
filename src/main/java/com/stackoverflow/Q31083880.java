package com.stackoverflow;

import javax.annotation.Nonnull;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

public class Q31083880
{
    public static int compile(@Nonnull final File file)
    {
        final JavaCompiler c = ToolProvider.getSystemJavaCompiler();
        return c.run(null, null, null, file.getAbsolutePath());
    }

    public static void listClassPathRoots()
    {
        try
        {
            final Enumeration<URL> roots = ClassLoader.getSystemClassLoader().getResources("");
            while (roots.hasMoreElements())
            {
                System.out.println("roots.nextElement() = " + roots.nextElement());
            }
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public static void invoke(@Nonnull final String name)
    {
        try
        {
            final URLClassLoader cl = (URLClassLoader) ClassLoader.getSystemClassLoader();
            final Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            final File f = new File(System.getProperty("user.home"));
            method.invoke(cl, f.toURI().toURL());
            listClassPathRoots();
            final Method m;
            try { m = Class.forName(name).getMethod("main", String[].class); }
            catch (ClassNotFoundException e) { throw new RuntimeException(e); }
            final String[] args = new String[0];
            m.invoke(null, new Object[]{args});
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(final String[] args)
    {
        final File f = new File(System.getProperty("user.home"), "Test.java");
        try
        {
            final FileWriter fw = new FileWriter(f);
            try
            {
                final String source = "public class Test { public static void main(String[] args) { System.out.println(\"Testing\"); } }";
                fw.write(source);
            }
            catch (IOException e) { throw new RuntimeException(e); }
            finally
            {
                try { fw.close(); } catch (final IOException e) { System.err.println(e.getMessage()); }
            }
            if (compile(f) == 0) { invoke("Test"); }
            else { throw new RuntimeException("compile failed!"); }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

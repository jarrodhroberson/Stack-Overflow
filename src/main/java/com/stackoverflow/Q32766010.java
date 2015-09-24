package com.stackoverflow;

public class Q32766010
{
    public static void main(final String[] args)
    {
        final Example e = new Example();
        e.doSomething(23,42);
    }

    public static class Example
    {
        public void doSomething(final int a, final int b)
        {
            class Closure {
                void doThis() { System.out.println("a = " + a);}
                void doThat() { System.out.println("b = " + b);}
            }
            final Closure c = new Closure();
            c.doThis();
            c.doThat();
        }
    }
}

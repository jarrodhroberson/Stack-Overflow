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
                Closure doThis() { System.out.println("a = " + a); return this; }
                Closure doThat() { System.out.println("b = " + b); return this; }
            }
            new Closure().doThis().doThat();
        }
    }
}

package com.stackoverflow;

import java.util.Scanner;

public class Q33636764
{
    private static int getData(final Scanner scanner, final String message)
    {
        System.out.print(message);
        if (scanner.hasNextInt()) { return scanner.nextInt(); }
        else
        {
            System.out.println("Please enter an Integer!");
            return getData(scanner, message);
        }
    }

    public static void main(final String[] args)
    {
        final Scanner keyboard = new Scanner(System.in);

        final int dist = getData(keyboard, "Enter the distance : ");
        final int hrs = getData(keyboard, "Enter the hours : ");
        final int mins = getData(keyboard, "Enter the minutes : ");

        final Mph mph = new Mph(dist, hrs, mins);
        System.out.format("%d miles in %d hours and %d minutes %.2f mph.", dist, hrs, mins, mph.calculate());
        System.out.println();
    }

    public static class Mph
    {
        private final double distance;
        private final double hours;
        private final double minutes;

        public Mph(final double distance, final double hours, final double minutes)
        {
            this.distance = distance;
            this.hours = hours;
            this.minutes = minutes;
        }

        public double calculate()
        {
            return this.distance / this.hours + this.minutes / 60;
        }
    }
}

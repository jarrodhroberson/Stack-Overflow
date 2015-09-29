package com.stackoverflow;

import java.util.Scanner;

public class Q32835243
{
    private static double farhenheitToCelsius(double fahrenheit) {return (fahrenheit - 32) * 5 / 9;}

    public static void main(final String[] args)
    {
        final Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter a temperature in Fahrenheit: ");
        if (keyboard.hasNextDouble())
        {
            final double fahrenheit = keyboard.nextDouble();
            final double celsius = farhenheitToCelsius(fahrenheit);
            System.out.format("The temperature in Celsius is: %8.4f%n", celsius);
            System.out.format("Fahrenheit\t Celsius%n");

            for (int i=0; i < 100; i+=10)
            {
                System.out.format("%8.4f\t%8.4f%n", fahrenheit + i, farhenheitToCelsius(fahrenheit + i));
            }
        }
        else
        {
            System.err.format("%s is not a double!%nPlease enter a double value.", keyboard.next());
            System.exit(1);
        }
    }
}
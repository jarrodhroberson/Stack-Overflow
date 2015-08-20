package com.stackoverflow;

import com.google.common.base.Joiner;

import javax.annotation.Nonnull;
import java.io.PrintStream;
import java.util.*;

import static java.lang.String.format;

public class Q32106461
{
    public static class Car
    {
        private final String id;
        private final String description;
        private final Double fee;

        public Car(@Nonnull final String id, @Nonnull final String description, @Nonnull final Double fee)
        {
            this.id = id; this.description = description; this.fee = fee;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }
            final Car car = (Car) o; return Objects.equals(id, car.id);
        }

        @Override
        public String toString() { return format("Car{id='%s', description='%s', fee=%s}", id, description, fee); }

        @Override
        public int hashCode() { return Objects.hash(id, description, fee); }
    }

    public static void main(final String[] args)
    {
        final Comparator<Car> carc = new Comparator<Car>() {
            @Override
            public int compare(@Nonnull final Car o1, @Nonnull final Car o2) { return o1.id.compareTo(o2.id); }
        };
        final Set<Car> cars = new TreeSet<Car>(carc); // sorted set implementation
        final List<Car> carl = new ArrayList<>(100);
        final Map<String,Car> carm = new HashMap<>(100);
        final Car[] cara = new Car[100]; // avoid raw arrays at all costs

        final Scanner ssi = new Scanner(System.in);
        final PrintStream os = System.out;

        while(true)
        {
            final String id = getNextString("Enter a car ID: ", os, ssi);
            final String description = getNextString("Enter a car description: ", os, ssi);
            final Double fee = getNextDouble("Enter hire fee $ ", os, ssi);
            final Car c = new Car(id, description, fee);
            if (!cars.add(c)) { os.println(format("%s already exists in the set!", c.id)); } // duplicates will be ignored
            if (carl.contains(c)) { os.println(format("%s already exists in the list!", c.id)); }
            else { carl.add(c); } // lookups get expensive as the list grows
            if (carm.containsKey(c.id)) { os.println(format("%s already exists in the map!", c.id)); }
            else { carm.put(c.id, c); } // keys are an unsorted set
            // /* uncomment out the following to see why raw arrays and nulls should be avoided always */
            //if (cara.length > 0) { Arrays.sort(cara, carc); }
            //final int pos = Arrays.binarySearch(cara,c);
            //if (pos == 0) { cara[0] = c; }
            os.print("Continue? [Y/N]");
            if ("n".equalsIgnoreCase(ssi.next())) { break; }
        }
        System.out.println("cars = " + Joiner.on(',').join(cars));
        System.out.println("carl = " + Joiner.on(',').join(carl));
        System.out.println("carm = " + Joiner.on(',').withKeyValueSeparator(":").join(carm));
    }

    private static String getNextString(@Nonnull final String prompt, @Nonnull final PrintStream ps, @Nonnull final Scanner scanner)
    {
        ps.print(prompt);
        if (scanner.hasNext()) { return scanner.next(); }
        else { return ""; }
    }

    private static Double getNextDouble(@Nonnull final String prompt, @Nonnull final PrintStream ps, @Nonnull final Scanner scanner)
    {
        ps.print(prompt);
        if (scanner.hasNext()) { return scanner.nextDouble(); }
        else { return Double.NaN; }
    }
}

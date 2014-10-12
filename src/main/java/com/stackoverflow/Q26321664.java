package com.stackoverflow;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.common.math.LongMath;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.*;

public class Q26321664
{
    private static final Set<Team> TL1;
    private static final Set<Team> TL2;
    private static final Set<Team> TL3;

    static
    {
        TL1 = Team.createTeams("bulls,lakers,suns,pistons,hornets".split(","));
        TL2 = Team.createTeams("suns,cletics,spurs,lakers,bulls".split(","));
        TL3 = Team.createTeams("heat,lakers,jazz,suns,wizards".split(","));
    }

    private static class Team implements Comparable<Team>
    {
        public static Set<Team> createTeams(@Nonnull final String ... names)
        {
            return Sets.newTreeSet(Lists.transform(Arrays.asList(names), new Function<String, Team>()
            {
                @Nullable
                @Override
                public Team apply(@Nullable final String name)
                {
                    return new Team(name);
                }
            }));
        }

        @Nonnull
        private final String name;

        private Team(@Nonnull final String name)
        {
            this.name = name;
        }

        @Override
        public boolean equals(@Nullable final Object o)
        {
            if (this == o) { return true; }
            final Team team = (Team) o;
            return team != null && name.equals(team.name);

        }

        @Override
        public int compareTo(final Team t) { return this.name.compareTo(t.name); }

        @Override
        public int hashCode() { return name.hashCode(); }
    }

    private static final Comparable[] findMatchingItems(Object[] collections)
    {
        final Set<Comparable> allTeams = new HashSet<>();
        allTeams.addAll(TL1);
        allTeams.addAll(TL2);
        allTeams.addAll(TL3);

        final Multiset<Team> ms = HashMultiset.create();
        for (final Object o : collections)
        {
            ms.addAll((Collection) o);
        }

        final List<Comparable> intersections = Lists.newArrayList(Iterables.filter(allTeams, new Predicate<Comparable>()
        {
            @Override
            public boolean apply(@Nullable final Comparable input)
            {
                return ms.count(input) > 1;
            }
        }));
        return intersections.toArray(new Comparable[intersections.size()]);
    }

    private static double mean(@Nonnull final long[] times)
    {
        double sum = 0;
        for (final long time : times)
        {
            sum = sum + time;
        }
        return sum / times.length;
    }

    private static double median(@Nonnull final long[] times)
    {
        final int midpoint = times.length / 2;
        final long mp1 = times[midpoint];
        final long mp2 = times[midpoint + 1];
        return (mp1 + mp2) / 2;
    }

    public static void main(final String[] args)
    {
        final ImmutableList<Set<Team>> of = ImmutableList.of(TL1, TL2, TL3);
        final Object[] collections = of.toArray(new Object[of.size()]);
        final int repetitions = 1000000;
        final long[] elapsedTimes = new long[repetitions]; // this is to limit impact on what goes on in the loop!
        for(int i =0; i < repetitions; i++)
        {
            final long s = System.nanoTime();
            findMatchingItems(collections);
            elapsedTimes[i] = System.nanoTime() - s;
        }
        Arrays.sort(elapsedTimes);
        System.out.println("All times in nano seconds!");
        System.out.println("elapsedTimes[0] = " + elapsedTimes[0]);
        System.out.println("elapsedTimes[last] = " + elapsedTimes[elapsedTimes.length - 1]);
        System.out.println("mean(elapsedTimes) = " + mean(elapsedTimes));
        System.out.println("median(elapsedTimes) = " + median(elapsedTimes));
    }
}

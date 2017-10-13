package com.svd;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import javafx.util.Pair;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.*;

public class StreamVsDecorator
{
    public static void main(String[] args)
    {
        final Double ZERO = 0.0d;
        final Double ONE = 1.0d;
        final Iterable<Double> probes = ImmutableList.copyOf(new Random().doubles(1000, 0.0d, 1.0d).iterator());
        //probes.forEach(probe -> System.out.printf("Probe #%f\n",probe));
        transform(limit(filter(probes, probe -> ZERO.compareTo(probe) < 0 || ONE.compareTo(probe) > 0), 10), new Function<Double, Pair<Integer, Double>>()
        {
            final AtomicInteger index = new AtomicInteger(0);

            @Nonnull
            @Override
            public Pair<Integer, Double> apply(@Nonnull final Double probe)
            {
                System.out.printf("Probe #%f - %s \n",probe, ZERO.compareTo(probe) == 0 || ONE.compareTo(probe) == 0);
                return new Pair<>(index.incrementAndGet(), probe);
            }
        }).forEach(idp -> System.out.printf("Probe #%d: %f\n", idp.getKey(), idp.getValue() * 100.0d));
    }
}

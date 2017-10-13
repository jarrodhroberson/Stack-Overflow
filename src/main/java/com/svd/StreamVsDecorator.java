package com.svd;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import javafx.util.Pair;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.*;

public class StreamVsDecorator
{
    public static void main(String[] args)
    {
        final Double ZERO = 0.0d;
        final Double ONE = 1.0d;
        final Iterable<Double> probes = ImmutableList.of();

        transform(limit(filter(probes, input -> ZERO.compareTo(checkNotNull(input)) == 0 || ONE.compareTo(input) == 0), 10), new Function<Double, Pair<Integer, Double>>()
        {
            final AtomicInteger index = new AtomicInteger(0);

            @Nonnull
            @Override
            public Pair<Integer, Double> apply(@Nonnull final Double input)
            {
                return new Pair<>(index.incrementAndGet(), input);
            }
        }).forEach(idp -> System.out.printf("Probe #%d: %f", idp.getKey(), idp.getValue() * 100.0d));
    }
}

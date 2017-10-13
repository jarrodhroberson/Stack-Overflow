package com.svd;

import com.google.auto.value.AutoValue;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.collect.Iterables.*;

public class StreamVsDecoratorAutoValued
{
    @AutoValue
    public static abstract class IndexedProbe
    {
        static IndexedProbe create(@Nonnull final Integer index, @Nonnull final Double value)
        {
            return new AutoValue_StreamVsDecorator_IndexedProbe(index, value);
        }

        abstract Integer index();

        abstract Double probe();
    }


    public static void main(String[] args)
    {
        final Double ZERO = 0.0d;
        final Double ONE = 1.0d;
        final Iterable<Double> probes = ImmutableList.copyOf(new Random().doubles(1000, 0.0d, 1.0d).iterator());

        transform(limit(filter(probes, probe -> ZERO.compareTo(probe) < 0 || ONE.compareTo(probe) > 0), 10), new Function<Double, IndexedProbe>()
        {
            final AtomicInteger index = new AtomicInteger(0);

            @Nonnull
            @Override
            public IndexedProbe apply(@Nonnull final Double probe)
            {
                return IndexedProbe.create(index.incrementAndGet(), probe);
            }
        }).forEach(idp -> System.out.printf("Probe #%d: %f\n", idp.index(), idp.probe() * 100.0d));
    }
}

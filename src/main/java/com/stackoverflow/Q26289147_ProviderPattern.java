package com.stackoverflow;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * Unlike most of my exmamples this one requires JDK 7 or higher because of the use of <code>String</code>
 * based <code>switch</code> statement.
 */
public class Q26289147_ProviderPattern
{
    private static final List<String> CLASS_NAMES = ImmutableList.of("String", "Integer", "Boolean");
    private static final Map<String, Provider<StrawManParameterizedClass>> PROVIDERS;

    static
    {
        final ImmutableMap.Builder<String, Provider<StrawManParameterizedClass>> imb = ImmutableMap.builder();
        for (final String cn : CLASS_NAMES)
        {
            switch (cn)
            {
                case "String":
                    imb.put(cn, new Provider<StrawManParameterizedClass>()
                    {
                        @Override
                        public StrawManParameterizedClass<String> get() { return new StrawManParameterizedClass<String>() {}; }
                    });
                    break;
                case "Integer":
                    imb.put(cn, new Provider<StrawManParameterizedClass>()
                    {
                        @Override
                        public StrawManParameterizedClass<Integer> get() { return new StrawManParameterizedClass<Integer>() {}; }
                    });
                    break;
                case "Boolean":
                    imb.put(cn, new Provider<StrawManParameterizedClass>()
                    {
                        @Override
                        public StrawManParameterizedClass<Integer> get() { return new StrawManParameterizedClass<Integer>() {}; }
                    });
                    break;
                default:
                    throw new IllegalArgumentException(String.format("%s is not a supported type %s", cn, Joiner.on(",").join(CLASS_NAMES)));
            }
        }
        PROVIDERS = imb.build();
    }

    static <T> void read(@Nonnull final StrawManParameterizedClass<T> smpc) { System.out.println(smpc.type.toString()); }

    static abstract class StrawManParameterizedClass<T>
    {
        final TypeToken<T> type = new TypeToken<T>(getClass()) {};

        @Override
        public String toString() { return type.getRawType().getCanonicalName(); }
    }

    public static void main(final String[] args)
    {
        for (final String cn : CLASS_NAMES)
        {
            read(PROVIDERS.get(cn).get());
        }
    }
}

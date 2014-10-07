import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

public class Scratch
{
    public static void main(final String[] args)
    {
        final Map<String, String> m = ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3");
        final Collection<String> c = Maps.transformEntries(m, new Maps.EntryTransformer<String, String, String>()
        {
            @Override public String transformEntry(@Nullable final String key, @Nullable final String value)
            {
                return Joiner.on(' ').join(key, value);
            }
        }).values();
        System.out.println(c);
    }
}

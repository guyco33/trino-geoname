package io.prestosql.geoname.functions;

import com.google.common.collect.ImmutableSet;
import io.prestosql.spi.Plugin;

import java.util.Set;

public class GeonameFunctionsPlugin
        implements Plugin
{
    public Set<Class<?>> getFunctions()
    {
        return ImmutableSet.<Class<?>>builder()
                .add(GeonameFunctions.class)
                .build();
    }
}

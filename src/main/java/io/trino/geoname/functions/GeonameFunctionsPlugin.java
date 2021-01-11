package io.trino.geoname.functions;

import com.google.common.collect.ImmutableSet;
import io.trino.spi.Plugin;

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

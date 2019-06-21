package io.prestosql.geoname.functions;

import org.testng.annotations.Test;

import static io.airlift.slice.Slices.utf8Slice;
import static org.testng.Assert.*;

public class GeonameFunctionsTest {

    @Test
    public void testGeoname() {
        assertEquals(GeonameFunctions.geoname(29.896322, 35.059227, utf8Slice("countrycode")).toStringUtf8(), "IL");
        assertEquals(GeonameFunctions.geoname(29.896322, 35.059227, utf8Slice("city")).toStringUtf8(), "Eilat");
    }
}
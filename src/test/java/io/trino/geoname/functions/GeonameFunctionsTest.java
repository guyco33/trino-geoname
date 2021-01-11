package io.trino.geoname.functions;

import org.testng.annotations.Test;

import static io.airlift.slice.Slices.utf8Slice;
import static org.testng.Assert.*;

public class GeonameFunctionsTest {


    @Test
    public void testGeoname() {
        assertEquals(GeonameFunctions.geoname(29.896322, 35.059227).toStringUtf8(),
                "{\"geoNameId\":295277,\"name\":\"Eilat\",\"latitude\":29.55805,\"longitude\":34.94821,\"countryCode\":\"IL\",\"timeZone\":\"Asia/Jerusalem\",\"admin1\":\"Southern District\",\"admin2\":null,\"countryName\":\"Israel\",\"capital\":\"Jerusalem\",\"countryArea\":20770.0,\"countryPopulation\":7353985,\"continent\":\"AS\",\"currencyCode\":\"ILS\",\"currencyName\":\"Shekel\"}");
   }

    @Test
    public void testGeonameWithAttribute() {
        assertEquals(GeonameFunctions.geoname(29.896322, 35.059227, utf8Slice("countrycode")).toStringUtf8(), "IL");
        assertEquals(GeonameFunctions.geoname(29.896322, 35.059227, utf8Slice("city")).toStringUtf8(), "Eilat");
        assertEquals(GeonameFunctions.geoname(29.896322, 35.059227, utf8Slice("id")).toStringUtf8(), "295277");
        assertEquals(GeonameFunctions.geoname(29.896322, 35.059227, utf8Slice("latlon")).toStringUtf8(), "29.558050,34.948210");
        assertEquals(GeonameFunctions.geoname(29.896322, 35.059227, utf8Slice("countryname")).toStringUtf8(), "Israel");
        assertEquals(GeonameFunctions.geoname(29.896322, 35.059227, utf8Slice("currency")).toStringUtf8(), "ILS");
        assertEquals(GeonameFunctions.geoname(29.896322, 35.059227, utf8Slice("continent")).toStringUtf8(), "AS");
    }

}
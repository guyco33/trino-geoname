package io.trino.geoname.functions;

import io.trino.sql.query.QueryAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;

import static io.airlift.slice.Slices.utf8Slice;
import static io.trino.geoname.functions.GeonameFunctions.geoname;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@TestInstance(PER_CLASS)
@Execution(CONCURRENT)
public class TestGeonameFunctions
{
    private QueryAssertions assertions;

    public TestGeonameFunctions()
    {
        assertions = new QueryAssertions();
        assertions.addPlugin(new GeonameFunctionsPlugin());
    }

    @AfterAll
    public void teardown()
    {
        assertions.close();
    }

    @Test
    public void testGeoname()
    {
        String expected = "{\"geoNameId\":295277,\"name\":\"Eilat\",\"latitude\":29.55805,\"longitude\":34.94821,\"countryCode\":\"IL\",\"timeZone\":\"Asia/Jerusalem\",\"admin1\":\"Southern District\",\"admin2\":null,\"countryName\":\"Israel\",\"capital\":\"Jerusalem\",\"countryArea\":20770.0,\"countryPopulation\":7353985,\"continent\":\"AS\",\"currencyCode\":\"ILS\",\"currencyName\":\"Shekel\"}";
        assertEquals(geoname(29.896322, 35.059227).toStringUtf8(),
                expected);

        assertThat(assertions.expression("geoname(29.896322, 35.059227)"))
                .isEqualTo(expected);
    }

    @Test
    public void testGeonameWithAttribute()
    {
        assertThat(assertions.expression("geoname(29.896322, 35.059227, 'countrycode')")).isEqualTo("IL");
        assertEquals(geoname(29.896322, 35.059227, utf8Slice("city")).toStringUtf8(), "Eilat");
        assertEquals(geoname(29.896322, 35.059227, utf8Slice("id")).toStringUtf8(), "295277");
        assertEquals(geoname(29.896322, 35.059227, utf8Slice("latlon")).toStringUtf8(), "29.558050,34.948210");
        assertEquals(geoname(29.896322, 35.059227, utf8Slice("countryname")).toStringUtf8(), "Israel");
        assertEquals(geoname(29.896322, 35.059227, utf8Slice("currency")).toStringUtf8(), "ILS");
        assertEquals(geoname(29.896322, 35.059227, utf8Slice("continent")).toStringUtf8(), "AS");
    }
}
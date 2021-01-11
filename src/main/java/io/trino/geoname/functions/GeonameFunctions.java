package io.trino.geoname.functions;

import atlas.Atlas;
import atlas.City;
import io.airlift.slice.Slice;
import io.trino.spi.TrinoException;
import io.trino.spi.function.Description;
import io.trino.spi.function.ScalarFunction;
import io.trino.spi.function.SqlNullable;
import io.trino.spi.function.SqlType;
import io.trino.spi.type.StandardTypes;

import static io.airlift.slice.Slices.utf8Slice;
import static io.trino.spi.StandardErrorCode.INVALID_FUNCTION_ARGUMENT;
import static io.trino.spi.type.StandardTypes.VARCHAR;
import static java.lang.String.format;

public class GeonameFunctions {

    private static final String attributes_desc = "countrycode | city | timezone | id | admin1 | admin2 | countryname | latlon | currency | continent";

    private GeonameFunctions() {}

    @ScalarFunction("geoname")
    @Description("Returns nearest geoname from it's centroid to a given latitude and longitude")
    @SqlType(VARCHAR)
    @SqlNullable
    public static Slice geoname(
            @SqlType(StandardTypes.DOUBLE) double lat,
            @SqlType(StandardTypes.DOUBLE) double lon
    ) {
        City city = new Atlas().find(lat, lon);
        return (city == null) ? null : utf8Slice(city.toString());
    }

    @ScalarFunction("geoname")
    @Description("Returns nearest geoname attribute from it's centroid to a given latitude and longitude. Attribute can be one of: " + attributes_desc)
    @SqlType(VARCHAR)
    @SqlNullable
    public static Slice geoname(
            @SqlType(StandardTypes.DOUBLE) double lat,
            @SqlType(StandardTypes.DOUBLE) double lon,
            @SqlType(VARCHAR) Slice attr
            ) {
        City city = new Atlas().find(lat, lon);
        return (city == null) ? null : utf8Slice(getTypeValue(city, attr.toStringUtf8().toLowerCase()));
    }

    private static String getTypeValue(City city, String attr) {
        if (attr.equals("country") || attr.equals("countrycode") || attr.equals("country_code")) {
            return city.countryCode;
        }
        if (attr.equals("city") || attr.equals("cityname") || attr.equals("city_name")) {
            return city.name;
        }
        if (attr.equals("timezone")) {
            return city.timeZone;
        }
        if (attr.equals("admin1")) {
            return city.admin1;
        }
        if (attr.equals("admin2")) {
            return city.admin2;
        }
        if (attr.equals("id")) {
            return String.valueOf(city.geoNameId);
        }
        if (attr.equals("latlon")) {
            return format("%f,%f", city.latitude, city.longitude);
        }
        if (attr.equals("countryname") || attr.equals("country_name")) {
            return city.countryName;
        }
        if (attr.equals("currency") || attr.equals("currencycode") || attr.equals("currency_code") ) {
            return city.currencyCode;
        }
        if (attr.equals("continent")) {
            return city.continent;
        }
        throw new TrinoException(INVALID_FUNCTION_ARGUMENT, format("Valid attributes are: %s", attributes_desc));
    }


}

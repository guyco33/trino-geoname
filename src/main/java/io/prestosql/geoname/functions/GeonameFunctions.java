package io.prestosql.geoname.functions;

import atlas.Atlas;
import atlas.City;
import io.airlift.slice.Slice;
import io.prestosql.spi.PrestoException;
import io.prestosql.spi.function.Description;
import io.prestosql.spi.function.ScalarFunction;
import io.prestosql.spi.function.SqlNullable;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.StandardTypes;

import static io.airlift.slice.Slices.utf8Slice;
import static io.prestosql.spi.StandardErrorCode.INVALID_FUNCTION_ARGUMENT;
import static java.lang.String.format;

public class GeonameFunctions {

    private static final String attributes_desc = "countrycode | city | timezone | admin1 | admin2";

    private GeonameFunctions() {}

    @ScalarFunction("geoname")
    @Description("Returns geoname attribute for a given latitude and longitude. Attribute can be one of: " + attributes_desc)
    @SqlType(StandardTypes.VARCHAR)
    @SqlNullable
    public static Slice geoname (
            @SqlType(StandardTypes.DOUBLE) double lat,
            @SqlType(StandardTypes.DOUBLE) double lon,
            @SqlType(StandardTypes.VARCHAR) Slice attr
            ) {
        City city = new Atlas().find(lat, lon);
        return (city == null) ? null : utf8Slice(getTypeValue(city, attr.toStringUtf8().toLowerCase()));
    }

    private static String getTypeValue(City city, String attr) {
        if (attr.equals("countrycode") || attr.equals("country_code")) {
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
        throw new PrestoException(INVALID_FUNCTION_ARGUMENT, format("Valid attributes are: %s", attributes_desc));
    }
}

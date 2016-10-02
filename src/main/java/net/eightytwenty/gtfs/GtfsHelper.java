package net.eightytwenty.gtfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Created by gordon on 9/30/16.
 */
public class GtfsHelper {
    protected static <T> T[] parseHelper(InputStream input,
                                         Class<T> targetClass,
                                         Predicate<String> isValidHeader,
                                         Function<String[],T> parseObject) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String header = reader.readLine();

        // first line should match HEADER or we don't know what we're parsing
        if (!isValidHeader.test(header)) {
            throw new IllegalArgumentException("Unknown format for input file, header not recognized");
        }

        List<T> result = reader.lines().map(
                parsedLine -> {
                    final String[] fields = parsedLine.split(",");
                    return parseObject.apply(fields);
                }).collect(toList());
        T[] resultArray = (T[])Array.newInstance(targetClass,result.size());
        return result.toArray(resultArray);
    }


    static String tryGet(String[] fields, int i) {
        return tryGet(fields, i, null);
    }

    /**
     * Get the value of fields[i], returning defaultValue if i > fields.length
     * or if fields[i] is null
     * @param fields
     * @param i
     * @param defaultValue
     * @return
     */
    static String tryGet(String[] fields, int i, String defaultValue) {
        if (fields.length <= i) {
            return defaultValue;
        }
        return Optional.ofNullable(fields[i]).orElse(defaultValue);
    }

}

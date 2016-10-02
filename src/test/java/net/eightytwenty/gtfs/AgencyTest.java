package net.eightytwenty.gtfs;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**¡
 */
public class AgencyTest {

    @Test
    public void parse() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("gtfs/agency.txt").getFile());
        FileInputStream fileInputStream = new FileInputStream(file);

        Agency[] result = Agency.parse(fileInputStream);

        assertEquals(1, result.length);
        Agency agency = result[0];
        assertEquals("http://rtd-denver.com", agency.getUrl());
        assertEquals("Regional Transportation District", agency.getName());
        assertEquals(TimeZone.getTimeZone("America/Denver"), agency.getTimezone());
        assertEquals("RTD", agency.getId());
        assertEquals(Locale.ENGLISH, agency.getLanguage());

    }
}
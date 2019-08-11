package edu.tamu.scholars.middleware.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatUtility {

    // TODO: optimize by matching date value to regex and get matching date time formatter from a map

    // @formatter:off
    private static final DateTimeFormatter[] formatters = {
        DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    };
    // @formatter:on

    public static LocalDate parse(String value) {
        DateTimeParseException dtpe = null;
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(value, formatter);
            } catch (DateTimeParseException e) {
                dtpe = e;
            }
        }
        throw dtpe;
    }

}

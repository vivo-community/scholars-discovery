package edu.tamu.scholars.middleware.utility;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.i18n.LocaleContextHolder;

public class DateFormatUtility {

    private static final String[] datePatterns = {
        "yyyy",
        "E MMM dd HH:mm:ss z yyyy",
        "yyyy-MM-dd'T'HH:mm:ss'Z'"
    };

    public static ZonedDateTime parse(String value) throws ParseException {
        Locale locale = LocaleContextHolder.getLocale();
        Date date = DateUtils.parseDate(value, locale, datePatterns);
        return date.toInstant().atZone(ZoneId.systemDefault());
    }

}

package edu.tamu.scholars.middleware.export.utility;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import edu.tamu.scholars.middleware.export.argument.ExportArg;

public class ArgumentUtility {

    private final static String EXPORT_QUERY_PARAM_KEY = "export";

    public static List<ExportArg> getExportArguments(HttpServletRequest request) {
        // @formatter:off
        return Collections.list(request.getParameterNames()).stream()
            .filter(paramName -> paramName.equals(EXPORT_QUERY_PARAM_KEY))
            .map(request::getParameterValues)
            .map(Arrays::asList)
            .flatMap(list -> list.stream())
            .map(ExportArg::of)
            .collect(Collectors.toList());
        // @formatter:on
    }

}

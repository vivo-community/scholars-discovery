package edu.tamu.scholars.middleware.export.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.tamu.scholars.middleware.export.exception.ExportException;
import edu.tamu.scholars.middleware.export.exception.ExportQueryParameterRequiredException;
import edu.tamu.scholars.middleware.export.exception.UnknownExporterTypeException;
import edu.tamu.scholars.middleware.export.exception.UnsupportedExporterTypeException;

@ControllerAdvice
public class ExportControllerAdvice {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UnknownExporterTypeException.class)
    public @ResponseBody String handleUnknownExporterTypeException(UnknownExporterTypeException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UnsupportedExporterTypeException.class)
    public @ResponseBody String handleUnsupportedExporterTypeException(UnsupportedExporterTypeException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ExportQueryParameterRequiredException.class)
    public @ResponseBody String handleExportQueryParameterRequiredException(ExportQueryParameterRequiredException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ExportException.class)
    public @ResponseBody String handleExportException(ExportException exception) {
        return exception.getMessage();
    }

}

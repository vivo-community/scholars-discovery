package edu.tamu.scholars.middleware.discovery.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.tamu.scholars.middleware.discovery.exception.ExportQueryParameterRequiredException;
import edu.tamu.scholars.middleware.discovery.exception.InvalidValuePathException;
import edu.tamu.scholars.middleware.discovery.exception.UnknownExporterTypeException;

@ControllerAdvice
public class DiscoveryControllerAdvice {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UnknownExporterTypeException.class)
    public @ResponseBody String handleUnknownExporterTypeException(UnknownExporterTypeException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvalidValuePathException.class)
    public @ResponseBody String handleInvalidValuePathException(InvalidValuePathException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ExportQueryParameterRequiredException.class)
    public @ResponseBody String handleExportQueryParameterRequiredException(ExportQueryParameterRequiredException exception) {
        return exception.getMessage();
    }

}

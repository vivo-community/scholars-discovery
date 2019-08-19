package edu.tamu.scholars.middleware.discovery.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.tamu.scholars.middleware.discovery.exception.InvalidValuePathException;

@ControllerAdvice
public class DiscoveryControllerAdvice {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvalidValuePathException.class)
    public @ResponseBody String handleInvalidValuePathException(InvalidValuePathException exception) {
        return exception.getMessage();
    }

}

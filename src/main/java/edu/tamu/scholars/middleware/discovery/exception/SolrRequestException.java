package edu.tamu.scholars.middleware.discovery.exception;

public class SolrRequestException extends RuntimeException {

    private static final long serialVersionUID = 385092384750932845L;

    public SolrRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}

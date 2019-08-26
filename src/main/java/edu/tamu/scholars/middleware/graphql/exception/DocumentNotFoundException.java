package edu.tamu.scholars.middleware.graphql.exception;

public class DocumentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7349397414028849497L;

    public DocumentNotFoundException(String message) {
        super(message);
    }

}

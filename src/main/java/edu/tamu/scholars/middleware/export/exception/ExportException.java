package edu.tamu.scholars.middleware.export.exception;

public class ExportException extends RuntimeException {

    private static final long serialVersionUID = 3058530112449800844L;

    public ExportException(String message) {
        super(message);
    }

}
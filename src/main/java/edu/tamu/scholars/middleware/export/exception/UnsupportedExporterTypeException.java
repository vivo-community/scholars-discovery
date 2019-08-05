package edu.tamu.scholars.middleware.export.exception;

public class UnsupportedExporterTypeException extends RuntimeException {

    private static final long serialVersionUID = -1343712418180719211L;

    public UnsupportedExporterTypeException(String message) {
        super(message);
    }

}

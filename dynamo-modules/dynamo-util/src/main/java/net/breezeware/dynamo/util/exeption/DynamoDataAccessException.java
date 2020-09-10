package net.breezeware.dynamo.util.exeption;

public class DynamoDataAccessException extends Exception {

    private static final long serialVersionUID = 1L;

    public DynamoDataAccessException(String message) {
        super(message);
    }
}
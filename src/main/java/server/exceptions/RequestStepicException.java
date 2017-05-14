package server.exceptions;

/**
 * Exception thrown when request Stepic is not successful
 */
public class RequestStepicException extends RuntimeException {
    public RequestStepicException(String s) {
        super(s);
    }
}

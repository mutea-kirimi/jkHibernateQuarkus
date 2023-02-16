package domain.common.exceptions;

public class PreconditionFailedException extends RuntimeException {
    public PreconditionFailedException(String message) {
        super(message);
    }

    public PreconditionFailedException() {
        super();
    }
}

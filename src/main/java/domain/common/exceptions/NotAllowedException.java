package domain.common.exceptions;

public class NotAllowedException extends RuntimeException{
    public NotAllowedException(String s) {
        super(s);
    }

    public NotAllowedException() {
    }
}

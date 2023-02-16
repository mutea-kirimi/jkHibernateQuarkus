package domain.common.exceptions;

public class NotPresentException extends RuntimeException{
    public NotPresentException() {
        super();
    }

    public NotPresentException(String s) {
        super(s);
    }
}

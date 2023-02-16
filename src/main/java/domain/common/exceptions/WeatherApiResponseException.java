package domain.common.exceptions;

public class WeatherApiResponseException extends RuntimeException{

    private static final long serialVersionUID = -5471604591122191757L;

    public WeatherApiResponseException(String message) {
        super(message);
    }
}

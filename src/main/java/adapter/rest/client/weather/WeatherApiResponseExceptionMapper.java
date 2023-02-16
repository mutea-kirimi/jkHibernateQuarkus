package adapter.rest.client.weather;

import domain.common.exceptions.WeatherApiResponseException;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class WeatherApiResponseExceptionMapper implements ResponseExceptionMapper<WeatherApiResponseException> {

    @Override
    public WeatherApiResponseException toThrowable(Response response) {
        return switch(response.getStatus()){
            case 101 -> new WeatherApiResponseException("User did supply no or invalid access key");
            case 404 -> new WeatherApiResponseException("User requested a resource which does not exist.");
            case 102 -> new WeatherApiResponseException("User account is inactive or blocked.");
            case 103 -> new WeatherApiResponseException("User requested a non-existent API function.");
            case 104 -> new WeatherApiResponseException("User has reached his subscription's monthly request allowance.");
            case 105 -> new WeatherApiResponseException("The user's current subscription does not support this API function");
            case 601 -> new WeatherApiResponseException("An invalid (or missing) query value was specified.");
            case 602 -> new WeatherApiResponseException("The API request did not return any results.");
            case 615 -> new WeatherApiResponseException("API request has failed");
            default -> null;
        };
    }

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return status == 101 || status == 404 || status == 102 || status == 103 || status == 104
                || status == 105 || status == 601 || status == 602 || status == 615;
    }
}

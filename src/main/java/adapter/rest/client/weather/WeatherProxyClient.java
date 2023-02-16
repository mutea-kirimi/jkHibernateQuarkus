package adapter.rest.client.weather;

import adapter.rest.dto.WeatherDto;
import domain.common.exceptions.WeatherApiResponseException;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static java.time.temporal.ChronoUnit.SECONDS;

@SuppressWarnings("ALL")
@Singleton
@Retry(maxRetries = 2, maxDuration = 10, durationUnit = SECONDS)
@Timeout(value = 10, unit = SECONDS)
@Produces(MediaType.APPLICATION_JSON)
@Path("/current")
@RegisterProvider(WeatherApiResponseExceptionMapper.class)
@RegisterRestClient(configKey = "weather-api")
@ClientHeaderParam(name = "API_KEY", value = "{adapter.rest.client.weather.WeatherApiKeyProvider.getApiKey}")
public interface WeatherProxyClient {

    @GET
    @Path("")
    WeatherDto get(@QueryParam("access_key") String key, @QueryParam("query") String location) throws WeatherApiResponseException;
}

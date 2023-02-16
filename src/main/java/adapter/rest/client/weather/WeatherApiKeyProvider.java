package adapter.rest.client.weather;

import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;

/* Get API_KEY from Application.properties */
@ApplicationScoped
public class WeatherApiKeyProvider {

    public static String getApiKey() {
        var config = ConfigProvider.getConfig();
        return config.getValue("weather-api.key", String.class);
    }
}

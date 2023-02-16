package domain.model.weather;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "current_weather")
public class Weather {
    @Embedded
    private Request request;
    @Embedded
    private Location location;
    @Embedded
    private Current current;

    private LocalDateTime updatedAt;
    @Id
    private UUID id;
    private UUID townId;

    protected Weather() {
    }

    public Weather(Request request, Location location, Current current) {
        this.request = request;
        this.location = location;
        this.current = current;
    }

    public static Weather toEntity (Weather weather, UUID townId) {
        weather.updatedAt = LocalDateTime.now();
        weather.id = UUID.randomUUID();
        weather.townId = townId;
        return weather;
    }

    public Request getRequest() {
        return request;
    }

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(request, weather.request) && Objects.equals(location, weather.location) && Objects.equals(current, weather.current) && Objects.equals(updatedAt, weather.updatedAt) && Objects.equals(id, weather.id) && Objects.equals(townId, weather.townId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, location, current, updatedAt, id, townId);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "request=" + request +
                ", location=" + location +
                ", current=" + current +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                ", townId=" + townId +
                '}';
    }
}

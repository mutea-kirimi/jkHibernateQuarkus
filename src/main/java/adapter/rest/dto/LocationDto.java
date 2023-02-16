package adapter.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.model.weather.Location;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public record LocationDto(
        String name,
        String country,
        String region,
        double lat,
        double lon,
        String timezone_id,
        long localtime_epoch,
        double utc_offset,
        @JsonProperty("localtime")
        String localtimeString
) {
        public static Location toModel(LocationDto dto){
                LocalDateTime localDateTime =  Instant.ofEpochMilli(dto.localtime_epoch())
                        .atZone(ZoneId.of(dto.timezone_id().trim()))
                        .toLocalDateTime();
                return new Location(
                        dto.name,
                        dto.country(),
                        dto.region(),
                        dto.lat(),
                        dto.lon(),
                        dto.timezone_id(),
                        localDateTime,
                        dto.utc_offset()
                );
        }
}

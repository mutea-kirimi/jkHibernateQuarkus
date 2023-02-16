package domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/*
* If quarkus.write-dates-as-timestamps(in Application.properties), Jackson will serialize dates as numeric value(s). When disabled(default), they are serialized in ISO 8601 format.
* Jackson serializes java.util.Date class to numeric format and java.time.LocalDateTime, LocalDate and LocalTime to int[] as shown below in the tests
* You can only customize the resulting format to exclude for example seconds and milliseconds but the result will still be numeric or int[] in serialisation
* and the incoming will also be numeric or int[] and then converted to Date or Datetime in the provided formatt using Jackson.
* check https://howtodoinjava.com/jackson/jackson-dates/#41-custom-serializer
* */

public class DemoDefaultDateAndDatetimeDeserialisationAndSerialisation {
    private static class Message {

        private Long id;
        private String text;
        private Date date;
        private LocalDateTime localDateTime;

        public Message() {
        }

        public Message(Long id, String text, Date date, LocalDateTime localDateTime) {
            this.id = id;
            this.text = text;
            this.date = date;
            this.localDateTime = localDateTime;
        }

        public Long getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public Date getDate() {
            return date;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }
    }

    @Test
    void testDefaultSerialization_ThenSuccess() throws JsonProcessingException {
        //given
        Date date = new Date(1661518473905L);
        LocalDateTime localDateTime = LocalDateTime.of(2022,8,30,14,16,20,0);

        Message message = new Message(1L, "test", date, localDateTime);

        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());

        //when
        String json = jsonMapper.writeValueAsString(message);

        //then
        assertThat("{\"id\":1,\"text\":\"test\"," +
                "\"date\":1661518473905,\"localDateTime\":[2022,8,30,14,16,20]}").isEqualTo(json);
    }

    @Test
    void testDefaultDeserialization_ThenSuccess() throws JsonProcessingException {
        //given
        String json = "{\"id\":1,\"text\":\"test\",\"date\":1661518473905," +
                "\"localDateTime\":[2022,8,30,14,16,20]}";

        //when
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        Message message = jsonMapper.readValue(json, Message.class);

        //then
        assertThat(1661518473905L).isEqualTo(message.getDate().getTime());
        assertThat(LocalDateTime.of(2022,8,30,14,16,20,0))
                .isEqualTo(message.getLocalDateTime());
    }
}

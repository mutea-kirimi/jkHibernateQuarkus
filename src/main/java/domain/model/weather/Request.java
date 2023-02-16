package domain.model.weather;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Request {

    @Enumerated(EnumType.STRING)
    private RequestType type;
    private String query;
    private String language;
    @Enumerated(EnumType.STRING)
    private UnitType unit;

    public Request() {
    }

    public Request(RequestType type, String query, String language, UnitType unit) {
        this.type = type;
        this.query = query;
        this.language = language;
        this.unit = unit;
    }

    public String getQuery() {
        return query;
    }

    public String getLanguage() {
        return language;
    }

    public RequestType getRequestType() {
        return type;
    }

    public UnitType getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", query='" + query + '\'' +
                ", language='" + language + '\'' +
                ", unit=" + unit +
                '}';
    }
}

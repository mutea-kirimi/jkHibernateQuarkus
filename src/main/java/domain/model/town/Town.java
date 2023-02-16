package domain.model.town;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "towns")
public class Town {
    @Id
    private UUID id;
    private String name;
    private String country;

    protected Town() {
    }

    public Town(String name, String country) {
       this(UUID.randomUUID(), name, country);
    }

    public Town(UUID id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UUID getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Town town = (Town) o;
        return Objects.equals(id, town.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country);
    }

    @Override
    public String toString() {
        return "Towns{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

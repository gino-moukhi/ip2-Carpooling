package be.kdg.ip2.carpooling.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter @Setter @NoArgsConstructor @ToString
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private Name name;
    private Address address;
    //@Indexed(direction = IndexDirection.ASCENDING)
    private int age;
    private Gender gender;
    private boolean smoker;
    private Vehicle vehicle;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, Name name, Address address, int age, Gender gender, boolean smoker, Vehicle vehicle) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.smoker = smoker;
        this.vehicle = vehicle;
    }
}

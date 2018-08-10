package be.kdg.ip2.carpooling.domain.user;

import be.kdg.ip2.carpooling.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private Name name;
    private Address address;
    private int age;
    private Gender gender;
    private boolean smoker;
    private Vehicle vehicle;

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

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.name = userDto.getName();
        this.address = userDto.getAddress();
        this.age = userDto.getAge();
        this.gender = userDto.getGender();
        this.smoker = userDto.isSmoker();
        this.vehicle = userDto.getVehicle();
    }
}

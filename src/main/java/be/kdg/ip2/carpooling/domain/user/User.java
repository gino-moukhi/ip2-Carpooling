package be.kdg.ip2.carpooling.domain.user;

import be.kdg.ip2.carpooling.dto.UserDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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
    private List<Role> roles;

    public User(String email, String password, Name name, Address address, int age, Gender gender, boolean smoker, Vehicle vehicle) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.smoker = smoker;
        this.vehicle = vehicle;
        this.roles = new ArrayList<>();
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
        this.roles = new ArrayList<>();
    }
}

package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.user.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDto {
    private String id;
    private String email;
    private String password;
    private Name name;
    private Address address;
    private int age;
    private Gender gender;
    private boolean smoker;
    private Vehicle vehicle;

    public UserDto(String email, String password, Name name, Address address, int age, Gender gender, boolean smoker, Vehicle vehicle) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.smoker = smoker;
        this.vehicle = vehicle;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.address = user.getAddress();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.smoker = user.isSmoker();
        this.vehicle = user.getVehicle();
    }
}

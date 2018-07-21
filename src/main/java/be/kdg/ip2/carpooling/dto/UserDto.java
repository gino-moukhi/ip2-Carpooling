package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.user.Address;
import be.kdg.ip2.carpooling.domain.user.Gender;
import be.kdg.ip2.carpooling.domain.user.Name;
import be.kdg.ip2.carpooling.domain.user.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {
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
}

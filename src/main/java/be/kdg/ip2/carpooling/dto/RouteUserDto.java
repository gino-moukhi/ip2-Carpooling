package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.user.Gender;
import be.kdg.ip2.carpooling.domain.user.Name;
import be.kdg.ip2.carpooling.domain.user.RouteUser;
import be.kdg.ip2.carpooling.domain.user.Vehicle;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteUserDto {
    private String id;
    private String email;
    private Name name;
    private int age;
    private Gender gender;
    private boolean smoker;
    private Vehicle vehicle;

    public RouteUserDto(String email, Name name, int age, Gender gender, boolean smoker, Vehicle vehicle) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.smoker = smoker;
        this.vehicle = vehicle;
    }

    public RouteUserDto(UserDto userDto) {
        this.id = userDto.getId();
        this.email = userDto.getEmail();
        this.name = userDto.getName();
        this.age = userDto.getAge();
        this.gender = userDto.getGender();
        this.smoker = userDto.isSmoker();
        this.vehicle = userDto.getVehicle();
    }

    public RouteUserDto(RouteUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.smoker = user.isSmoker();
        this.vehicle = user.getVehicle();
    }
}

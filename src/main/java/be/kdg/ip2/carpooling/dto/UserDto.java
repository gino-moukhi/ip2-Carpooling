package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.user.Address;
import be.kdg.ip2.carpooling.domain.user.Gender;
import be.kdg.ip2.carpooling.domain.user.Name;
import be.kdg.ip2.carpooling.domain.user.Vehicle;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}

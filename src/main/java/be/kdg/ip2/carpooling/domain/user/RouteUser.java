package be.kdg.ip2.carpooling.domain.user;

import be.kdg.ip2.carpooling.dto.RouteUserDto;
import be.kdg.ip2.carpooling.dto.UserDto;
import lombok.*;

import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteUser implements Comparable<RouteUser> {
    private String id;
    private String email;
    private Name name;
    private int age;
    private Gender gender;
    private boolean smoker;
    private Vehicle vehicle;

    public RouteUser(String email, Name name, int age, Gender gender, boolean smoker, Vehicle vehicle) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.smoker = smoker;
        this.vehicle = vehicle;
    }

    public RouteUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.smoker = user.isSmoker();
        this.vehicle = user.getVehicle();
    }

    public RouteUser(UserDto dto) {
        this.id = dto.getId();
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.age = dto.getAge();
        this.gender = dto.getGender();
        this.smoker = dto.isSmoker();
        this.vehicle = dto.getVehicle();
    }

    public RouteUser(RouteUserDto userDto) {
        this.id = userDto.getId();
        this.email = userDto.getEmail();
        this.name = userDto.getName();
        this.age = userDto.getAge();
        this.gender = userDto.getGender();
        this.smoker = userDto.isSmoker();
        this.vehicle = userDto.getVehicle();
    }

    @Override
    public int compareTo(RouteUser ru) {
        int i;
        if (id == null && ru.getId() == null) {
            i = 0;
        } else if (id.isEmpty() && ru.getId().isEmpty()) {
            i = 0;
        } else {
            i = id.compareTo(ru.id);
        }
        if (i != 0) return i;
        i = email.compareTo(ru.getEmail());
        if (i != 0) return i;
        i = name.getFirstName().compareTo(ru.getName().getFirstName());
        if (i != 0) return i;
        i = name.getLastName().compareTo(ru.getName().getLastName());
        if (i != 0) return i;
        i = Integer.compare(age, ru.getAge());
        if (i != 0) return i;
        i = gender.compareTo(ru.getGender());
        if (i != 0) return i;
        i = isSmoker() == ru.isSmoker() ? 0 : -1;
        if (i != 0) return i;
        i = vehicle.compareTo(ru.getVehicle());
        if (i != 0) return i;
        return i;
    }
}

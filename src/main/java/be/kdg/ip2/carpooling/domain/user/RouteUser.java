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
@EqualsAndHashCode
public class RouteUser implements Comparable<User>{
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
    public int compareTo(User u) {
        int i;
        boolean same;
        if (id == null && u.getId() == null) {
            same = true;
        } else if (id.isEmpty() && u.getId().isEmpty()) {
            same = true;
        } else {
            same = this.id.equals(u.getId());
        }
        if (!same) return -1;
        same = this.email.equals(u.getEmail());
        if (!same) return -1;
        same = this.name.equals(u.getName());
        if (!same) return -1;
        i = Integer.compare(this.age, u.getAge());
        if (i != 0) return -1;
        same = this.gender.equals(u.getGender());
        if (!same) return -1;
        i = Boolean.compare(this.smoker,u.isSmoker());
        if (i != 0) return -1;
        same = this.vehicle.equals(u.getVehicle());
        if (!same) return -1;
        return 0;
    }
}

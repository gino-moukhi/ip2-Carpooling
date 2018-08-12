package be.kdg.ip2.carpooling.domain.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Name {
    private String firstName;
    private String lastName;

    private String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFullName();
    }


}

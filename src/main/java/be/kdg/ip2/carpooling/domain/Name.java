package be.kdg.ip2.carpooling.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class Name {
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    private String fullName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}

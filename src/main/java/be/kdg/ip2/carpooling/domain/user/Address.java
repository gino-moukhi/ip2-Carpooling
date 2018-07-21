package be.kdg.ip2.carpooling.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class Address {
    private String street;
    private int streetNumber;
    private int zipCode;
    private String city;

    public Address(String street, int streetNumber, int zipCode, String city) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }
}

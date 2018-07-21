package be.kdg.ip2.carpooling.domain.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    private String street;
    private int streetNumber;
    private int zipCode;
    private String city;
}

package be.kdg.ip2.carpooling.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RouteLocationDto {
    private String name;
    private double lat;
    private double lng;
}

package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.user.Name;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RegisterUserDto {
    private String id;
    private String username;
    private String password;
    private Name name;
    private String token;
}

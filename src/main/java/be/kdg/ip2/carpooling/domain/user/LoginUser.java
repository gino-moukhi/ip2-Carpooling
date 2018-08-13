package be.kdg.ip2.carpooling.domain.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginUser {
    private String username;
    private String email;
}

package be.kdg.ip2.carpooling.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginUserDto {
    private String id;
    private String username;
    private String password;
    private String token;

    public LoginUserDto(String username, String password) {
        this.id = "";
        this.username = username;
        this.password = password;
        this.token = "";
    }
}

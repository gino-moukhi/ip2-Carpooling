package be.kdg.ip2.carpooling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class CarpoolingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarpoolingApplication.class, args);
    }
}

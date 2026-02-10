package elms.security;

import elms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //BCryptPasswordEncoder actual Algorithm.
        // Add 1.One Way Hashing , 2.salt , 3.Slow[Helps to reduce attack types like BruteForce]
    }
    //encode(rawPassword): Turns "pass123" into a mess of characters.
    //matches(rawPassword, encodedPassword): Checks if the "pass123" the user just typed matches the "mess of characters" in the database.




}

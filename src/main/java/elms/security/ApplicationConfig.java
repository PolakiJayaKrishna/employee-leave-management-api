package elms.security;

import elms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //Factor
@RequiredArgsConstructor
public class ApplicationConfig { //The Engine that connects to the Database to find users.

    private final UserRepository userRepository;

    // 1. THE FIX: This tells the Filter how to find users in YOUR database
    @Bean //Workers
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // 2. THE MANAGER: Needed for the Login API later
    @Bean
    public AuthenticationProvider authenticationProvider() {
        //Creating a "blank" authentication logic machine.1.It doesn't know where your users are and how your passwords are scrambled (BCrypt? SHA256? Plain text?).
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //Get User details
        authProvider.setUserDetailsService(userDetailsService());
        //Get Password and Scrambled it.
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    //Our own Database we are using.So, we use DTO. If we are logging in Google, then we use OAuth2AuthenticationProvider.

    @Bean   //We use this in Controller
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    /*
    AuthenticationConfiguration config: This is a built-in Spring Boot class. It holds the default security configuration created automatically by Spring Boot starter.
    config.getAuthenticationManager(): We are grabbing the default manager that Spring has already built and returning it.
    by default, the AuthenticationManager is Internal/Private.
    We are building a Custom API (/api/auth/login). We want to call authenticate() manually in your Controller.
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


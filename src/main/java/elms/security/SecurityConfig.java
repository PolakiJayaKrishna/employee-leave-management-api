package elms.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig { //The Rule Book (Traffic Laws) for the whole app.

    // 1. INJECT THE TOOLS WE BUILT YESTERDAY
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 2. DISABLE CSRF (Standard for APIs)
                .csrf(csrf -> csrf.disable())

                // 3. THE TRAFFIC RULES
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Open Door: Login/Register is public
                        .anyRequest().authenticated()                // Closed Door: Everything else needs a Token
                )

                // 4. NO SESSIONS (The "Stateless" Rule)
                .sessionManagement(sess -> sess
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        // We don't save login state in server RAM. The Token holds the state.
                )

                // 5. CONNECT THE WIRES
                .authenticationProvider(authenticationProvider) // Use our DB lookup
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Check Token BEFORE checking Password

        return http.build();
    }
}
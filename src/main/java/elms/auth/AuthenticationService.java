package elms.auth;

import elms.entities.Role;
import elms.exception.DuplicateEmailException;
import elms.repository.UserRepository;
import elms.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import elms.entities.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
            // Check if email already exists
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new DuplicateEmailException("Email already registered: " + request.getEmail());
            }
                var user = User.builder()
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword())) // <--- HASHING!
                                .role(Role.EMPLOYEE)
                                .build();
                // Save to DB
                userRepository.save(user);

                // Generate Token
                var jwtToken = jwtService.generateToken(user);

                // Return Token
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        // 2.Logic
        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                // Call the "Boss" to verify username/password
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                // If we get here, the user is valid. Now find them in DB.
                var user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(); // Throw error if not found (shouldn't happen if auth worked)

                // Generate a new Token
                var jwtToken = jwtService.generateToken(user);

                // Return it
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }
}

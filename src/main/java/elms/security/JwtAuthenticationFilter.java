package elms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,   //Letter Contains Header and Body
            @NotNull HttpServletResponse response, //In Controller
            @NotNull FilterChain filterChain       //Conveyor Belt . If not here means never reached the Controller.
    ) throws ServletException, IOException {

        // 1. GET THE HEADER
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. CHECK IF HEADER IS MISSING OR WRONG FORMAT
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request , response);  //Let them pass because they might be going to the Login Page.
            return;
        }

        // 3. EXTRACT TOKEN
        jwt = authHeader.substring(7); // Remove "Bearer " (7 characters)
        userEmail = jwtService.extractUsername(jwt);

        // 4. CHECK IF USER IS NOT CONNECTED YET
        if(userEmail != null || SecurityContextHolder.getContext().getAuthentication() == null ){ //Security -> If they are already logged in, we don't need to check the token again.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        }



    }
}

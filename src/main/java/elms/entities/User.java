package elms.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();

    private Integer totalLeaveBalance = 24; // Default starting balance for the year

    @Column(name = "remaining_leave_balance", nullable = false)
    private Integer remainingLeaveBalance = 24;

    // --- NEW METHODS REQUIRED BY THE INTERFACE ---
    //This is for Spring Security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    //1.role.name(): Takes your Enum and turns it into a String "MANAGER".
    //2.new SimpleGrantedAuthority(...): Takes that String and laminates it into an official ID Badge.
    //3.Why a List? Because in the future, a user might be a MANAGER and an ADMIN. They would have two badges in their wallet.

    //This is to Convert Enum -> GrantedAuthority objects. Because Spring Security only understands GrantedAuthority objects.

    @Override
    public String getUsername(){
        return email; // We are returning Email.Because , this is Unique in our system.
    }

    // These 4 are just standard "Yes" answers for now
    @Override public boolean isAccountNonExpired() { return true; } //Is your membership still valid, or did it expire last year?
    @Override public boolean isAccountNonLocked() { return true; } //Are you currently banned for fighting?
    @Override public boolean isCredentialsNonExpired() { return true; } //Is your password too old? Do you need to reset it? We are not forcing to Change Password for every 90 Days
    @Override public boolean isEnabled() { return true; } //Have you verified your email address? "YES" for now . In the future, we will Change
}

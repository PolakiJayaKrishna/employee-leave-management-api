package elms.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true , nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();

    private Integer totalLeaveBalance = 24; // Default starting balance for the year

    @Column(name = "remaining_leave_balance", nullable = false)
    private Integer remainingLeaveBalance = 24;
}

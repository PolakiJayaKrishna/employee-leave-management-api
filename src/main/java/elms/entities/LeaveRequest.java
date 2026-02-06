package elms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
@SQLDelete(sql = "UPDATE leave_requests SET deleted = true WHERE id=?")
// This line tells Hibernate: "Instead of deleting, just set the 'deleted' column to true."
@org.hibernate.annotations.SQLRestriction("deleted = false")
// This line tells Hibernate: "Whenever I ask for data, ignore anything where deleted is true."
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "leave_type_id" , nullable = false)
    private LeaveType leaveType;

    @NotNull(message = "Start date is mandatory")
    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;

    @NotNull(message = "End date is mandatory")
    @Future(message = "End date must be a future date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING;

    @NotBlank(message = "Reason for leave is required")
    @Size(min = 10, max = 255, message = "Reason must be between 10 and 255 characters")
    private String reason;

    private LocalDate createdAt = LocalDate.now();

    private Integer duration; // Calculated as (endDate - startDate)

    private boolean deleted = false;
}

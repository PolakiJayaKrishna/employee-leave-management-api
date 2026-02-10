package elms.dto;

import elms.entities.LeaveStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class LeaveRequestResponseDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer duration;
    private String reason;
    private LeaveStatus status;
    private UserResponseDTO user; // Nested DTO
}
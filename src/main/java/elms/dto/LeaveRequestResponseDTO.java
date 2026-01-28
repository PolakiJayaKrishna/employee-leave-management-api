package elms.dto;

import elms.entities.LeaveStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveRequestResponseDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer duration;
    private String reason;
    private LeaveStatus status;
    private UserResponseDTO user; // Nested DTO
}
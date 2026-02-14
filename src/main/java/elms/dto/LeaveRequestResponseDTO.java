package elms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import elms.entities.LeaveStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class LeaveRequestResponseDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private Integer duration;
    private String reason;
    private LeaveStatus status;
    private UserResponseDTO user; // Nested DTO
}
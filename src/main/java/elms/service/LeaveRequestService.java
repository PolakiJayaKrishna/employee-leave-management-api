package elms.service;


import elms.dto.LeaveRequestResponseDTO;
import elms.entities.LeaveRequest;


import java.util.List;

public interface LeaveRequestService {
    LeaveRequestResponseDTO applyForLeave(LeaveRequest request);
    List<LeaveRequestResponseDTO> getAllRequests();
    List<LeaveRequestResponseDTO> getRequestByUserId(Long userId);
    List<LeaveRequestResponseDTO> getRequestByStatus(String status);

    // Use this name consistently:
    LeaveRequestResponseDTO updateLeaveStatus(Long id, String status);
    LeaveRequestResponseDTO updateStatus(Long requestId, String status);
    LeaveRequestResponseDTO updateLeaveRequest(Long id, LeaveRequest updatedRequest);
    void deleteLeaveRequest(Long id);
}

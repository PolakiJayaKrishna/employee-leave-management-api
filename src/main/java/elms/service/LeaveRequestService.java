package elms.service;


import elms.entities.LeaveRequest;
import elms.entities.LeaveStatus;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface LeaveRequestService {
    LeaveRequest applyForLeave(LeaveRequest request);
    List<LeaveRequest> getAllRequests();
    LeaveRequest updateStatus(Long requestId , String status);

    List<LeaveRequest> getRequestByUserId(Long userId);

    LeaveRequest updateLeaveRequest(Long id, LeaveRequest updatedRequest);
    LeaveRequest updateLeaveStatus(Long id, String status);

    @Nullable List<LeaveRequest> getRequestByStatus(String status);
}

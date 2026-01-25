package elms.service;


import elms.entities.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {
    LeaveRequest applyForLeave(LeaveRequest request);
    List<LeaveRequest> getAllRequests();
    LeaveRequest updateStatus(Long requestId , String status);

    List<LeaveRequest> getRequestByUserId(Long userId);

    LeaveRequest updateLeaveRequest(Long id, LeaveRequest updatedRequest);
}

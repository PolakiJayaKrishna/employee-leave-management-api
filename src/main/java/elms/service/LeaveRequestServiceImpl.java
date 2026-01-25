package elms.service;

import elms.entities.LeaveRequest;
import elms.entities.LeaveStatus;
import elms.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;

    @Override
    public LeaveRequest applyForLeave(LeaveRequest request){
        return leaveRequestRepository.save(request);

    }

    @Override
    public List<LeaveRequest> getAllRequests(){
        return leaveRequestRepository.findAll();
    }

    @Override
    public LeaveRequest updateStatus(Long requestId , String status){
        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found with id: " + requestId));
        request.setStatus(LeaveStatus.valueOf(status.toUpperCase()));
        return leaveRequestRepository.save(request);
    }

    @Override
    public List<LeaveRequest> getRequestByUserId(Long userId){
        return leaveRequestRepository.findByUserId(userId);
    }

    @Override
    public LeaveRequest updateLeaveRequest(Long id , LeaveRequest updatedRequest){
        LeaveRequest existingRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave Request not found with id: " + id));

        // Update the fields
        existingRequest.setStartDate(updatedRequest.getStartDate());
        existingRequest.setEndDate(updatedRequest.getEndDate());
        existingRequest.setReason(updatedRequest.getReason());
        existingRequest.setLeaveType(updatedRequest.getLeaveType());

        return leaveRequestRepository.save(existingRequest);
    }
}

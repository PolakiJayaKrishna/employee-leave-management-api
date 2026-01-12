package elms.service;

import elms.entities.LeaveRequest;
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
        return null;
    }
}

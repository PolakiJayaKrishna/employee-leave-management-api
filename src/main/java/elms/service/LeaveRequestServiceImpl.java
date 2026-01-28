package elms.service;

import elms.dto.LeaveRequestResponseDTO;
import elms.dto.UserResponseDTO;
import elms.entities.LeaveRequest;
import elms.entities.LeaveStatus;
import elms.entities.User;
import elms.repository.LeaveRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import elms.repository.UserRepository;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    @Override
    public LeaveRequestResponseDTO applyForLeave(LeaveRequest request){

        //1.Calculate the Leave Duration
        long days = ChronoUnit.DAYS.between(request.getStartDate() , request.getEndDate()) + 1;

        //2.End Date Cannot be Before the Start Date
        if(days<=0)  throw new RuntimeException("Invalid Dates: End date must be after start date.");

        //3.Fetch User from the DataBase
        User user = userRepository.findById(request.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not Found"));

        request.setUser(user);

        //4.Business Rule: Check if they have enough days left
        if(days > user.getRemainingLeaveBalance()) throw new RuntimeException(("Insufficient Leave Balance. You requested " + days +
                " days, but only have " + user.getRemainingLeaveBalance() + " left."));

        //5.If all Good, Set the Duration and Save
        request.setDuration((int) days);
        request.setStatus(LeaveStatus.PENDING);

        LeaveRequest savedRequest = leaveRequestRepository.save(request);
        return convertToResponseDTO(savedRequest);
    }

    @Override
    public List<LeaveRequestResponseDTO> getAllRequests() {
        return leaveRequestRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO) // Converts every entity in the list to a DTO
                .collect(Collectors.toList());
    }
    @Override
    @Transactional // 1. CRITICAL: Ensures DB consistency if the balance update fails
    public LeaveRequestResponseDTO updateStatus(Long requestId, String status) {
        // 2. Fetch the request+1
        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found with id: " + requestId));

        // 3. Convert input string to Enum
        LeaveStatus newStatus = LeaveStatus.valueOf(status.toUpperCase());

        // 4. BUSINESS LOGIC: Subtract days only if the status is changing to APPROVED
        if (request.getStatus() == LeaveStatus.PENDING && newStatus == LeaveStatus.APPROVED) {
            User user = request.getUser();

            int updatedBalance = user.getRemainingLeaveBalance() - request.getDuration();

            // Safety Check
            if (updatedBalance < 0) {
                throw new RuntimeException("Cannot approve: User has insufficient balance.");
            }

            user.setRemainingLeaveBalance(updatedBalance);
            userRepository.save(user); // Save the updated user balance
        }

        // 5. Update and save the leave request
        request.setStatus(newStatus);
        LeaveRequest savedRequest = leaveRequestRepository.save(request);

        // 6. Return the clean DTO (hides passwords)
        return convertToResponseDTO(savedRequest);
    }

    @Override
    public List<LeaveRequestResponseDTO> getRequestByUserId(Long userId) {
        return leaveRequestRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveRequestResponseDTO updateLeaveRequest(Long id, LeaveRequest updatedRequest) {
        // 1. Find existing record
        LeaveRequest existingRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave Request not found with id: " + id));

        // 2. RE-CALCULATE Duration (Crucial!)
        long newDays = ChronoUnit.DAYS.between(updatedRequest.getStartDate(), updatedRequest.getEndDate()) + 1;
        if (newDays <= 0) throw new RuntimeException("Invalid Dates.");

        // 3. CHECK Balance again
        User user = existingRequest.getUser();
        if (newDays > user.getRemainingLeaveBalance()) {
            throw new RuntimeException("Insufficient balance for this update.");
        }

        // 4. Update fields
        existingRequest.setStartDate(updatedRequest.getStartDate());
        existingRequest.setEndDate(updatedRequest.getEndDate());
        existingRequest.setReason(updatedRequest.getReason());
        existingRequest.setLeaveType(updatedRequest.getLeaveType());
        existingRequest.setDuration((int) newDays); // Save the new duration

        // 5. Save and Return clean DTO
        LeaveRequest saved = leaveRequestRepository.save(existingRequest);
        return convertToResponseDTO(saved);
    }

    @Override
    @Transactional
    public LeaveRequestResponseDTO updateLeaveStatus(Long id , String status){

        //1.Find the ID
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave Request not found with id: " + id));

        //2.Convert String to ENUM
        LeaveStatus newStatus = LeaveStatus.valueOf(status.toUpperCase());

        //3.Business Logic: If moving from PENDING to APPROVED, subtract days
        if(leaveRequest.getStatus() == LeaveStatus.PENDING && newStatus == LeaveStatus.APPROVED){
            User user = leaveRequest.getUser();

            //Calculate the New Balance
            int updatedBalance = user.getRemainingLeaveBalance() - leaveRequest.getDuration();

            //Safety Check
            if(updatedBalance<0) throw new RuntimeException("Cannot approve: User has insufficient balance.");

            user.setRemainingLeaveBalance(updatedBalance);
            userRepository.save(user);
        }

        //4.Update the request
        leaveRequest.setStatus(newStatus);
        LeaveRequest savedRequest = leaveRequestRepository.save(leaveRequest);

        return convertToResponseDTO(savedRequest);
    }

    @Override
    public List<LeaveRequestResponseDTO> getRequestByStatus(String status) {
        // 1. Convert the String to the Enum safely
        LeaveStatus leaveStatus = LeaveStatus.valueOf(status.toUpperCase());

        // 2. Fetch and Map to DTOs
        return leaveRequestRepository.findByStatus(leaveStatus)
                .stream()
                .map(this::convertToResponseDTO) // Hides passwords and metadata
                .collect(Collectors.toList());
    }

    private LeaveRequestResponseDTO convertToResponseDTO(LeaveRequest leaveRequest) {
        LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();
        dto.setId(leaveRequest.getId());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setDuration(leaveRequest.getDuration());
        dto.setReason(leaveRequest.getReason());
        dto.setStatus(leaveRequest.getStatus());

        if (leaveRequest.getUser() != null) {
            UserResponseDTO userDTO = new UserResponseDTO();
            userDTO.setId(leaveRequest.getUser().getId());
            userDTO.setName(leaveRequest.getUser().getName());
            userDTO.setEmail(leaveRequest.getUser().getEmail());
            userDTO.setRemainingLeaveBalance(leaveRequest.getUser().getRemainingLeaveBalance());
            dto.setUser(userDTO);
        }
        return dto;
    }
}

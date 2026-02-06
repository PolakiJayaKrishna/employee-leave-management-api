package elms.controller;

import elms.dto.LeaveRequestResponseDTO;
import elms.entities.LeaveRequest; // Ensure this matches your package name
import elms.service.LeaveRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    // 1. Apply for Leave
    @PostMapping("/apply")
    public ResponseEntity<LeaveRequestResponseDTO> applyForLeave(@Valid @RequestBody LeaveRequest leaveRequest){
        // Service returns DTO -> Controller catches DTO. No more red lines!
        return new ResponseEntity<>(leaveRequestService.applyForLeave(leaveRequest), HttpStatus.CREATED);
    }

    // 2. Get All Requests (Returns a List of DTOs)
    @GetMapping("/all")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getAllRequests(){
        return ResponseEntity.ok(leaveRequestService.getAllRequests());
    }

    // 3. Update Status (Approving or Rejecting)
    @PutMapping("/{id}/status")
    public ResponseEntity<LeaveRequestResponseDTO> updateStatus(@PathVariable Long id, @RequestParam String status){
        // We call updateLeaveStatus (the smart method we built)
        return ResponseEntity.ok(leaveRequestService.updateLeaveStatus(id, status));
    }

    // 4. Employee History
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getEmployeeLeaveHistory(@PathVariable Long userId){
        return ResponseEntity.ok(leaveRequestService.getRequestByUserId(userId));
    }

    // 5. Update Leave Details (The Edit button)
    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequestResponseDTO> updateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequest updatedRequest) {
        return ResponseEntity.ok(leaveRequestService.updateLeaveRequest(id, updatedRequest));
    }

    // 6. Filter by Status (Pending/Approved/Rejected)
    @GetMapping("/filter")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getLeavesByStatus(@RequestParam String status){
        return ResponseEntity.ok(leaveRequestService.getRequestByStatus(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
        return ResponseEntity.ok("Leave request cancelled and balance restored (if applicable).");
    }
}
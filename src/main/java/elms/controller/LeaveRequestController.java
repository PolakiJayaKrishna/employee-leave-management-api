package elms.controller;

import elms.entities.LeaveRequest;
import elms.service.LeaveRequestService;
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

    //1.EndPoint for applying leave
    @PostMapping("/apply")
    public ResponseEntity<LeaveRequest> applyForLeave(@RequestBody LeaveRequest leaveRequest){
        LeaveRequest savedRequest = leaveRequestService.applyForLeave(leaveRequest);
        return new ResponseEntity<>(savedRequest , HttpStatus.CREATED);
    }

    //2. EndPoint to GetAll Requests
    @GetMapping("/all")
    public ResponseEntity<List<LeaveRequest>> getAllRequests(){
        List<LeaveRequest> requests = leaveRequestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    //3.EndPoint to Update Status
    @PutMapping("/{id}/status")
    public ResponseEntity<LeaveRequest> updateStatus(@PathVariable Long id , @RequestParam String status){
        LeaveRequest updatedRequest = leaveRequestService.updateStatus(id , status);
        return ResponseEntity.ok(updatedRequest);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LeaveRequest>> getEmployeeLeaveHistory(@PathVariable Long userId){
        return ResponseEntity.ok(leaveRequestService.getRequestByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequest updatedRequest) {
        return ResponseEntity.ok(leaveRequestService.updateLeaveRequest(id, updatedRequest));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<LeaveRequest>> getLeavesByStatus(@RequestParam String status){
        return ResponseEntity.ok(leaveRequestService.getRequestByStatus(status));
    }
}

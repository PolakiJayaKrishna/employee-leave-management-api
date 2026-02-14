package elms.controller;

import elms.dto.LeaveRequestResponseDTO;
import elms.entities.LeaveRequest; // Ensure this matches your package name
import elms.service.LeaveRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    // 1. Apply for Leave
    @Operation(summary = "Apply for leave", description = "Submit a new leave request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Leave request created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid dates or insufficient leave balance")
    })
    @PostMapping("/apply")
    public ResponseEntity<LeaveRequestResponseDTO> applyForLeave(@Valid @RequestBody LeaveRequest leaveRequest){
        // Service returns DTO -> Controller catches DTO. No more red lines!
        return new ResponseEntity<>(leaveRequestService.applyForLeave(leaveRequest), HttpStatus.CREATED);
    }

    // 2. Get All Requests (Returns a List of DTOs)
    @Operation(summary = "Get all leave requests", description = "Retrieve all leave requests in the system")
    @ApiResponse(responseCode = "200", description = "List of all leave requests")
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/all")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getAllRequests(){
        return ResponseEntity.ok(leaveRequestService.getAllRequests());
    }

    // 3. Update Status (Approving or Rejecting)
    @Operation(summary = "Update leave status", description = "Approve or reject a leave request by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Leave request not found"),
            @ApiResponse(responseCode = "400", description = "Insufficient balance to approve"),
            @ApiResponse(responseCode = "403", description = "Access denied — Manager role required")
    })
    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}/status")
    public ResponseEntity<LeaveRequestResponseDTO> updateStatus(@PathVariable Long id, @RequestParam String status){
        // We call updateLeaveStatus (the smart method we built)
        return ResponseEntity.ok(leaveRequestService.updateLeaveStatus(id, status));
    }

    // 4. Employee History
    @Operation(summary = "Get employee leave history", description = "Get all leave requests for a specific user")
    @ApiResponse(responseCode = "200", description = "List of leave requests for the user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getEmployeeLeaveHistory(@PathVariable Long userId){
        return ResponseEntity.ok(leaveRequestService.getRequestByUserId(userId));
    }

    // 5. Update Leave Details (The Edit button)
    @Operation(summary = "Update leave details", description = "Edit an existing leave request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leave request updated successfully"),
            @ApiResponse(responseCode = "404", description = "Leave request not found"),
            @ApiResponse(responseCode = "400", description = "Invalid dates or insufficient balance")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequestResponseDTO> updateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequest updatedRequest) {
        return ResponseEntity.ok(leaveRequestService.updateLeaveRequest(id, updatedRequest));
    }

    // 6. Filter by Status (Pending/Approved/Rejected)
    @Operation(summary = "Filter leaves by status", description = "Filter leave requests by status: PENDING, APPROVED, or REJECTED")
    @ApiResponse(responseCode = "200", description = "Filtered list of leave requests")
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/filter")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getLeavesByStatus(@RequestParam String status){
        return ResponseEntity.ok(leaveRequestService.getRequestByStatus(status));
    }

    @Operation(summary = "Delete leave request", description = "Cancel a leave request and restore leave balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leave request cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Leave request not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
        return ResponseEntity.ok("Leave request cancelled and balance restored (if applicable).");
    }
}
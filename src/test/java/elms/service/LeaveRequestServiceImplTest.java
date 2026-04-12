package elms.service;

import elms.dto.LeaveRequestResponseDTO;
import elms.entities.LeaveRequest;
import elms.entities.LeaveStatus;
import elms.entities.Role;
import elms.entities.User;
import elms.exception.LeaveBalanceException;
import elms.exception.ResourceNotFoundException;
import elms.repository.LeaveRequestRepository;
import elms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeaveRequestServiceImplTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LeaveRequestServiceImpl leaveRequestService;

    private User user;
    private LeaveRequest request;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setRemainingLeaveBalance(10);
        user.setRole(Role.EMPLOYEE);
        user.setFirstname("Jaya");
        user.setLastname("Krishna");
        user.setEmail("jaya@test.com");

        request = new LeaveRequest();
        request.setUser(user);
        request.setStartDate(LocalDate.of(2025, 4, 15));
        request.setEndDate(LocalDate.of(2025, 4, 17));
        request.setReason("Family function");
    }

    @Test
    public void applyLeave() {

        // Step 3: When fake repo is called with ID 1, return our fake user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Step 4: When fake repo saves the request, return that same request
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(request);

        // Step 5: Call the real service method
        LeaveRequestResponseDTO result = leaveRequestService.applyForLeave(request);

        // Step 6: Assert - verify the result is correct
        assertNotNull(result);
        assertEquals(LeaveStatus.PENDING, request.getStatus());

    }

    @Test
    public void applyLeave_userNotFound() {

        // Step 3: When fake repo is called with ID 1, return our fake user
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> leaveRequestService.applyForLeave(request));
    }

    @Test
    public void inSufficentBalance() {
        // Step 3: When fake repo is called with ID 1, return our fake user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Set Remaining Leave Balance
        user.setRemainingLeaveBalance(2);

        assertThrows(LeaveBalanceException.class,
                () -> leaveRequestService.applyForLeave(request));
    }

}

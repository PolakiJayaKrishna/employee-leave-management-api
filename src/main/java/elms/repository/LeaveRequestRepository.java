package elms.repository;

import elms.entities.LeaveRequest;
import elms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest , Long> {
    List<LeaveRequest> findByUser(User user);
    List<LeaveRequest> findByStatus(String status);
}

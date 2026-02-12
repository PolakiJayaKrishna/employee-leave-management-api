package elms.service;

import elms.dto.LoginRequest;
import elms.dto.UserResponseDTO;
import elms.entities.User;
import elms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserResponseDTO login(LoginRequest loginRequest){
        // 1. Find user by email
        User user = userRepository.findByEmailAndPassword(loginRequest.getEmail() , loginRequest.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        //2.JWT will replace this
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return convertToUserDTO(user);
    }


    private UserResponseDTO convertToUserDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getFirstname());
        dto.setName(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());

        //Convert Enum to String -> we prefer .name() rather than toString() . Because , this is final
        //If user.getRole() is Role.MANAGER, then .name() returns the String "MANAGER".

        dto.setRemainingLeaveBalance(user.getRemainingLeaveBalance());
        return dto;
    }
}

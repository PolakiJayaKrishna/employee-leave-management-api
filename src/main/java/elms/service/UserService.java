package elms.service;

import elms.dto.UserResponseDTO;
import elms.dto.LoginRequest;

public interface UserService {
    UserResponseDTO login(LoginRequest loginRequest);
}
package vn.com.openlab.api.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.com.openlab.api.token.dto.RefreshTokenDTO;
import vn.com.openlab.api.user.dto.UpdateUserDTO;
import vn.com.openlab.api.user.dto.UserDTO;
import vn.com.openlab.api.user.model.User;
import vn.com.openlab.api.user.response.LoginResponse;
import vn.com.openlab.helper.exception.payload.DataNotFoundException;

public interface UserService {
    User createUser(UserDTO userDTO) throws Exception;

    String login(String phoneNumber, String password) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    User updateUser(Long userId, UpdateUserDTO updateUserDTO) throws Exception;

    LoginResponse refreshToken(RefreshTokenDTO refreshTokenDTO);

    Page<User> findAllUsers(String keyword, Pageable pageable);

    void resetPassword(Long userId, String newPassword) throws Exception;

    void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException;

}
package edu.ptit.openlab.controller.auth;

import edu.ptit.openlab.DTO.AuthenticationResponse;
import edu.ptit.openlab.DTO.LoginRequestDTO;
import edu.ptit.openlab.DTO.RegisterRequestDTO;
import edu.ptit.openlab.entity.User;
import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
// @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
public class AuthController {
    private final AuthenticationService authenticationService;

    // Login
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequestDTO loginRequest) {
        BaseResponse response = new BaseResponse();
        try {
            AuthenticationResponse authResponse = authenticationService.login(loginRequest.getEmail(),
                    loginRequest.getPassword());
            String token = authResponse.getToken();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Login successfully");
            response.setData(authResponse);
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(response);
        } catch (BadCredentialsException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Invalid email or password");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        try {
            User user = authenticationService.register(request.getEmail(), request.getPassword(),request.getVaiTro());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse> getUser(@PathVariable Long userId) {
        Optional user = authenticationService.getUser(userId);
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.setStatus(200);
            baseResponse.setMessage("Successfully");
            baseResponse.setData(user.get());
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(400);
            baseResponse.setMessage("FAILED");
            return ResponseEntity.ok(baseResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllUsers() {
        List<User> listUsers = authenticationService.getAllUsers();
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.setStatus(200);
            baseResponse.setMessage("Successfully");
            baseResponse.setData(listUsers);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(400);
            baseResponse.setMessage("FAILED");
            return ResponseEntity.ok(baseResponse);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable Long userId,
                                                   @RequestParam(required = false) String email,
                                                   @RequestParam(required = false) String password,
                                                   @RequestParam(required = false) String newAddress,
                                                   @RequestParam(required = false) String newPhoneNumber,
                                                   @RequestParam(required = false) String newUserName,
                                                   @RequestParam(required = false) MultipartFile newThumbnail,
                                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date newDob,
                                                   @RequestParam(required = false) Integer vaiTro
                                                   ){
        User updateUser = authenticationService.updateUser(userId, email, password, newAddress, newPhoneNumber, newUserName, newThumbnail, newDob, vaiTro);
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.setStatus(200);
            baseResponse.setMessage("Updated successfully");
            baseResponse.setData(updateUser);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e){
            baseResponse.setStatus(500);
            baseResponse.setMessage("Update faile: " + e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        BaseResponse baseResponse = authenticationService.deleteUser(userId);

        if (baseResponse.getStatus() == 200) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content khi xóa thành công
        } else if (baseResponse.getStatus() == 404) {
            return new ResponseEntity<>(baseResponse.getMessage(), HttpStatus.NOT_FOUND);  // 404 Not Found nếu không tìm thấy user
        } else {
            return new ResponseEntity<>(baseResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // 500 Internal Server Error nếu có lỗi
        }
    }
}

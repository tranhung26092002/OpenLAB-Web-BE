package edu.ptit.openlab.service;

import edu.ptit.openlab.DTO.AuthenticationResponse;
import edu.ptit.openlab.entity.User;
import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Key;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final StorageService storageService;

    // Đoạn JWT_SECRET là bí mật, chỉ có phía server biết
    @Value("${jwt.secret-key}")
    private String JWT_SECRET_STRING;

    // method sử dụng key mở khóa
    private Key getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(JWT_SECRET_STRING);
        return Keys.hmacShaKeyFor(decodedKey);
    }
    //    private final Key JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    // Thời gian có hiêu lực của jwt (10 ngày)
    private final long JWT_EXPIRATION = 86400000L;

    public AuthenticationResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Thong tin dang nhap dung tao ra jwt token
            String token = createToken(user);
            String role = mapRoles(user.getVaiTro()).get(0);

            // trả về email
            AuthenticationResponse response = new AuthenticationResponse(token, user.getId(), role, email);
            return response;
        }
        throw new BadCredentialsException("Invalid email or password");
    }

    public User register(String email, String password, int vaiTro) throws IllegalAccessException {
        //Kiểm tra xem email tồn tại không
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(email));
        if(existingUser.isPresent()){
            throw new IllegalAccessException("Email already in use");
        }

        // Ma hoa mat khau
        String encryptedPassword = passwordEncoder.encode(password);

        // Tao nguoi dung moi va luu vao co so du lieu
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(encryptedPassword);
        newUser.setVaiTro(vaiTro);

        return userRepository.save(newUser);
    }

    public User updateUser(Long userId, String newEmail, String newPassword, String newAddress, String newPhoneNumber, String newUserName, MultipartFile newThumbnail, Date newDob, Integer newVaiTro){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new NoSuchElementException("User not found with id: " + userId));

        if (newEmail != null && !newEmail.isEmpty()) {
            user.setEmail(newEmail);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        if (newAddress != null && !newAddress.isEmpty()) {
            user.setAddress(newAddress);
        }
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            user.setPhoneNumber(newPhoneNumber);
        }
        if (newUserName != null && !newUserName.isEmpty()) {
            user.setUsername(newUserName);
        }
        String fileName = storageService.uploadImageToFileSystem(newThumbnail);

        if (newThumbnail != null && !newThumbnail.isEmpty()) {
            user.setThumbnail(fileName);
        }
        if (newDob != null) {
            user.setDob(newDob);
        }
        if (newVaiTro != null) {
            user.setVaiTro(newVaiTro);
        }
        return userRepository.save(user);
    }

    public BaseResponse deleteUser(Long userId) {
        BaseResponse response = new BaseResponse();

        try {
            // Kiểm tra xem user có tồn tại không
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                response.setStatus(200);
                response.setMessage("User deleted successfully.");
            } else {
                response.setStatus(404);
                response.setMessage("User not found.");
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error occurred while deleting the user: " + e.getMessage());
        }

        return response;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional getUser(Long userId) {
        return userRepository.findById(userId);
    }

    private String createToken(User user) {
        // Thời gian hết hạn của Token
        Date expiryDate = new Date(System.currentTimeMillis() + JWT_EXPIRATION);
        //Tạo chuỗi json web token từ id của user
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles",mapRoles(user.getVaiTro()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    public List<String> mapRoles(int vaiTro) {
        List<String> roles = new ArrayList<>();
        switch (vaiTro){
            case 1:
                roles.add("ROLE_USER");
                break;
            case 2:
                roles.add("ROLE_ADMIN");
                break;
        }
        return roles;
    }

    public UserDetails verifyToken(String token){
        try {
            // Giải mã token
            String email = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey()).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            User user = userRepository.findByEmail(email);
            // Trả về một đối tượng UserDetails
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : mapRoles(user.getVaiTro())) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        } catch (Exception e){
            // Nếu token không hợp lệ, hoặc hết hạn trả về null
            return null;
        }
    }
}

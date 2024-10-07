package vn.com.openlab.api.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import lombok.*;
import vn.com.openlab.api.role.model.Role;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    @JsonProperty("token")
    private String token;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JoinColumn(name = "role_id")
    private Role role;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("refresh_token")
    private String refreshToken;
}


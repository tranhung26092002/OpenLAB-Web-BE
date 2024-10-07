package vn.com.openlab.service;

import vn.com.openlab.model.Token;
import vn.com.openlab.model.User;

public interface TokenService {
    Token addTokenEndRefreshToken(User user, String token, boolean isMobile);
}

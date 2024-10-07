package vn.com.openlab.api.token.service;

import vn.com.openlab.api.token.model.Token;
import vn.com.openlab.api.user.model.User;

public interface TokenService {
    Token addTokenEndRefreshToken(User user, String token, boolean isMobile);
}

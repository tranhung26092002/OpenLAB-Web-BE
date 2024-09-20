package vn.com.openlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.model.Token;
import vn.com.openlab.model.User;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);

    Optional<Token> findByUserId(Long userId);

    List<Token> findByUser(User user);

    Optional<Token> findByRefreshToken(String refreshToken);
}


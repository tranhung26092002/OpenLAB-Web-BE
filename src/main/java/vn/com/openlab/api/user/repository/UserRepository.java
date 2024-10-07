package vn.com.openlab.api.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.openlab.api.user.model.User;
import vn.com.openlab.utils.object.ConfixSql;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);

    // lấy ra tất cả user (ngoại trừ admin) với truyền admin
    @Query(ConfixSql.User.GET_ALL_USER)
    Page<User> fillAll(@Param("keyword") String keyword, Pageable pageable);
}


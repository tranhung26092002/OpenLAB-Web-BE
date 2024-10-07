package vn.com.openlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.model.Coupon;

import java.util.Optional;

public interface CouponRespository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String couponCode);
}

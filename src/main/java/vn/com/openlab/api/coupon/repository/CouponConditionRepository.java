package vn.com.openlab.api.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.api.coupon.model.CouponCondition;

import java.util.List;

public interface CouponConditionRepository extends JpaRepository<CouponCondition, Long> {
    List<CouponCondition> findByCouponId(Long couponId);
}


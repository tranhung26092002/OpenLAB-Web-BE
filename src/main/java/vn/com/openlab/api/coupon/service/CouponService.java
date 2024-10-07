package vn.com.openlab.api.coupon.service;

public interface CouponService {
    double calculateCouponValue(String couponCode, double totalAmount);
}

package vn.com.openlab.service;

public interface CouponService {
    double calculateCouponValue(String couponCode, double totalAmount);
}

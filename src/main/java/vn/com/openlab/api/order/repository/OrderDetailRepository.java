package vn.com.openlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.model.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);
}
package vn.com.openlab.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.api.order.model.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);
}
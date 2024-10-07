package vn.com.openlab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.com.openlab.api.order.dto.OrderDTO;
import vn.com.openlab.model.Order;
import vn.com.openlab.response.order.OrderResponse;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;

    Order getOrderById(Long id);

    Order updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<Order> findByUserId(Long userId);

    Page<OrderResponse> findByKeyword(String keyword, Pageable pageable);
}

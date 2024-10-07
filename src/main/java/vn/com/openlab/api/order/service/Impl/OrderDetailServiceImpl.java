package vn.com.openlab.api.order.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.openlab.api.order.dto.OrderDetailDTO;
import vn.com.openlab.helper.exception.payload.DataNotFoundException;
import vn.com.openlab.api.order.model.Order;
import vn.com.openlab.api.order.model.OrderDetail;
import vn.com.openlab.api.product.model.Product;
import vn.com.openlab.api.order.repository.OrderDetailRepository;
import vn.com.openlab.api.order.repository.OrderRepository;
import vn.com.openlab.api.product.repository.ProductRepository;
import vn.com.openlab.api.order.service.OrderDetailService;
import vn.com.openlab.utils.object.LocalizationUtils;
import vn.com.openlab.utils.object.MessageKeys;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final LocalizationUtils localizationUtils;

    @Override
    @Transactional
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        // tìm xem orderId có tồn tại hay không
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, orderDetailDTO.getOrderId())));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, orderDetailDTO.getProductId())));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .price(orderDetailDTO.getPrice())
                .color(orderDetailDTO.getColor())
                .build();

        // lưu vào DB
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) {
        // tìm xem orderDetail có tồn tại hay không
        OrderDetail existsOrderDetail = getOrderDetail(id);
        Order existsOrder = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, orderDetailDTO.getOrderId())));
        Product existsProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, orderDetailDTO.getProductId())));

        existsOrderDetail.setProduct(existsProduct);
        existsOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        existsOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existsOrderDetail.setPrice(orderDetailDTO.getPrice());
        existsOrderDetail.setColor(orderDetailDTO.getColor());
        existsOrderDetail.setId(id);
        existsOrderDetail.setOrder(existsOrder);
        return orderDetailRepository.save(existsOrderDetail);
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    private String translate(String message, Object... listMessages) {
        return localizationUtils.getLocalizedMessage(message, listMessages);
    }
}


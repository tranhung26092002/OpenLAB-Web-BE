package vn.com.openlab.api.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.com.openlab.component.TranslateMessages;
import vn.com.openlab.api.order.dto.OrderDetailDTO;
import vn.com.openlab.helper.base.response.ApiResponse;
import vn.com.openlab.api.order.model.OrderDetail;
import vn.com.openlab.api.order.response.order_detail.OrderDetailResponse;
import vn.com.openlab.api.order.service.OrderDetailService;
import vn.com.openlab.utils.object.MessageKeys;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController extends TranslateMessages {

    private final OrderDetailService orderDetailService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(
                        ApiResponse.builder()
                                .message(translate(MessageKeys.ERROR_MESSAGE))
                                .errors(errorMessages.stream().map(this::translate).toList()).build()
                );
            }

            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(ApiResponse.builder().success(true)
                    .message(translate(MessageKeys.CREATE_ORDER_DETAILS_SUCCESS))
                    .data(OrderDetailResponse.fromOrderDetail(newOrderDetail)).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .message(translate(MessageKeys.CREATE_ORDER_DETAILS_SUCCESS))
                            .error(e.getMessage()).build()
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) throws Exception {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok(ApiResponse.builder().success(true).data(OrderDetailResponse.fromOrderDetail(orderDetail)).build());
        // return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        List<OrderDetailResponse> orderDetailResponses = orderDetailService.findByOrderId(orderId)
                .stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .toList();
        return ResponseEntity.ok(ApiResponse.builder().success(true).data(orderDetailResponses).build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            @PathVariable("id") Long id
    ) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok(ApiResponse.builder().success(true).data(OrderDetailResponse.fromOrderDetail(orderDetail)).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder().error(e.getMessage()).build());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok().body(ApiResponse.builder().success(true)
                    .message(translate(MessageKeys.MESSAGE_DELETE_SUCCESS)).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .error(e.getMessage())
                    .message(translate(MessageKeys.MESSAGE_DELETE_SUCCESS)).build());
        }
    }

}


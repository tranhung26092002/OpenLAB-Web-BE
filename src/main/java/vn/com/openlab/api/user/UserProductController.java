package vn.com.openlab.api.user;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.com.openlab.component.TranslateMessages;
import vn.com.openlab.helper.base.response.ApiResponse;
import vn.com.openlab.api.user.model.User;
import vn.com.openlab.api.product.service.ProductService;
import vn.com.openlab.api.user.service.UserService;
import vn.com.openlab.api.product.response.ProductPageResponse;
import vn.com.openlab.api.product.response.ProductResponse;
import vn.com.openlab.utils.object.MessageKeys;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("${api.prefix}/my-product")
@RequiredArgsConstructor
public class UserProductController extends TranslateMessages {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ProductService productService;

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden")
    })
//    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllMyProducts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "", name = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "limit") int limit,
            @RequestHeader("Authorization") String token
    ) {
        try {
            String extractedToken = token.substring(7); // Loại bỏ "Bearer " từ token
            User user = userService.getUserDetailsFromToken(extractedToken);

            if (!user.getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                        ApiResponse.<ProductPageResponse>builder()
                                .message(translate(MessageKeys.MESSAGE_ERROR_FORBIDDEN))
                                .build()
                );
            }

            PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
            Page<ProductResponse> productPage = productService.findAllMyProducts(userId, keyword, pageRequest)
                    .map(ProductResponse::fromProduct);

            List<ProductResponse> productResponses = productPage.getContent();

            return ResponseEntity.ok(ProductPageResponse.builder()
                    .products(productResponses)
                    .pageNumber(page)
                    .pageSize(productPage.getSize())
                    .totalElements(productPage.getTotalElements())
                    .totalPages(productPage.getTotalPages())
                    .isLast(productPage.isLast())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<ProductPageResponse>builder()
                            .message(translate(MessageKeys.MESSAGE_ERROR_GET))
                            .error(e.getMessage())
                            .build()
            );
        }
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    @PostMapping("/add/{userId}")
    public ResponseEntity<ApiResponse<?>> addProductToUser(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestHeader("Authorization") String token
    ) {
        try {

            String extractedToken = token.substring(7); // Loại bỏ "Bearer " từ token
            User user = userService.getUserDetailsFromToken(extractedToken);

            if (!user.getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                        ApiResponse.<String>builder()
                                .message(translate(MessageKeys.MESSAGE_ERROR_FORBIDDEN))
                                .build()
                );
            }

            ProductResponse productResponse = productService.addProductToUser(userId, productId);

            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message(translate(MessageKeys.MESSAGE_SUCCESS_PRODUCT_ADDED))
                    .payload(productResponse)
                    .build());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .message(translate(MessageKeys.MESSAGE_ERROR_ADD_PRODUCT))
                            .error(e.getMessage())
                            .build()
            );
        }
    }

    //    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    @PostMapping("/add-subib/{userId}")
    public ResponseEntity<ApiResponse<?>> addProductToUserBySubId(
            @PathVariable Long userId,
            @RequestParam String subId,
            @RequestHeader("Authorization") String token
    ) {
        try {

            String extractedToken = token.substring(7); // Loại bỏ "Bearer " từ token
            User user = userService.getUserDetailsFromToken(extractedToken);

            if (!user.getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                        ApiResponse.<String>builder()
                                .message(translate(MessageKeys.MESSAGE_ERROR_FORBIDDEN))
                                .build()
                );
            }

            ProductResponse productResponse = productService.addProductToUserBySubId(userId, subId);

            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message(translate(MessageKeys.MESSAGE_SUCCESS_PRODUCT_ADDED))
                    .payload(productResponse)
                    .build());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .message(translate(MessageKeys.MESSAGE_ERROR_ADD_PRODUCT))
                            .error(e.getMessage())
                            .build()
            );
        }
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<ApiResponse<?>> removeProductFromUser(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestHeader("Authorization") String token
    ) {
        try {
            String extractedToken = token.substring(7); // Loại bỏ "Bearer " từ token
            User user = userService.getUserDetailsFromToken(extractedToken);

            if (!user.getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                        ApiResponse.<String>builder()
                                .message(translate(MessageKeys.MESSAGE_ERROR_FORBIDDEN))
                                .build()
                );
            }

            productService.removeProductFromUser(userId, productId);

            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .success(true)
                    .message(translate(MessageKeys.MESSAGE_SUCCESS_PRODUCT_REMOVED, productId))
                            .id(productId)
                    .build());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .message(translate(MessageKeys.MESSAGE_ERROR_REMOVE_PRODUCT, productId))
                            .error(e.getMessage())
                            .build()
            );
        }
    }
}

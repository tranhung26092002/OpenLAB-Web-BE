package edu.ptit.openlab.controller.user;

import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    // Lấy danh sách tất cả cái khóa học
    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllProducts() {
        BaseResponse response = productService.getAllProducts();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Lấy danh sách các khóa học với phân trang
    @GetMapping("/all-paginate")
    public ResponseEntity<BaseResponse> getProductPaginated(@RequestParam int page, @RequestParam int size) {
        BaseResponse response = productService.getProductPaginated(page, size);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Tìm kiếm khóa học theo từ khóa
    @GetMapping("/search")
    public ResponseEntity<BaseResponse> searchListProduct(@RequestParam String search) {
        BaseResponse response = productService.searchListProduct(search);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Lấy chi tiết một khóa học dựa trên ID
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getProduct(@PathVariable Long id) {
        BaseResponse response = productService.getProduct(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}

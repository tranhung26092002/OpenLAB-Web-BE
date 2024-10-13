package edu.ptit.openlab.controller.user;

import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.service.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-product")
public class UserProductController {
    private final UserProductService userProductService;

    @GetMapping("/all/{userId}")
    public ResponseEntity<BaseResponse> getAllProduct(@PathVariable Long userId) {
        BaseResponse response = userProductService.getAllProduct(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/register/{userId}/{productId}")
    public ResponseEntity<BaseResponse> registerProduct(@PathVariable Long userId, @PathVariable Long productId) {
        BaseResponse response = userProductService.registerProduct(userId, productId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/register-sub-id/{userId}/{subId}")
    public ResponseEntity<BaseResponse> registerProductBySubId(@PathVariable Long userId, @PathVariable String subId) {
        BaseResponse response = userProductService.registerProductBySubId(userId, subId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

//    @PutMapping("{userId}/update/{productId}")
//    public ResponseEntity<BaseResponse> updateProduct(@PathVariable Long userId, @PathVariable Long productId) {
//        BaseResponse response = userProductService.updateProduct(userId, productId);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
//    }

    @DeleteMapping("/delete/{userId}/{productId}")
    public ResponseEntity<BaseResponse> deleteProduct(@PathVariable Long userId, @PathVariable Long productId) {
        BaseResponse response = userProductService.deleteProduct(userId, productId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}

package edu.ptit.openlab.controller.admin;

import edu.ptit.openlab.entity.Product;
import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.service.ProductService;
import edu.ptit.openlab.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/product")
public class ProductControllerAdmin {

    private final ProductService productService;

    private final StorageService storageService;

    @Value("${app.base-upload-image-url}")
    private String BASE_UPLOAD_IMAGE_URL;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(
            @RequestParam("nameProduct") String nameProduct,
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @RequestParam("typeProduct") String typeProduct,
            @RequestParam("description") String description
        ) {
        try {
            // Lưu file vào một thư mục cụ thể
            String fileName = storageService.uploadImageToFileSystem(thumbnail);
            String uniqueId = "OL-Product-" + UUID.randomUUID().toString();

            Product product = new Product();

            product.setSubId(uniqueId);
            product.setNameProduct(nameProduct);
            product.setThumbnail(fileName);
            product.setTypeProduct(typeProduct);
            product.setCreatedBy("Admin");
            product.setDescription(description);

            BaseResponse response = productService.save(product);
            if (response.getStatus() == 200) {
                return new ResponseEntity<>(response.getData(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Error from service: " + response.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestParam(value = "nameProduct", required = false) Optional<String> nameProduct,
            @RequestParam(value = "thumbnail", required = false) Optional<MultipartFile> thumbnail,
            @RequestParam(value = "typeProduct", required = false) Optional<String> typeProduct,
            @RequestParam(value = "description", required = false) Optional<String> description
        ) {
        BaseResponse fetchedProduct = productService.getProduct(id);
        if (fetchedProduct.getStatus() != 200) {
            return new ResponseEntity<>(fetchedProduct.getMessage(), HttpStatus.NOT_FOUND);
        }

        Product product = (Product) fetchedProduct.getData();

        try {
            // Kiểm tra và cập nhật các thuộc tính nếu có trong yêu cầu
            nameProduct.ifPresent(product::setNameProduct);
            typeProduct.ifPresent(product::setTypeProduct);
            description.ifPresent(product::setDescription);

            // Kiểm tra và xử lý file thumbnail nếu có
            if (thumbnail.isPresent()) {
                String fileName = storageService.uploadImageToFileSystem(thumbnail.get());
                product.setThumbnail(fileName);
            }

            BaseResponse updateResponse = productService.updateProduct(id, product);
            return (updateResponse.getStatus() == 200)
                    ? new ResponseEntity<>(updateResponse.getData(), HttpStatus.OK)
                    : new ResponseEntity<>(updateResponse.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        BaseResponse deleteResponse = productService.deleteProduct(id);
        if (deleteResponse.getStatus() == 200) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(deleteResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

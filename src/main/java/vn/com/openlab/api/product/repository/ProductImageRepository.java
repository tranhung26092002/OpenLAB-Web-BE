package vn.com.openlab.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.api.product.model.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}


package vn.com.openlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.model.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}


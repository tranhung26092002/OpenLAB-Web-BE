package edu.ptit.openlab.repository;

import edu.ptit.openlab.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select product FROM Product product WHERE product.nameProduct LIKE CONCAT('%',:search,'%')")
    List<Product> searchProduct(@Param("search") String search);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_product WHERE product_id = ?1", nativeQuery = true)
    void deleteUserProductsByProductId(Long productId);

    Optional<Product> findBySubId(String subId);
}

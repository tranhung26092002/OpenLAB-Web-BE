package vn.com.openlab.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.openlab.model.Product;
import vn.com.openlab.response.product.ProductResponse;
import vn.com.openlab.utils.object.ConfixSql;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    Page<Product> findAll(Pageable pageable);

    @Query(ConfixSql.Product.SEARCH_PRODUCT_BY_KEYWORD)
    Page<Product> searchProducts(@Param("keyword") String keyword,
                                 @Param("categoryId") Long categoryId,
                                 Pageable pageable);

    @Query(ConfixSql.Product.GET_DETAIL_PRODUCT)
    Optional<Product> getDetailProducts(@Param("productId") Long productId);

    @Query(ConfixSql.Product.FIND_PRODUCT_BY_IDS)
    List<Product> findProductByIds(@Param("productIds") List<Long> productIds);
    @Query(ConfixSql.Product.GET_ALL_MY_PRODUCTS)
    Page<Product> fillAll(@Param("userId") Long userId,
                          @Param("keyword") String keyword,
                          Pageable pageable);

    Optional<Product> findBySubId(String subId);
}


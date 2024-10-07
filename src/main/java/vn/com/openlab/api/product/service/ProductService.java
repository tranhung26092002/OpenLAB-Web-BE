package vn.com.openlab.api.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.com.openlab.api.product.dto.ProductDTO;
import vn.com.openlab.api.product.dto.ProductImageDTO;
import vn.com.openlab.api.product.model.Product;
import vn.com.openlab.api.product.model.ProductImage;
import vn.com.openlab.api.product.response.ProductResponse;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;

    Product getProductById(Long id) throws Exception;

//    Page<ProductResponse> getAllProducts(String keyword,
//                                         Long categoryId,
//                                         PageRequest pageRequest);

    Page<ProductResponse> getAllProducts(String keyword,
                                         Long categoryId,
                                         PageRequest pageRequest,
                                         String sortField,
                                         String sortDirection);

    Product updateProduct(Long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(Long id);

    boolean existsProduct(String name);

    ProductImage createProductImage(Long productId,
                                    ProductImageDTO productImageDTO);

    Product getDetailProducts(long productId) throws Exception;

    List<Product> findProductsByIds(List<Long> productIds);

    Page<Product> findAllMyProducts(Long userId, String keyword, Pageable pageable);

    ProductResponse addProductToUser(Long userId, Long productId);

    ProductResponse addProductToUserBySubId(Long userId, String subId);

    void removeProductFromUser(Long userId, Long productId);

//     Elasticsearch
//    Page<ProductResponse> searchProducts(String keyword, PageRequest pageRequest);
}


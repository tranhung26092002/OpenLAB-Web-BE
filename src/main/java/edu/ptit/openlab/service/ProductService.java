package edu.ptit.openlab.service;

import edu.ptit.openlab.entity.Product;
import edu.ptit.openlab.payload.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    BaseResponse getProduct(Long id);

    BaseResponse getAllProducts();

    BaseResponse getProductPaginated(int page, int size);

    BaseResponse searchListProduct(String search);

    BaseResponse save(Product product);

    BaseResponse updateProduct(Long id, Product product);

    BaseResponse deleteProduct(Long id);
}

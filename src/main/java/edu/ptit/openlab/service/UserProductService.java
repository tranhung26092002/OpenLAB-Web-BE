package edu.ptit.openlab.service;

import edu.ptit.openlab.payload.response.BaseResponse;

public interface UserProductService {
    BaseResponse getAllProduct(Long userId);
    BaseResponse registerProduct(Long userId, Long productId);

    BaseResponse registerProductBySubId(Long userId, String subId);

//    BaseResponse updateProduct(Long userId, Long productId);

    BaseResponse deleteProduct(Long userId, Long productId);
}

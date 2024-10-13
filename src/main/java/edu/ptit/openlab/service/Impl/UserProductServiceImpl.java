package edu.ptit.openlab.service.Impl;

import edu.ptit.openlab.DTO.ProductResponseDTO;
import edu.ptit.openlab.entity.Product;
import edu.ptit.openlab.entity.User;
import edu.ptit.openlab.mapper.ProductMapper;
import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.repository.ProductRepository;
import edu.ptit.openlab.repository.UserRepository;
import edu.ptit.openlab.service.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public BaseResponse getAllProduct(Long userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return new BaseResponse(404, "User not found", null);
            }

            List<Product> userProducts = user.getProducts();

            if (userProducts.isEmpty()) {
                return new BaseResponse(200, "Products not found", null);
            }

            List<ProductResponseDTO> userProductsDTO = userProducts.stream()
                    .map(productMapper::productToProductDTO)
                    .collect(Collectors.toList());

            return new BaseResponse(200, "Product registered successfully", userProductsDTO);
        } catch (Exception e) {
            return new BaseResponse(500, "Error registering Product to user", null);
        }
    }

    @Override
    public BaseResponse registerProduct(Long userId, Long productId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            Product product = productRepository.findById(productId).orElse(null);
            if (user == null) {
                return new BaseResponse(404, "User not found", null);
            }
            if (product == null) {
                return new BaseResponse(404, "Product not found", null);
            }

            List<Product> userProducts = user.getProducts();
            boolean productExists = userProducts.stream().anyMatch(c -> c.getId().equals(productId));

            if (productExists) {
                return new BaseResponse(400, "The Product already exists in the user's Product list", null);
            }

            user.registerProduct(product);
            userRepository.save(user);

            return new BaseResponse(200, "Product registered successfully", user);
        } catch (Exception e) {
            return new BaseResponse(500, "Error registering Product to user", null);
        }
    }

    @Override
    public BaseResponse registerProductBySubId(Long userId, String subId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            Product product = productRepository.findBySubId(subId).orElse(null);
            if (user == null) {
                return new BaseResponse(404, "User not found", null);
            }
            if (product == null) {
                return new BaseResponse(404, "Product not found", null);
            }

            List<Product> userProducts = user.getProducts();
            boolean productExists = userProducts.stream().anyMatch(c -> c.getId().equals(product.getId()));

            if (productExists) {
                return new BaseResponse(400, "The Product already exists in the user's Product list", null);
            }

            user.registerProduct(product);
            userRepository.save(user);

            return new BaseResponse(200, "Product registered successfully", user);
        } catch (Exception e) {
            return new BaseResponse(500, "Error registering Product to user", null);
        }
    }

//    @Override
//    public BaseResponse updateProduct(Long userId, Long productId) {
//        try {
//            User user = userRepository.findById(userId).orElse(null);
//
//            if (user == null) {
//                return new BaseResponse(404, "User not found", null);
//            }
//
//            Product product = user.getProducts().stream()
//                    .filter(c -> c.getId().equals(productId))
//                    .findFirst()
//                    .orElse(null);
//
//            if (product == null) {
//                return new BaseResponse(404, "Product not found in user's Products", null);
//            }
//
//            productRepository.save(product);
//
//            return new BaseResponse(200, "Product updated successfully", product);
//        } catch (Exception e) {
//            return new BaseResponse(500, "Error updating Product", null);
//        }
//    }

    @Override
    public BaseResponse deleteProduct(Long userId, Long productId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return new BaseResponse(404, "User not found", null);
            }

            Product product = user.getProducts().stream()
                    .filter(c -> c.getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (product == null) {
                return new BaseResponse(404, "Product not found in user's Products", null);
            }

            user.getProducts().remove(product);
            userRepository.save(user);

            return new BaseResponse(200, "Product deleted successfully", null);
        } catch (Exception e) {
            return new BaseResponse(500, "Error deleting Product", null);
        }
    }
}

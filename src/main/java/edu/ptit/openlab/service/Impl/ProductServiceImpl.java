package edu.ptit.openlab.service.Impl;

import edu.ptit.openlab.DTO.ProductResponseDTO;
import edu.ptit.openlab.entity.Product;
import edu.ptit.openlab.mapper.ProductMapper;
import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.repository.ProductRepository;
import edu.ptit.openlab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    
    @Override
    @Transactional
    public BaseResponse save(Product product) {
        try {
            product = productRepository.save(product);
            return new BaseResponse(200, "Saved successfully", product);
        } catch (Exception e) {
            return new BaseResponse(400, "Failed save", null);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponse getProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return new BaseResponse(200, "Retrieved successfully", optionalProduct.get());
        } else {
            return new BaseResponse(404, "Product not found", null);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponse getProductPaginated(int page, int size) {
        try {
            Page<Product> products = productRepository.findAll(PageRequest.of(page, size));
            return new BaseResponse(200, "Retrieved successfully", products);
        } catch (Exception e) {
            return new BaseResponse(500, "Error retrieving Product", null);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponse getAllProducts() {
        try {
            List<Product> productList = productRepository.findAll();

            List<ProductResponseDTO> productDTOList = productList.stream()
                    .map(productMapper::productToProductDTO)
                    .collect(Collectors.toList());

            return new BaseResponse(200, "Retrieved successfully", productDTOList);
        } catch (Exception e) {
            return new BaseResponse(500, "Error retrieving all Product", null);
        }
    }

    @Override
    @Transactional
    public BaseResponse searchListProduct(String search) {
        try {
            List<Product> productList = productRepository.searchProduct(search);
            if (productList.isEmpty()) {
                return new BaseResponse(200, "No results found for the search query", null);
            } else {
                return new BaseResponse(200, "Search results retrieved successfully", productList);
            }
        } catch (Exception e) {
            return new BaseResponse(500, "Error processing search", null);
        }
    }

    @Override
    @Transactional
    public BaseResponse updateProduct(Long id, Product updateProduct) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                product.setNameProduct(updateProduct.getNameProduct());
                product.setThumbnail(updateProduct.getThumbnail());
                product.setCreatedBy(updateProduct.getCreatedBy());
                product.setTypeProduct(updateProduct.getTypeProduct());
                product.setDescription(updateProduct.getDescription());

                Product updatedProduct = productRepository.save(product);

                return new BaseResponse(200, "Product updated successfully", updatedProduct);
            } else {
                return new BaseResponse(404, "Product not found", null);
            }
        } catch (Exception e) {
            return new BaseResponse(500, "Error update Product", null);
        }
    }

    @Override
    @Transactional
    public BaseResponse deleteProduct(Long id) {
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteUserProductsByProductId(id);
                productRepository.deleteById(id);

                return new BaseResponse(200, "Product deleted successfully", null);
            } else {
                return new BaseResponse(404, "Product not found", null);
            }
        } catch (Exception e) {
            return new BaseResponse(500, "Error delete Product", null);
        }
    }

}

package vn.com.openlab.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.openlab.component.TranslateMessages;
import vn.com.openlab.dto.ProductDTO;
import vn.com.openlab.dto.ProductImageDTO;
import vn.com.openlab.helper.base.response.ApiResponse;
import vn.com.openlab.helper.exception.payload.DataNotFoundException;
import vn.com.openlab.helper.exception.payload.InvalidParamException;
import vn.com.openlab.mapper.ProductMapper;
import vn.com.openlab.model.*;
import vn.com.openlab.repository.CategoryRepository;
import vn.com.openlab.repository.ProductImageRepository;
import vn.com.openlab.repository.ProductRepository;
import vn.com.openlab.repository.UserRepository;
import vn.com.openlab.response.product.ProductResponse;
import vn.com.openlab.service.ProductService;
import vn.com.openlab.utils.object.MessageKeys;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends TranslateMessages implements ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductMapper productMapper;
//    private final ProductSearchRepository productSearchRepository;

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existsCategory = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                translate(MessageKeys.CATEGORY_NOT_FOUND, productDTO.getCategoryId())
                        )
                );
        Product savedProduct = productMapper.toProduct(productDTO);
        savedProduct.setCategory(existsCategory);
        // Generate and set the subId
        String uniqueId = "OK-" + UUID.randomUUID().toString();
        savedProduct.setIsPublish(true);
        savedProduct.setSubId(uniqueId);

        return productRepository.save(savedProduct);
    }

    @Override
    public Product getProductById(Long productId) throws DataNotFoundException {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.PRODUCT_NOT_FOUND, productId)));
    }

    @Override
    public Page<ProductResponse> getAllProducts(String keyword,
                                                Long categoryId,
                                                PageRequest pageRequest,
                                                String sortField,
                                                String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                direction, sortField
        );
        Page<Product> productPage = productRepository.searchProducts(keyword, categoryId, pageable);
        return productPage.map(ProductResponse::fromProduct);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        Product existsProduct = getProductById(id);
        if (existsProduct != null) {
            // copy các thuộc tính từ DTO -> Product
            Category existsCategory = categoryRepository
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new DataNotFoundException(
                            translate(MessageKeys.CATEGORY_NOT_FOUND, productDTO.getCategoryId())
                    ));

            existsProduct.setName(productDTO.getName());
            existsProduct.setCategory(existsCategory);
            existsProduct.setPrice(productDTO.getPrice());
            existsProduct.setDescription(productDTO.getDescription());
            existsProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existsProduct);
        }

        return null;
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        // tìm ra product
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsProduct(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId,
                                           ProductImageDTO productImageDTO) throws DataNotFoundException{
        Product existsProduct = productRepository
                .findById(productId)
                .orElseThrow(() -> new DataNotFoundException(
                        translate(MessageKeys.CATEGORY_NOT_FOUND, productId))
                );

        ProductImage productImage = ProductImage.builder()
                .product(existsProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

        // không cho insert quá 5 ảnh cho một sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of images lest " +
                    ProductImage.MAXIMUM_IMAGES_PER_PRODUCT + " reached");
        }

        return productImageRepository.save(productImage);
    }

    @Override
    public Product getDetailProducts(long productId) throws DataNotFoundException {
        Optional<Product> optionalProduct = productRepository.getDetailProducts(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException(translate(MessageKeys.PRODUCT_NOT_FOUND, productId));
    }

    @Override
    public List<Product> findProductsByIds(List<Long> productIds) {
        return productRepository.findProductByIds(productIds);
    }

    @Override
    public Page<Product> findAllMyProducts(Long userId, String keyword, Pageable pageable) {
        return productRepository.fillAll(userId, keyword, pageable);
    }

    @Override
    public ProductResponse addProductToUser(Long userId, Long productId) throws DataNotFoundException{
        // Tìm người dùng dựa trên userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, userId)));

       // Tìm sản phẩm dựa trên productId
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, productId)));

        // Thêm sản phẩm vào danh sách sản phẩm của người dùng
        user.getProducts().add(product);

        // Lưu người dùng sau khi cập nhật
        userRepository.save(user);

        // Trả về sản phẩm đã thêm
        return ProductResponse.fromProduct(product); // Gọi phương thức từ đối tượng ProductResponse
    }

    @Override
    public ProductResponse addProductToUserBySubId(Long userId, String subId) throws DataNotFoundException{
        // Tìm người dùng dựa trên userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, userId)));

        // Tìm sản phẩm dựa trên productId
        Product product = productRepository.findBySubId(subId)
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, subId)));

        // Thêm sản phẩm vào danh sách sản phẩm của người dùng
        user.getProducts().add(product);

        // Lưu người dùng sau khi cập nhật
        userRepository.save(user);

        // Trả về sản phẩm đã thêm
        return ProductResponse.fromProduct(product); // Gọi phương thức từ đối tượng ProductResponse
    }

    @Override
    public void removeProductFromUser(Long userId, Long productId) throws DataNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, userId)));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(translate(MessageKeys.NOT_FOUND, productId)));

        // Loại bỏ sản phẩm khỏi danh sách sản phẩm của người dùng
        user.getProducts().remove(product);

        // Lưu lại thay đổi của người dùng
        userRepository.save(user);
    }


//    @Override
//    public Page<ProductResponse> searchProducts(String keyword, PageRequest pageRequest) {
//        Pageable pageable = PageRequest.of(
//                pageRequest.getPageNumber(),
//                pageRequest.getPageSize(),
//                Sort.by(Sort.Direction.ASC, "name")
//        );
//        Page<Product> productPage = productSearchRepository.findByNameContainingOrDescriptionContaining(keyword, keyword, pageable);
//        return productPage.map(ProductResponse::fromProduct);
//    }

}


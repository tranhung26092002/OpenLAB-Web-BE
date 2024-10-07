package vn.com.openlab.api.product.mapper;

import org.mapstruct.Mapper;
import vn.com.openlab.api.product.dto.ProductDTO;
import vn.com.openlab.api.product.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductDTO productDTO);
}


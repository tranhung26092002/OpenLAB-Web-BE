package vn.com.openlab.mapper;

import org.mapstruct.Mapper;
import vn.com.openlab.dto.ProductDTO;
import vn.com.openlab.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductDTO productDTO);
}


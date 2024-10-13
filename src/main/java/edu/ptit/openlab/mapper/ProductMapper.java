package edu.ptit.openlab.mapper;

import edu.ptit.openlab.DTO.ProductResponseDTO;
import edu.ptit.openlab.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO productToProductDTO(Product product);
}

package edu.ptit.openlab.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String thumbnail;
    private String nameProduct;
    private String description;
    private String typeProduct;
}

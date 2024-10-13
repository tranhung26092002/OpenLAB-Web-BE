package edu.ptit.openlab.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String nameProduct;
    private String thumbnail;
    private String createdBy;
    private String typeProduct;
    private Date createdAt;
    private Date updatedAt;
}

package vn.com.openlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import vn.com.openlab.utils.object.MessageKeys;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1, message = MessageKeys.PRODUCT_ID_REQUIRED)
    private Long productId;

    @Size(min = 5, max = 300, message = MessageKeys.IMAGE_SIZE_REQUIRED)
    @JsonProperty("image_url")
    private String imageUrl;
}


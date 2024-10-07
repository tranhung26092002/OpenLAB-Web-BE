package vn.com.openlab.api.category.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import vn.com.openlab.utils.object.MessageKeys;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    @NotEmpty(message = MessageKeys.CATEGORIES_NAME_REQUIRED)
    private String name;
}

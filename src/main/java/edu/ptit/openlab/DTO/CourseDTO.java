package edu.ptit.openlab.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private String nameCourse;
    private MultipartFile thumbnail;
    private String typeCourse;
    private String description;
    private Double originalPrice;
}

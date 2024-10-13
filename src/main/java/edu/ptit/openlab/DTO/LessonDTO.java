package edu.ptit.openlab.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonDTO {
    private String nameLesson;
    private MultipartFile thumbnail;
    private MultipartFile videoUrl;
    private String documentUrl;
    private String description;
}

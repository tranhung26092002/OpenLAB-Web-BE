package edu.ptit.openlab.controller.admin;

import edu.ptit.openlab.entity.Course;
import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.service.CourseService;
import edu.ptit.openlab.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/course")
// @PreAuthorize("hasRole('ROLE_ADMIN')")
public class CourseControllerAdmin {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StorageService storageService;

    @Value("${app.base-upload-image-url}")
    private String BASE_UPLOAD_IMAGE_URL;

    @PostMapping("/create")
    public ResponseEntity<?> createCourse(
            @RequestParam("subId") String subId,
            @RequestParam("title") String title,
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @RequestParam("createdBy") String createdBy,
            @RequestParam("typeProduct") String typeProduct,
            @RequestParam("isPublish") boolean isPublish,
            @RequestParam("description") String description,
            @RequestParam("originalPrice") String originalPrice) {
        try {
            // Lưu file vào một thư mục cụ thể
            String fileName = storageService.uploadImageToFileSystem(thumbnail);

            Course course = new Course();

            course.setSubId(subId);
            course.setTitle(title);
            course.setCreatedBy(createdBy);
            course.setTypeProduct(typeProduct);
            course.setIsPublish(isPublish);
            course.setThumbnail(fileName);
            course.setDescription(description);
            course.setOriginalPrice(Double.parseDouble(originalPrice));

            // Các giá trị mặc định
            course.setIsCompleted(false);
            course.setStartDate(null);

            BaseResponse response = courseService.save(course);
            if (response.getStatus() == 200) {
                return new ResponseEntity<>(response.getData(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Error from service: " + response.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {

            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long id,
            @RequestParam(value = "subId", required = false) Optional<String> subId,
            @RequestParam(value = "title", required = false) Optional<String> title,
            @RequestParam(value = "thumbnail", required = false) Optional<MultipartFile> thumbnail,
            @RequestParam(value = "createdBy", required = false) Optional<String> createdBy,
            @RequestParam(value = "typeProduct", required = false) Optional<String> typeProduct,
            @RequestParam(value = "isPublish", required = false) Optional<Boolean> isPublish,
            @RequestParam(value = "description", required = false) Optional<String> description,
            @RequestParam(value = "originalPrice", required = false) Optional<String> originalPrice) {

        BaseResponse fetchedKhoiCamBien = courseService.getCourse(id);
        if (fetchedKhoiCamBien.getStatus() != 200) {
            return new ResponseEntity<>(fetchedKhoiCamBien.getMessage(), HttpStatus.NOT_FOUND);
        }

        Course course = (Course) fetchedKhoiCamBien.getData();

        try {
            // Kiểm tra và cập nhật các thuộc tính nếu có trong yêu cầu
            subId.ifPresent(course::setSubId);
            title.ifPresent(course::setTitle);
            createdBy.ifPresent(course::setCreatedBy);
            typeProduct.ifPresent(course::setTypeProduct);
            isPublish.ifPresent(course::setIsPublish);
            description.ifPresent(course::setDescription);
            originalPrice.ifPresent(price -> course.setOriginalPrice(Double.parseDouble(price)));

            // Kiểm tra và xử lý file thumbnail nếu có
            if (thumbnail.isPresent()) {
                String fileName = storageService.uploadImageToFileSystem(thumbnail.get());
                course.setThumbnail(fileName);
            }

            // Cập nhật course
            BaseResponse updateResponse = courseService.updateCourse(id, course);
            return (updateResponse.getStatus() == 200)
                    ? new ResponseEntity<>(updateResponse.getData(), HttpStatus.OK)
                    : new ResponseEntity<>(updateResponse.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable long id) {
        BaseResponse deleteResponse = courseService.deleteCourse(id);
        if (deleteResponse.getStatus() == 200) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(deleteResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

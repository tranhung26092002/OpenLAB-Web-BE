package edu.ptit.openlab.controller.admin;

import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/lesson")
// @PreAuthorize("hasRole('ROLE_ADMIN')")
public class LessonControllerAdmin {
    private final LessonService lessonService;

    @PostMapping("/create/{courseId}")
    public ResponseEntity<BaseResponse> createLesson(
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @RequestParam("videoUrl") MultipartFile videoUrl,
            @RequestParam("nameLesson") String nameLesson,
            @RequestParam("document") String document,
            @RequestParam("description") String description,
            @PathVariable("courseId") Long courseId) {
        BaseResponse response = lessonService.createLesson(thumbnail, videoUrl, nameLesson, document, description, courseId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PutMapping("/update/{lessonId}")
    public ResponseEntity<BaseResponse> updateLesson(
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestParam(value = "videoUrl", required = false) MultipartFile videoUrl,
            @RequestParam(value = "nameLesson", required = false) String nameLesson,
            @RequestParam(value = "document", required = false) String document,
            @RequestParam(value = "description", required = false) String description,
            @PathVariable("lessonId") Long lessonId) {
        BaseResponse response = lessonService.updateLesson(thumbnail, videoUrl, nameLesson, document, description, lessonId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @DeleteMapping("/delete/{lessonId}")
    public ResponseEntity<BaseResponse> deleteLesson(@PathVariable Long lessonId) {
        BaseResponse response = lessonService.deleteLesson(lessonId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}

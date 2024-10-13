package edu.ptit.openlab.controller.user;

import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lesson")
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllLesson() {
        BaseResponse response = lessonService.getAllLesson();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @GetMapping("/all/{courseId}")
    public ResponseEntity<BaseResponse> getAllLessonOfCourse(@PathVariable Long courseId) {
        BaseResponse response = lessonService.getAllLessonOfCourse(courseId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}

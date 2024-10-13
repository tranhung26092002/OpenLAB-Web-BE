package edu.ptit.openlab.controller.user;

import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-course")
public class UserCourseController {
    private final UserCourseService userCourseService;

    @GetMapping("/all/{userId}")
    public ResponseEntity<BaseResponse> getAllCourse(@PathVariable Long userId) {
        BaseResponse response = userCourseService.getAllCourse(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/register/{userId}/{courseId}")
    public ResponseEntity<BaseResponse> registerCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        BaseResponse response = userCourseService.registerCourse(userId, courseId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/register-sub-id/{userId}/{subId}")
    public ResponseEntity<BaseResponse> registerCourseBySubId(@PathVariable Long userId, @PathVariable String subId) {
        BaseResponse response = userCourseService.registerCourseBySubId(userId, subId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

//    @PutMapping("/update/{userId}/{courseId}")
//    public ResponseEntity<BaseResponse> updateCourse(@PathVariable Long userId, @PathVariable Long courseId) {
//        BaseResponse response = userCourseService.updateCourse(userId, courseId);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
//    }

    @DeleteMapping("/delete/{userId}/{courseId}")
    public ResponseEntity<BaseResponse> deleteCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        BaseResponse response = userCourseService.deleteCourse(userId, courseId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}

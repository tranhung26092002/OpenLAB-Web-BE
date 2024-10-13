package edu.ptit.openlab.service;

import edu.ptit.openlab.payload.response.BaseResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface LessonService {

    BaseResponse getAllLesson();

    BaseResponse getAllLessonOfCourse(Long courseId);

    BaseResponse createLesson(MultipartFile thumbnail, MultipartFile videoUrl, String nameLesson, String document, String description, Long courseId);

    BaseResponse updateLesson(MultipartFile thumbnail, MultipartFile videoUrl, String nameLesson, String document, String description, Long lessonId);

    BaseResponse deleteLesson(Long lessonId);
}

package edu.ptit.openlab.service.Impl;

import edu.ptit.openlab.entity.Course;
import edu.ptit.openlab.entity.Lesson;
import edu.ptit.openlab.payload.response.BaseResponse;
import edu.ptit.openlab.repository.CourseRepository;
import edu.ptit.openlab.repository.LessonRepository;
import edu.ptit.openlab.service.LessonService;
import edu.ptit.openlab.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final StorageService storageService;

    private final CourseRepository courseRepository;

    private final LessonRepository lessonRepository;

    @Override
    public BaseResponse getAllLesson() {
        try {
            List<Lesson> lessons = lessonRepository.findAll();
            return new BaseResponse(200, "Success", lessons);
        } catch (Exception e) {
            return new BaseResponse(500, "Error retrieving lessons", null);
        }
    }

    @Override
    public BaseResponse getAllLessonOfCourse(Long courseId) {
        try {
            List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
            return new BaseResponse(200, "Success", lessons);
        } catch (Exception e) {
            return new BaseResponse(500, "Error retrieving lessons for course", null);
        }
    }

    @Override
    @Transactional
    public BaseResponse createLesson(MultipartFile thumbnail, MultipartFile videoUrl, String nameLesson, String document, String description,
            Long courseId) {
        if (!storageService.isVideoFileWithTika(videoUrl)) {
            return new BaseResponse(400, "File is not a valid video type", null);
        }

        String savedFilePath = storageService.saveFile(videoUrl);
        String fileName = storageService.uploadImageToFileSystem(thumbnail);

        try {
            Course course = courseRepository.findById(courseId).orElse(null);

            if (course == null) {
                return new BaseResponse(404, "Course not found", null);
            }

            Lesson lesson = new Lesson();
            lesson.setNameLesson(nameLesson);
            lesson.setThumbnail(fileName);
            lesson.setVideoUrl(savedFilePath);
            lesson.setDocumentUrl(document);
            lesson.setDescription(description);
            lesson.setCourse(course);

            lessonRepository.save(lesson);

            return new BaseResponse(200, "Lesson created successfully", lesson);
        } catch (Exception e) {
            return new BaseResponse(500, "Error creating lesson", null);
        }
    }

    @Override
    @Transactional
    public BaseResponse updateLesson(MultipartFile thumbnail, MultipartFile videoUrl, String nameLesson, String document, String description, Long lessonId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
            if (lesson == null) {
                return new BaseResponse(404, "Lesson not found", null);
            }

            // Kiểm tra và cập nhật tên bài học
            if (nameLesson != null && !nameLesson.isEmpty()) {
                lesson.setNameLesson(nameLesson);
            }

            // Kiểm tra và cập nhật thumbnail nếu có file mới
            if (thumbnail != null && !thumbnail.isEmpty()) {
                String fileName = storageService.uploadImageToFileSystem(thumbnail);
                lesson.setThumbnail(fileName);
            }

            // Kiểm tra và cập nhật tài liệu nếu có
            if (document != null && !document.isEmpty()) {
                lesson.setDocumentUrl(document);
            }

            // Kiểm tra và cập nhật mô tả nếu có
            if (description != null && !description.isEmpty()) {
                lesson.setDescription(description);
            }

            // Kiểm tra và cập nhật video nếu có file mới
            if (videoUrl != null && !videoUrl.isEmpty()) {
                if (!storageService.isVideoFileWithTika(videoUrl)) {
                    return new BaseResponse(400, "File is not a valid video type", null);
                }
                String savedFilePath = storageService.saveFile(videoUrl);
                lesson.setVideoUrl(savedFilePath);
            }

            lessonRepository.save(lesson);
            return new BaseResponse(200, "Lesson updated successfully", lesson);
        } catch (Exception e) {
            return new BaseResponse(500, "Error updating lesson: " + e.getMessage(), null);
        }
    }


    @Override
    @Transactional
    public BaseResponse deleteLesson(Long lessonId) {
        try {
            Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
            if (optionalLesson.isPresent()) {
                lessonRepository.deleteById(lessonId);
                return new BaseResponse(200, "Lesson deleted successfully", null);
            } else {
                return new BaseResponse(404, "Lesson not found", null);
            }
        } catch (Exception e) {
            return new BaseResponse(500, "Error deleting lesson", null);
        }
    }
}

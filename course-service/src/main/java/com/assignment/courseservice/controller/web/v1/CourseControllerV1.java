package com.assignment.courseservice.controller.web.v1;

import com.assignment.courseservice.exception.InvalidCourseOrSeatNotAvailableException;
import com.assignment.courseservice.exception.NoCourseAvailableException;
import com.assignment.courseservice.model.Course;
import com.assignment.courseservice.model.StudentCourse;
import com.assignment.courseservice.service.CourseService;
import com.assignment.courseservice.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/courses")
@AllArgsConstructor
public class CourseControllerV1 {

    private final CourseService courseService;
    private final StudentService studentService;

    @GetMapping("/{username}")
    public List<Course> getAllCourse(@PathVariable("username") String username) {
        if (courseService.findAllNotRegisteredCourses(username).isEmpty())
            throw new NoCourseAvailableException();
        else return courseService.findAllNotRegisteredCourses(username);

    }

    @PostMapping("/register")
    public StudentCourse register(@RequestBody StudentCourse studentCourse) {
        Optional<StudentCourse> studentCourseDetails = courseService
                .findStudentCourse(studentCourse.getCourseId(), studentCourse.getUsername());
        return courseService.findCourse(studentCourse.getCourseId())
                .filter(course -> course.getAvailableSeats() > 0 && !course.getExpired() && !studentCourseDetails.isPresent())
                .map(course -> courseService.register(studentCourse.getCourseId(), studentCourse.getUsername()))
                .orElseThrow(InvalidCourseOrSeatNotAvailableException::new);
    }
}
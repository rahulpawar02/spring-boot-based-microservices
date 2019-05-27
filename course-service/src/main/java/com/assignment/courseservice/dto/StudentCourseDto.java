package com.assignment.courseservice.dto;

import com.assignment.courseservice.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class StudentCourseDto {
    private final String courseId;
    private final String courseName;
    private final String description;
    private final Status status;
}

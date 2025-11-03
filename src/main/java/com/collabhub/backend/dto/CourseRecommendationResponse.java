package com.collabhub.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRecommendationResponse {
    private UUID courseId;
    private String title;
    private String description;
    private String skillName;
    private double relevanceScore; // optional: useful if recommending based on user skills
}

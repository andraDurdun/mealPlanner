package com.example.mealPlanner.dto;

import com.example.mealPlanner.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {
    //todo validation
    private Long id;
    private String name;
    private LocalDateTime ateAt;
    private Integer calories;
    private User user;
}
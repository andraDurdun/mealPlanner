package com.example.mealPlanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {
    //todo validation
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private Integer calories;
    private Long userId;
}

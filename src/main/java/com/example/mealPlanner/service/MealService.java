package com.example.mealPlanner.service;

import com.example.mealPlanner.dto.MealDto;
import com.example.mealPlanner.dto.MealFiltersDto;
import com.example.mealPlanner.dto.PageRequestDto;
import com.example.mealPlanner.dto.PageResponseDto;

public interface MealService {
    MealDto createMeal(MealDto mealDto);

    MealDto updateMeal(MealDto mealDto);

    void deleteMeal(Long id);

    MealDto getMeal(Long id);

    PageResponseDto<MealDto> getMeals(PageRequestDto pageRequestDto, MealFiltersDto mealFiltersDto);
}

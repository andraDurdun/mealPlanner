package com.example.mealPlanner.controller;

import com.example.mealPlanner.dto.MealDto;
import com.example.mealPlanner.dto.PageRequestDto;
import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping("/{id}")
    public MealDto getMeal(@PathVariable Long id) {
        return mealService.getMeal(id);
    }

    @GetMapping
    public PageResponseDto<MealDto> getMeals(@ModelAttribute @Valid PageRequestDto pageRequestDto) {
        return mealService.getMeals(pageRequestDto);
    }

    @PostMapping
    public MealDto createMeal(@RequestBody MealDto mealDto) {
        return mealService.createMeal(mealDto);
    }

    @PutMapping("/{id}")
    public MealDto updateMeal(@PathVariable Long id, @RequestBody MealDto mealDto) {
        mealDto.setId(id);
        return mealService.updateMeal(mealDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
    }
}

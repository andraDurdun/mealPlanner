package com.example.mealPlanner.controller;

import com.example.mealPlanner.dto.MealDto;
import com.example.mealPlanner.dto.MealFiltersDto;
import com.example.mealPlanner.dto.PageRequestDto;
import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public PageResponseDto<MealDto> getMeals(@ModelAttribute @Valid PageRequestDto pageRequestDto, @ModelAttribute MealFiltersDto mealFiltersDto) {
        return mealService.getMeals(pageRequestDto, mealFiltersDto);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or principal.id == #mealDto.userId")
    public MealDto createMeal(@RequestBody MealDto mealDto) {
        //todo check why there are 2 queries done for user table
        return mealService.createMeal(mealDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or principal.id == #mealDto.userId")
    public MealDto updateMeal(@PathVariable Long id, @RequestBody MealDto mealDto) {
        mealDto.setId(id);
        return mealService.updateMeal(mealDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
    }
}

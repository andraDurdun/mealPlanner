package com.example.mealPlanner.mapper;

import com.example.mealPlanner.dto.MealDto;
import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.entity.Meal;
import com.example.mealPlanner.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealMapper {

    public Meal toEntity(MealDto mealDto, User user) {
        return Meal.builder()
                .name(mealDto.getName())
                .calories(mealDto.getCalories())
                .date(mealDto.getDate())
                .time(mealDto.getTime())
                .user(user)
                .build();
    }

    public MealDto toDto(Meal meal) {
        return MealDto.builder()
                .id(meal.getId())
                .name(meal.getName())
                .calories(meal.getCalories())
                .date(meal.getDate())
                .time(meal.getTime())
                .build();
    }

    public PageResponseDto<MealDto> toDto(Page<Meal> meals) {
        List<MealDto> mealDtos = meals.getContent().stream()
                .map(this::toDto)
                .toList();
        return new PageResponseDto<>(mealDtos, meals.getNumber(), meals.getNumberOfElements(), meals.getTotalElements());
    }
}

package com.example.mealPlanner.mapper;

import com.example.mealPlanner.dto.MealDto;
import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.entity.Meal;
import com.example.mealPlanner.entity.User;
import com.example.mealPlanner.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MealMapper {

    private final AuthenticationService authenticationService;

    public Meal toEntity(MealDto mealDto) {
        User authenticatedUser = authenticationService.getAuthenticatedUser();

        return Meal.builder()
                .name(mealDto.getName())
                .calories(mealDto.getCalories())
                .ateAt(mealDto.getAteAt())
                .user(authenticatedUser)
                .build();
    }

    public MealDto toDto(Meal meal) {
        return MealDto.builder()
                .id(meal.getId())
                .name(meal.getName())
                .calories(meal.getCalories())
                .ateAt(meal.getAteAt())
                .build();
    }

    public PageResponseDto<MealDto> toDto(Page<Meal> meals) {
        List<MealDto> mealsDtos = meals.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageResponseDto<>(mealsDtos, meals.getNumber(), meals.getNumberOfElements(), meals.getTotalElements());
    }
}

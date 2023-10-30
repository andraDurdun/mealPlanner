package com.example.mealPlanner.service.impl;

import com.example.mealPlanner.dto.MealDto;
import com.example.mealPlanner.dto.PageRequestDto;
import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.entity.Meal;
import com.example.mealPlanner.entity.User;
import com.example.mealPlanner.exception.ResourceNotFoundException;
import com.example.mealPlanner.mapper.MealMapper;
import com.example.mealPlanner.repository.MealRepository;
import com.example.mealPlanner.service.AuthenticationService;
import com.example.mealPlanner.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final AuthenticationService authenticationService;
    private final MealMapper mealMapper;

    @Override
    public MealDto getMealByUser(Long id) {
        User user = authenticationService.getAuthenticatedUser();
        Meal meal = getMealByUser(id, user);
        return mealMapper.toDto(meal);
    }

    @Override
    public PageResponseDto<MealDto> getMealsByUser(PageRequestDto pageRequestDto) {
        User user = authenticationService.getAuthenticatedUser();
        Page<Meal> meals = mealRepository.findAllByUser(user, PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()));
        return mealMapper.toDto(meals);
    }

    @Override
    public MealDto createMeal(MealDto mealDto) {
        Meal meal = mealMapper.toEntity(mealDto);
        mealRepository.save(meal);
        return mealMapper.toDto(meal);
    }

    @Override
    public MealDto updateMeal(MealDto mealDto) {
        User user = authenticationService.getAuthenticatedUser();
        Meal meal = getMealByUser(mealDto.getId(), user);
        meal.setName(mealDto.getName());
        meal.setCalories(mealDto.getCalories());
        meal.setAteAt(mealDto.getAteAt());

        return mealMapper.toDto(meal);
    }

    @Override
    public void deleteMeal(Long id) {
        User user = authenticationService.getAuthenticatedUser();
        int deletedMeals = mealRepository.deleteByIdAndUser(id, user);

        if (deletedMeals == 0) {
            throw new ResourceNotFoundException("Meal with id: " + id + " was not deleted for user with id: " + user.getId());
        }
    }

    private Meal getMealByUser(Long id, User user) {
        return mealRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find meal with id: " + id + " for user with id: " + user.getId()));
    }

}

package com.example.mealPlanner.service.impl;

import com.example.mealPlanner.dto.MealDto;
import com.example.mealPlanner.dto.MealFiltersDto;
import com.example.mealPlanner.dto.PageRequestDto;
import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.entity.Meal;
import com.example.mealPlanner.entity.Role;
import com.example.mealPlanner.entity.User;
import com.example.mealPlanner.exception.ResourceNotFoundException;
import com.example.mealPlanner.mapper.MealMapper;
import com.example.mealPlanner.repository.MealRepository;
import com.example.mealPlanner.service.AuthenticationService;
import com.example.mealPlanner.service.MealService;
import com.example.mealPlanner.service.UserService;
import com.example.mealPlanner.specification.MealSpecification;
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
    private final UserService userService;
    private final MealMapper mealMapper;

    @Override
    public MealDto getMeal(Long id) {
        User user = authenticationService.getAuthenticatedUser();
        Meal meal = getMealByUser(id, user);
        return mealMapper.toDto(meal);
    }

    @Override
    public PageResponseDto<MealDto> getMeals(PageRequestDto pageRequestDto, MealFiltersDto mealFiltersDto) {
        User user = authenticationService.getAuthenticatedUser();
        MealSpecification mealSpecification = new MealSpecification(mealFiltersDto);
        Page<Meal> meals = getMealsByUser(pageRequestDto, mealSpecification, user);
        return mealMapper.toDto(meals);
    }

    @Override
    public MealDto createMeal(MealDto mealDto) {
        User user = userService.getUserById(mealDto.getUserId());
        Meal meal = mealMapper.toEntity(mealDto, user);
        mealRepository.save(meal);
        return mealMapper.toDto(meal);
    }

    @Override
    public MealDto updateMeal(MealDto mealDto) {
        User user = userService.getUserById(mealDto.getUserId());
        Meal meal = getMealByUser(mealDto.getId(), user);
        meal.setName(mealDto.getName());
        meal.setCalories(mealDto.getCalories());
        meal.setDate(mealDto.getDate());
        meal.setTime(mealDto.getTime());

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
        if (Role.ADMIN.equals(user.getRole())) {
            return mealRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot find meal with id: " + id));
        }
        return mealRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find meal with id: " + id + " for user with id: " + user.getId()));
    }

    private Page<Meal> getMealsByUser(PageRequestDto pageRequestDto, MealSpecification mealSpecification, User user) {
        if (Role.USER.equals(user.getRole())) {
            mealSpecification.setUserId(user.getId());
        }

        return mealRepository.findAll(mealSpecification, PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()));
    }

}

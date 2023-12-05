package com.example.mealPlanner.specification;

import com.example.mealPlanner.dto.MealFiltersDto;
import com.example.mealPlanner.entity.Meal;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MealSpecification implements Specification<Meal> {

    private final MealFiltersDto mealFiltersDto;
    private Long userId;

    public MealSpecification(MealFiltersDto mealFiltersDto) {
        this.mealFiltersDto = mealFiltersDto;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<Meal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (mealFiltersDto.getDateFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), mealFiltersDto.getDateFrom()));
        }

        if (mealFiltersDto.getDateTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), mealFiltersDto.getDateTo()));
        }

        if (mealFiltersDto.getTimeFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("time"), mealFiltersDto.getTimeFrom()));
        }

        if (mealFiltersDto.getTimeTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("time"), mealFiltersDto.getTimeTo()));
        }

        if (userId != null) {
            predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

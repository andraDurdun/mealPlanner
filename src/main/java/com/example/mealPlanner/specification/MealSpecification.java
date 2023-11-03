package com.example.mealPlanner.specification;

import com.example.mealPlanner.dto.MealFiltersDto;
import com.example.mealPlanner.entity.Meal;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MealSpecification implements Specification<Meal> {

    private MealFiltersDto mealFiltersDto;

    @Override
    public Predicate toPredicate(Root<Meal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (mealFiltersDto.getDateFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), mealFiltersDto.getDateFrom()));
        }

        if (mealFiltersDto.getDateTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), mealFiltersDto.getDateFrom()));
        }

        if (mealFiltersDto.getTimeFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("time"), mealFiltersDto.getTimeFrom()));
        }

        if (mealFiltersDto.getTimeFrom() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("time"), mealFiltersDto.getTimeFrom()));
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

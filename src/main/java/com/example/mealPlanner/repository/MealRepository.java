package com.example.mealPlanner.repository;

import com.example.mealPlanner.entity.Meal;
import com.example.mealPlanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long>, JpaSpecificationExecutor<Meal> {

    Optional<Meal> findByIdAndUser(Long id, User user);

    @Modifying
    @Query("DELETE FROM Meal m where m.id = :id and m.user = :user")
    int deleteByIdAndUser(Long id, User user);
}

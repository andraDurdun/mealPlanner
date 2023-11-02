package com.example.mealPlanner.repository;

import com.example.mealPlanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByIdIsNotAndEmail(Long id, String email);

    @Modifying
    @Query("DELETE FROM User u where u.id = :id")
    int deleteUserById(Long id);

}

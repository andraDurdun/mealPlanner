package com.example.mealPlanner.dto;

import com.example.mealPlanner.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    //todo validation
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}

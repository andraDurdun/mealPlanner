package com.example.mealPlanner.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {
    @Min(0)
    @Builder.Default
    private int page = 0;

    @Positive
    @Max(200)
    @Builder.Default
    private int pageSize = 100;
}

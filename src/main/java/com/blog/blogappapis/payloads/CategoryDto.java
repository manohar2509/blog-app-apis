package com.blog.blogappapis.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
    
    private int categoryId;
    @NotBlank
    @Size(min=4, max = 50, message = "Category title must be between 4 and 50 characters")
    private String categoryTitle;
    @NotBlank
    @Size(min=4, max = 50, message = "Category description must be between 4 and 50 characters")
    private String categoryDescription;
}

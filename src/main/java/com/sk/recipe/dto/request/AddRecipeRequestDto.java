package com.sk.recipe.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.NotNull;
import com.sk.recipe.dto.RecipeIngredientDto;

import lombok.Data;

@Data
public class AddRecipeRequestDto {

	@NotNull(message = "recipe name cannot be null")
    @NotBlank(message = "recipe name cannot be blank")
    private String name ;

    private boolean vegan ;

    @NotNull(message = "serving number cannot be null")
    @Positive(message = " serving number cannot be 0 or negative")
    private Long servingNumber ;

    @NotNull(message = "Please provide some instructions for the recipe")
    @NotBlank(message = "Please provide some instructions for the recipe")
    private String instruction;

    @NotNull(message = "ingredients cannot have a null value")
    @NotEmpty(message = "ingredients cannot be empty")
    @Valid
    private List<RecipeIngredientDto> ingredients;
}

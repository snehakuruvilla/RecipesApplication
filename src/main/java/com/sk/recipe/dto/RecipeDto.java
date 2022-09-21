package com.sk.recipe.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
public class RecipeDto {

		@NotNull
	    @Positive
	    private Long id ;

	    @NotNull(message = "recipe name cannot be null")
	    @NotBlank(message = "recipe name cannot be blank")
	    private String name ;

	    private boolean vegan;

	    @NotNull(message = "serving number cannot be null")
	    @Positive(message = " serving number cannot be 0 or negative")
	    private Long servingNumber;

	    @NotNull(message = "Please provide some instructions for the recipe")
	    @NotBlank(message = "Please provide some instructions for the recipe")
	    private String instruction ;

	    @NotEmpty
	    @NotNull
	    private List<RecipeIngredientDto> ingredients;
	}

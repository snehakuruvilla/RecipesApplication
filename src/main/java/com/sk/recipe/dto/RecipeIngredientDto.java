package com.sk.recipe.dto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeIngredientDto {

	private Long id;

	private RecipeDto recipe;

	@NotNull
	@Valid
	private IngredientsDto ingredient;

	@NotNull
	@Positive
	private Long quantity;
}

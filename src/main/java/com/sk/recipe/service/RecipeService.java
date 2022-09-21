package com.sk.recipe.service;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import com.sk.recipe.dto.RecipeDto;
import com.sk.recipe.dto.request.AddRecipeRequestDto;
import com.sk.recipe.dto.request.FilterSearchRecipeDto;

public interface RecipeService {
	
	/**
     * save recipe data
     * @param AddRecipeRequestDto
     * @return RecipeDto
     */
	RecipeDto addNewRecipe(AddRecipeRequestDto addRecipeRequestDto);
	
	RecipeDto updateRecipe(RecipeDto recipeDto);
	
	void deleteRecipe(Long id);

	Page<RecipeDto> filterRecipes(@NotNull FilterSearchRecipeDto filterSearchRecipeDto);	
	
}

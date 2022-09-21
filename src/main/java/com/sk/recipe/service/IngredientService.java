package com.sk.recipe.service;

import java.util.List;

import com.sk.recipe.dto.IngredientsDto;
import com.sk.recipe.dto.request.AddIngredientRequestDto;
import com.sk.recipe.exception.NoItemFoundException;

public interface IngredientService {

	IngredientsDto addNewIngredient(AddIngredientRequestDto addIngredientRequestDto);
	
	IngredientsDto updateIngredient(IngredientsDto  ingredient);

    void deleteIngredient(Long id) ;

    List<IngredientsDto> findIngredientByName(String name) throws NoItemFoundException;

    List<IngredientsDto> retrieveAllIngredient();
}

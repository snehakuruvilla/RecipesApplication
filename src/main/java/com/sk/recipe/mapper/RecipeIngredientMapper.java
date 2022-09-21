package com.sk.recipe.mapper;

import java.util.ArrayList;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sk.recipe.dto.RecipeDto;
import com.sk.recipe.dto.RecipeIngredientDto;
import com.sk.recipe.entity.JpaRecipe;
import com.sk.recipe.entity.JpaRecipeIngredient;
import com.sk.recipe.exception.ExceptionMessage;
import com.sk.recipe.exception.MapperNullObjectException;

@Component
public class RecipeIngredientMapper implements BaseMapper<RecipeIngredientDto , JpaRecipeIngredient> {

	private IngredientMapper ingredientMapper;
	
	public RecipeIngredientMapper(IngredientMapper ingredientMapper) {
        this.ingredientMapper = ingredientMapper;
    }
	
	@Override
	public JpaRecipeIngredient fromDtoToEntity(RecipeIngredientDto dto) {
		if(dto != null){
	          JpaRecipeIngredient entity = new JpaRecipeIngredient() ;
	          entity.setId(dto.getId());
	          entity.setIngredient(ingredientMapper.fromDtoToEntity(dto.getIngredient()));
	          if(dto.getRecipe() !=  null ){
	              JpaRecipe recipe = new JpaRecipe();
	              recipe.setId(dto.getRecipe().getId());
	              entity.setRecipe(recipe);
	          }

	          entity.setQuantity(dto.getQuantity());
	          return entity;
	      }else {
	          throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);

	      }
	}

	@Override
	public RecipeIngredientDto fromEntityToDto(JpaRecipeIngredient entity) {
		 if(entity != null){
			 RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto() ;
	           recipeIngredientDto.setId(entity.getId());
	           recipeIngredientDto.setIngredient(ingredientMapper.fromEntityToDto(entity.getIngredient()));
	           if(entity.getRecipe() != null){
	               RecipeDto recipeDto = new RecipeDto();
	               recipeDto.setId(entity.getRecipe().getId());
	               recipeDto.setVegan(entity.getRecipe().isVeg());
	               recipeDto.setInstruction(entity.getRecipe().getInstruction());
	               recipeDto.setServingNumber(entity.getRecipe().getServingNumber());
	               recipeDto.setName(entity.getRecipe().getName());
	               recipeIngredientDto.setRecipe(recipeDto);
	           }

	           recipeIngredientDto.setQuantity(entity.getQuantity());
	           return recipeIngredientDto;
	       }else{
	           throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
	       }
	}

	@Override
	public List<JpaRecipeIngredient> fromDtoListToEntityList(List<RecipeIngredientDto> dtoList) {
		 if(dtoList != null){
	           List<JpaRecipeIngredient> result = new ArrayList<>();
	           dtoList.forEach(item -> result.add(fromDtoToEntity(item)));
	           return result;
	       }else{
	           throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
	       }
	}

	@Override
	public List<RecipeIngredientDto> fromEntityListToDtoList(List<JpaRecipeIngredient> entityList) {
		 if(entityList != null){
	           List<RecipeIngredientDto> result = new ArrayList<>();
	           entityList.forEach(item ->
	                   result.add(fromEntityToDto(item)));
	           return result;
	       }else {
	           throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
	       }
	}
	
	public Page<RecipeIngredientDto> mapPage(Page<JpaRecipeIngredient> entityPage) {
	       if(entityPage != null){
	           return entityPage.map(this::fromEntityToDto);
	       }else{
	           throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
	       }
	    }

}

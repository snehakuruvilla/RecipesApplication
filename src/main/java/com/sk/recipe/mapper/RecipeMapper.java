package com.sk.recipe.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sk.recipe.dto.RecipeDto;
import com.sk.recipe.entity.JpaRecipe;
import com.sk.recipe.exception.ExceptionMessage;
import com.sk.recipe.exception.MapperNullObjectException;

@Component
public class RecipeMapper implements BaseMapper<RecipeDto , JpaRecipe>{

	private RecipeIngredientMapper recipeIngredientMapper;

    public RecipeMapper(RecipeIngredientMapper recipeIngredientMapper) {
        this.recipeIngredientMapper = recipeIngredientMapper;
    }
    
	@Override
	public JpaRecipe fromDtoToEntity(RecipeDto dto) {
		if(dto != null) {
			JpaRecipe entity = new JpaRecipe();
			entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setVeg(dto.isVegan());
            entity.setInstruction(dto.getInstruction());
            entity.setServingNumber(dto.getServingNumber());
            entity.setIngredients(recipeIngredientMapper.fromDtoListToEntityList(dto.getIngredients()));     
            return entity;
		}else {
			throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
		}
	}

	@Override
	public RecipeDto fromEntityToDto(JpaRecipe entity) {
		 if(entity != null){
	            RecipeDto dto  = new RecipeDto();
	            dto.setId(entity.getId());
	            dto.setName(entity.getName());
	            dto.setVegan(entity.isVeg());
	            dto.setInstruction(entity.getInstruction());
	            dto.setServingNumber(entity.getServingNumber());
	            dto.setIngredients(recipeIngredientMapper.fromEntityListToDtoList(entity.getIngredients()));
	            return dto;
	        }else{
	            throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
	        }
	}

	@Override
	public List<JpaRecipe> fromDtoListToEntityList(List<RecipeDto> dtoList) {
		if(dtoList != null){
	           List<JpaRecipe> result = new ArrayList<>();
	           dtoList.forEach(item ->
	                   result.add(fromDtoToEntity(item)));
	           return result;
	       }else {
	           throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
	       }
	}

	@Override
	public List<RecipeDto> fromEntityListToDtoList(List<JpaRecipe> entityList) {
		if(entityList != null){
            List<RecipeDto> result = new ArrayList<>();
            entityList.forEach(item ->
                    result.add(fromEntityToDto(item)));
            return result;
        }else {
            throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);

        }
	}

	public Page<RecipeDto> mapPage(Page<JpaRecipe> entityPage) {
        if(entityPage != null){
            return entityPage.map(this::fromEntityToDto);
        }else {
            throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
        }
    }

}

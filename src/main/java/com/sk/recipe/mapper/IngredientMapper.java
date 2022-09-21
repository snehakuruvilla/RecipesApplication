package com.sk.recipe.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sk.recipe.dto.IngredientsDto;
import com.sk.recipe.entity.JpaIngredients;
import com.sk.recipe.exception.ExceptionMessage;
import com.sk.recipe.exception.MapperNullObjectException;


@Component
public class IngredientMapper implements BaseMapper<IngredientsDto , JpaIngredients> {

	@Override
	public JpaIngredients fromDtoToEntity(IngredientsDto dto) {
		 if (dto != null){
	           return new JpaIngredients(dto.getId() , dto.getName());
	       }else {
	           throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
	       }
	}

	@Override
	public IngredientsDto fromEntityToDto(JpaIngredients entity) {
		if( entity!= null){
            return new IngredientsDto(entity.getId() , entity.getName());
        }else {
            throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
        }
	}

	@Override
	public List<JpaIngredients> fromDtoListToEntityList(List<IngredientsDto> dtoList) {
		 List<JpaIngredients> result = new ArrayList<>();
	        if(dtoList != null){
	            dtoList.forEach(item ->
	                    result.add(fromDtoToEntity(item)));
	            return result;
	        }else{
	            throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
	        }
	}

	@Override
	public List<IngredientsDto> fromEntityListToDtoList(List<JpaIngredients> entityList) {
		List<IngredientsDto> result = new ArrayList<>();
        if(entityList != null){
            entityList.forEach(item ->
                    result.add(fromEntityToDto(item)));
            return result;
        }else {
            throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
        }

	}
	
	public Page<IngredientsDto> mapPage(Page<JpaIngredients> entityPage) {
        if (entityPage != null){
            return entityPage.map(this::fromEntityToDto);
        }else{
            throw new MapperNullObjectException(ExceptionMessage.CANNOT_MAP_NULL_MSG);
        }
    }

}

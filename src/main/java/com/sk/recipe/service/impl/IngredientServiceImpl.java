package com.sk.recipe.service.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.stereotype.Service;

import com.sk.recipe.dto.IngredientsDto;
import com.sk.recipe.dto.request.AddIngredientRequestDto;
import com.sk.recipe.dto.request.AddRecipeRequestDto;
import com.sk.recipe.entity.JpaIngredients;
import com.sk.recipe.entity.JpaRecipe;
import com.sk.recipe.exception.NoItemFoundException;
import com.sk.recipe.mapper.IngredientMapper;
import com.sk.recipe.repository.IngredientRepository;
import com.sk.recipe.service.IngredientService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService{

	private Validator validator;
	
	private IngredientMapper ingredientMapper;
	
	private IngredientRepository ingredientRepository;
	
	public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }
	
	@Override
	public IngredientsDto addNewIngredient(AddIngredientRequestDto addIngredientRequestDto) {
		log.debug("Starting adding new ingredient : {}", addIngredientRequestDto);
		Set<ConstraintViolation<AddIngredientRequestDto>> violations = validator.validate(addIngredientRequestDto);
		if(violations.isEmpty()) {
			JpaIngredients ingredient = new JpaIngredients();
			ingredient.setName(addIngredientRequestDto.getName());
            return ingredientMapper.fromEntityToDto(ingredientRepository.save(ingredient));
		}else {
            throw new ConstraintViolationException("Object does not accept constraints {} " , violations);
		}
	}

	@Override
	public IngredientsDto updateIngredient(IngredientsDto ingredient) {
		 log.debug("Starting updating ingredient {}",ingredient);
	        if (ingredient != null){
	           return ingredientMapper.fromEntityToDto(
	                   ingredientRepository.save(ingredientMapper.fromDtoToEntity(ingredient)));

	        }
	        throw new IllegalArgumentException("Ingredient null to update");
	}

	@Override
	public void deleteIngredient(Long id) {
		log.debug("Deleting ingredient {}", id);
        if(ingredientRepository.existsById(id)){
            ingredientRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Id of ingredient does not exist");
        }
	}

	@Override
	public List<IngredientsDto> findIngredientByName(String name) throws NoItemFoundException {
		log.debug("Search Ingredient by name :{}", name);
        List<JpaIngredients> ingredientList = ingredientRepository.findByNameContainsIgnoreCase(name);
        if(ingredientList.isEmpty()){
            throw new NoItemFoundException("No Ingredient Found");
        }
        return ingredientMapper.fromEntityListToDtoList(ingredientList);
	}

	@Override
	public List<IngredientsDto> retrieveAllIngredient() {
		return ingredientMapper.fromEntityListToDtoList(ingredientRepository.findAll());
	}

}

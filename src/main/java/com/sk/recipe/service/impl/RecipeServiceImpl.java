package com.sk.recipe.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sk.recipe.dto.RecipeDto;
import com.sk.recipe.dto.request.AddRecipeRequestDto;
import com.sk.recipe.dto.request.FilterSearchRecipeDto;
import com.sk.recipe.entity.JpaRecipe;
import com.sk.recipe.entity.JpaRecipeIngredient;
import com.sk.recipe.mapper.IngredientMapper;
import com.sk.recipe.mapper.RecipeIngredientMapper;
import com.sk.recipe.mapper.RecipeMapper;
import com.sk.recipe.repository.RecipeRepository;
import com.sk.recipe.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

	private Validator validator;

	private RecipeIngredientMapper recipeIngredientMapper;

	private RecipeMapper recipeMapper;
	
	private RecipeRepository recipeRepository;
	
	private IngredientMapper ingredientMapper;

	/**
     * paramterized constructor
     *
     * @param recipeRepository
     * @param recipeMapper
     * @param recipeIngredientMapper
     */
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper,
                             RecipeIngredientMapper recipeIngredientMapper , IngredientMapper ingredientMapper, Validator validator) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.recipeIngredientMapper = recipeIngredientMapper;
        this.ingredientMapper = ingredientMapper ;
        this.validator = validator;
    }
    
	@Override
	public RecipeDto addNewRecipe(AddRecipeRequestDto addRecipeRequestDto) {
		log.debug("Starting adding new recipe : {}", addRecipeRequestDto);
		Set<ConstraintViolation<AddRecipeRequestDto>> violations = validator.validate(addRecipeRequestDto);
		if(violations.isEmpty()) {
			JpaRecipe recipe = new JpaRecipe();
			recipe.setName(addRecipeRequestDto.getName());
            recipe.setVeg (addRecipeRequestDto.isVegan());
            recipe.setServingNumber(addRecipeRequestDto.getServingNumber());
            recipe.setInstruction(addRecipeRequestDto.getInstruction());
            recipe.setIngredients(recipeIngredientMapper.fromDtoListToEntityList(addRecipeRequestDto.getIngredients()));
            recipe.getIngredients().forEach(item -> item.setRecipe(recipe));
            return recipeMapper.fromEntityToDto(recipeRepository.save(recipe));
		}else {
            throw new ConstraintViolationException("Object does not accept constraints {} " , violations);
		}
	}

	@Override
	public RecipeDto updateRecipe(RecipeDto recipeDto) {
		log.debug("Starting updating recipe : {}", recipeDto);
		if(recipeDto.getId() != null && recipeRepository.existsById(recipeDto.getId())) {
			return recipeMapper.fromEntityToDto(recipeRepository.save(recipeMapper.fromDtoToEntity(recipeDto)));
		}else {
			throw new EntityNotFoundException("Id of recipe not found");
		}
	}

	@Override
	public void deleteRecipe(Long id) {
		log.debug("Starting updating recipe : {}", id);
		if(id != null && recipeRepository.existsById(id)) {
			recipeRepository.deleteById(id);
		}else {
			throw new EntityNotFoundException("Id of recipe not found");
		}
	}

	@Override
	public Page<RecipeDto> filterRecipes(@NotNull FilterSearchRecipeDto filterSearchRecipeDto) {
		return recipeMapper.mapPage(recipeRepository.findAll((Specification<JpaRecipe>) (root, query, criteriaBuilder) ->
		prepareRecipe(filterSearchRecipeDto, root, criteriaBuilder),
        PageRequest.of(filterSearchRecipeDto.getPageNum(),
        		filterSearchRecipeDto.getPageSize())));
	}
	
	public Predicate prepareRecipe(FilterSearchRecipeDto filterSearchRecipeDto, Root<JpaRecipe> root, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicateList = new ArrayList<>();
		
		if(filterSearchRecipeDto.getRecipeName() !=null && !filterSearchRecipeDto.getRecipeName().isBlank()) {
			predicateList.add(criteriaBuilder.like(root.get("name"), "%" + filterSearchRecipeDto.getRecipeName()+ "%"));
		}
		
		if(filterSearchRecipeDto.getVegan() !=null) {
			predicateList.add(criteriaBuilder.like(root.get("veg"), "%" + filterSearchRecipeDto.getVegan()));
		}
		
		if (filterSearchRecipeDto.getInstruction() != null && !filterSearchRecipeDto.getInstruction().isBlank()) {
            predicateList.add(criteriaBuilder.like(root.get("instruction"), "%" + filterSearchRecipeDto.getInstruction() + "%"));
        }
        if(filterSearchRecipeDto.getServingNumber() != null && filterSearchRecipeDto.getServingNumber() > 0){
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("servingNumber"), filterSearchRecipeDto.getServingNumber()));
        }
        if (filterSearchRecipeDto.getIncludedIngredients() != null && !filterSearchRecipeDto.getIncludedIngredients().isEmpty()) {
            Join<JpaRecipe, JpaRecipeIngredient> joinIngredientRecipe = root.join("ingredients");
            predicateList.add(joinIngredientRecipe.get("ingredient").in(ingredientMapper.fromDtoListToEntityList(
            		filterSearchRecipeDto.getIncludedIngredients())));
        }
     
        if (filterSearchRecipeDto.getExcludedIngredients() != null && !filterSearchRecipeDto.getExcludedIngredients().isEmpty()) {
            Join<JpaRecipe, JpaRecipeIngredient> joinIngredientRecipe = root.join("ingredients",JoinType.LEFT);
            joinIngredientRecipe.on(joinIngredientRecipe.get("ingredient").in(ingredientMapper.fromDtoListToEntityList(
            		filterSearchRecipeDto.getExcludedIngredients())));
            predicateList.add(criteriaBuilder.isNull(joinIngredientRecipe.get("ingredient")));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
	}

}

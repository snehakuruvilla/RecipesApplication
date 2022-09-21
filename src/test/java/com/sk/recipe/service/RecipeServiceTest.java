package com.sk.recipe.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sk.recipe.dto.IngredientsDto;
import com.sk.recipe.dto.RecipeDto;
import com.sk.recipe.dto.RecipeIngredientDto;
import com.sk.recipe.dto.request.AddRecipeRequestDto;
import com.sk.recipe.dto.request.FilterSearchRecipeDto;
import com.sk.recipe.entity.JpaRecipe;
import com.sk.recipe.entity.JpaRecipeIngredient;
import com.sk.recipe.mapper.IngredientMapper;
import com.sk.recipe.mapper.RecipeIngredientMapper;
import com.sk.recipe.mapper.RecipeMapper;
import com.sk.recipe.repository.RecipeRepository;
import com.sk.recipe.service.impl.RecipeServiceImpl;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class RecipeServiceTest {

    private RecipeServiceImpl recipeService;

    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private RecipeMapper recipeMapper;

    @MockBean
    private RecipeIngredientMapper recipeIngredientMapper;

    @MockBean
    private IngredientMapper ingredientMapper;

    private Validator validator;

    @BeforeAll
    public void init(){
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.recipeService = new RecipeServiceImpl(recipeRepository, recipeMapper,
                recipeIngredientMapper,ingredientMapper, validator);
        when(recipeRepository.save(any(JpaRecipe.class))).then(returnsFirstArg());
    }

    @Test
     void test_add_recipe_success(){

        AddRecipeRequestDto addRecipeRequestDto = new AddRecipeRequestDto();
        addRecipeRequestDto.setName("First Recipe");
        addRecipeRequestDto.setVegan(Boolean.TRUE);
        addRecipeRequestDto.setServingNumber(5L);
        addRecipeRequestDto.setInstruction("put everything in the oven");
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
        recipeIngredientDto.setQuantity(2L);
        recipeIngredientDto.setIngredient(new IngredientsDto(1L , "Tomato"));
        List<RecipeIngredientDto> ingredientDtoList = Collections.singletonList(recipeIngredientDto);
        addRecipeRequestDto.setIngredients(ingredientDtoList);
        when(recipeIngredientMapper.fromDtoListToEntityList(ingredientDtoList))
                .thenReturn(Collections.singletonList(new JpaRecipeIngredient()));

        recipeService.addNewRecipe(addRecipeRequestDto);
        verify(recipeRepository,times(1)).save(any(JpaRecipe.class));
    }

    @Test
     void test_add_recipe_fail(){
        AddRecipeRequestDto addRecipeRequestDto = new AddRecipeRequestDto();
        addRecipeRequestDto.setVegan(Boolean.TRUE);
        addRecipeRequestDto.setServingNumber(5L);
        addRecipeRequestDto.setInstruction("put everything in the oven");
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
        recipeIngredientDto.setQuantity(2L);
        recipeIngredientDto.setIngredient(new IngredientsDto(1L , "Tomato"));
        List<RecipeIngredientDto> ingredientDtoList = Collections.singletonList(recipeIngredientDto);
        addRecipeRequestDto.setIngredients(ingredientDtoList);
        when(recipeIngredientMapper.fromDtoListToEntityList(ingredientDtoList))
                .thenReturn(Collections.singletonList(new JpaRecipeIngredient()));
        Assertions.assertThrows(ConstraintViolationException.class,() -> {
            recipeService.addNewRecipe(addRecipeRequestDto);
        });

    }

    @Test
     void test_update_recipe_fail_id_null(){
        RecipeDto recipeDto = new RecipeDto() ;

        Assertions.assertThrows(EntityNotFoundException.class,() -> {
            recipeService.updateRecipe(recipeDto);
        });


    }

    @Test
     void test_update_recipe_faill_not_exist(){
        when(recipeRepository.existsById(1L)).thenReturn(Boolean.FALSE);
        RecipeDto recipeDto = new RecipeDto() ;
        Assertions.assertThrows(EntityNotFoundException.class,() -> {

            recipeService.updateRecipe(recipeDto);
        });

    }

    @Test
     void test_update_recipe_success(){
        when(recipeRepository.existsById(1L)).thenReturn(Boolean.TRUE);
        RecipeDto recipeDto = new RecipeDto() ;
        recipeDto.setId(1L);
        when(recipeMapper.fromDtoToEntity(recipeDto)).thenReturn(new JpaRecipe());

        recipeService.updateRecipe(recipeDto);
        verify(recipeRepository,times(1)).save(any(JpaRecipe.class));

    }

    @Test
     void test_delete_recipe_faill_not_exist(){
        when(recipeRepository.existsById(1L)).thenReturn(Boolean.FALSE);
        Assertions.assertThrows(EntityNotFoundException.class,() -> {

            recipeService.deleteRecipe(1L);
        });

    }

    @Test
     void test_delete_recipe_success(){
            when(recipeRepository.existsById(1L)).thenReturn(Boolean.TRUE);
            recipeService.deleteRecipe(1L);
           verify(recipeRepository,times(1)).deleteById(1L);
    }

    @Test
     void test_filter_recipes(){
    	FilterSearchRecipeDto filterSearchRecipeDto = new FilterSearchRecipeDto();
    	filterSearchRecipeDto.setRecipeName("First");
    	filterSearchRecipeDto.setInstruction("oven");
    	filterSearchRecipeDto.setVegan(Boolean.TRUE);
    	filterSearchRecipeDto.setIncludedIngredients(Collections.singletonList(new IngredientsDto(1L, "tomato")));
    	filterSearchRecipeDto.setExcludedIngredients(Collections.singletonList(new IngredientsDto(2L , "Potato")));
    	filterSearchRecipeDto.setPageNum(0);
    	filterSearchRecipeDto.setPageSize(10);
        when(recipeRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl(Collections.singletonList(new JpaRecipe())));
        when(recipeMapper.mapPage(any())).thenReturn(new PageImpl(Collections.singletonList(new RecipeDto())));
        Page<RecipeDto> res =recipeService.filterRecipes(filterSearchRecipeDto);
        Assertions.assertEquals(1L, res.getTotalElements());
    }

   /* @Test
    public void test_prepare_predicate(){
        FilterRecipeRequestDto filterRecipeRequestDto = new FilterRecipeRequestDto();
        filterRecipeRequestDto.setRecipeName("First");
        filterRecipeRequestDto.setInstruction("oven");
        filterRecipeRequestDto.setVegan(Boolean.TRUE);
        filterRecipeRequestDto.setIncludedIngredients(Collections.singletonList(new IngredientDto(1L, "tomato")));
        filterRecipeRequestDto.setExcludedIngredients(Collections.singletonList(new IngredientDto(2L , "Potato")));
        filterRecipeRequestDto.setPageNum(0);
        filterRecipeRequestDto.setPageSize(10);
        Root<JpaRecipe> root = mock(Root.class);
        when(root.join("ingredients")).thenReturn(mock(Join.class));
        Predicate predicate =this.recipeService.preparePredicateRecipes(filterRecipeRequestDto,mock(Root.class), mock(CriteriaBuilder.class));
        Assertions.assertEquals(predicate.getExpressions().size(),5L);
    }*/




}

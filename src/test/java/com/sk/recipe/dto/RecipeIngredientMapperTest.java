package com.sk.recipe.dto;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sk.recipe.entity.JpaIngredients;
import com.sk.recipe.entity.JpaRecipe;
import com.sk.recipe.entity.JpaRecipeIngredient;
import com.sk.recipe.exception.MapperNullObjectException;
import com.sk.recipe.mapper.IngredientMapper;
import com.sk.recipe.mapper.RecipeIngredientMapper;

import java.util.Collections;
import java.util.List;


import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class RecipeIngredientMapperTest {

    @InjectMocks
    private RecipeIngredientMapper recipeIngredientMapper;

    @MockBean
    private IngredientMapper ingredientMapper;

    @BeforeAll
    public void init(){
      this.recipeIngredientMapper = new RecipeIngredientMapper(ingredientMapper);
    }

    @Test
     void test_from_Dto_To_Entity(){
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto() ;
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        IngredientsDto ingredientDto = new IngredientsDto(1L , "Tomato");
        recipeIngredientDto.setId(1L);
        recipeIngredientDto.setRecipe(recipeDto);
        recipeIngredientDto.setIngredient(ingredientDto);
        recipeIngredientDto.setQuantity(2L);
        when(ingredientMapper.fromDtoToEntity(ingredientDto))
                .thenReturn(new JpaIngredients());
        JpaRecipeIngredient result =this.recipeIngredientMapper.fromDtoToEntity(recipeIngredientDto);
        Assertions.assertEquals(1L , result.getId());
        Assertions.assertEquals(2L,result.getQuantity());

    }

    @Test
     void test_from_Dto_To_Entity_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.recipeIngredientMapper.fromDtoToEntity(null);

        });
    }


    @Test
     void test_from_entity_to_dto(){
        JpaRecipe recipe = new JpaRecipe();
        recipe.setId(1L);
        recipe.setName("First Recipe");
        recipe.setVeg(Boolean.TRUE);
        recipe.setServingNumber(5L);
        recipe.setInstruction("put everything in oven");
        JpaIngredients ingredient = new JpaIngredients();
        JpaRecipeIngredient jpaRecipeIngredient = new JpaRecipeIngredient() ;
        jpaRecipeIngredient.setRecipe(recipe);
        jpaRecipeIngredient.setIngredient(ingredient);
        jpaRecipeIngredient.setId(1L);
        jpaRecipeIngredient.setQuantity(2L);
        when(ingredientMapper.fromEntityToDto(ingredient))
                .thenReturn(new IngredientsDto(1L , "tomato"));
        RecipeIngredientDto result =this.recipeIngredientMapper.fromEntityToDto(jpaRecipeIngredient);
        Assertions.assertEquals(1L , result.getId());
        Assertions.assertEquals(2L,result.getQuantity());
        Assertions.assertEquals("First Recipe" ,result.getRecipe().getName());
    }

    @Test
     void test_from_entity_to_dto_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.recipeIngredientMapper.fromEntityToDto(null);
        });
    }

    @Test
     void test_from_entityList_to_dtoList(){
        JpaRecipe recipe = new JpaRecipe();
        recipe.setId(1L);
        recipe.setName("First Recipe");
        recipe.setVeg(Boolean.TRUE);
        recipe.setServingNumber(5L);
        recipe.setInstruction("put everything in oven");
        JpaIngredients ingredient = new JpaIngredients();
        JpaRecipeIngredient jpaRecipeIngredient = new JpaRecipeIngredient() ;
        jpaRecipeIngredient.setRecipe(recipe);
        jpaRecipeIngredient.setIngredient(ingredient);
        jpaRecipeIngredient.setId(1L);
        jpaRecipeIngredient.setQuantity(2L);
        List<JpaRecipeIngredient> entityList = Collections.singletonList(jpaRecipeIngredient);
        when(ingredientMapper.fromEntityToDto(ingredient))
                .thenReturn(new IngredientsDto(1L , "tomato"));
        List<RecipeIngredientDto> recipeDtos = recipeIngredientMapper.fromEntityListToDtoList(entityList);
        Assertions.assertEquals(1 , recipeDtos.size());
        Assertions.assertEquals(2L,recipeDtos.get(0).getQuantity());
    }

    @Test
     void test_from_entityList_to_dtoList_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.recipeIngredientMapper.fromEntityListToDtoList(null);
        });
    }

    @Test
     void test_from_dtoList_to_entityList(){
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto() ;
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("First Recipe");
        recipeDto.setVegan(Boolean.TRUE);
        recipeDto.setServingNumber(5L);
        recipeDto.setInstruction("put everything in oven");
        IngredientsDto ingredientDto = new IngredientsDto(1L , "Tomato");
        recipeIngredientDto.setId(1L);
        recipeIngredientDto.setRecipe(recipeDto);
        recipeIngredientDto.setIngredient(ingredientDto);
        recipeIngredientDto.setQuantity(2L);
        when(ingredientMapper.fromDtoToEntity(ingredientDto))
                .thenReturn(new JpaIngredients());
        List<JpaRecipeIngredient> entityList = recipeIngredientMapper.fromDtoListToEntityList(Collections.singletonList(recipeIngredientDto));
        Assertions.assertEquals(1 , entityList.size());
        Assertions.assertEquals(2L,entityList.get(0).getQuantity());
    }

    @Test
     void test_from_dtoList_to_entityList_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.recipeIngredientMapper.fromDtoListToEntityList(null);
        });
    }

    @Test
     void test_map_page(){
        JpaRecipe recipe = new JpaRecipe();
        recipe.setId(1L);
        recipe.setName("First Recipe");
        recipe.setVeg(Boolean.TRUE);
        recipe.setServingNumber(5L);
        recipe.setInstruction("put everything in oven");
        JpaIngredients ingredient = new JpaIngredients();
        JpaRecipeIngredient jpaRecipeIngredient = new JpaRecipeIngredient() ;
        jpaRecipeIngredient.setRecipe(recipe);
        jpaRecipeIngredient.setIngredient(ingredient);
        jpaRecipeIngredient.setId(1L);
        jpaRecipeIngredient.setQuantity(2L);
        List<JpaRecipeIngredient> entityList = Collections.singletonList(jpaRecipeIngredient);
        when(ingredientMapper.fromEntityToDto(ingredient))
                .thenReturn(new IngredientsDto(1L , "tomato"));
        Page<RecipeIngredientDto> dtoPage = recipeIngredientMapper.mapPage(new PageImpl<>(entityList));
        Assertions.assertEquals(1 , dtoPage.getTotalElements());
        Assertions.assertEquals(2L,dtoPage.getContent().get(0).getQuantity());
    }

    @Test
     void test_map_page_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.recipeIngredientMapper.mapPage(null);
        });
    }

}

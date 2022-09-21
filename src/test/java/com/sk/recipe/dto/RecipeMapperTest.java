package com.sk.recipe.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.sk.recipe.entity.JpaRecipe;
import com.sk.recipe.entity.JpaRecipeIngredient;
import com.sk.recipe.exception.MapperNullObjectException;
import com.sk.recipe.mapper.RecipeIngredientMapper;
import com.sk.recipe.mapper.RecipeMapper;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class RecipeMapperTest {

    private RecipeMapper recipeMapper;

    @MockBean
    private RecipeIngredientMapper recipeIngredientMapper;


    @BeforeAll
    public  void init(){

        this.recipeMapper=new RecipeMapper(recipeIngredientMapper);
    }


    @Test
     void test_from_Dto_To_Entity(){
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("First Recipe");
        recipeDto.setVegan(Boolean.TRUE);
        recipeDto.setServingNumber(5L);
        recipeDto.setInstruction("put everything in oven");
        recipeDto.setIngredients(Collections.singletonList(new RecipeIngredientDto()));
        when(recipeIngredientMapper.fromDtoListToEntityList(anyList()))
                .thenReturn(Collections.singletonList(new JpaRecipeIngredient()));
        JpaRecipe result =this.recipeMapper.fromDtoToEntity(recipeDto);
        Assertions.assertEquals(1L , result.getId());
        Assertions.assertEquals("First Recipe",result.getName());
        Assertions.assertEquals(5L ,result.getServingNumber());
        Assertions.assertTrue(result.isVeg());
    }

    @Test
     void test_from_Dto_To_Entity_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.recipeMapper.fromDtoToEntity(null);

        });
    }


    @Test
     void test_from_entity_to_dto(){
        JpaRecipe entity = new JpaRecipe();
        entity.setId(1L);
        entity.setName("First Recipe");
        entity.setVeg(Boolean.TRUE);
        entity.setServingNumber(5L);
        entity.setInstruction("put everything in oven");
        entity.setIngredients(Collections.singletonList(new JpaRecipeIngredient()));
        when(recipeIngredientMapper.fromEntityListToDtoList(anyList()))
                .thenReturn(Collections.singletonList(new RecipeIngredientDto()));
        RecipeDto result =this.recipeMapper.fromEntityToDto(entity);
        Assertions.assertEquals(1L , result.getId());
        Assertions.assertEquals("First Recipe",result.getName());
        Assertions.assertEquals(5L ,result.getServingNumber());
        Assertions.assertTrue(result.isVegan());
    }

    @Test
     void test_from_entity_to_dto_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.recipeMapper.fromEntityToDto(null);
        });
    }

    @Test
     void test_from_entityList_to_dtoList(){
        JpaRecipe entity = new JpaRecipe() ;
        entity.setId(1L);
        entity.setName("First");
        List<JpaRecipe> entityList = Collections.singletonList(entity);
        when(recipeIngredientMapper.fromEntityListToDtoList(any()))
                .thenReturn(Collections.singletonList(new RecipeIngredientDto()));
        List<RecipeDto> recipeDtos = recipeMapper.fromEntityListToDtoList(entityList);
        Assertions.assertEquals(1 , recipeDtos.size());
        Assertions.assertEquals("First",recipeDtos.get(0).getName());
    }

    @Test
     void test_from_entityList_to_dtoList_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.recipeMapper.fromEntityListToDtoList(null);
        });
    }

    @Test
     void test_from_dtoList_to_entityList(){
        RecipeDto recipeDto = new RecipeDto() ;
        recipeDto.setId(1L);
        recipeDto.setName("First");
        List<RecipeDto> dtoList = Collections.singletonList(recipeDto);
        when(recipeIngredientMapper.fromDtoListToEntityList(any()))
                .thenReturn(Collections.singletonList(new JpaRecipeIngredient()));
        List<JpaRecipe> entityList = recipeMapper.fromDtoListToEntityList(dtoList);
        Assertions.assertEquals(1 , entityList.size());
        Assertions.assertEquals("First",entityList.get(0).getName());
    }

    @Test
     void test_from_dtoList_to_entityList_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.recipeMapper.fromDtoListToEntityList(null);
        });
    }

    @Test
     void test_map_page(){
        JpaRecipe entity = new JpaRecipe() ;
        entity.setId(1L);
        entity.setName("First");
        Page<JpaRecipe> entityPage = new PageImpl<>(Collections.singletonList(entity));
        when(recipeIngredientMapper.fromEntityListToDtoList(any()))
                .thenReturn(Collections.singletonList(new RecipeIngredientDto()));
        Page<RecipeDto> dtoPage = recipeMapper.mapPage(entityPage);
        Assertions.assertEquals(1 , dtoPage.getTotalElements());
        Assertions.assertEquals("First",dtoPage.getContent().get(0).getName());
    }

    @Test
     void test_map_page_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.recipeMapper.mapPage(null);
        });
    }

}

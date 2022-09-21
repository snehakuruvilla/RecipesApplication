package com.sk.recipe.dto;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.sk.recipe.entity.JpaIngredients;
import com.sk.recipe.exception.MapperNullObjectException;
import com.sk.recipe.mapper.IngredientMapper;

import java.util.Collections;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class IngredientMapperTest {

    private IngredientMapper ingredientMapper;


    @BeforeAll
    public  void init(){
        this.ingredientMapper=new IngredientMapper();
    }


    @Test
     void test_from_Dto_To_Entity(){
        IngredientsDto ingredientDto = new IngredientsDto(1L , "Tomato");
        JpaIngredients result =this.ingredientMapper.fromDtoToEntity(ingredientDto);
        Assertions.assertEquals(1L , result.getId());
        Assertions.assertEquals("Tomato",result.getName());
    }

    @Test
     void test_from_Dto_To_Entity_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            JpaIngredients result =this.ingredientMapper.fromDtoToEntity(null);

        });
    }


    @Test
     void test_from_entity_to_dto(){

        JpaIngredients jpaIngredient = new JpaIngredients(1L , "Tomato");
        IngredientsDto result =this.ingredientMapper.fromEntityToDto(jpaIngredient);
        Assertions.assertEquals(1L , result.getId());
        Assertions.assertEquals("Tomato",result.getName());
    }

    @Test
     void test_from_entity_to_dto_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            IngredientsDto result =this.ingredientMapper.fromEntityToDto(null);
        });
    }

    @Test
     void test_from_entityList_to_dtoList(){
        List<JpaIngredients> entityList = Collections.singletonList(new JpaIngredients(1L , "Potato"));
        List<IngredientsDto> ingredientDtos = ingredientMapper.fromEntityListToDtoList(entityList);
        Assertions.assertEquals(1 , ingredientDtos.size());
        Assertions.assertEquals("Potato",ingredientDtos.get(0).getName());
    }

    @Test
     void test_from_entityList_to_dtoList_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
           this.ingredientMapper.fromEntityListToDtoList(null);
        });
    }

    @Test
     void test_from_dtoList_to_entityList(){
        List<IngredientsDto> ingredientDtos = Collections.singletonList(new IngredientsDto(1L , "Potato"));
        List<JpaIngredients> entityList = ingredientMapper.fromDtoListToEntityList(ingredientDtos);
        Assertions.assertEquals(1 , entityList.size());
        Assertions.assertEquals("Potato",entityList.get(0).getName());
    }

    @Test
     void test_from_dtoList_to_entityList_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.ingredientMapper.fromDtoListToEntityList(null);
        });
    }

    @Test
     void test_map_page(){
        Page<JpaIngredients> entityPage = new PageImpl<>(Collections.singletonList(new JpaIngredients(1L , "Potato")));

        Page<IngredientsDto> dtoPage = ingredientMapper.mapPage(entityPage);
        Assertions.assertEquals(1 , dtoPage.getTotalElements());
        Assertions.assertEquals("Potato",dtoPage.getContent().get(0).getName());
    }

    @Test
     void test_map_page_fail(){
        Assertions.assertThrows(MapperNullObjectException.class,() -> {
            this.ingredientMapper.mapPage(null);
        });
    }



}

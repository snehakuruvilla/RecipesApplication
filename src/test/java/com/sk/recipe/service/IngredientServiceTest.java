package com.sk.recipe.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sk.recipe.dto.IngredientsDto;
import com.sk.recipe.dto.request.AddIngredientRequestDto;
import com.sk.recipe.entity.JpaIngredients;
import com.sk.recipe.exception.NoItemFoundException;
import com.sk.recipe.mapper.IngredientMapper;
import com.sk.recipe.repository.IngredientRepository;
import com.sk.recipe.service.impl.IngredientServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class IngredientServiceTest {


    private IngredientService ingredientService ;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private IngredientMapper ingredientMapper;

    @BeforeAll
    public void init(){
        this.ingredientService = new IngredientServiceImpl(ingredientRepository,ingredientMapper);
        when(ingredientRepository.save(any(JpaIngredients.class))).then(returnsFirstArg());

    }

    @Test
     void test_add_ingredient_success(){
        AddIngredientRequestDto ingredientRequestDto = new AddIngredientRequestDto();
        ingredientRequestDto.setName("Tomato");
        ingredientService.addNewIngredient(ingredientRequestDto);
        verify(ingredientRepository,times(1)).save(any(JpaIngredients.class));
    }

    @Test
     void test_add_ingredient_exception(){
        AddIngredientRequestDto dto = new AddIngredientRequestDto();
        Assertions.assertThrows(IllegalArgumentException.class,() -> {

            ingredientService.addNewIngredient(dto);

        });
    }

    @Test
     void test_update_ingredient_success(){
        when(ingredientMapper.fromDtoToEntity(any(IngredientsDto.class))).thenReturn(new JpaIngredients());
        IngredientsDto ingredientDto = new IngredientsDto(1L,"Tomato");
        ingredientService.updateIngredient(ingredientDto);
        verify(ingredientRepository,times(1)).save(any(JpaIngredients.class));
    }

    @Test
     void test_update_ingredient_fail(){
        Assertions.assertThrows(IllegalArgumentException.class,() -> {

            ingredientService.updateIngredient(null);

        });
    }

    @Test
     void test_delete_ingredient_success(){
        when(ingredientRepository.existsById(any(Long.class))).thenReturn(Boolean.TRUE);
        ingredientService.deleteIngredient(1L);
        verify(ingredientRepository,times(1)).deleteById(any(Long.class));
    }

    @Test
     void test_delete_ingredient_fail(){
        when(ingredientRepository.existsById(any(Long.class))).thenReturn(Boolean.FALSE);
        Assertions.assertThrows(EntityNotFoundException.class,() -> {
            ingredientService.deleteIngredient(2L);
        });
    }

    @Test
     void test_find_ingredient_by_name_sucess() throws NoItemFoundException {
        when(ingredientRepository.findByNameContainsIgnoreCase(any(String.class))).
                thenReturn(Collections.singletonList(new JpaIngredients()));
        when(ingredientMapper.fromEntityListToDtoList(anyList())).
                thenReturn(Collections.singletonList(new IngredientsDto(1L , "Tomato")));

        List<IngredientsDto> res = ingredientService.findIngredientByName("tomato");
        Assertions.assertEquals(1L, res.size());
    }

    @Test
     void test_find_ingredient_by_name_fail() throws NoItemFoundException {
        when(ingredientRepository.findByNameContainsIgnoreCase(any(String.class))).
                thenReturn(Collections.emptyList());
        Assertions.assertThrows(NoItemFoundException.class,() -> {
            ingredientService.findIngredientByName("tomato");
        });

    }





}

package com.sk.recipe.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import com.sk.recipe.dto.IngredientsDto;
import com.sk.recipe.dto.request.AddIngredientRequestDto;
import com.sk.recipe.util.Navigation;

import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class IngredientControllerTest {

    @Autowired
    private TestRestTemplate restTemplate ;

    @Test
    void add_ingredient_success(){
        String url = Navigation.INGREDIENT_API+"/add";
        AddIngredientRequestDto request = new AddIngredientRequestDto();
        request.setName("Tomato");
        ResponseEntity<IngredientsDto> responseEntity = restTemplate.postForEntity(url,request,IngredientsDto.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void add_ingredient_fail(){
        String url = Navigation.INGREDIENT_API+"/add";
        AddIngredientRequestDto request = new AddIngredientRequestDto();
        request.setName("");
        ResponseEntity responseEntity = restTemplate.postForEntity(url,request, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals("name of the ingredient cannot be blank",((HashMap<String, String>)responseEntity.getBody()).get("name"));
    }

    @Test
    void update_ingredient_success(){
        String url = Navigation.INGREDIENT_API+"/update";
        IngredientsDto request = new IngredientsDto(1L , "Tomato");
        HttpEntity<IngredientsDto> httpEntity = new HttpEntity<>(request);
        ResponseEntity<IngredientsDto> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,httpEntity,IngredientsDto.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(1L , responseEntity.getBody().getId());
    }

    @Test
    void update_ingredient_fail(){
        String url = Navigation.INGREDIENT_API+"/update";
        IngredientsDto request = new IngredientsDto();
        HttpEntity<IngredientsDto> httpEntity = new HttpEntity<>(request);
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.PUT,httpEntity,Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals("must not be null",((HashMap<String, String>)responseEntity.getBody()).get("id"));
    }



    @Test
    void list_ingredient_success(){
        String url = Navigation.INGREDIENT_API+"/search/Tomato";
        HttpEntity httpEntity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET,httpEntity,Object.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void list_ingredient_not_found(){
        String url = Navigation.INGREDIENT_API+"/search/test";
        HttpEntity httpEntity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET,httpEntity,Object.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void delete_ingredient_success(){
        String url = Navigation.INGREDIENT_API+"/"+1;
        restTemplate.delete(url);
    }


}

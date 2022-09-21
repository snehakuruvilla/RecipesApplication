package com.sk.recipe.controller;

import java.util.Collections;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import com.sk.recipe.dto.IngredientsDto;
import com.sk.recipe.dto.RecipeDto;
import com.sk.recipe.dto.RecipeIngredientDto;
import com.sk.recipe.dto.request.AddIngredientRequestDto;
import com.sk.recipe.dto.request.AddRecipeRequestDto;
import com.sk.recipe.dto.request.FilterSearchRecipeDto;
import com.sk.recipe.util.HelperPage;
import com.sk.recipe.util.Navigation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	IngredientsDto tomatoIngredient;

	IngredientsDto potatoIngredient;

	RecipeDto recipeDto;

	@BeforeAll
	public void init() {
		addIngredients();
	}

	private void addIngredients() {
		String url = Navigation.INGREDIENT_API + "/add";
		AddIngredientRequestDto request = new AddIngredientRequestDto();
		request.setName("Tomato");
		ResponseEntity<IngredientsDto> responseEntity = restTemplate.postForEntity(url, request, IngredientsDto.class);
		tomatoIngredient = responseEntity.getBody();
		request.setName("Potato");
		responseEntity = restTemplate.postForEntity(url, request, IngredientsDto.class);
		potatoIngredient = responseEntity.getBody();
	}

	@Test
	@Order(1)
	void add_recipe_success() {
		String url = Navigation.RECIPE_API + "/add";
		AddRecipeRequestDto addRecipeRequestDto = new AddRecipeRequestDto();
		addRecipeRequestDto.setName("First Recipe");
		addRecipeRequestDto.setVegan(Boolean.TRUE);
		addRecipeRequestDto.setInstruction("put everything in oven");
		addRecipeRequestDto.setServingNumber(4L);
		RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
		recipeIngredientDto.setQuantity(2L);
		recipeIngredientDto.setIngredient(tomatoIngredient);
		addRecipeRequestDto.setIngredients(Collections.singletonList(recipeIngredientDto));
		ResponseEntity<RecipeDto> responseEntity = restTemplate.postForEntity(url, addRecipeRequestDto,
				RecipeDto.class);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		this.recipeDto = responseEntity.getBody();
	}

	@Test
	@Order(2)
	void add_recipe_bad_request() {
		String url = Navigation.RECIPE_API + "/add";
		AddRecipeRequestDto addRecipeRequestDto = new AddRecipeRequestDto();
		addRecipeRequestDto.setServingNumber(4L);
		RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
		recipeIngredientDto.setQuantity(2L);
		recipeIngredientDto.setIngredient(tomatoIngredient);
		addRecipeRequestDto.setIngredients(Collections.singletonList(recipeIngredientDto));
		ResponseEntity<RecipeDto> responseEntity = restTemplate.postForEntity(url, addRecipeRequestDto,
				RecipeDto.class);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Order(3)
	void update_recipe_success() {
		String url = Navigation.RECIPE_API + "/update";
		recipeDto.setServingNumber(7L);
		HttpEntity<RecipeDto> httpEntity = new HttpEntity<>(recipeDto);
		ResponseEntity<RecipeDto> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity,
				RecipeDto.class);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertEquals(7L, responseEntity.getBody().getServingNumber());
	}

	@Test
	@Order(4)
	void update_ingredient_fail() {
		String url = Navigation.RECIPE_API + "/update";
		recipeDto.setServingNumber(0L);
		HttpEntity<RecipeDto> httpEntity = new HttpEntity<>(recipeDto);
		ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Object.class);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Order(5)
	void list_ingredient_success() {
		String url = Navigation.RECIPE_API + "/search";
		FilterSearchRecipeDto filter = new FilterSearchRecipeDto();
		filter.setRecipeName("First");
		filter.setInstruction("oven");
		filter.setIncludedIngredients(Collections.singletonList(tomatoIngredient));
		filter.setPageNum(0);
		filter.setPageSize(10);
		ResponseEntity<HelperPage> responseEntity = restTemplate.postForEntity(url, filter, HelperPage.class);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertEquals(1L, responseEntity.getBody().getTotalElements());
	}

	@Test
	@Order(6)
	void list_ingredient_no_results() {
		String url = Navigation.RECIPE_API + "/search";
		FilterSearchRecipeDto filter = new FilterSearchRecipeDto();
		filter.setRecipeName("First");
		filter.setInstruction("oven");
		filter.setVegan(Boolean.TRUE);
		filter.setServingNumber(3L);
		filter.setExcludedIngredients(Collections.singletonList(tomatoIngredient));
		filter.setPageNum(0);
		filter.setPageSize(10);
		ResponseEntity<HelperPage> responseEntity = restTemplate.postForEntity(url, filter, HelperPage.class);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertEquals(0L, responseEntity.getBody().getTotalElements());
	}

	@Test
	@Order(7)
	void delete_recipe_success() {
		String url = Navigation.RECIPE_API + "/" + recipeDto.getId();
		restTemplate.delete(url);
	}

}

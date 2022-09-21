package com.sk.recipe.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.recipe.dto.IngredientsDto;
import com.sk.recipe.dto.RecipeDto;
import com.sk.recipe.dto.request.AddIngredientRequestDto;
import com.sk.recipe.exception.NoItemFoundException;
import com.sk.recipe.service.IngredientService;
import com.sk.recipe.util.Navigation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Navigation.INGREDIENT_API)
@Tag(name = "Ingredients", description = "Endpoints for managing ingredients")
@Slf4j
public class IngredientsController implements IngredientsControllerInterface {

	private IngredientService ingredientService;
	
	public IngredientsController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

	@Override
	@PostMapping("add")
	public ResponseEntity<IngredientsDto> addIngredients(
			@RequestBody @Valid AddIngredientRequestDto addIngredientRequestDto) {
		log.info("Starting adding recipe {}", addIngredientRequestDto);
		return ResponseEntity.ok(ingredientService.addNewIngredient(addIngredientRequestDto));
	}

	@Override
	@PutMapping("update")
	public ResponseEntity<IngredientsDto> updateIngredient(
			@RequestBody @Parameter(description = "Ingredient model to update") @Validated IngredientsDto ingredientDto) {
		log.info("Starting update operation for ingredient : {}", ingredientDto);
		return ResponseEntity.ok(ingredientService.updateIngredient(ingredientDto));
	}

	@Override
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteIngredient(@PathVariable
            @Parameter(description = "Id of the ingredient to delete") @NotNull Long id)  {
		log.info("Starting delete operation for ingredient id :{}",id);
		ingredientService.deleteIngredient(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}


	@Override
	 @GetMapping("search/{name}")
	public ResponseEntity<List<IngredientsDto>> findIngredientByName(
			@Valid @NotNull @Pattern(regexp = "^[A-Za-z]*$", message = "name of ingredient should be only characters") @NotBlank String name) throws NoItemFoundException {
		log.info("Start searching for ingredient by name : {}", name);
        return ResponseEntity.ok(ingredientService.findIngredientByName(name));
	}

	@Override
	@GetMapping("list")
	public ResponseEntity<List<IngredientsDto>> retrieveAllIngredients() {
		log.info("retrieving all ingredients");
        return ResponseEntity.ok(ingredientService.retrieveAllIngredient());
	}

}

/**
 * 
 */
package com.sk.recipe.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
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

import com.sk.recipe.dto.RecipeDto;
import com.sk.recipe.dto.request.AddRecipeRequestDto;
import com.sk.recipe.dto.request.FilterSearchRecipeDto;
import com.sk.recipe.service.RecipeService;
import com.sk.recipe.util.Navigation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SNKURUVI
 *
 */
@RestController
@RequestMapping(Navigation.RECIPE_API)
@Tag(name = "Recipe", description = "Endpoints for managing recipe")
@Slf4j
public class RecipeController implements RecipeControllerInterface{
	
	private RecipeService recipeService ;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("add")
	public ResponseEntity<RecipeDto> addRecipe(@RequestBody @Valid AddRecipeRequestDto addRecipeRequestDto) {
		 log.info("Starting adding recipe {}" , addRecipeRequestDto);
		 return ResponseEntity.ok(recipeService.addNewRecipe(addRecipeRequestDto));
	}
    
    @PutMapping
    public ResponseEntity<RecipeDto> updateRecipe(@RequestBody @Valid RecipeDto recipeDto){
    	log.info("Starting update recipe {}" , recipeDto);
    	return ResponseEntity.ok(recipeService.updateRecipe(recipeDto));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRecipe(@PathVariable
            @Parameter(description = "Id of the Recipe to delete") @Validated @NotNull Long id){
    	log.info("Starting delete recipe {}" , id);
    	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @GetMapping
    public ResponseEntity<Page<RecipeDto>> searchRecipes(@RequestBody @Validated @NotNull  FilterSearchRecipeDto filterSearchRecipeDto){
        log.info("Starting searching query by {}", filterSearchRecipeDto);
        return ResponseEntity.ok(recipeService.filterRecipes(filterSearchRecipeDto));
    }
	
}

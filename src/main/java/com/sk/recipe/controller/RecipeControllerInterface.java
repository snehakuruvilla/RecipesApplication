package com.sk.recipe.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.sk.recipe.dto.RecipeDto;
import com.sk.recipe.dto.request.AddRecipeRequestDto;
import com.sk.recipe.dto.request.FilterSearchRecipeDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface RecipeControllerInterface {

	@Operation(summary = "Add Recipe", description = "Add Recipe to Database.", tags = { "Recipe" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400"),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	ResponseEntity<RecipeDto> addRecipe(@RequestBody @Valid AddRecipeRequestDto addRecipeRequestDto);

	@Operation(summary = "Update Recipe", description = "Update Recipe data.", tags = { "Recipe" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400"),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	ResponseEntity<RecipeDto> updateRecipe(@RequestBody @Valid RecipeDto recipeDto);

	@Operation(summary = "Delete Recipe", description = "Delete Recipe from database.", tags = {
			"Recipe" }, responses = { @ApiResponse(description = "Success", responseCode = "204", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	ResponseEntity<String> deleteRecipe(
			@PathVariable @Parameter(description = "Id of the Recipe to delete") @Validated @NotNull Long id);

	@Operation(summary = "Search Recipe", description = "Search Recipe By name , servingNumber , instruction and ingredients.", tags = {
			"Recipe" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400"),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	ResponseEntity<Page<RecipeDto>> searchRecipes(
			@RequestBody @Validated @NotNull FilterSearchRecipeDto filterSearchRecipeDto);
}

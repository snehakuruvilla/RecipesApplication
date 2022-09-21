package com.sk.recipe.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.sk.recipe.dto.IngredientsDto;
import com.sk.recipe.dto.RecipeDto;
import com.sk.recipe.dto.request.AddIngredientRequestDto;
import com.sk.recipe.exception.NoItemFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface IngredientsControllerInterface {

	@Operation(summary = "Add Ingredients", description = "Add Ingredients to Database.", tags = {
			"Ingredients" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientsDto.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400"),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	ResponseEntity<IngredientsDto> addIngredients(@RequestBody @Valid AddIngredientRequestDto addIngredientRequestDto);

	@Operation(summary = "Update Ingredients", description = "Update Ingredients data.", tags = { "Ingredients" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientsDto.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400"),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	ResponseEntity<IngredientsDto> updateIngredient(@RequestBody @Valid IngredientsDto ingredientsDto);

	@Operation(summary = "Delete Ingredient", description = "Delete Ingredient from database.", tags = {
			"Ingredients" }, responses = {
					@ApiResponse(description = "Success", responseCode = "204", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	ResponseEntity<String> deleteIngredient(
			@PathVariable @Parameter(description = "Id of the ingredient to delete") @NotNull Long id);

	@Operation(summary = "Search Ingredient", description = "Search ingredient in Database.", tags = {
			"Ingredients" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400"),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	ResponseEntity<List<IngredientsDto>> findIngredientByName(
			@PathVariable @Valid @NotNull @Pattern(regexp = "^[A-Za-z]*$", message = "name of ingredient should be only characters") @NotBlank String name) throws NoItemFoundException;

	@Operation(summary = "List all Ingredients", description = "List all Ingredients.", tags = {
			"Ingredients" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<List<IngredientsDto>> retrieveAllIngredients();
}

package com.sk.recipe.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.sk.recipe.dto.IngredientsDto;
import lombok.Data;

@Data
public class FilterSearchRecipeDto {

	private String recipeName;

    private Boolean vegan;

    private Long servingNumber;

    private List<IngredientsDto> includedIngredients ;

    private List<IngredientsDto> excludedIngredients;

    private String instruction ;
    
    @PositiveOrZero
    @NotNull
    private Integer pageNum;

    @PositiveOrZero
    @NotNull
    private Integer pageSize ;
}

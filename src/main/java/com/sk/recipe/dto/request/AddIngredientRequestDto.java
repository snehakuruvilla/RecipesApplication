package com.sk.recipe.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class AddIngredientRequestDto {
	
	@NotNull(message = "name of the ingredient cannot be null")
    @NotBlank(message = "name of the ingredient cannot be blank")
    @Pattern(regexp="^[A-Za-z]*$",message = "name of ingredient should be only characters")
    private String name ;

}

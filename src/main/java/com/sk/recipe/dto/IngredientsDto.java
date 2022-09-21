package com.sk.recipe.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientsDto {

    @NotNull
    @Positive
    private Long id ;

    @NotNull
    @NotBlank
    @Pattern(regexp="^[A-Za-z]*$",message = "name of ingredient should be only characters")
    private String name ;
}

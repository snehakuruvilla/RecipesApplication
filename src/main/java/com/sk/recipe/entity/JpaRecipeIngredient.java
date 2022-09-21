package com.sk.recipe.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="recipe_ingredients")
public class JpaRecipeIngredient implements Serializable{

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id ;

	    @ManyToOne
	    @JoinColumn(name = "recipe_id")
	    private JpaRecipe recipe ;

	    @ManyToOne
	    @JoinColumn(name = "ingredient_id")
	    private JpaIngredients ingredient ;

	    @Column(name = "quantity")
	    private Long quantity ;
}

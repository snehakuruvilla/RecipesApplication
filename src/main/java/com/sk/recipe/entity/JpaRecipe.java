package com.sk.recipe.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "recipe")
public class JpaRecipe implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "recipe_name")
	private String name;
	
	private boolean veg;
	
	@Column(name = "serving_number")
	private Long servingNumber;
	
	@Column(name = "instruction")
	private String instruction;
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<JpaRecipeIngredient> ingredients;

}

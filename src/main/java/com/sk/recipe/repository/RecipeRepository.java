package com.sk.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sk.recipe.entity.JpaRecipe;

@Repository
public interface RecipeRepository extends JpaRepository<JpaRecipe , Long> , JpaSpecificationExecutor<JpaRecipe> {
}

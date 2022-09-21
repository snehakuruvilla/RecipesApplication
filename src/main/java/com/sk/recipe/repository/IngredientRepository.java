package com.sk.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sk.recipe.entity.JpaIngredients;

@Repository
public interface IngredientRepository extends JpaRepository<JpaIngredients, Long> {

	List<JpaIngredients> findByNameContainsIgnoreCase(String name );
}

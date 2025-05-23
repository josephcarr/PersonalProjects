package edu.ncsu.csc.CoffeeMaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;

/**
 * IngredientRepository is used to provide CRUD operations for the Ingredient
 * model. Spring will generate appropriate code with JPA.
 *
 * @author Nathan Turpin (naturpin)
 *
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    /**
     * Finds an Ingredient object with the provided id. Spring will generate
     * code to make this happen.
     *
     * @param id
     *            Id of the ingredient
     * @return Found ingredient, null if none.
     */
    Ingredient findById ( long id );

}

package edu.ncsu.csc.CoffeeMaker.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.repositories.IngredientRepository;

/**
 * The IngredientService is used to handle CRUD operations on the Ingredient
 * model. In addition to all functionality from `Service`, we also have
 * functionality for retrieving a single Ingredient by name.
 *
 * @author Nathan Turpin (naturpin)
 *
 */
@Component
@Transactional
public class IngredientService extends Service<Ingredient, Long> {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    protected JpaRepository<Ingredient, Long> getRepository () {
        return ingredientRepository;
    }

    /**
     * Find an ingredient with the provided id
     *
     * @param id
     *            Id of the ingredient to find
     * @return found id, null if none
     */
    public Ingredient findById ( final long id ) {
        return ingredientRepository.findById( id );
    }

}

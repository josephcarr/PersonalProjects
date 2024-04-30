package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

/**
 * This is the controller that holds the REST endpoints that handle CRUD
 * operations for Ingredients.
 *
 * Spring will automatically convert all of the ResponseEntity and List results
 * to JSON
 *
 * @author Nathan Turpin (naturpin)
 *
 */
@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class APIIngredientController extends APIController {

    /**
     * IngredientService object, to be autowired in by Spring to allow for
     * manipulating the Ingredient model
     */
    @Autowired
    private IngredientService service;

    /**
     * REST API method to provide GET access to all ingredients in the system
     *
     * @return JSON representation of all ingredients
     */
    @GetMapping ( BASE_PATH + "/ingredients" )
    public List<Ingredient> getIngredients () {
        return service.findAll();
    }

    /**
     * REST API method to provide the next available ID in the Ingredient table
     *
     * @return the next ID as a Long.
     * 
     */
    @GetMapping ( BASE_PATH + "/ingredients/identifier" )
    public Long getNextId () {
        final List<Ingredient> is = service.findAll();
        return is.get( is.size() - 1 ).getId() + 1;
    }

    /**
     * REST API method to provide GET access to a specific ingredient, as
     * indicated by the path variable provided (the id of the ingredient
     * desired)
     *
     * @param id
     *            ingredient id
     * @return response to the request
     */
    @GetMapping ( BASE_PATH + "ingredients/{id}" )
    public ResponseEntity getIngredient ( @PathVariable ( "id" ) final Long id ) {

        final Ingredient ingr = service.findById( id );

        if ( null == ingr ) {
            return new ResponseEntity( errorResponse( "No ingredient found with id " + id ), HttpStatus.NOT_FOUND );
        }

        return new ResponseEntity( ingr, HttpStatus.OK );
    }

    /**
     * REST API method to provide POST access to the Ingredient model. This is
     * used to create a new Ingredient by automatically converting the JSON
     * RequestBody provided to a Ingredient object. Invalid JSON will fail.
     *
     * @param ingredient
     *            The valid Ingredient to be saved.
     * @return ResponseEntity indicating success if the Ingredient could be
     *         saved to the inventory, or an error if it could not be
     */
    @PostMapping ( BASE_PATH + "/ingredients" )
    public ResponseEntity createingredientient ( @RequestBody final Ingredient ingredient ) {
        if ( null != service.findById( ingredient.getId() ) ) {
            return new ResponseEntity(
                    errorResponse( "Ingredient with the id " + ingredient.getId() + " already exists" ),
                    HttpStatus.CONFLICT );
        }
        else {
            service.save( ingredient );
            return new ResponseEntity( successResponse( ingredient.getId() + " successfully created" ), HttpStatus.OK );
        }

    }

    /**
     * REST API method to allow deleting a Ingredient from the CoffeeMaker's
     * Inventory, by making a DELETE request to the API endpoint and indicating
     * the ingredient to delete (as a path variable)
     *
     * @param id
     *            The id of the Ingredient to delete
     * @return Success if the ingredient could be deleted; an error if the
     *         ingredient does not exist
     */
    @DeleteMapping ( BASE_PATH + "/ingredients/{id}" )
    public ResponseEntity deleteIngredient ( @PathVariable final Long id ) {
        final Ingredient ingredient;

        ingredient = service.findById( id );
        if ( null == ingredient ) {
            return new ResponseEntity( errorResponse( "No ingredient found for id " + id ), HttpStatus.NOT_FOUND );
        }

        service.delete( ingredient );

        return new ResponseEntity( successResponse( "Ingredient with this id was deleted successfully" ),
                HttpStatus.OK );

    }

    /**
     * REST API method to provide PUT access to the Ingredient model. This is
     * used to update an existing Ingredient.
     *
     * @param ingredient
     *            ingredient to update
     * @return response to the request
     */
    @PutMapping ( BASE_PATH + "/ingredients/{id}" )
    public ResponseEntity updateIngredient ( @RequestBody final Ingredient ingredient, @PathVariable final long id ) {
        final Ingredient ingredientCurrent = service.findById( id );

        ingredientCurrent.setAmount( ingredient.getAmount() );

        service.save( ingredientCurrent );
        return new ResponseEntity( ingredientCurrent, HttpStatus.OK );

    }
}

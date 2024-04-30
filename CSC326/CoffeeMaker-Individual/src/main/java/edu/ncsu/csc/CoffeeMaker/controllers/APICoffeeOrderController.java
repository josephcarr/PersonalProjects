package edu.ncsu.csc.CoffeeMaker.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.CoffeeOrder;
import edu.ncsu.csc.CoffeeMaker.services.CoffeeOrderService;

/**
 * This is the controller that holds the REST endpoints that handle CRUD
 * operations for Orders.
 *
 * Spring will automatically convert all of the ResponseEntity and List results
 * to JSON
 *
 * @author Nathan Turpin (naturpin)
 *
 */
@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class APICoffeeOrderController extends APIController {

    /**
     * OrderService object, to be autowired in by Spring to allow for
     * manipulating the Order model
     */
    @Autowired
    private CoffeeOrderService service;

    /**
     * REST API method to provide GET access to all orders in the system
     *
     * @return JSON representation of all orders
     */
    @GetMapping ( BASE_PATH + "/orders" )
    public List<CoffeeOrder> getOrders () {
        return service.findAll();
    }

    /**
     * REST API method to provide GET access to a specific order, as indicated
     * by the path variable provided (the name of the order desired)
     *
     * @param id
     *            order id
     * @return response to the request
     */
    @GetMapping ( BASE_PATH + "/orders/{id}" )
    public ResponseEntity getOrder ( @PathVariable ( "id" ) final long id ) {
        final CoffeeOrder order = service.findById( id );
        return null == order
                ? new ResponseEntity( errorResponse( "No order found with id " + id ), HttpStatus.NOT_FOUND )
                : new ResponseEntity( order, HttpStatus.OK );
    }

    @GetMapping ( BASE_PATH + "/orders/user" )
    public ResponseEntity getUserOrders ( final Authentication user ) {

        if ( user.getName().equals( "AnonymousUser" ) ) {
            return new ResponseEntity( errorResponse( "User not signed in" ), HttpStatus.BAD_REQUEST );
        }

        final List<CoffeeOrder> orders = service.findByUserName( user.getName() );

        return new ResponseEntity( orders, HttpStatus.OK );

    }

    /**
     * REST API method to provide POST access to the Order model. This is used
     * to create a new Order by automatically converting the JSON RequestBody
     * provided to a Order object. Invalid JSON will fail.
     *
     * @param order
     *            The valid Order to be saved.
     * @return ResponseEntity indicating success if the Order could be saved, or
     *         an error if it could not be
     */
    @PostMapping ( BASE_PATH + "/orders" )
    public ResponseEntity createOrder ( @RequestBody final CoffeeOrder order ) {
        if ( null != service.findById( order.getId() ) ) {
            return new ResponseEntity( errorResponse( "Order with the id " + order.getId() + " already exists" ),
                    HttpStatus.CONFLICT );
        }
        else {
            order.setDate( LocalDateTime.now() );
            service.save( order );

            return new ResponseEntity( successResponse( order.getId() + " successfully created" ), HttpStatus.OK );
        }

    }

    /**
     * REST API method to allow deleting a Order by making a DELETE request to
     * the API endpoint and indicating the order to delete (as a path variable)
     *
     * @param id
     *            The id of the Order to delete
     * @return Success if the order could be deleted; an error if the order does
     *         not exist
     */
    @DeleteMapping ( BASE_PATH + "/orders/{id}" )
    public ResponseEntity deleteOrder ( @PathVariable final long id ) {
        final CoffeeOrder order = service.findById( id );
        if ( null == order ) {
            return new ResponseEntity( errorResponse( "No order found for id " + id ), HttpStatus.NOT_FOUND );
        }
        service.delete( order );

        return new ResponseEntity( successResponse( id + " was deleted successfully" ), HttpStatus.OK );
    }

    /**
     * REST API method to allow editing an Order. Converts the JSON RequestBody
     * into a new Order and searches for an existing Order with the same name,
     * then updates it if found.
     *
     * If a matching Order by name is not found, a 404 response is sent.
     *
     */
    @PutMapping ( BASE_PATH + "/orders" )
    public ResponseEntity editOrder ( @RequestBody final CoffeeOrder r ) {
        final CoffeeOrder order = service.findById( r.getId() );
        if ( null == order ) {
            return new ResponseEntity( errorResponse( "No order found for id " + r.getId() ), HttpStatus.NOT_FOUND );
        }
        // System.out.println( r.getFulfilled() );
        order.setFulfilled( r.getFulfilled() );
        order.setRecipe( r.getRecipe() );
        // order.setUserID( r.getUserID() );
        // order.setUserName( r.getUserName() );

        service.save( order );

        return new ResponseEntity( successResponse( r.getId() + " was updated successfully" ), HttpStatus.OK );
    }
}

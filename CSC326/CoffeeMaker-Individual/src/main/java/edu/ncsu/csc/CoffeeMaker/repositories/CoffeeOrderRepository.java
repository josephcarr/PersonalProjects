package edu.ncsu.csc.CoffeeMaker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.CoffeeMaker.models.CoffeeOrder;

/**
 * OrderRepository is used to provide CRUD operations for the Order model.
 * Spring will generate appropriate code with JPA.
 *
 * @author Nathan Turpin (naturpin)
 *
 */
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
    /**
     * Finds an Order object with the provided id. Spring will generate code to
     * make this happen.
     *
     * @param id
     *            Id of the ingredient
     * @return Found order, null if none.
     */
    CoffeeOrder findById ( long id );

    /**
     * Finds multiple Order objects with the provided username. Spring will
     * generate code to make this happen.
     *
     * @param username
     *            the username field of the CoffeeOrder objects.
     *
     * @return A list of found CoffeeOrders
     *
     */
    List<CoffeeOrder> findByUserName ( String username );
}

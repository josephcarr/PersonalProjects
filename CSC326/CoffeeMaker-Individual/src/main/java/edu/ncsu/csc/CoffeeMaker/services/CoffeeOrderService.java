package edu.ncsu.csc.CoffeeMaker.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.CoffeeMaker.models.CoffeeOrder;
import edu.ncsu.csc.CoffeeMaker.repositories.CoffeeOrderRepository;

/**
 * The OrderService is used to handle CRUD operations on the Order model. In
 * addition to all functionality from `Service`, we also have functionality for
 * retrieving a single Order by id.
 *
 * @author Nathan Turpin (naturpin)
 *
 */
@Component
@Transactional
public class CoffeeOrderService extends Service<CoffeeOrder, Long> {

    /**
     * OrderRepository, to be autowired in by Spring and provide CRUD operations
     * on Order model.
     */
    @Autowired
    private CoffeeOrderRepository orderRepository;

    @Override
    protected JpaRepository<CoffeeOrder, Long> getRepository () {
        return orderRepository;
    }

    /**
     * Find a order with the provided name
     *
     * @param name
     *            Name of the order to find
     * @return found order, null if none
     */
    public CoffeeOrder findById ( final long id ) {
        return orderRepository.findById( id );
    }

    /**
     * Get all orders with a specified user_name field.
     *
     * @param username
     *            the username to search by
     * @return A list of CoffeeOrder objects
     * 
     */
    public List<CoffeeOrder> findByUserName ( final String username ) {
        return this.orderRepository.findByUserName( username );
    }
}

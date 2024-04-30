package edu.ncsu.csc.CoffeeMaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.CoffeeMaker.models.User;

/**
 * UserRepository is used to provide CRUD operations for the User model. Spring
 * will generate appropriate code with JPA.
 *
 * @author Nathan Turpin (naturpin)
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User object with the provided id. Spring will generate code to
     * make this happen.
     *
     * @param id
     *            Id of the ingredient
     * @return Found user, null if none.
     */
    User findById ( long id );

    /**
     * Finds a User object with the provided name. Spring will generate code to
     * make this happen.
     *
     * @param name
     *            Name of the recipe
     * @return Found recipe, null if none.
     */
    User findByUsername ( String username );
}

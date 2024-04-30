package edu.ncsu.csc.CoffeeMaker.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.ncsu.csc.CoffeeMaker.models.enums.Role;

/**
 * Controller class for the URL mappings for CoffeeMaker. The controller returns
 * the approprate HTML page in the /src/main/resources/templates folder. For a
 * larger application, this should be split across multiple controllers.
 *
 * @author Kai Presler-Marshall
 */
@Controller
public class MappingController {

    /**
     * On a GET request to /index, the IndexController will return
     * /src/main/resources/templates/index.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/index", "/" } )
    public String index ( final Authentication auth, final Model model ) {

        final String role = auth.getAuthorities().toArray()[0].toString();

        if ( role.equals( Role.Manager.name() ) ) {
            return "manager";
        }
        else if ( role.equals( Role.Worker.name() ) ) {
            return "worker";
        }
        else if ( role.equals( Role.Customer.name() ) ) {
            return "customer";
        }

        return "login";

    }

    /**
     * On a GET request to /recipe, the RecipeController will return
     * /src/main/resources/templates/recipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/recipe", "/recipe.html" } )
    public String ChangeRecipePage ( final Model model ) {
        return "recipe";
    }

    /**
     * On a GET request to /recipe, the RecipeController will return
     * /src/main/resources/templates/addrecipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/addrecipe", "/addrecipe.html" } )
    public String addRecipePage ( final Model model ) {
        return "addrecipe";
    }

    /**
     * On a GET request to /deleterecipe, the DeleteRecipeController will return
     * /src/main/resources/templates/deleterecipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/deleterecipe", "/deleterecipe.html" } )
    public String deleteRecipeForm ( final Model model ) {
        return "deleterecipe";
    }

    /**
     * On a GET request to /editrecipe, the EditRecipeController will return
     * /src/main/resources/templates/editrecipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/editrecipe", "/editrecipe.html" } )
    public String editRecipeForm ( final Model model ) {
        return "editrecipe";
    }

    /**
     * Handles a GET request for inventory. The GET request provides a view to
     * the client that includes the list of the current ingredients in the
     * inventory and a form where the client can enter more ingredients to add
     * to the inventory.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/inventory", "/inventory.html" } )
    public String inventoryForm ( final Model model ) {
        return "inventory";
    }

    /**
     * On a GET request to /makecoffee, the MakeCoffeeController will return
     * /src/main/resources/templates/makecoffee.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/makecoffee", "/makecoffee.html" } )
    public String makeCoffeeForm ( final Model model ) {
        return "makecoffee";
    }

    /**
     * On a GET request to /addingredient, the RecipeController will return
     * /src/main/resources/templates/addingredient.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/addingredient", "/addingredient.html" } )
    public String addIngredientPage ( final Model model ) {
        return "addingredient";
    }

    /**
     * On a GET request to /customer, the UserController will return
     * /src/main/resources/templates/customer.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/customer", "/customer.html" } )
    public String customerPage ( final Model model ) {
        return "customer";
    }

    /**
     * On a GET request to /placeorder, the UserController will return
     * /src/main/resources/templates/placeorder.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/placeorder", "/placeorder.html" } )
    public String placeOrderPage ( final Model model ) {
        return "placeorder";
    }

    /**
     * On a GET request to /pickuporder, the UserController will return
     * /src/main/resources/templates/pickuporder.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/pickuporder", "/pickuporder.html" } )
    public String pickupOrderPage ( final Model model ) {
        return "pickuporder";
    }

    /**
     * On a GET request to /worker, the UserController will return
     * /src/main/resources/templates/worker.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/worker", "/worker.html" } )
    public String workerPage ( final Model model ) {
        return "worker";
    }

    /**
     * On a GET request to /manager, the UserController will return
     * /src/main/resources/templates/manager.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/manager", "/manager.html" } )
    public String managerPage ( final Model model ) {
        return "manager";
    }

    /**
     * On a GET request to /orderviewandfulfill, the OrderController will return
     * /src/main/resources/templates/orderviewandfulfill.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/orderviewandfulfill", "/orderviewandfulfill.html" } )
    public String orderViewAndFulfillPage ( final Model model ) {
        return "orderviewandfulfill";
    }

    /**
     * On a GET request to /login, the UserController will return
     * /src/main/resources/templates/login.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/login", "/login.html" } )
    public String loginPage ( final Model model ) {
        return "login";
    }

    /**
     * On a GET request to /signup, the UserController will return
     * /src/main/resources/templates/signup.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/signup", "/signup.html" } )
    public String signupPage ( final Model model ) {
        return "signup";
    }

    /**
     * On a GET request to /adduser, the UserController will return
     * /src/main/resources/templates/adduser.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/adduser", "/adduser.html" } )
    public String addUserPage ( final Model model ) {
        return "adduser";
    }

}

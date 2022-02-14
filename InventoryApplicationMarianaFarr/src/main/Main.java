package main;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * The Inventory Application is an application for managing an inventory
 * of products (which also consist of associated parts) and parts.
 * FUTURE ENHANCEMENT:
 * In the future, it would be helpful if we could make the search functions dynamic, so that the user can see the filtered list
 * as they type. As of now, they have to click the search button to see the results. It would also be helpful to make the search
 * function accept input that is capitalized differently than our data, or to suggest spell check if the user has typos in their
 * search input. This would be even more helpful as our data set grows and users cannot readily see all parts or products on the list.
 * Additionally, we could create an advanced search where users can look up parts/products by inventory, max or min stock, associated parts
 * (if applicable), or by company name or machine ID.
 * It would also be nice to have a sort function that allows the user to sort data by inventory, max/min stock, company name, part name, etc.
 *
 *  Javadocs directory location: Find in another zipped file named "Javadocs.html"
 * @author Mariana Farr
 */


public class Main extends Application {


    /**
     * The start method creates the FXML stage and loads the scene.
     * @param stage Stage for our application.
     * @throws Exception FXML Loader
     */
    @Override
    public void start(Stage stage) throws Exception {

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setTitle("Inventory Application");
            stage.setScene(new Scene(root,800, 600 ));
            stage.show();

        }

    /**
     * main is the entry point of the application.
     *In main, sample objects for parts and products are created, then subsequently added to the Inventory.
     *Finally, the application is launched.
     * @param args the parts and products.
     */
            public static void main(String[] args) {
                //create part objects before any screens load up below (via the launch method), add to inventory.
                InHouse part1 = new InHouse(1, "Brakes", 599.50, 12, 2, 24, 1000);
                InHouse part2 = new InHouse(2, "Horn", 29.60, 30, 15, 50, 2000);
                InHouse part3 = new InHouse(3, "Basket", 20.70, 50, 25, 100, 3000);
                InHouse part4= new InHouse(4,"Handlebars", 20.99, 40,20,80, 4000);

                Outsourced part5 = new Outsourced(5, "Battery", 39.99, 60, 20, 120, "LG");
                Outsourced part6 = new Outsourced(6, "Windshield Wipers", 30.49, 2000, 1000, 4000, "Dry Co.");

                Inventory.addPart(part1);
                Inventory.addPart(part2);
                Inventory.addPart(part3);
                Inventory.addPart(part4);
                Inventory.addPart(part5);
                Inventory.addPart(part6);

                //Create product objects, add its associated parts, and add products to Inventory.
                Product product1 = new Product(10, "Automobile",200.90,22, 10, 400);
                product1.addAssociatedPart(part1);
                product1.addAssociatedPart(part2);
                product1.addAssociatedPart(part5);
                product1.addAssociatedPart(part6);

                Product product2 = new Product(20, "Bike", 500.98,109,20, 500);
                product2.addAssociatedPart(part3);
                product2.addAssociatedPart(part4);


                Inventory.addProduct(product1);
                Inventory.addProduct(product2);
                launch(args);
            }
        }


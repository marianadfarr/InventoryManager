package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Models an inventory of Parts and Products.
 *
 * The class is the data for the Inventory application.
 *
 * @author Mariana Farr
 */
public class Inventory {
    /** Observable list of all parts  - references an observable array list object
     * @param part */


    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**Observable list of all products - references an observable array list object
     * @param product*/

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** adds a part Observable list of all parts  - does not return anything
     * @param part  */
    public static void addPart(Part part) { //when this is called we will pass in a part object onto the AllParts Observable List
        allParts.add(part);
    }

    /**Observable list of filtered parts - references an observable array list object
     * @param ObservableList<Part> allFilteredParts */
    private static ObservableList<Part> allFilteredParts = FXCollections.observableArrayList();

    /**Observable list of filtered products - references an observable array list object*/

    private static ObservableList<Product> allFilteredProducts = FXCollections.observableArrayList();

    /** adds a product to Observable list of all products  - does not return anything */
    public static void addProduct(Product product) //public because it's a method
    { //when this is called we will pass in a product object onto the AllProducts Observable List
        allProducts.add(product); //the add method adds a product to the end of the observable list
    }

    /**
     * Gets a list of all products in inventory.
     *
     * @return A list of product objects.
     */
    public static ObservableList<Product> getAllProducts() {
        //with this method we will return the observable list full of products
        return allProducts;
    }

    /**
     * Gets a list of all parts in inventory.
     *
     * @return A list of parts objects.
     */
    public static ObservableList<Part> getAllParts() {
        //with this method we will return the observable list full of animals.
        return allParts;
    }

    /**
     * Gets a list of all filtered parts in inventory.
     *
     * @return A list of filtered parts objects.
     */
    public static ObservableList<Part> getAllFilteredParts() {
        return allFilteredParts;
    }

    /**
     * Gets a list of all filtered products in inventory.
     *
     * @return A list of filtered products objects.
     */
    public static ObservableList<Product> getAllFilteredProducts() {
        return allFilteredProducts;
    }

    /**
     * Looks up parts by name or partial name
     *
     * @return A list of all parts found with matching criteria
     */

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                partsFound.add(part);
            }
        }

        return partsFound;
    }


    /**
     * Looks up product by name or partial name
     * @param  productName
     * @return A list of all products found with matching criteria
     */


    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
// my code said equals instead of contains, which gave an error
        // Caused by: java.lang.NullPointerException: Cannot invoke "javafx.scene.control.TextField.getText()" because "this.SearchProductTxt" is null
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                productsFound.add(product);
            }
        }

        return productsFound;
    }

    /**Looks up parts by ID
     * @return part with matching ID
     * @param id
     */

    //look up part by ID
    public static Part lookupPart(int id) {
        for (Part part : allParts) {
            if (part.getId() == id) {
                return part;
            }
        }
        return null;


    }

    /**
     * Looks up products by ID
     * @param ID
     * @return product with matching ID
     */



    public static Product lookupProduct(int ID) {
        for (Product product : allProducts) {
            if (product.getId() == ID) {
                return product;
            }
        }
        return null;
    }
        /**
         * Removes the selected part from the allParts list
         * @param selectedPart
         * @return A boolean true if it  was successfully deleted, or false otherwise.
         */


    //delete selected part
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes the selected product from the allParts list
     *
     * @return A boolean true if it  was successfully deleted, or false otherwise.
     */

    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }


}




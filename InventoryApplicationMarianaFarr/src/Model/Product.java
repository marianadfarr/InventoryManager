package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Models Product class - which has information about a product object, including an observable list of Associated Parts of that product
         * @author Mariana Farr
        */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    public Product( int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name
     */

    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */

    public double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */

    public int getStock() {
        return stock;
    }
    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min number of stock
     */

    public int getMin() {
        return min;
    }
    /**
     * @param min the minimum stock to set
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @return the max number of stock
     */

    public int getMax() {
        return max;
    }
    /**
     * @param max the maximum stock to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return associatedParts list
     */

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    /**
     * @param associatedParts the associated parts to set
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    /**
     * @param part The part to add to the associatedParts list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    /**
     * Deletes a part from the associated parts list for the product.
     *
     * @param  selectedAssociatedPart The part to delete
     * @return a boolean confirming or denying deletion.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else
            return false;
    }
}


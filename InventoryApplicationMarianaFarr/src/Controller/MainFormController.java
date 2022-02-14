package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.deletePart;
import static Model.Inventory.deleteProduct;
/**
 * Controller class that provides control logic for the "Main Form" screen of the application.
 Student ID: 008491124

 RUNTIME ERROR-

 Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException

 This error was caused by not properly linking the FXML file that was supposed to load after pressing the "Add" button for the part.
 I left out the ".fxml" part of the string, and after I ran the application and tried to navigate to the Add Part page,
 The application crashed. I fixed this error by properly linking to the correct FXML file.
 */

public class MainFormController implements Initializable {

    Stage stage;
    Parent scene;

    private static Part partToModify;
    private static Product productToModify;
    @FXML
    private Button AddPart;

    @FXML
    private Button AddProduct;

    @FXML
    private Button DeletePart;

    @FXML
    private Button DeleteProduct;

    @FXML
    private Button ModifyPart;

    @FXML
    private Button ModifyProduct;

    @FXML
    private TableView<Part> PartsTableView;

    @FXML
    private TableView<Product> ProductTableView;

    @FXML
    private TableColumn<Part, Integer> PartIDCol;

    @FXML
    private TableColumn<Part, Integer> PartInventoryCol;

    @FXML
    private TableColumn<Part, String> PartNameCol;

    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    @FXML
    private TableColumn<Product, Double> ProductCostCol;

    @FXML
    private TableColumn<Product, Integer> ProductIDCol;

    @FXML
    private TableColumn<Product, Integer> ProductInvCol;

    @FXML
    private TableColumn<Product, String> ProductNameCol;


    @FXML
    private Button SearchByPart;

    @FXML
    private Button SearchByProduct;

    @FXML
    private TextField SearchProductTxt;

    @FXML
    private Button exitMainMenu;

    @FXML
    private TextField searchPartTxt;

    /** This will allow us to publicly have our selected part available for other controllers
     @return partToModify */
    @FXML

    public static Part getPartToModify() {
        return partToModify;
    }
    /** This will allow us to publicly have our selected product available for other controllers
     @return  productToModify */
    public static Product getProductToModify() {

        return productToModify;
    }
    /** This will allow us to go to the Create Part Screen
     @param event create button action.
    @throws IOException From FXMLLoader */

    @FXML
    void OnActionCreatePart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This will allow us to go to the Create Product Screen -
     *  @param event create button action.
     * @throws IOException From FXMLLoader. */
    @FXML
    void OnActionCreateProduct(ActionEvent event) throws IOException { //exception just in case this FXML file doesn't exist , input output error
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This will allow us to go to select, verify selection, and then delete a part.
     * If no part is selected and the delete button is clicked, there is an error displayed
     *   @param event delete part button action.
     *   @throws IOException From FXMLLoader. */
    @FXML
    void OnActionDeletePart(ActionEvent event) throws IOException {
        Part selectedPart;
        selectedPart = PartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Select a part to delete");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the part. Are you sure you want to proceed?"); //alert is an overloaded method
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) { //it will return a boolean if there's a button inside optional container and if it is the ok button
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            deletePart(selectedPart);

        }

    }

    /** This will allow us to go to delete a product after a warning message has been confirmed by the user.
     * If a product has associated parts to it, there will be an error message
     * explaining that products with associated parts cannot be deleted
     * @param event delete product button
     */
    @FXML
    void OnActionDeleteProduct(ActionEvent event) {
        Product selectedProduct;
        selectedProduct = ProductTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Select a product to delete");
            alert.showAndWait();
            return;
        }

        if (selectedProduct.getAssociatedParts().size() > 0)
         {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("You cannot delete a product with associated parts.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the product. Are you sure you want to proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

                try {
                    scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(scene));
                stage.show();
                deleteProduct(selectedProduct);
            }

        }
    }

    /** This will allow us to select a part, then go to the modify screen.
    If no part is selected prior to clicking the button, the user will see an error alert.
      @param event create button action.
     @throws IOException From FXMLLoader. */
    @FXML
    void OnActionModifyPart(ActionEvent event) throws IOException {
        partToModify = PartsTableView.getSelectionModel().getSelectedItem();
//if nothing is selected before pressing Modify Button, show error
        if (partToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("First, select a part to modify");
            alert.showAndWait();
            return;
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ModifyPart.fxml"));
        stage.setScene(new Scene(scene)); //load the scene to the stage
        stage.show();
    }
    /** This will allow us to select a product, then go to the modify screen of that product.
     If no product is selected prior to clicking the button, the user will ser an error alert.
     @param event create button action.
      @throws IOException From FXMLLoader. */
    @FXML
    void OnActionModifyProduct(ActionEvent event) throws IOException {
        productToModify= ProductTableView.getSelectionModel().getSelectedItem();
        //if nothing is selected, error
        if (productToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Select a part to modify");
            alert.showAndWait();
            return;
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ModifyProductForm.fxml"));
        stage.setScene(new Scene(scene)); //load the scene to the stage
        stage.show();
    }
/**  After we click search part button, we look for matching items with part of name or ID.
 We take in user input. If input matches part of or the full name of a part,
 OR, matches ID number, it returns all parts that match.
 If no part is found, it returns an error message.
 Idea for enhancement: make it work with lowercase /non-matching cases.
 Idea for enhancement: make the search list dynamic / give suggestions and matches as user types.
  @param event search part button action */

    @FXML
    void OnActionSearchPart(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        String search = searchPartTxt.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).equals(search) ||
                    part.getName().contains(search)) {
                filteredParts.add(part);
            }
        }

        PartsTableView.setItems(filteredParts);
        if (filteredParts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No parts with that name or ID found");
            alert.showAndWait();

        }


        }

    /** This brings us back to all our part items when search field is empty.
     *  @param event KeyEvent being empty */
    @FXML
    void PartSearchTextEmpty (KeyEvent event) {

        if (searchPartTxt.getText().isEmpty()) {
            PartsTableView.setItems(Inventory.getAllParts());
        }

    }


    /**  After we click search product button, look for matching items with part of name or ID.
     We take in user input. If input matches product name or part of product name;
     OR, matches ID number, it returns all products that match.
     If no product is found, it returns an error message.
     Idea for enhancement: make it work with lowercase /non-matching cases
     Idea for enhancement: make the search list dynamic/ give suggestions and matches as you type
     *   @param event search product button  action. */
    @FXML
    void OnActionSearchProduct(ActionEvent event) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        String search = SearchProductTxt.getText();


        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).equals(search) ||
                    product.getName().contains(search)) {
                filteredProducts.add(product);
            }
        }

        ProductTableView.setItems(filteredProducts);
        if (filteredProducts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No products with that name or ID found");
            alert.showAndWait();
            return;


        }
    }

    /** This brings us back to all our part items when search field is empty
      @param event == empty search bar */
    @FXML
    void ProductSearchTextEmpty (KeyEvent event) {

        if (SearchProductTxt.getText().isEmpty()) {
            ProductTableView.setItems(Inventory.getAllProducts());
        }

    }



    /** Exits the application after confirming with user
     *   @param event Exit button action. */
    @FXML
    void OnActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the application. Are you sure you want to proceed?"); //alert is an overloaded method
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)

        System.exit(0); //exit the program
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartsTableView.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));


        //loads when this screen loads
        ProductTableView.setItems(Inventory.getAllProducts());
        ProductIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));

        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ProductInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        ProductCostCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }}
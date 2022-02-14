package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Controller class that provides control logic for the "Modify Product" screen of the application.

 */


public class ModifyProductController implements Initializable {



    Stage stage;
    Parent scene;
    private Product selectedProduct;

    private ObservableList<Part> AssociatedParts = FXCollections.observableArrayList();


    @FXML
    private TextField SearchText;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<Part> AssociatedPartsTable;

    @FXML
    private Button AddAssociatedPart;
    @FXML
    private TableView<Part> AllPartsTable;
    @FXML
    private TableColumn<Part, Double> AddCostPerUnit;

    @FXML
    private TableColumn<Part, Integer> AddInventoryLevel;

    @FXML
    private TableColumn<Part, Integer> AddPartID;

    @FXML
    private TableColumn<Part, String> AddPartName;

    @FXML
    private TextField AddProductMin;

    @FXML
    private Button CancelButton;

    @FXML
    private TextField InventoryProduct;

    @FXML
    private TextField MachineID;

    @FXML
    private TableColumn<Part, Integer> PartIDRvCOL;

    @FXML
    private TableColumn<Part, Integer> PartInvRemoveCol;

    @FXML
    private TableColumn<Part, String> PartNameRevCol;

    @FXML
    private TextField PriceProduct;

    @FXML
    private TableColumn<Part, Double> PriceRemoveCol;

    @FXML
    private TextField ProductID;

    @FXML
    private TextField ProductMax;

    @FXML
    private TextField ProductName;

    @FXML
    private Button RemoveAssoPart;


    @FXML
    private Button SaveButton;

    /** This button gets a selected part and adds it to the existing AssociatedParts observable list.
     * This list is then set to the AssociatedPartsTable Table View */

    @FXML
    void OnActionAddAssociatedPart(ActionEvent event) {
        Part PartToAdd = AllPartsTable.getSelectionModel().getSelectedItem();
        AssociatedParts = selectedProduct.getAssociatedParts();
        AssociatedParts.add(PartToAdd); // add part to observable list
        AssociatedPartsTable.setItems(AssociatedParts); //set all items to associated parts table

    }

    /** This brings the user back to the main form after they confirm that all changes will be lost if they proceed.
     * @param event Add Associated Part button
     * @throws IOException From FXMLLoader.
     */


    @FXML
    void OnActionMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will not save any changes made");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            stage.setScene(new Scene(scene)); //load the scene to the stage
            stage.show(); //this will bring us to the appropriate screen (main Menu)
        }
    }
    /** This removes a selected associated part from the AssociatedParts observable list after the user confirms.
     * It also removes the associated part from the AssociatedPartsTable TableView.
     * @param event Remove button action
     *
     */

    @FXML
    void OnActionRemoveAssoPart(ActionEvent event) {
         AssociatedParts = selectedProduct.getAssociatedParts();
            Part PartToDelete;
            PartToDelete= AssociatedPartsTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Do you want to remove the selected part?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    AssociatedParts.remove(PartToDelete);
                    AssociatedPartsTable.setItems(AssociatedParts);
        }
    }

    /**  After we click save product button, this throws exceptions for invalid input (if any).
     * If there are any associated parts are added to the AssociatedParts observable list,
     * They are added to the product as well, and to the AssociatedPartsTable TableView.
     * @param event Save button action.
     * @throws IOException From FXMLLoader.
     */

    @FXML
    void OnActionSave(ActionEvent event) throws IOException {


        try {
            if (Integer.parseInt(InventoryProduct.getText()) > Integer.parseInt(ProductMax.getText()) || Integer.parseInt(InventoryProduct.getText()) < Integer.parseInt(AddProductMin.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be a number between the minimum and maximum value. Try again.");
                alert.showAndWait();
            }
           else if (!(Integer.class.isInstance(Integer.parseInt(InventoryProduct.getText())))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value you chose needs to be an integer");
                alert.showAndWait();
            } else if (Integer.parseInt(AddProductMin.getText()) > Integer.parseInt(ProductMax.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid minimum value. It must be less than the maximum value. Try again.");
                    alert.showAndWait();
            } else if (!(Double.class.isInstance(Double.parseDouble(PriceProduct.getText())))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid price (example: 1.50");
                alert.showAndWait();
            }
           else {
                int ID = selectedProduct.getId(); //stays the same
                String name = ProductName.getText();
                int stock = Integer.parseInt(InventoryProduct.getText());
                Double price = Double.parseDouble(PriceProduct.getText());
                int min = Integer.parseInt(AddProductMin.getText());
                int max = Integer.parseInt(ProductMax.getText());
                boolean NewProductAdded;
                Product AddedProduct = new Product(ID, name, price, stock, min, max);


                if (AssociatedParts.size() > 0) { //this adds any associated parts if they exist.
                    AddedProduct.setAssociatedParts(AssociatedParts);
                    Inventory.addProduct(AddedProduct);
                }
                else {

                    Inventory.addProduct(AddedProduct);
                }
                NewProductAdded = true;

                if (NewProductAdded) { //if new product added is successful, delete old part
                        Inventory.deleteProduct(selectedProduct);
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                        stage.setScene(new Scene(scene)); //load the scene to the stage
                        stage.show(); //this will bring us to the appropriate screen (main Menu)

                    }

                }
            } catch(NumberFormatException | IOException e){

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please enter a valid value for all text fields in order to save part");
                alert.showAndWait();
            }
        }

    /**  After we click search product button, look for matching items with part of name or ID.
     We take in user input. If input matches product name or part of product name;
     OR, matches ID number, it returns all products that match.
     If no product is found, it returns an error message.
     * @param event Search button action.
     Idea for enhancement: make it work with lowercase /non-matching cases
     Idea for enhancement: make the search list dynamic/ give suggestions and matches as you type*/
    @FXML
    void onActionSearch(ActionEvent event) {


        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        String search = SearchText.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).equals(search) ||
                    part.getName().contains(search)) {
                filteredParts.add(part);
            }
        }

        AllPartsTable.setItems(filteredParts);
        if (filteredParts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No parts with that name or ID found");
            alert.showAndWait();

        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //fill in the fields with product information
        selectedProduct = MainFormController.getProductToModify();

        ProductID.setText(String.valueOf(selectedProduct.getId()));
        ProductName.setText(selectedProduct.getName());
        InventoryProduct.setText(String.valueOf(selectedProduct.getStock()));
       PriceProduct.setText(String.valueOf(selectedProduct.getPrice()));
        ProductMax.setText(String.valueOf(selectedProduct.getMax()));
        AddProductMin.setText(String.valueOf(selectedProduct.getMin()));

      //set all parts table

        AllPartsTable.setItems(Inventory.getAllParts());
        AddPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        AddPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        AddInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        AddCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("Price"));


        //set associated parts table

        selectedProduct = MainFormController.getProductToModify();
        AssociatedPartsTable.setItems(selectedProduct.getAssociatedParts());
        PartIDRvCOL.setCellValueFactory(new PropertyValueFactory<>("Id"));
       PartNameRevCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInvRemoveCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        PriceRemoveCol.setCellValueFactory(new PropertyValueFactory<>("Price"));



    }
}
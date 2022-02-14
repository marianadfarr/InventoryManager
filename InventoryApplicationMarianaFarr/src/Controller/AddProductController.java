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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import Model.Product;
import Model.Part;





/** Controller class that provides control logic for the "Add Product" screen of the application.

 */
public class AddProductController implements Initializable {


    Stage stage;
    Parent scene;
    Random rand = new Random();

    /** Observable list of Associated parts */

    private ObservableList<Part> AssociatedParts = FXCollections.observableArrayList();

    @FXML
    private Button AddAssociatedPart;

    @FXML
    private TextField SearchTxtField;

    @FXML
    private TextField ProductID;

    @FXML
    private TextField ProductMin;

    @FXML
    private TextField InventoryProduct;

    @FXML
    private TextField PriceProduct;
    @FXML
    private TextField ProductMachineID;

    @FXML
    private TextField ProductMax;

    @FXML
    private TextField ProductName;

    @FXML
    private Button Cancel;

    @FXML
    private TableView<Part> AllPartsTable;

    @FXML
    private TableView<Part> AssociatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> InventoryCol;

    @FXML
    private TableColumn<Part, Integer> InventoryLevelRemove;


    @FXML
    private TableColumn<Part, Integer> PartIDCol;

    @FXML
    private TableColumn<Part, Integer> PartIDRemove;

    @FXML
    private TableColumn<Part, String> PartNameCol;

    @FXML
    private TableColumn<Part, String> PartNameRemove;

    @FXML
    private TableColumn<Part, Double> PriceCol;

    @FXML
    private TableColumn<Part, Double> PriceRemove;


    @FXML
    private Button RemoveAssoPartButton;

    @FXML
    private Button SaveRemoveAssociated;

    @FXML
    private Button SearchProductButton;

/** This saves the new part and directs user to Main Form page when successful.
 * This form also validates user input to prevent IO errors.
 *  We can upgrade by making sure the ID isn't already existing in other Products before adding random number for the ID.
 * @param event Save button action. */
    @FXML
    void OnActionSaveProduct(ActionEvent event) {
        try {
//
            if (!(Integer.class.isInstance(Integer.parseInt(InventoryProduct.getText())))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value you chose needs to be an integer");
                alert.showAndWait();
            } else if (!(Double.class.isInstance(Double.parseDouble(PriceProduct.getText())))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid price (example: 1.50");
                alert.showAndWait();
            } else if (Integer.parseInt(ProductMin.getText()) > Integer.parseInt(ProductMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid minimum value. It must be less than the maximum value");
                alert.showAndWait();
            } else if (Integer.parseInt(InventoryProduct.getText()) > Integer.parseInt(ProductMax.getText()) || Integer.parseInt(InventoryProduct.getText()) < Integer.parseInt(ProductMin.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be a number between the minimum and maximum value.");
                alert.showAndWait();
            } else {
                int Id = rand.nextInt(5, 400); //assigned a random number between 5 and 400.

                String name = ProductName.getText();
                int stock = Integer.parseInt(InventoryProduct.getText());
                int max = Integer.parseInt(ProductMax.getText());
                int min = Integer.parseInt(ProductMin.getText());
                double price = Double.parseDouble(PriceProduct.getText());
                boolean NewProductAdded = false;
                Product NewProduct = new Product(Id, name, price, stock, min, max);
                 //add product to the list - had left this part out and products weren't adding
                if (AssociatedParts.size() > 0) { //this adds any associated parts to the product, if they exist.
                    NewProduct.setAssociatedParts(AssociatedParts);
                }
                Inventory.addProduct(NewProduct);
                NewProductAdded= true;

                if (NewProductAdded) { //if new product is successfully added, go to Main form.
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
    /** This takes the selected part and adds it to the AssociatedParts observable list.
     The Associated Parts Table view then gets all the AssociatedParts from the list and sets them for display
      * @param event Add button action. */
    @FXML
    void OnActionAddPart(ActionEvent event) {
       Part PartToAdd = AllPartsTable.getSelectionModel().getSelectedItem();
        AssociatedParts.add(PartToAdd); // add part to observable list
        AssociatedPartsTable.setItems(AssociatedParts); //set all items to associated parts table

    }


    /** This directs user to the Main Form if they click the exit button, before throwing a warning that all text fields will be cleared.
     * @param event Cancel button action.
     * @throws IOException From FXMLLoader.
     */

    @FXML
    void OnActionMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            stage.setScene(new Scene(scene)); //load the scene to the stage
            stage.show(); //this will bring us to the appropriate screen (main Menu)
        }
    }
/** This takes the selected part and removes it from the AssociatedParts observable list.
 * The Associated Parts Table view then gets all the AssociatedParts from the list and sets them for display.
 * @param event Remove button action. */

    @FXML
    void OnActionRemoveAssociatedPart(ActionEvent event) {
        Part PartToDelete;
        PartToDelete = AssociatedPartsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the part. Are you sure you want to proceed?"); //alert is an overloaded method
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()&&result.get()==ButtonType.OK)

        {
            AssociatedParts.remove(PartToDelete);
            AssociatedPartsTable.setItems(AssociatedParts);

        }
    }

/** This takes user input for an ID or a Product name (or part of the name), and if the product exists it returns that product,
 otherwise; it returns a window that explains that no parts with that Name or ID exist.
  @param event search button action. */

    @FXML
    void OnActionSearchbyProIDorPart(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        String search = SearchTxtField.getText();


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
            return;

        }


    }

    /** This allows the All Parts Table to be repopulated with original parts list after the search field has been emptied.
     *
     @param event keyEvent empty action. */
    @FXML
     void PartSearchTxtEmpty (KeyEvent event) {

        if (SearchTxtField.getText().isEmpty()) {
            AllPartsTable.setItems(Inventory.getAllParts());
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set all parts table
        AllPartsTable.setItems(Inventory.getAllParts());//get, then set items from observable list to AllPartsTableView (original list)
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        //then it will populate the first cell in the ID column
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        InventoryCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        // add associated parts - add /remove parts to the Product

        PartIDRemove.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PartNameRemove.setCellValueFactory(new PropertyValueFactory<>("Name"));
        InventoryLevelRemove.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        PriceRemove.setCellValueFactory(new PropertyValueFactory<>("Price"));

    }
}

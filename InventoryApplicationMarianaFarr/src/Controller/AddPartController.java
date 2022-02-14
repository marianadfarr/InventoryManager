package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Random;
        /** Controller class that provides control logic for the "Add Part" screen of the application.
        */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;
    Random rand = new Random();

    @FXML
    private RadioButton InHouseButton;

    @FXML
    private RadioButton OutsourcedButton;

    @FXML
    private Button PartCancel;

    @FXML
    private TextField PartID;

    @FXML
    private TextField PartInventory;

    @FXML
    private TextField PartMax;

    @FXML
    private TextField PartMin;

    @FXML
    private TextField PartName;

    @FXML
    private TextField PartPrice;

    @FXML
    private Button PartSave;

    @FXML
    private ToggleGroup PartsRadio;
    @FXML
    private Label IDorCompLabel;

    @FXML
    private TextField IDorCompTxt;

/** This sets the label Machine ID if inHouseButton is selected

        @param event In-House Radio button action.  */

    @FXML
    void inHouseRadioButtonSelected(ActionEvent event) {

        IDorCompLabel.setText("Machine ID");
    }

    /** This sets the label Company Name if Outsourced button is selected

     *         @param event Outsourced Radio button action.  */

    @FXML
    void outsourcedRadioButtonSelected(ActionEvent event) {

        IDorCompLabel.setText("Company Name");
    }

    /** This directs users to Main form if Cancel button is pressed.
     * It verifies with the user before proceeding.

     @param event Cancel button action.
     @throws IOException From FXMLLoader.
     */

    @FXML
    void OnActionMainForm(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) { //it will return a boolean if there's a button inside optional container & it is the ok button
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            stage.setScene(new Scene(scene)); //load the scene to the stage
            stage.show(); //this will bring us to the appropriate screen (main Menu)
        }
    }

    /** This saves the new part and directs user to Main Form page when successful.
     This form also validates user input to prevent IO errors.
      @param event Cancel button action.
      @throws IOException From FXMLLoader. */

    @FXML
    void OnActionSavePart(ActionEvent event) throws IOException {


            try{
                 if (!(Integer.class.isInstance(Integer.parseInt(PartInventory.getText())))){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value you chose needs to be an integer");
                    alert.showAndWait();
                } else if (!(Double.class.isInstance(Double.parseDouble(PartPrice.getText())))){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid price (example: 1.50");
                    alert.showAndWait();
                }
                else if (Integer.parseInt(PartMin.getText()) > Integer.parseInt(PartMax.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid minimum value. It must be less than the maximum value");
                    alert.showAndWait();
                } else if (Integer.parseInt(PartInventory.getText()) > Integer.parseInt(PartMax.getText()) || Integer.parseInt(PartInventory.getText()) < Integer.parseInt(PartMin.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be a number between the minimum and maximum value.");
                    alert.showAndWait();
                }else {
                   int id = rand.nextInt(5,400); //assigned a random number between 5 and 400.
                     //we can upgrade by making sure the ID isn't already existing.
                    String name = PartName.getText();
                    int stock = Integer.parseInt(PartInventory.getText());
                    int min = Integer.parseInt(PartMin.getText());
                    int max = Integer.parseInt(PartMax.getText());
                    double price = Double.parseDouble(PartPrice.getText());
                    boolean isInHouse;
                    int machineId;
                    String companyName;


                    if  (InHouseButton.isSelected()) { //add InHousePart
                        isInHouse = true;
                        machineId = Integer.parseInt(IDorCompTxt.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max,machineId);
                        Inventory.addPart(newInHousePart);


                    }
                    else { //addOutsourcedPart
                        isInHouse = false;
                        companyName =IDorCompTxt.getText();
                        Outsourced NewOutsourcedPart=  new Outsourced(id, name, price, stock, min, max, companyName);
                        Inventory.addPart(NewOutsourcedPart);
                    }

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                    stage.setScene(new Scene(scene)); //load the scene to the stage
                    stage.show(); //this will bring us to the appropriate screen (main Menu)
                }


            }

            catch(NumberFormatException e){

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please enter a valid value for all text fields in order to save part");
                alert.showAndWait();

            }
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

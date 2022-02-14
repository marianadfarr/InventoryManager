package Controller;

import Model.*;
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
/**
 * Controller class that provides control logic for the "Modify Part" screen of the application.

 Student ID: 008491124

 */
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    private Part selectedPart;


    @FXML
    private Button CancelButton;

    @FXML
    private Label CompOrMachineID;

    @FXML
    private RadioButton InHouseButton;

    @FXML
    private TextField ModifyCompany;

    @FXML
    private TextField ModifyID;

    @FXML
    private TextField ModifyInventory;

    @FXML
    private TextField ModifyMax;

    @FXML
    private TextField ModifyMin;

    @FXML
    private TextField ModifyName;

    @FXML
    private TextField ModifyPrice;

    @FXML
    private RadioButton OutsourcedButton;

    @FXML
    private Button SaveButton;
    /** This selects the InHouse Button
     @param event InHouse button action. */
    @FXML
    void InHouseRadioButtonSelected(ActionEvent event) {

        CompOrMachineID.setText("Machine ID");
    }
    /** This selects the outsourced Button
    @param event outsourced button action. */
    @FXML
    void OutsourcedButtonSelected(ActionEvent event) {

        CompOrMachineID.setText("Company Name");
    }


    /** This saves the new and updated part - InHouse or Outsourced, depending on which radio button is selected.
     *  This deletes the old part.
     * It also directs user to Main Form page when successful *
     * This form also validates user input to prevent IO errors.
      @param event Save button action.
     */


    @FXML
    void OnActionSave(ActionEvent event) { //make distinction between in-house and outsourced
        try {

            if (!(Integer.class.isInstance(Integer.parseInt(ModifyInventory.getText())))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value you chose needs to be an integer");
                alert.showAndWait();
            } else if (!(Double.class.isInstance(Double.parseDouble(ModifyPrice.getText())))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid price (example: 1.50");
                alert.showAndWait();
            } else if (Integer.parseInt(ModifyMin.getText()) > Integer.parseInt(ModifyMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid minimum value. It must be less than the maximum value");
                alert.showAndWait();
            } else if (Integer.parseInt(ModifyInventory.getText()) > Integer.parseInt(ModifyMax.getText()) || Integer.parseInt(ModifyInventory.getText()) < Integer.parseInt(ModifyMin.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be a number between the minimum and maximum value.");
                alert.showAndWait();
            } else {
                int id = selectedPart.getId(); //stays the same
                String name = ModifyName.getText();
                int stock = Integer.parseInt(ModifyInventory.getText());
                Double price = Double.parseDouble(ModifyPrice.getText());
                int min = Integer.parseInt(ModifyMin.getText());
                int max = Integer.parseInt(ModifyMax.getText());
                boolean isInHouse;
                boolean NewPartAdded = false;
                int machineId;
                String companyName;


                if (InHouseButton.isSelected()) { //if this button is pressed
                    isInHouse = true;
                    machineId = Integer.parseInt(ModifyCompany.getText());
                    InHouse AddedInHouse = new InHouse(id, name, price, stock, max, min, machineId);
                    Inventory.addPart(AddedInHouse);
                    NewPartAdded = true;


                } else {
                    isInHouse = false;
                    companyName = ModifyCompany.getText();
                    Outsourced AddedOutsourced = new Outsourced(id, name, price, stock, max, min, companyName);
                    Inventory.addPart(AddedOutsourced);
                    NewPartAdded = true;
                }

                {
                    if (NewPartAdded) { //if new part added is successful, delete old part
                        Inventory.deletePart(selectedPart);

                    }
                } stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(scene)); //load the scene to the stage
                stage.show(); //this will bring us to the appropriate screen (main Menu)

            }


        } catch (NumberFormatException | IOException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for all text fields in order to save part");
            alert.showAndWait();
        }
    }

    /** This brings the user back to the main form after they confirm that all changes will be lost if they proceed.
     * @param event cancel button
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void OnActionMainForm(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values"); //alert is an overloaded method
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene)); //load the scene to the stage
            stage.show(); //this will bring us to the appropriate screen (main Menu)
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = MainFormController.getPartToModify();

        if (selectedPart instanceof InHouse) {
            InHouseButton.setSelected(true);
            CompOrMachineID.setText("Machine ID");
            ModifyCompany.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof Outsourced){
            OutsourcedButton.setSelected(true);
            CompOrMachineID.setText("Company Name");
            ModifyCompany.setText(((Outsourced) selectedPart).getCompanyName());
        }

       ModifyID.setText(String.valueOf(selectedPart.getId()));
        ModifyName.setText(selectedPart.getName());
        ModifyInventory.setText(String.valueOf(selectedPart.getStock()));
        ModifyPrice.setText(String.valueOf(selectedPart.getPrice()));
        ModifyMax.setText(String.valueOf(selectedPart.getMax()));
      ModifyMin.setText(String.valueOf(selectedPart.getMin()));
    }

    }


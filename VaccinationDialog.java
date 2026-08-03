import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * VaccinationDialog - records a single vaccination shot for an animal.
 * Creates a fresh VaccinationType per entry for simplicity; if you later want to
 * track "3rd dose of the same vaccine type" properly, this is the place to add
 * a lookup against existing VaccinationTypes instead of always creating a new one.
 */
public class VaccinationDialog {

    public static void open(Animal animal, Runnable onSaved) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Vaccination");

        TextField vaccineNameField = new TextField();
        DatePicker datePicker = new DatePicker(LocalDate.now());
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        saveButton.setOnAction(e -> {
            String vaccineName = vaccineNameField.getText();
            if (vaccineName == null || vaccineName.isBlank()) {
                errorLabel.setText("Vaccine name is required.");
                return;
            }

            VaccinationType type = new VaccinationType(vaccineName);
            type.setVaccinationList(new ArrayList<>()); // avoid NPE - constructor doesn't initialize this
            Vaccination vaccination = new Vaccination(type, datePicker.getValue());
            type.addVaccinationDose(vaccination);

            animal.addVaccine(vaccination);
            stage.close();
            if (onSaved != null) onSaved.run();
        });
        cancelButton.setOnAction(e -> stage.close());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        grid.add(new Label("Vaccine name:"), 0, 0);
        grid.add(vaccineNameField, 1, 0);
        grid.add(new Label("Date given:"), 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(errorLabel, 0, 2, 2, 1);
        grid.add(new HBox(10, saveButton, cancelButton), 0, 3, 2, 1);

        stage.setScene(new Scene(grid));
        stage.showAndWait();
    }
}

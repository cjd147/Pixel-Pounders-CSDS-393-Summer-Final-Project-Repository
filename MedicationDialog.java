import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * MedicationDialog - records a medication or dewormer given to an animal.
 * Dosage fields are optional - leave the amount fields blank to skip recording
 * a dose, since not every entry needs one (e.g. a one-off treatment note).
 */
public class MedicationDialog {

    public static void open(Animal animal, Runnable onSaved) {
        open(animal, false, onSaved);
    }

    public static void open(Animal animal, boolean presetAsDewormer, Runnable onSaved) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(presetAsDewormer ? "Add Dewormer" : "Add Medication");

        TextField nameField = new TextField();
        DatePicker datePicker = new DatePicker(LocalDate.now());
        CheckBox isDewormerBox = new CheckBox("This is a dewormer");
        isDewormerBox.setSelected(presetAsDewormer);
        isDewormerBox.setDisable(presetAsDewormer); // locked - button already decided this

        TextField medAmountField = new TextField();
        TextField medUnitField = new TextField();
        medUnitField.setPromptText("e.g. ml");
        TextField weightAmountField = new TextField();
        TextField weightUnitField = new TextField();
        weightUnitField.setPromptText("e.g. lbs");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            if (name == null || name.isBlank()) {
                errorLabel.setText("Name is required.");
                return;
            }

            Medication medication = isDewormerBox.isSelected() ? new Dewormer() : new Medication();
            medication.setName(name);
            medication.setDiagnoseDate(datePicker.getValue());

            // Dosage is optional - only set it if both amount fields were filled in
            String medAmountText = medAmountField.getText();
            String weightAmountText = weightAmountField.getText();
            if (medAmountText != null && !medAmountText.isBlank()
                    && weightAmountText != null && !weightAmountText.isBlank()) {
                try {
                    double medAmount = Double.parseDouble(medAmountText);
                    double weightAmount = Double.parseDouble(weightAmountText);
                    Measurement medMeasurement = new Measurement(medAmount, medUnitField.getText());
                    Measurement weightMeasurement = new Measurement(weightAmount, weightUnitField.getText());
                    Dosage dosage = new Dosage(medMeasurement, weightMeasurement);
                    medication.setDosage(animal.getClass(), dosage);
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Dosage amounts must be numbers.");
                    return;
                }
            }

            if (isDewormerBox.isSelected()) {
                animal.addDewormer((Dewormer) medication);
            } else {
                animal.addMedication(medication);
            }

            stage.close();
            if (onSaved != null) onSaved.run();
        });
        cancelButton.setOnAction(e -> stage.close());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        int row = 0;
        grid.add(new Label("Name:"), 0, row);
        grid.add(nameField, 1, row++);
        grid.add(new Label("Date given:"), 0, row);
        grid.add(datePicker, 1, row++);
        grid.add(isDewormerBox, 0, row++, 2, 1);
        grid.add(new Separator(), 0, row++, 2, 1);
        grid.add(new Label("Dosage (optional):"), 0, row++, 2, 1);
        grid.add(new Label("Medication amount:"), 0, row);
        grid.add(medAmountField, 1, row++);
        grid.add(new Label("Medication unit:"), 0, row);
        grid.add(medUnitField, 1, row++);
        grid.add(new Label("Per body weight:"), 0, row);
        grid.add(weightAmountField, 1, row++);
        grid.add(new Label("Weight unit:"), 0, row);
        grid.add(weightUnitField, 1, row++);
        grid.add(errorLabel, 0, row++, 2, 1);
        grid.add(new HBox(10, saveButton, cancelButton), 0, row, 2, 1);

        stage.setScene(new Scene(grid));
        stage.showAndWait();
    }
}

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.function.BiConsumer;

/**
 * MedicalEntryDialog - a small reusable "name + date" form.
 * Used for creating MedicalCondition and MedicalProcedure entries, which don't
 * need anything beyond a name and the date it was diagnosed/performed.
 */
public class MedicalEntryDialog {

    // dateFieldLabel lets the caller say "Diagnosed on" vs "Performed on" etc.
    public static void open(String windowTitle, String dateFieldLabel, BiConsumer<String, LocalDate> onSubmit) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(windowTitle);

        TextField nameField = new TextField();
        DatePicker datePicker = new DatePicker(LocalDate.now());
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button submitButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            if (name == null || name.isBlank()) {
                errorLabel.setText("Name is required.");
                return;
            }
            onSubmit.accept(name, datePicker.getValue());
            stage.close();
        });
        cancelButton.setOnAction(e -> stage.close());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label(dateFieldLabel + ":"), 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(errorLabel, 0, 2, 2, 1);
        grid.add(new HBox(10, submitButton, cancelButton), 0, 3, 2, 1);

        stage.setScene(new Scene(grid));
        stage.showAndWait();
    }
}

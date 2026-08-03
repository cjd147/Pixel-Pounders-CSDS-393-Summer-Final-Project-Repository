import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * AddAnimalDialog - lets the user create a new animal within a specific herd.
 * The herd already knows its animal type (Sheep.class, Cow.class, etc.), so this
 * dialog uses reflection on that type's (nickname, isFemale) constructor rather than
 * asking the user to pick a species - it always matches the herd it's added to.
 */
public class AddAnimalDialog {

    // opens the dialog; calls onAnimalAdded (if the user confirms) so the caller can refresh its UI
    @SuppressWarnings("unchecked")
    public static <T extends Animal> void open(Herd<T> herd, Runnable onAnimalAdded) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Animal to " + herd.getHerdName());

        TextField nicknameField = new TextField();
        ComboBox<String> sexBox = new ComboBox<>();
        sexBox.getItems().addAll("Female", "Male");
        sexBox.getSelectionModel().selectFirst();

        // Parents are optional and chosen from existing members of this herd
        ComboBox<Animal> motherBox = new ComboBox<>();
        motherBox.getItems().add(null); // represents "None"
        motherBox.getItems().addAll((List<Animal>) herd.getMembers());
        motherBox.setPromptText("None");
        setAnimalCellFactory(motherBox);

        ComboBox<Animal> fatherBox = new ComboBox<>();
        fatherBox.getItems().add(null);
        fatherBox.getItems().addAll((List<Animal>) herd.getMembers());
        fatherBox.setPromptText("None");
        setAnimalCellFactory(fatherBox);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");

        addButton.setOnAction(e -> {
            String nickname = nicknameField.getText();
            if (nickname == null || nickname.isBlank()) {
                errorLabel.setText("Nickname is required.");
                return;
            }
            boolean isFemale = "Female".equals(sexBox.getValue());

            try {
                Class<T> animalType = herd.getAnimalType();
                Constructor<T> constructor = animalType.getConstructor(String.class, boolean.class);
                T newAnimal = constructor.newInstance(nickname, isFemale);

                Animal mother = motherBox.getValue();
                Animal father = fatherBox.getValue();
                if (mother != null || father != null) {
                    newAnimal.setParents(mother, father);
                }

                herd.add(newAnimal);
                stage.close();
                if (onAnimalAdded != null) {
                    onAnimalAdded.run();
                }
            } catch (ReflectiveOperationException ex) {
                errorLabel.setText("Could not create animal: " + ex.getMessage());
            }
        });

        cancelButton.setOnAction(e -> stage.close());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        grid.add(new Label("Nickname:"), 0, 0);
        grid.add(nicknameField, 1, 0);
        grid.add(new Label("Sex:"), 0, 1);
        grid.add(sexBox, 1, 1);
        grid.add(new Label("Mother:"), 0, 2);
        grid.add(motherBox, 1, 2);
        grid.add(new Label("Father:"), 0, 3);
        grid.add(fatherBox, 1, 3);
        grid.add(errorLabel, 0, 4, 2, 1);

        HBoxButtons buttons = new HBoxButtons(addButton, cancelButton);
        grid.add(buttons.box, 0, 5, 2, 1);

        stage.setScene(new Scene(grid));
        stage.showAndWait();
    }

    private static void setAnimalCellFactory(ComboBox<Animal> box) {
        box.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Animal animal, boolean empty) {
                super.updateItem(animal, empty);
                setText(empty ? null : (animal == null ? "None" : animal.getNickname()));
            }
        });
        box.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Animal animal, boolean empty) {
                super.updateItem(animal, empty);
                setText(empty ? null : (animal == null ? "None" : animal.getNickname()));
            }
        });
    }

    // small helper so the grid.add(...) call above stays readable
    private static class HBoxButtons {
        final javafx.scene.layout.HBox box;
        HBoxButtons(Button... buttons) {
            box = new javafx.scene.layout.HBox(10, buttons);
        }
    }
}

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * MedicalRecordsUI - shows one animal's medical history: conditions, procedures,
 * vaccinations, medications, dewormer history, and general notes.
 * Adding a note is supported now; adding new conditions/procedures/etc. would need
 * their own forms (test types, dosages, date pickers) - noted as a next step.
 */
public class MedicalRecordsUI {

    public static void openWindow(Animal animal) {
        Stage stage = new Stage();
        stage.setTitle("Medical Records - " + animal.getNickname());

        VBox root = new VBox(12);
        root.setPadding(new Insets(15));

        root.getChildren().add(sectionLabel("Medical Conditions"));
        root.getChildren().add(namesList(animal.getMedicalConditionsList().stream()
                .map(MedicalData::getName).toList()));

        root.getChildren().add(sectionLabel("Medical Procedures"));
        root.getChildren().add(namesList(animal.getMedicalProceduresList().stream()
                .map(MedicalData::getName).toList()));

        root.getChildren().add(sectionLabel("Vaccinations"));
        root.getChildren().add(namesList(animal.getVaccineRecord().stream()
                .map(v -> v.getVaccinationType() != null ? v.getVaccinationType().getVaccineName() : "(unnamed)")
                .toList()));

        root.getChildren().add(sectionLabel("Medications"));
        root.getChildren().add(namesList(animal.getMedicationList().stream()
                .map(MedicalData::getName).toList()));

        root.getChildren().add(sectionLabel("Dewormer History"));
        root.getChildren().add(namesList(animal.getDewormerHistory().stream()
                .map(MedicalData::getName).toList()));

        root.getChildren().add(sectionLabel("Notes"));
        ListView<Note> notesListView = new ListView<>();
        notesListView.getItems().addAll(animal.getNotes());
        notesListView.setPrefHeight(120);
        root.getChildren().add(notesListView);

        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField noteField = new TextField();
        noteField.setPromptText("Note text");
        Button addNoteButton = new Button("Add Note");

        addNoteButton.setOnAction(e -> {
            String author = authorField.getText();
            String text = noteField.getText();
            if (author == null || author.isBlank() || text == null || text.isBlank()) {
                return;
            }
            animal.addNote(author, text);
            notesListView.getItems().setAll(animal.getNotes());
            authorField.clear();
            noteField.clear();
        });

        HBox addNoteRow = new HBox(8, authorField, noteField, addNoteButton);
        root.getChildren().add(addNoteRow);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        stage.setScene(new Scene(scrollPane, 450, 600));
        stage.show();
    }

    private static Label sectionLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }

    private static ListView<String> namesList(java.util.List<String> names) {
        ListView<String> listView = new ListView<>();
        if (names.isEmpty()) {
            listView.getItems().add("(none yet)");
        } else {
            listView.getItems().addAll(names);
        }
        listView.setPrefHeight(70);
        return listView;
    }
}

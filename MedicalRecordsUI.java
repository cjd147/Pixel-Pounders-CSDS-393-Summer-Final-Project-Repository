import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * MedicalRecordsUI - shows one animal's medical history: conditions, procedures,
 * vaccinations, medications, dewormer history, and general notes. Each section has
 * an "Add" button to record a new entry.
 */
public class MedicalRecordsUI {

    public static void openWindow(Animal animal) {
        Stage stage = new Stage();
        stage.setTitle("Medical Records - " + animal.getNickname());

        VBox root = new VBox(12);
        root.setPadding(new Insets(15));

        ListView<String> conditionsView = namesList(animal);
        ListView<String> proceduresView = namesList(animal);
        ListView<String> vaccinationsView = namesList(animal);
        ListView<String> medicationsView = namesList(animal);
        ListView<String> dewormersView = namesList(animal);

        Button addConditionBtn = new Button("Add");
        Button addProcedureBtn = new Button("Add");
        Button addVaccinationBtn = new Button("Add");
        Button addMedicationBtn = new Button("Add");
        Button addDewormerBtn = new Button("Add");

        addConditionBtn.setOnAction(e -> MedicalEntryDialog.open("Add Medical Condition", "Diagnosed on", (name, date) -> {
            MedicalCondition condition = new MedicalCondition();
            condition.setName(name);
            condition.setDiagnoseDate(date);
            animal.addMedicalCondition(condition);
            refresh(conditionsView, animal.getMedicalConditionsList().stream().map(MedicalData::getName).toList());
        }));

        addProcedureBtn.setOnAction(e -> MedicalEntryDialog.open("Add Medical Procedure", "Performed on", (name, date) -> {
            MedicalProcedure procedure = new MedicalProcedure();
            procedure.setName(name);
            procedure.setDiagnoseDate(date);
            animal.addMedicalProcedure(procedure);
            refresh(proceduresView, animal.getMedicalProceduresList().stream().map(MedicalData::getName).toList());
        }));

        addVaccinationBtn.setOnAction(e -> VaccinationDialog.open(animal, () ->
                refresh(vaccinationsView, animal.getVaccineRecord().stream()
                        .map(v -> v.getVaccinationType() != null ? v.getVaccinationType().getVaccineName() : "(unnamed)")
                        .toList())));

        addMedicationBtn.setOnAction(e -> MedicationDialog.open(animal, () -> {
            refresh(medicationsView, animal.getMedicationList().stream().map(MedicalData::getName).toList());
            refresh(dewormersView, animal.getDewormerHistory().stream().map(MedicalData::getName).toList());
        }));

        addDewormerBtn.setOnAction(e -> MedicationDialog.open(animal, true, () -> {
            refresh(medicationsView, animal.getMedicationList().stream().map(MedicalData::getName).toList());
            refresh(dewormersView, animal.getDewormerHistory().stream().map(MedicalData::getName).toList());
        }));

        // initial population
        refresh(conditionsView, animal.getMedicalConditionsList().stream().map(MedicalData::getName).toList());
        refresh(proceduresView, animal.getMedicalProceduresList().stream().map(MedicalData::getName).toList());
        refresh(vaccinationsView, animal.getVaccineRecord().stream()
                .map(v -> v.getVaccinationType() != null ? v.getVaccinationType().getVaccineName() : "(unnamed)")
                .toList());
        refresh(medicationsView, animal.getMedicationList().stream().map(MedicalData::getName).toList());
        refresh(dewormersView, animal.getDewormerHistory().stream().map(MedicalData::getName).toList());

        root.getChildren().add(sectionRow("Medical Conditions", addConditionBtn));
        root.getChildren().add(conditionsView);
        root.getChildren().add(sectionRow("Medical Procedures", addProcedureBtn));
        root.getChildren().add(proceduresView);
        root.getChildren().add(sectionRow("Vaccinations", addVaccinationBtn));
        root.getChildren().add(vaccinationsView);
        root.getChildren().add(sectionRow("Medications", addMedicationBtn));
        root.getChildren().add(medicationsView);
        root.getChildren().add(sectionRow("Dewormer History", addDewormerBtn));
        root.getChildren().add(dewormersView);

        root.getChildren().add(sectionRow("Notes", null));
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

        stage.setScene(new Scene(scrollPane, 480, 700));
        stage.show();
    }

    private static HBox sectionRow(String title, Button addButton) {
        Label label = new Label(title);
        label.setStyle("-fx-font-weight: bold;");
        HBox row = new HBox(10, label);
        if (addButton != null) {
            row.getChildren().add(addButton);
        }
        return row;
    }

    private static ListView<String> namesList(Animal animal) {
        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(70);
        return listView;
    }

    private static void refresh(ListView<String> listView, java.util.List<String> names) {
        listView.getItems().clear();
        if (names.isEmpty()) {
            listView.getItems().add("(none yet)");
        } else {
            listView.getItems().addAll(names);
        }
    }
}

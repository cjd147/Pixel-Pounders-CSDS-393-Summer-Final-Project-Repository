import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

/**
 * FarmUI - main entry point. Shows herds on this farm, lets you pick an animal
 * within a herd, add new animals, view its ancestor/descendant tree, and view
 * its medical records.
 */
public class FarmUI extends Application {

    private Farm farm;
    private ListView<Herd> herdListView;
    private ListView<Animal> animalListView;

    @Override
    public void start(Stage stage) {
        farm = buildSampleFarm(); // TODO: replace with real persisted data once you have it

        Label title = new Label("Farm Manager - " + farm.getFarmName());
        title.setFont(Font.font("SansSerif", FontWeight.BOLD, 18));

        // Left: herd list
        herdListView = new ListView<>();
        herdListView.getItems().addAll(farm.getFarmHerds());
        herdListView.setCellFactory(list -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Herd herd, boolean empty) {
                super.updateItem(herd, empty);
                setText(empty || herd == null ? null : herd.getHerdName() + " (" + herd.getMembers().size() + ")");
            }
        });

        Button addAnimalButton = new Button("Add Animal");
        addAnimalButton.setDisable(true);

        herdListView.getSelectionModel().selectedItemProperty().addListener((obs, oldHerd, newHerd) -> {
            refreshAnimalList(newHerd);
            addAnimalButton.setDisable(newHerd == null);
        });

        addAnimalButton.setOnAction(e -> {
            Herd selectedHerd = herdListView.getSelectionModel().getSelectedItem();
            if (selectedHerd != null) {
                AddAnimalDialog.open(selectedHerd, () -> {
                    herdListView.refresh(); // updates the "(count)" label
                    refreshAnimalList(selectedHerd);
                });
            }
        });

        // Right: animal list for the selected herd
        animalListView = new ListView<>();
        animalListView.setCellFactory(list -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Animal animal, boolean empty) {
                super.updateItem(animal, empty);
                setText(empty || animal == null ? null : animal.getNickname());
            }
        });

        Button viewAncestorsBtn = new Button("View Ancestors");
        Button viewDescendantsBtn = new Button("View Descendants");
        Button medicalRecordsBtn = new Button("Medical Records");
        viewAncestorsBtn.setDisable(true);
        viewDescendantsBtn.setDisable(true);
        medicalRecordsBtn.setDisable(true);

        animalListView.getSelectionModel().selectedItemProperty().addListener((obs, oldAnimal, newAnimal) -> {
            boolean hasSelection = newAnimal != null;
            viewAncestorsBtn.setDisable(!hasSelection);
            viewDescendantsBtn.setDisable(!hasSelection);
            medicalRecordsBtn.setDisable(!hasSelection);
        });

        viewAncestorsBtn.setOnAction(e -> {
            Animal selected = animalListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                AnimalTree tree = new AnimalTree(selected);
                GenealogyTreeUI.openWindow(tree.getAncestors(), "Ancestors of " + selected.getNickname());
            }
        });

        viewDescendantsBtn.setOnAction(e -> {
            Animal selected = animalListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                AnimalTree tree = new AnimalTree(selected);
                GenealogyTreeUI.openWindow(tree.getDescendants(), "Descendants of " + selected.getNickname());
            }
        });

        medicalRecordsBtn.setOnAction(e -> {
            Animal selected = animalListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                MedicalRecordsUI.openWindow(selected);
            }
        });

        HBox buttonRow = new HBox(10, viewAncestorsBtn, viewDescendantsBtn, medicalRecordsBtn);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setPadding(new Insets(10, 0, 0, 0));

        VBox herdPane = new VBox(10, new Label("Herds"), herdListView, addAnimalButton);
        VBox animalPane = new VBox(10, new Label("Animals"), animalListView, buttonRow);

        HBox content = new HBox(15, herdPane, animalPane);
        content.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setTop(title);
        root.setCenter(content);
        BorderPane.setMargin(title, new Insets(0, 0, 10, 0));

        stage.setTitle("Farm Manager");
        stage.setScene(new Scene(root, 750, 480));
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private void refreshAnimalList(Herd newHerd) {
        animalListView.getItems().clear();
        if (newHerd != null) {
            List<Animal> members = newHerd.getMembers();
            animalListView.getItems().addAll(members);
        }
    }

    // Builds a small in-memory farm with real Herd/Animal objects, including a
    // multi-generation sheep pedigree so the tree view has something to show.
    // TODO: replace this with loading real farm data once you have persistence set up.
    private Farm buildSampleFarm() {
        Farm sampleFarm = new Farm("Pixel Pounders Farm");

        Herd<Sheep> sheepHerd = new Herd<>("Sheep Flock A", Sheep.class);
        Sheep grandma = new Sheep("Grandma", true);
        Sheep grandpa = new Sheep("Grandpa", false);
        Sheep mom = new Sheep("Mom", true);
        mom.setParents(grandma, grandpa);
        Sheep kid1 = new Sheep("Kid1", true);
        Sheep kid2 = new Sheep("Kid2", false);
        kid1.setParents(mom, null);
        kid2.setParents(mom, null);
        sheepHerd.add(grandma);
        sheepHerd.add(grandpa);
        sheepHerd.add(mom);
        sheepHerd.add(kid1);
        sheepHerd.add(kid2);

        Herd<Cow> cowHerd = new Herd<>("Dairy Herd", Cow.class);
        cowHerd.add(new Cow("Bessie", true));
        cowHerd.add(new Cow("Ferdinand", false));

        Herd<Goat> goatHerd = new Herd<>("Goat Group", Goat.class);
        goatHerd.add(new Goat("Billy", false));
        goatHerd.add(new Goat("Nan", true));

        sampleFarm.addFarmHerd(sheepHerd);
        sampleFarm.addFarmHerd(cowHerd);
        sampleFarm.addFarmHerd(goatHerd);

        return sampleFarm;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

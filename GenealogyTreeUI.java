import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

/**
 * GenealogyTreeUI - renders an AnimalTree.Node structure (ancestors or descendants)
 * as a simple visual diagram.
 *
 * This is NOT a JavaFX Application itself - call openWindow(...) from inside an already-running
 * JavaFX app (e.g. from a button handler in FarmUI) to pop up a new window with the tree.
 *
 * Usage:
 *   AnimalTree tree = new AnimalTree(someAnimal);
 *   GenealogyTreeUI.openWindow(tree.getAncestors(), "Ancestors of " + someAnimal.getNickname());
 */
public class GenealogyTreeUI {

    private static final int NODE_WIDTH = 140;
    private static final int NODE_HEIGHT = 40;
    private static final int H_GAP = 30;
    private static final int V_GAP = 80;

    // Opens a new window showing the given tree. Safe to call multiple times/from event handlers.
    public static void openWindow(AnimalTree.Node root, String title) {
        Group canvas = new Group();
        if (root == null) {
            canvas.getChildren().add(new Text(20, 20, "No data to display."));
        } else {
            drawNode(canvas, root, 800, 40, 400);
        }

        ScrollPane scrollPane = new ScrollPane(canvas);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(scrollPane, 900, 600));
        stage.show();
    }

    private static void drawNode(Group canvas, AnimalTree.Node node, double x, double y, double horizontalSpread) {
        if (node == null) return;

        Rectangle box = new Rectangle(x - NODE_WIDTH / 2.0, y, NODE_WIDTH, NODE_HEIGHT);
        box.setArcWidth(12);
        box.setArcHeight(12);
        box.setFill(Color.rgb(220, 235, 220));
        box.setStroke(Color.DARKGRAY);

        String label = node.getAnimal() != null ? node.getAnimal().getNickname() : "Unknown";
        Text text = new Text(label);
        text.setX(x - text.getLayoutBounds().getWidth() / 2);
        text.setY(y + NODE_HEIGHT / 2.0 + 4);

        canvas.getChildren().addAll(box, text);

        double childY = y + V_GAP;

        if (node.getMother() != null || node.getFather() != null) {
            // Ancestor-style node: mother on left, father on right
            if (node.getMother() != null) {
                double childX = x - horizontalSpread;
                drawEdge(canvas, x, y + NODE_HEIGHT, childX, childY);
                drawNode(canvas, node.getMother(), childX, childY, Math.max(horizontalSpread / 2, NODE_WIDTH));
            }
            if (node.getFather() != null) {
                double childX = x + horizontalSpread;
                drawEdge(canvas, x, y + NODE_HEIGHT, childX, childY);
                drawNode(canvas, node.getFather(), childX, childY, Math.max(horizontalSpread / 2, NODE_WIDTH));
            }
        } else if (node.getChildren() != null && !node.getChildren().isEmpty()) {
            // Descendant-style node: spread all children evenly
            List<AnimalTree.Node> kids = node.getChildren();
            int count = kids.size();
            double totalWidth = (count - 1) * (NODE_WIDTH + H_GAP);
            double startX = x - totalWidth / 2;
            for (int i = 0; i < count; i++) {
                double childX = startX + i * (NODE_WIDTH + H_GAP);
                drawEdge(canvas, x, y + NODE_HEIGHT, childX, childY);
                drawNode(canvas, kids.get(i), childX, childY, Math.max(horizontalSpread / 2, NODE_WIDTH));
            }
        }
    }

    private static void drawEdge(Group canvas, double x1, double y1, double x2, double y2) {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(Color.GRAY);
        canvas.getChildren().add(line);
    }
}

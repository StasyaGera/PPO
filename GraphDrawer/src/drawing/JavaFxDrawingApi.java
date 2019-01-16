package drawing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class JavaFxDrawingApi extends Application implements DrawingApi {
    private static int W = 1000;
    private static int H = 600;
    private static List<Line> edges = new ArrayList<>();
    private static List<Circle> nodes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Graph by JavaFX");
        Group root = new Group();
        Canvas canvas = new Canvas(W, H);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Line edge: edges) {
            gc.beginPath();
            gc.moveTo(edge.getStartX(), edge.getStartY());
            gc.lineTo(edge.getEndX(), edge.getEndY());
            gc.stroke();
            gc.closePath();
        }
        for (int i = 0; i < nodes.size(); i++) {
            double x = nodes.get(i).getCenterX(), y = nodes.get(i).getCenterY();
            double r = nodes.get(i).getRadius();
            gc.setFill(Color.WHITE);
            gc.fillOval(x - r, y - r, 2 * r, 2 * r);
            gc.strokeOval(x - r, y - r, 2 * r, 2 * r);
            gc.strokeText(Integer.toString(i), x, y);
        }
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public long getDrawingAreaWidth() {
        return W;
    }

    @Override
    public long getDrawingAreaHeight() {
        return H;
    }

    @Override
    public void addNode(double x, double y, double r) {
        Circle node = new Circle(x, y, r);
        nodes.add(node);
    }

    @Override
    public void addEdge(double x1, double y1, double x2, double y2) {
        edges.add(new Line(x1, y1, x2, y2));
    }

    @Override
    public void showDrawing() {
        launch();
    }
}

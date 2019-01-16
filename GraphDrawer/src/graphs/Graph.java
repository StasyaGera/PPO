package graphs;

import drawing.DrawingApi;
import javafx.util.Pair;

public abstract class Graph {
    protected int vertices;
    protected DrawingApi drawingApi;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    protected Pair<Double, Double> getCoordinates(int v) {
        double phi = 2 * Math.PI / vertices;
        double yStart = 0.5 * drawingApi.getDrawingAreaHeight();
        double xStart = 0.5 * drawingApi.getDrawingAreaWidth();
        double vX = xStart + 150 * Math.cos(v * phi);
        double vY = yStart + 150 * Math.sin(v * phi);
        return new Pair<>(vX, vY);
    }

    public void drawGraph() {
        for (int i = 0; i < vertices; i++) {
            Pair<Double, Double> coords = getCoordinates(i);
            drawingApi.addNode(coords.getKey(), coords.getValue(), 50);
        }
    }

    public abstract void fromString(String input);
}

package graphs;

import drawing.DrawingApi;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdjacencyMatrixGraph extends Graph {
    private List<List<Boolean>> edges;

    public AdjacencyMatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void drawGraph() {
        super.drawGraph();
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (edges.get(i).get(j)) {
                    Pair<Double, Double> from = super.getCoordinates(i);
                    Pair<Double, Double> to = super.getCoordinates(j);
                    drawingApi.addEdge(from.getKey(), from.getValue(), to.getKey(), to.getValue());
                }
            }
        }
        drawingApi.showDrawing();
    }

    @Override
    public void fromString(String input) {
        List<String> split = Arrays.asList(input.split("\\s+"));
        vertices = Integer.parseInt(split.get(0));
        edges = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            List<Boolean> row = new ArrayList<>();
            edges.add(row);
            for (int j = 0; j < vertices; j++) {
                int val = Integer.parseInt(split.get(1 + i * vertices + j));
                row.add(val == 1);
            }
        }
    }
}

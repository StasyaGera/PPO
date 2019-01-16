package graphs;

import drawing.DrawingApi;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EdgeListGraph extends Graph {
    private List<Edge> edges;

    public EdgeListGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void drawGraph() {
        super.drawGraph();
        for (Edge edge : edges) {
            Pair<Double, Double> from = super.getCoordinates(edge.from);
            Pair<Double, Double> to = super.getCoordinates(edge.to);
            drawingApi.addEdge(from.getKey(), from.getValue(), to.getKey(), to.getValue());
        }
        drawingApi.showDrawing();
    }

    @Override
    public void fromString(String input) {
        List<String> split = Arrays.asList(input.split("\\s+"));
        vertices = Integer.parseInt(split.get(0));
        edges = new ArrayList<>();
        for (int i = 1; i < split.size(); i += 2) {
            int from = Integer.parseInt(split.get(i));
            int to = Integer.parseInt(split.get(i + 1));
            edges.add(new Edge(from, to));
        }
    }

    private class Edge {
        private int from;
        private int to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}

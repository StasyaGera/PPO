import drawing.AwtDrawingApi;
import drawing.DrawingApi;
import drawing.JavaFxDrawingApi;
import graphs.AdjacencyMatrixGraph;
import graphs.EdgeListGraph;
import graphs.Graph;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Graph graph = null;
        DrawingApi api = null;

        switch (args[0]) {
            case "awt":
                api = new AwtDrawingApi();
                break;
            case "javafx":
                api = new JavaFxDrawingApi();
                break;
            default:
                System.out.println("Wrong api, choose <awt> or <javafx>");
                System.exit(1);
        }
        switch (args[1]) {
            case "matrix":
                graph = new AdjacencyMatrixGraph(api);
                break;
            case "list":
                graph = new EdgeListGraph(api);
                break;
            default:
                System.out.println("Wrong graph type, choose <matrix> or <list>");
                System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        graph.fromString(scanner.nextLine());
        graph.drawGraph();
    }
}

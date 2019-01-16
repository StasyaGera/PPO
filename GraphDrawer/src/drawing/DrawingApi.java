package drawing;

public interface DrawingApi {
    long getDrawingAreaWidth();
    long getDrawingAreaHeight();
    void addNode(double x, double y, double r);
    void addEdge(double x1, double y1, double x2, double y2);
    void showDrawing();
}

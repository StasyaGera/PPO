package drawing;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

public class AwtDrawingApi extends Frame implements DrawingApi {
    private int W = 1000;
    private int H = 600;
    private List<Line2D> edges = new ArrayList<>();
    private List<Ellipse2D> nodes = new ArrayList<>();

    public AwtDrawingApi() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        this.setSize(W, H);
    }

    public long getDrawingAreaWidth() {
        return this.getWidth();
    }

    public long getDrawingAreaHeight() {
        return this.getHeight();
    }

    public void addNode(double x, double y, double r) {
        nodes.add(new Ellipse2D.Double(x - r / 2, y - r / 2, r, r));
    }

    public void addEdge(double x1, double y1, double x2, double y2) {
        edges.add(new Line2D.Double(x1, y1, x2 , y2));
    }

    @Override
    public void showDrawing() {
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D)g;
        graphics.setPaint(Color.BLACK);
        for (Line2D edge: edges) {
            graphics.draw(edge);
        }
        for (int i = 0; i < nodes.size(); i++) {
            Ellipse2D node = nodes.get(i);
            graphics.setPaint(Color.WHITE);
            graphics.fill(node);
            graphics.setPaint(Color.BLACK);
            graphics.draw(node);
            graphics.drawString(Integer.toString(i), (float)node.getCenterX(), (float)node.getCenterY());
        }
    }
}
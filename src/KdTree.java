import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

// TODO stub implementation copied from PointSET
public class KdTree {

    private final static boolean VERTICAL = false;
    private final static boolean HORIZONTAL = true;

    private class HorizontalPointComparator implements Comparator<Point2D> {
        @Override
        public int compare(Point2D o1, Point2D o2) {
            if (o1.x() > o2.x()) return 1;
            if (o1.x() < o2.x()) return -1;
            return 0;
        }
    }

    private class VerticalPointComparator implements Comparator<Point2D> {
        @Override
        public int compare(Point2D o1, Point2D o2) {
            if (o1.y() > o2.y()) return 1;
            if (o1.y() < o2.y()) return -1;
            return 0;
        }
    }

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node left;
        private Node right;

        Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    private Node root;
    private int size = 0;
    private HorizontalPointComparator horizontalPointComparator = new HorizontalPointComparator();
    private VerticalPointComparator verticalPointComparator = new VerticalPointComparator();

    /**
     * construct an empty set of points
     */
    public KdTree(){

    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return size;
    }

    private int size(Node node) {
        if (node == null) return 0;
        return size(node.left) + size(node.right) + 1;
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (contains(p)) return;

        if (isEmpty()) {
            root = new Node(p, new RectHV(0, 0, 1, 1));
        } else {
            insert(root, p, VERTICAL);
        }

        size++;
    }


    private void insert(Node parentNode, Point2D point, boolean orientation) {
        int cmp = getComparator(orientation).compare(parentNode.p, point);
        Node next = cmp > 0 ? parentNode.left : parentNode.right;

        if (next == null) {
            RectHV rectHV = createRect(parentNode, orientation, cmp > 0);
            System.out.println(rectHV.toString());
            if (cmp > 0) {
                parentNode.left = new Node(point, rectHV);
            } else {
                parentNode.right = new Node(point, rectHV);
            }
        } else {
            insert(next, point, !orientation);
        }
    }

    private RectHV createRect(Node node, boolean orientation, boolean isLeft) {
        if (isLeft) {
            double xMin = node.rect.xmin();
            double yMin = node.rect.ymin();
            double xMax = orientation == VERTICAL ? node.p.x() : node.rect.xmax();
            double yMax = orientation == VERTICAL ? node.rect.ymax() : node.p.y();
            return new RectHV(xMin, yMin, xMax, yMax);
        } else {
            double xMin = orientation == VERTICAL ? node.p.x() : node.rect.xmin();
            double yMin = orientation == VERTICAL ? node.rect.ymin() : node.p.y();
            double xMax = node.rect.xmax();
            double yMax = node.rect.ymax();
            return new RectHV(xMin, yMin, xMax, yMax);
        }
    }

    private Comparator<Point2D> getComparator(boolean orientation) {
        return orientation ? verticalPointComparator : horizontalPointComparator;
    }

    /**
     * does the set contain point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return false;

        return contains(root, p, false);
    }

    private boolean contains(Node node, Point2D p, boolean orientation) {
        if (node == null) return false;
        if (p.equals(node.p)) return true;

        int cmp = getComparator(orientation).compare(node.p, p);
        if (cmp > 0) return contains(node.left, p, !orientation);
        if (cmp < 0) return contains(node.right, p, !orientation);

        return false;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        draw(root, false);
    }

    private void draw(Node node, boolean orientation) {
        if (node == null) return;

        StdDraw.setPenColor(orientation ? StdDraw.BLUE : StdDraw.RED);
        StdDraw.setPenRadius();

        if (orientation == VERTICAL) {
            double x = node.p.x();
            double y1 = node.rect.ymin();
            double y2 = node.rect.ymax();
            StdDraw.line(x, y1, x, y2);
        } else {
            double x1 = node.rect.xmin();
            double x2 = node.rect.xmax();
            double y = node.p.y();
            StdDraw.line(x1, y, x2, y);
        }

        // Draw dot
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        draw(node.left, !orientation);
        draw(node.right, !orientation);
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> points = new ArrayList<Point2D>();
        if (isEmpty()) return points;

        gatherPoints(rect, root, VERTICAL, points);

        return points;
    }

    // TODO stupid method name, can't think about better one at 1 AM
    private void gatherPoints(RectHV rect, Node node, boolean orientation, List<Point2D> points) {
        if (node == null) return;

        if (rect.contains(node.p)) points.add(node.p);

        // TODO simplify
        if (orientation == VERTICAL) {
            if (node.p.x() > rect.xmin()) gatherPoints(rect, node.left, !orientation, points);
            if (node.p.x() < rect.xmax()) gatherPoints(rect, node.right, !orientation, points);
        } else {
            if (node.p.y() > rect.ymin()) gatherPoints(rect, node.left, !orientation, points);
            if (node.p.y() < rect.ymax()) gatherPoints(rect, node.right, !orientation, points);
        }
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        // TODO use square distance
        double dist;

        return null;
    }

public static void test() {
    RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
    StdDraw.enableDoubleBuffering();
    KdTree kdtree = new KdTree();
    while (true) {
        if (StdDraw.mousePressed()) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            StdOut.printf("%8.6f %8.6f\n", x, y);
            Point2D p = new Point2D(x, y);
            if (rect.contains(p)) {
                StdOut.printf("%8.6f %8.6f\n", x, y);
                kdtree.insert(p);
                StdDraw.clear();
                kdtree.draw();
                StdDraw.show();
            }
        }
        StdDraw.pause(50);
    }
}

    /**
     * unit testing of the methods (optional)
     * @param args
     */
    public static void main(String[] args) {
//test();
        String filename = "d:\\Projects\\Algo\\src\\kdtree\\circle10.txt ";
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the data structures with N points from standard input
        //PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        kdtree.draw();
        StdDraw.show();

        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.mousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a rectangle
            else if (StdDraw.mousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.mousePressed() && isDragging) {
                isDragging = false;
            }


            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                    Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            kdtree.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : kdtree.range(rect))
                p.draw();

            // draw the range search results for kd-tree in blue
            StdDraw.setPenRadius(.02);
            StdDraw.setPenColor(StdDraw.BLUE);
            for (Point2D p : kdtree.range(rect))
                p.draw();

            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
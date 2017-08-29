import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Iterator;

public class PointSET {

    private SET<Point2D> set;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        set = new SET<Point2D>();
    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return set.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    /**
     * does the set contain point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        Iterator<Point2D> iterator = set.iterator();
        while (iterator.hasNext()) {
            iterator.next().draw();
        }
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> points = new ArrayList<>();
        Iterator<Point2D> iterator = set.iterator();
        while (iterator.hasNext()) {
            Point2D p = iterator.next();
            if (rect.contains(p)) points.add(p);
        }
        return points;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Iterator<Point2D> iterator = set.iterator();
        Point2D nearest = null;
        // TODO refactor this ugly code
        double distance = 2;
        while (iterator.hasNext()) {
            Point2D next = iterator.next();
            double dist = p.distanceTo(next);
            if (dist < distance) {
                nearest = next;
                distance = dist;
            }
        }
        return nearest;
    }

    /**
     * unit testing of the methods (optional)
     * @param args
     */
    public static void main(String[] args) {

        String filename = "c:\\Users\\konstantinko\\workspace\\Algo\\src\\kdtree\\circle10.txt";
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        //KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            //kdtree.insert(p);
            brute.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
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
            brute.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();

            // draw the range search results for kd-tree in blue
            StdDraw.setPenRadius(.02);
            StdDraw.setPenColor(StdDraw.BLUE);
//            for (Point2D p : kdtree.range(rect))
//                p.draw();

            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
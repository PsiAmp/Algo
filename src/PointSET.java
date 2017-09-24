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
     *
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    /**
     * does the set contain point p?
     *
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
     *
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
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Iterator<Point2D> iterator = set.iterator();
        Point2D nearest = null;
        // TODO refactor this ugly code and call distanceSquaredTo
        double distance = 2;
        while (iterator.hasNext()) {
            Point2D next = iterator.next();
            // TODO use distanceSquaredTo
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
     *
     * @param args
     */
    public static void main(String[] args) {
    }
}
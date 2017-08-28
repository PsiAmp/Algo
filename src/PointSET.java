import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    /**
     * construct an empty set of points
     */
    public PointSET() {
    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {}

    /**
     * does the set contain point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {}

    /**
     * draw all points to standard draw
     */
    public void draw() {}

    /**
     * all points that are inside the rectangle (or on the boundary)
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {}

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {}

    /**
     * unit testing of the methods (optional)
     * @param args
     */
    public static void main(String[] args) {}
}
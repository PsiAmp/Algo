import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {

    private static final boolean VERTICAL = false;
    private static final boolean HORIZONTAL = true;

    private class HorizontalPointComparator implements Comparator<Point2D> {
        @Override
        public int compare(Point2D o1, Point2D o2) {
            return Double.compare(o1.x(), o2.x());
        }
    }

    private class VerticalPointComparator implements Comparator<Point2D> {
        @Override
        public int compare(Point2D o1, Point2D o2) {
            return Double.compare(o1.y(), o2.y());
        }
    }

    private static class Node {
        private final Point2D p;
        private final RectHV rect;
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
    public KdTree() {

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
     *
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
     *
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

        if (node.p.equals(p)) return true;

        int cmp = getComparator(orientation).compare(node.p, p);
        if (cmp > 0) return contains(node.left, p, !orientation);
        else return contains(node.right, p, !orientation);
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
     *
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
     * a nearest neighbor in the set to point point; null if the set is empty
     *
     * @param point
     * @return
     */
    public Point2D nearest(final Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        return nearest(root, point, root.p);
    }
// TODO not optimal rewrite this
    private Point2D nearest(Node node, Point2D queryPoint, Point2D closest) {
        if (node != null) {
            double minDistance = queryPoint.distanceSquaredTo(closest);
            if (node.rect.distanceSquaredTo(queryPoint) < minDistance) {
                if (node.p.distanceSquaredTo(queryPoint) < minDistance) {
                    closest = node.p;
                }
                closest = nearest(node.left, queryPoint, closest);
                closest = nearest(node.right, queryPoint, closest);
            }
        }
        return closest;
    }

    /**
     * unit testing of the methods (optional)
     *
     * @param args
     */
    public static void main(String[] args) {
        KdTree k = new KdTree();
        k.insert(new Point2D(0.875 ,0.625));
        k.insert(new Point2D(0.75 ,0.0));
        k.insert(new Point2D(0.0 ,0.75));
        k.insert(new Point2D(0.0 ,0.0));
        k.insert(new Point2D(0.625 ,1.0));
        k.insert(new Point2D(0.875 ,0.125));
        k.insert(new Point2D(0.625 ,0.25));
        k.insert(new Point2D(0.5 ,0.0));
        k.insert(new Point2D(0.5 ,0.25));
        k.insert(new Point2D(1.0 ,1.0));
        k.insert(new Point2D(1.0 ,0.25));
        k.insert(new Point2D(0.875 ,0.75));
        k.insert(new Point2D(0.5 ,0.375));
        k.insert(new Point2D(0.5 ,0.0));
        k.insert(new Point2D(0.875 ,0.75));
        k.insert(new Point2D(0.375 ,0.75));
        k.insert(new Point2D(0.125 ,0.75));
        k.insert(new Point2D(1.0 ,0.75));
        k.insert(new Point2D(0.5 ,0.375));
        k.insert(new Point2D(0.75 ,0.0));

        System.out.println(k.contains(new Point2D(0.875, 0.125)));
        System.out.println(k.size());
    }
}
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by konstantinko on 3/7/2017.
 */
public class FastCollinearPoints {

    private static final int MIN_COLLINEAR_POINTS = 4;
    private final List<LineSegment> segments;

    /**
     * Finds all line segments containing 4 or more points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (Point point : points) if (point == null) throw new IllegalArgumentException();

        Arrays.sort(points);
        for (int i = 0; i < points.length-1; i++) {
            if (points[i].equals(points[i+1])) throw new IllegalArgumentException();
        }

        segments = findCollinear(points);
    }

    /**
     * Utilizing stability of Arrays.sort() algorithm we can sort points twice:
     * 1. by position
     * 2. by slope
     * @param points
     * @return
     */
    private List<LineSegment> findCollinear(Point[] points) {
        List<LineSegment> lineSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            Point originPoint = points[i];

            Point[] pts = Arrays.copyOf(points, points.length);

            // Sort points by a slope with points[pointIndex]
            Arrays.sort(pts, originPoint.slopeOrder());

            // Origin point is at 0 index after sorting, as slopeTo to itself returns Double.NEGATIVE_INFINITY
            for (int j = 1; j < pts.length; j++) {
                double slope = originPoint.slopeTo(pts[j]);

                // Now count how many subsequent points have the same slope. Works with any number of collinear points
                int counter = j + 1;
                while (counter < pts.length && originPoint.slopeTo(pts[counter]) == slope) {
                    counter++;
                }

                // Check if there are enough collinear points
                if (counter - j >= MIN_COLLINEAR_POINTS - 1) {
                    // Adding one more point so there's a place for origin point
                    Point[] collinearPoints = Arrays.copyOfRange(pts, j, counter + 1);

                    // Add origin point
                    collinearPoints[collinearPoints.length - 1] = originPoint;

                    // Sort points by position
                    Arrays.sort(collinearPoints);

                    // Avoid duplicates. Add only segment that starts with original point
                    if (collinearPoints[0].equals(originPoint)) {
                        // Points are sorted by position. Form line segment from two points that are further apart
                        lineSegments.add(new LineSegment(collinearPoints[0], collinearPoints[collinearPoints.length - 1]));
                    }

                    j = counter;
                } else {
                    // Skipping points with the same slope but not enough to form a segment
                    j = counter - 1;
                }
            }
        }

        return lineSegments;
    }

    private boolean contains(List<LineSegment> lineSegments, LineSegment segment) {
        for (LineSegment lineSegment : lineSegments) {
            if (lineSegment.equals(segment)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The number of line segments
     * @return
     */
    public  int numberOfSegments() {
        return segments.size();
    }

    /**
     * The line segments
     * @return
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
        In in = new In("d:\\Projects\\Algo\\src\\collinear\\kw1260.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

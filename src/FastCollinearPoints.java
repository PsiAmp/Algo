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
    private final LineSegment[] segments;

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

        List<LineSegment> lineSegments = new ArrayList<LineSegment>();

        // TODO check if optimization works: i < points.length - MIN_COLLINEAR_POINTS + 1
        for (int i = 0; i < points.length - MIN_COLLINEAR_POINTS + 1; i++) {
            List<LineSegment> collinearSegments = findCollinear(Arrays.copyOf(points, points.length), i);
            for (LineSegment lineSegment : collinearSegments) {
                // If there's no such line segment - add it.
                if (!contains(lineSegments, lineSegment)) {
                    lineSegments.add(lineSegment);
                }
            }
        }

        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    /**
     * Utilizing stability of Arrays.sort() algorithm we can sort points twice:
     * 1. by position
     * 2. by slope
     * @param points
     * @param pointIndex
     * @return
     */
    private List<LineSegment> findCollinear(Point[] points, int pointIndex) {
        List<LineSegment> lineSegments = new ArrayList<LineSegment>();

        Point original = points[pointIndex];

        // Sort points by a slope with points[pointIndex]
        Arrays.sort(points, points[pointIndex].slopeOrder());

        for (int i = 1; i < points.length; i++) {
            // Origin point is at 0 index
            double slope = original.slopeTo(points[i]);

            // Now count how many subsequent points have the same slope. Works with any number of collinear points
            // TODO: Mini-optimization: jump 3 points ahead
            int counter = i+1;
            while (counter < points.length && original.slopeTo(points[counter]) == slope) {
                counter++;
            }

            // Check if there are enough collinear points
            if (counter - i >= MIN_COLLINEAR_POINTS - 1) {
                // TODO original array can be sorted providing from and to indexes
                // Adding one more point so there's a place for origin point
                Point[] collinearPoints = Arrays.copyOfRange(points, i, counter+1);

                // Add origin point
                collinearPoints[collinearPoints.length-1] = original;

                // Sort points by position
                Arrays.sort(collinearPoints);

                // Points are sorted by position. Form line segment from two points that are further apart
                lineSegments.add(new LineSegment(collinearPoints[0], collinearPoints[collinearPoints.length-1]));

                // Jump to the start of the next possible sequence
                i = counter+1;
            } else {
                // TODO mini-optimization. Skipping points with the same slope but not enough to form a segment
                i = counter-1;
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
        return segments.length;
    }

    /**
     * The line segments
     * @return
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    public static void main(String[] args) {
        In in = new In("d:\\Projects\\Algo\\src\\collinear\\rs1423.txt");
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

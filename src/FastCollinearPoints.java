import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.swing.text.Segment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by konstantinko on 3/7/2017.
 */
public class FastCollinearPoints {

    private static final int MIN_COLLINEAR_POINTS = 4;
    private LineSegment[] segments;

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

        for (int i = 0; i < points.length; i++) {
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

    private void oldWay(Point[] points) {
        List<LineSegment> lineSegments = new ArrayList<LineSegment>();

        // Sort points by position. As Arrays sort is stable order should be saved when slopePoints are sorted by slope below
        Arrays.sort(points);

        for (int i = 0; i < points.length- MIN_COLLINEAR_POINTS; i++) {
            // TODO think about reusing the same array. Nulls can be used as a marker of array end;
            // TODO reversed loop can be used so array grows instead of shrinking
            Point[] slopePoints = new Point[points.length - i - 1];
            for (int j = 0; j < slopePoints.length; j++) {
                slopePoints[j] = points[i+j+1];
            }

            // Sort point by slope with parent point. Important! This sort works together with the sort outside for-loop
            Arrays.sort(slopePoints, points[i].slopeOrder());

            //printSlopes(points[i], slopePoints);

            // Finding 3 subsequent collinear points in already sorted array
            for (int min = 0; min < slopePoints.length - MIN_COLLINEAR_POINTS +1; min++) {
                double slope = points[i].slopeTo(slopePoints[min]);

                // max is the top index that has the same slope
                // btw this won't work for sets of 2 points. see if statement below
                int max = min+1;
                while (max < slopePoints.length && points[i].slopeTo(slopePoints[max]) == slope) {
                    // increase max as sequence of equal slopes isn't interrupted
                    max++;
                }

                // check if enough collinear points were found
                if (max - min >= MIN_COLLINEAR_POINTS -1) {
                    // TODO max-1 is a mistake
                    lineSegments.add(new LineSegment(points[i], slopePoints[max-1]));
                    min = max;
                }
            }
        }
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
        List<LineSegment> segments = new ArrayList<LineSegment>();

        // Sort points by position.
        Arrays.sort(points);

        // Sort points by a slope with points[pointIndex]
        Arrays.sort(points, points[pointIndex].slopeOrder());

        for (int i = 0; i < points.length; i++) {
            Point currentPoint = points[i];

            // Get a slope for a current point
            double slope = points[pointIndex].slopeTo(currentPoint);

            // Now count how many subsequent points have the same slope
            // "pointIndex == i" is a corner case slope from point to itself is obtained.
            int counter = i;
            while (counter + 1 < points.length && (pointIndex == i || currentPoint.slopeTo(points[counter + 1]) == slope)) {
                counter++;
            }

            // Check if there are enough collinear points
            if (counter - i >= MIN_COLLINEAR_POINTS-1) {
                // As we also sorted points by position, now first and last points should be farther away and represent line segment (at least in theory)
                segments.add(new LineSegment(currentPoint, points[counter]));

                // Jump to the start of the next possible sequence
                i = counter+1;
            }
        }

        return segments;
    }

    private boolean contains(List<LineSegment> segments, LineSegment segment) {
        for (LineSegment lineSegment : segments) {
            if (lineSegment.equals(segment)) {
                return true;
            }
        }
        return false;
    }

    private void printSlopes(Point point, Point[] points) {
        for (int k = 0; k < points.length; k++) {
            System.out.println(point.slopeTo(points[k]));
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
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
        return segments;
    }

    public static void main(String[] args) {
        In in = new In("c:\\Users\\konstantinko\\workspace\\Algo\\src\\collinear\\input9.txt");
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

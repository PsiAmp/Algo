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

    // TODO rename as there could be more then 4 points
    public static final int MAX_COLLINEAR_POINTS = 4;
    private final List<LineSegment> lineSegments;

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

        lineSegments = new ArrayList();

        // Sort points by position. As Arrays sort is stable order should be saved when slopePoints are sorted by slope below
        Arrays.sort(points);

        for (int i = 0; i < points.length- MAX_COLLINEAR_POINTS; i++) {
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
            for (int min = 0; min < slopePoints.length - MAX_COLLINEAR_POINTS +1; min++) {
                double slope = points[i].slopeTo(slopePoints[min]);

                // max is the top index that has the same slope
                // btw this won't work for sets of 2 points. see if statement below
                int max = min+1;
                while (max < slopePoints.length && points[i].slopeTo(slopePoints[max]) == slope) {
                    // increase max as sequence of equal slopes isn't interrupted
                    max++;
                }

                // check if enough collinear points were found
                if (max - min >= MAX_COLLINEAR_POINTS-1) {
                    // TODO max-1 is a mistake
                    lineSegments.add(new LineSegment(points[i], slopePoints[max-1]));
                    min = max;
                }
            }
        }
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
        return lineSegments.size();
    }

    /**
     * The line segments
     * @return
     */
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    public static void main(String[] args) {
        In in = new In("c:\\Users\\konstantinko\\workspace\\Algo\\src\\collinear\\rs1423.txt");
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

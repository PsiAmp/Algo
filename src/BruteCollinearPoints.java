import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kostiantyn Kostin on 02.07.2017.
 */
public class BruteCollinearPoints {

    public static final int MAX_COLLENEAR_POINTS = 4;
    private LineSegment[] segments;

    /**
     * Finds all line segments containing 4 points
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (Point point : points) if (point == null) throw new IllegalArgumentException();

        Arrays.sort(points);
        for (int i = 0; i < points.length-1; i++) {
            if (points[i].equals(points[i+1])) throw new IllegalArgumentException();
        }

        List<LineSegment> lineSegments = new ArrayList();

        for (int i = 0; i < points.length - MAX_COLLENEAR_POINTS; i++) {
            for (int j = i+1; j < points.length; j++) {
                double slope1 = points[i].slopeTo(points[j]);
                for (int n = j+1; n < points.length; n++) {
                    double slope2 = points[j].slopeTo(points[n]);
                    if (slope1 == slope2) {
                        for (int m = n + 1; m < points.length; m++) {
                            double slope3 = points[n].slopeTo(points[m]);
                            if (slope3 == slope2) {
                                Point[] collenearPoints = new Point[4];
                                collenearPoints[0] = points[i];
                                collenearPoints[1] = points[j];
                                collenearPoints[2] = points[n];
                                collenearPoints[3] = points[m];
                                Arrays.sort(collenearPoints);
                                LineSegment lineSegment = new LineSegment(collenearPoints[0], collenearPoints[3]);
                                if (!contains(lineSegments, lineSegment)) {
                                    lineSegments.add(lineSegment);
                                }
                            }
                        }
                    }
                }
            }
        }

        this.segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    private boolean contains(List<LineSegment> segments, LineSegment segment) {
        for (LineSegment lineSegment : segments) {
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
    public int numberOfSegments() {
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
        // read the n points from a file
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
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

    private final LineSegment[] segments;

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

        List<LineSegment> lineSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                double slopeIJ = points[i].slopeTo(points[j]);
                for (int n = j+1; n < points.length; n++) {
                    double slopeJN = points[j].slopeTo(points[n]);
                    if (slopeIJ == slopeJN) {
                        for (int m = n + 1; m < points.length; m++) {
                            double slopeNM = points[n].slopeTo(points[m]);
                            if (slopeNM == slopeJN) {
                                Point[] collinearPoints = new Point[4];
                                collinearPoints[0] = points[i];
                                collinearPoints[1] = points[j];
                                collinearPoints[2] = points[n];
                                collinearPoints[3] = points[m];
                                if (collinearPoints[0] == points[i])
                                    lineSegments.add(new LineSegment(points[i], points[m]));
                            }
                        }
                    }
                }
            }
        }

        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
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
        return Arrays.copyOf(segments, segments.length);
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
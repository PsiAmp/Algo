import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by konstantinko on 3/7/2017.
 */
public class FastCollinearPoints {

    public static final int MAX_COLLENEAR_POINTS = 4;

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

        List<LineSegment> lineSegments = new ArrayList();

        for (int i = 0; i < points.length- MAX_COLLENEAR_POINTS; i++) {
            Point[] slopePoints = new Point[points.length - i - 1];
            for (int j = 0; j < slopePoints.length; j++) {
                slopePoints[j] = points[i+j+1];
            }
            Arrays.sort(slopePoints, points[i].slopeOrder());
            for (int k = 0; k < slopePoints.length; k++) {
                System.out.println(points[i].slopeTo(slopePoints[k]));
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    /**
     * The number of line segments
     * @return
     */
    public  int numberOfSegments() {
        return 0;
    }

    /**
     * The line segments
     * @return
     */
    public LineSegment[] segments() {
        return null;
    }

    public static void main(String[] args) {
        In in = new In("d:\\Projects\\Algo\\src\\collinear\\input10.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);

    }
}

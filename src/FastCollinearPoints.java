import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by konstantinko on 3/7/2017.
 */
public class FastCollinearPoints {
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

        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                points[i].slopeTo(points[j]);
            }
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
}

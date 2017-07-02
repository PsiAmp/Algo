import java.util.Arrays;

/**
 * Created by Kostiantyn Kostin on 02.07.2017.
 */
public class BruteCollinearPoints {

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


    }

    /**
     * The number of line segments
     * @return
     */
    public int numberOfSegments() {

    }

    /**
     * The line segments
     * @return
     */
    public LineSegment[] segments() {}

}

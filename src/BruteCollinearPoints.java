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


        for (int i = 0; i < points.length; i++) {
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
                                new LineSegment(collenearPoints[0], collenearPoints[3]);
                            }
                        }
                    }
                }
            }
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
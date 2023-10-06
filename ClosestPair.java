import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class ClosestPair {
    public static double closestPair(Point[] points) {
        int n = points.length;

        // Use brute force methods to solve the problem, when the number of points is small,
        if (n <= 3) {
            double minDistance = Double.POSITIVE_INFINITY;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    double dist = distance(points[i], points[j]);
                    minDistance = Math.min(minDistance, dist);
                }
            }
            return minDistance;
        }

        // Sort points by x-coordinate
        Arrays.sort(points, (a, b) -> Double.compare(a.x, b.x));

        // Divide into left and right subsets
        int mid = n / 2;
        Point[] leftSubset = Arrays.copyOfRange(points, 0, mid);
        Point[] rightSubset = Arrays.copyOfRange(points, mid, n);

        // Recursively solving left and right subsets
        double leftClosest = closestPair(leftSubset);
        double rightClosest = closestPair(rightSubset);

        // Calculate the minimum distance between the left and right subsets
        double delta = Math.min(leftClosest, rightClosest);

        // Find the minimum distance across the left and right subsets
        double stripClosest = stripClosest(points, mid, delta);

        // Returns the final minimum distance
        return Math.min(delta, stripClosest);
    }

    public static double stripClosest(Point[] points, int mid, double delta) {
        double minDistance = delta;

        // Finding the minimum distance in a banded area
        Point[] strip = new Point[points.length];
        int j = 0;

        for (int i = 0; i < points.length; i++) {
            if (Math.abs(points[i].x - points[mid].x) < delta) {
                strip[j] = points[i];
                j++;
            }
        }

        // Sort by y-coordinate
        Arrays.sort(strip, 0, j, (a, b) -> Double.compare(a.y, b.y));

        // Find the minimum distance in the sorted strip
        for (int i = 0; i < j; i++) {
            for (int k = i + 1; k < j && strip[k].y - strip[i].y < delta; k++) {
                double dist = distance(strip[i], strip[k]);
                minDistance = Math.min(minDistance, dist);
            }
        }

        return minDistance;
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }



    public static void main(String[] args) {
            long StartTime = System.currentTimeMillis();

            Random random = new Random();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the number of points：");
            int numPoints = scanner.nextInt();
            scanner.nextLine();

            Point[] points = new Point[numPoints];

            // Generate random coordinates for each point
            for (int i = 0; i < numPoints; i++) {
                double x = random.nextDouble() * 200 - 100; // Generate a random x-coordinate between 1 and 100
                double y = random.nextDouble() * 200 - 100; // Generate a random y-coordinate between 1 and 100

                points[i] = new Point(x, y);
                System.out.println("(" + x + ", " + y + ")");
            }

            double closestDistance = closestPair(points);
            System.out.println("Closest pair of points distance is : " + closestDistance);

            long EndTime = System.currentTimeMillis();
            System.out.println("Run time is" + " " + (EndTime - StartTime) + " " + "ms");
        }
    }




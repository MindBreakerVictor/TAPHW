package apt.victor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class DCFour {

    private static class Point implements Comparable<Point> {
        int x, y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point(Point source) {
            this.x = source.x;
            this.y = source.y;
        }

        double distance(Point right) {
            int a1 = x - right.x;
            int a2 = y - right.y;
            return Math.sqrt(a1 * a1 + a2 * a2);
        }

        boolean equals(Point right) {
            return x == right.x && y == right.y;
        }

        @Override
        public int compareTo(Point right) {
            if (x == right.x)
                return y - right.y;

            return x - right.x;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }

        Point revert() {
            return new Point(y, x);
        }
    }

    private Point closestPoint1, closestPoint2;
    private double minDist = Double.POSITIVE_INFINITY;

    private DCFour(Point[] points) {
        if (points.length <= 1)
            return;

        Point[] pointsX = new Point[points.length];
        System.arraycopy(points, 0, pointsX, 0, points.length);
        Arrays.sort(pointsX);

        // Check if there are 2 identical points.
        for (int i = 0; i < pointsX.length - 1; ++i) {
            if (pointsX[i].equals(pointsX[i + 1])) {
                minDist = 0.0;
                closestPoint1 = pointsX[i];
                closestPoint2 = pointsX[i + 1];
                return;
            }
        }

        Point[] pointsY = new Point[points.length];

        for (int i = 0; i < pointsX.length; ++i)
            pointsY[i] = new Point(pointsX[i].y, pointsX[i].x);

        GetMinDistPoints(0, pointsX.length, pointsX, pointsY);
    }

    private static Point[] Merge(Point[] a, Point[] b) {
        Point[] c = new Point[a.length + b.length];
        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            if (a[i].compareTo(b[j]) < 0) {
                c[k] = a[i];
                ++i;
            }
            else {
                c[k] = b[j];
                ++j;
            }

            ++k;
        }

        while (i < a.length) {
            c[k] = a[i];
            ++i;
            ++k;
        }

        while (j < b.length) {
            c[k] = b[j];
            ++j;
            ++k;
        }

        return c;
    }

    private double GetMinDistPoints(int left, int right, Point[] pointsX, Point[] pointsY) {
        if (left >= right - 1)
            return Double.POSITIVE_INFINITY;

        if (right - left == 2) {
            if (pointsY[left].compareTo(pointsY[left + 1]) > 0) {
                Point aux = pointsY[left];
                pointsY[left] = pointsY[left + 1];
                pointsY[left + 1] = aux;
            }

            return pointsX[left].distance(pointsX[left + 1]);
        }

        int middle = (left + right) / 2;
        double minDistance = Math.min(GetMinDistPoints(left, middle, pointsX, pointsY),
                GetMinDistPoints(middle, right, pointsX, pointsY));

        Point[] a = new Point[middle - left];
        Point[] b = new Point[right - middle];

        System.arraycopy(pointsY, left, a, 0, middle - left);
        System.arraycopy(pointsY, middle, b, 0, right - middle);

        Point[] V = Merge(a, b);

        System.arraycopy(V, 0, pointsY, left, right - left);

        int sizeOfV = 0;

        for (int i = left; i < right; ++i)
            if (Math.abs(pointsY[i].y - pointsX[middle].x) <= minDistance)
                V[sizeOfV++] = pointsY[i];

        for (int i = 0; i < sizeOfV; ++i) {
            for (int j = i + 1; j < sizeOfV && j - i < 8; ++j) {
                double dist = V[i].distance(V[j]);

                if (dist < minDistance) {
                    minDistance = dist;

                    if (dist < this.minDist) {
                        closestPoint1 = V[i].revert();
                        closestPoint2 = V[j].revert();
                        this.minDist = dist;
                    }
                }
            }
        }

        return minDistance;
    }

    private double GetMinDist() {
        return minDist;
    }

    private Point GetEither() {
        return closestPoint1;
    }

    private Point GetOther() {
        return closestPoint2;
    }

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("cmap.in"));
            PrintWriter out = new PrintWriter("cmap.out");

            int pointsNo;

            if (!in.hasNext())
                return;

            pointsNo = in.nextInt();

            Point[] points = new Point[pointsNo];

            for (int i = 0; i < pointsNo; ++i) {
                int x, y;

                if (!in.hasNext())
                    return;

                x = in.nextInt();

                if (!in.hasNext())
                    return;

                y = in.nextInt();

                points[i] = new Point(x, y);
            }

            in.close();

            DCFour prog = new DCFour(points);

            out.println(prog.GetMinDist());
            out.println(prog.GetEither() + " " + prog.GetOther());
            out.close();
        }
        catch (FileNotFoundException excep) {
            System.out.println(excep.getMessage());
        }
    }
}


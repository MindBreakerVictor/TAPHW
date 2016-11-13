import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    private static class Cube implements Comparable<Cube> {
        int index;
        int color;
        int dimension;

        Cube(int index, int dimension, int color) {
            this.index = index;
            this.color = color;
            this.dimension = dimension;
        }

        @Override
        public int compareTo(Cube right) {
            if (dimension < right.dimension)
                return -1;

            if (dimension > right.dimension)
                return 1;

            if (dimension == right.dimension) {
                if (color < right.color)
                    return -1;

                if (color > right.color)
                    return 1;
            }

            return Integer.compare(index, right.index);
        }

        boolean canRide(Cube base) {
            return dimension <= base.dimension && color != base.color;
        }
    }

    public static void main(String[] args) {
	    try {
            Scanner scanner = new Scanner(new File("data.in"));
            PrintWriter writer = new PrintWriter("data.out");

            TreeSet<Cube> cubes = new TreeSet<>();

            int index = 1;
            int noCubes = 0;
            int noColors = 0;

            if (scanner.hasNextInt())
                noCubes = scanner.nextInt();

            if (scanner.hasNextInt())
                noColors = scanner.nextInt();

            for (int i = 0; i < noCubes; ++i) {
                if (!scanner.hasNextInt()) {
                    System.out.println("There are less cubes in data.in than specified in the first line.");
                    System.out.println("Halting process.");
                    return;
                }

                int dim = scanner.nextInt();

                if (!scanner.hasNextInt()) {
                    System.out.println("We can't have a cube without color.");
                    System.out.println("Halting process.");
                    return;
                }

                int color = scanner.nextInt();
                cubes.add(new Cube(index++, dim, color));
            }

            int height = 0;
            Stack<Cube> tower = new Stack<>();

            while (!cubes.isEmpty()) {
                Cube next = cubes.pollLast();

                if (tower.isEmpty() || next.canRide(tower.peek())) {
                    tower.push(next);
                    height += next.dimension;
                }
            }

            writer.println(height);
            Cube[] tow = tower.toArray(new Cube[tower.size()]);

            for (Cube cube : tow) writer.printf("%d ", cube.index);

            scanner.close();
            writer.close();
        }
        catch (FileNotFoundException excep) {
            System.out.println(excep.getMessage());
        }
    }
}


package apt.victor;

import java.io.*;
import java.util.*;

public class DPOne {
    private static class Tower {
        int height;
        Stack<Cube> cubesStack;

        Tower() {
            height = 0;
            cubesStack = new Stack<>();
        }

        Tower(Tower source) {
            height = source.height;
            cubesStack = (Stack<Cube>)source.cubesStack.clone();
        }

        Cube getTop() {
            return cubesStack.peek();
        }

        void addTop(Cube cube) {
            cubesStack.add(cube);
            height += cube.dimension;
        }

        @Override
        public String toString() {
            Cube[] cubes = cubesStack.toArray(new Cube[cubesStack.size()]);

            String tower = cubes[cubes.length - 1].toString();
            for (int i = cubes.length - 2; i >= 0; --i)
                tower = tower + '\n' + cubes[i].toString();

            return tower;
        }
    }

    private static class Cube implements Comparable<Cube> {
        int index;
        int color;
        int dimension;

        Cube (int index, int color, int dimension) {
            this.index = index;
            this.color = color;
            this.dimension = dimension;
        }

        public int compareTo(Cube right) {
            if (dimension > right.dimension)
                return -1;

            if (dimension < right.dimension)
                return 1;

            if (dimension == right.dimension) {
                if (color > right.color)
                    return -1;

                if (color < right.color)
                    return 1;
            }

            return Integer.compare(index, right.index);
        }

        boolean canRide(Cube base) {
            return dimension < base.dimension && color != base.color;
        }

        @Override
        public String toString() {
            return Integer.toString(index) + " " + Integer.toString(dimension) + " " + Integer.toString(color);
        }
    }

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("dpone.in"));

            if (!in.hasNextInt())
                return;

            int cubesNo = in.nextInt();

            if (!in.hasNextInt())
                return;

            int colors = in.nextInt();

            TreeSet<Cube> cubes = new TreeSet<>();

            for (int i = 0; i < cubesNo; ++i) {
                if (!in.hasNextInt())
                    return;

                int dimension = in.nextInt();

                if (!in.hasNextInt() || dimension < 1)
                    return;

                int color = in.nextInt();

                if (color < 1 || color > colors)
                    return;

                cubes.add(new Cube(i + 1, color, dimension));
            }

            in.close();

            System.out.println("Index | Dimension | Color");

            for (Cube cube : cubes)
                System.out.println(Integer.toString(cube.index) + " " +
                Integer.toString(cube.dimension) + " " + Integer.toString(cube.color));

            ArrayList<Tower> towers = new ArrayList<>();
            Cube[] arrayCubes = cubes.toArray(new Cube[cubes.size()]);

            for (Cube cube : arrayCubes) {
                // Save the current towers count, cause we're checking
                // only them to see if we can put the current cube on their top.
                int actualTowers = towers.size();

                // If there are no towers create one with the current cube.
                if (actualTowers == 0) {
                    towers.add(new Tower());
                    towers.get(0).addTop(cube);
                    continue;
                }

                // Check if we put the current cube in any towers.
                boolean used = false;

                // For each tower, check if the current cube can be put on top.
                // If yes, create a new tower identical with current tower and put the current cube on top.
                for (int j = 0; j < actualTowers; ++j)
                    if (cube.canRide(towers.get(j).getTop())) {
                        towers.add(new Tower(towers.get(j)));
                        towers.get(towers.size() - 1).addTop(cube);
                        used = true;
                    }

                // If we didn't used the current cube in any towers,
                // create a new tower with it on top.
                // Should we always create a tower with the current cube on top?
                if (!used) {
                    towers.add(new Tower());
                    towers.get(towers.size() - 1).addTop(cube);
                }
            }

            if (towers.isEmpty())
                return;

            int maxHeight = 0;
            int maxHeightCount = 0;
            Tower maxTower = towers.get(0);

            for (Tower tower : towers)
                if (maxHeight < tower.height) {
                    maxTower = tower;
                    maxHeight = tower.height;
                    maxHeightCount = 1;
                }
                else if (maxHeight == tower.height)
                    ++maxHeightCount;

            PrintWriter out = new PrintWriter("dpone.out");
            out.println("Index | Dimension | Color");
            out.println(maxTower.toString());
            out.println(maxHeightCount);
            out.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}


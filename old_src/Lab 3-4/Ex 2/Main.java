import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

class UndirectedGraph {
    private ArrayList<ArrayDeque<Integer>> adjacencyList;

    UndirectedGraph(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));

        adjacencyList = new ArrayList<>();

        int vertices = scanner.nextInt();
        for (int i = 0; i < vertices; ++i)
            adjacencyList.add(i, new ArrayDeque<Integer>());

        int edges = scanner.nextInt();
        for (int i = 0; i < edges; ++i) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();

            adjacencyList.get(a - 1).push(b);
            adjacencyList.get(b - 1).push(a);
        }

        scanner.close();
    }

    @Override
    public String toString() {
        String out = "";

        for (int i = 1; i <= adjacencyList.size(); ++i)
            out += Integer.toString(i) + ": " + adjacencyList.get(i - 1).toString() + "\n";

        return out;
    }

    ArrayList<Integer> BreadthFirstSearch(int start) {
        if (start < 1 || start > adjacencyList.size())
            return new ArrayList<>();

        boolean[] visited = new boolean[adjacencyList.size()];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        ArrayList<Integer> connectedComponent = new ArrayList<>();

        for (int i = 0; i < visited.length; ++i)
            visited[i] = false;

        queue.push(start);
        visited[start - 1] = true;
        connectedComponent.add(start);

        while (!queue.isEmpty()) {
            int top = queue.peek();
            Integer[] neighbours = adjacencyList.get(top - 1).toArray(new Integer[0]);

            for (Integer neighbour : neighbours) {
                if (visited[neighbour - 1])
                    continue;

                queue.push(neighbour);
                visited[neighbour - 1] = true;
                connectedComponent.add(neighbour);
            }

            queue.pop();
        }

        return new ArrayList<>(connectedComponent);
    }
}

public class Main {

    public static void main(String[] args) {
        try {
            UndirectedGraph g = new UndirectedGraph("data.in");
            PrintWriter writer = new PrintWriter("data.out");

            writer.print(g.toString());
            ArrayList<Integer> cC = g.BreadthFirstSearch(8);

            for (Integer aCC : cC)
                writer.print(aCC.toString() + " ");

            writer.flush();
            writer.close();
        }
        catch (FileNotFoundException exc) {
            System.out.println("Couldn't open the file.");
            System.exit(1);
        }
    }
}


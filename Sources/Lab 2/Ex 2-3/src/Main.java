import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Element<T> {
    T info;
    Element<T> next;

    Element() {
        info = null;
        next = null;
    }

    Element(T x) {
        info = x;
        next = null;
    }

    Element(Element<T> source) {
        info = source.info;
        next = source.next;
    }

    public boolean equals(Element<T> right) {
        return right.info == info;
    }
}

class Queue<T>
{
    private Element<T> head;
    private Element<T> tail;
    private Integer size;

    Queue() {
        size = 0;
        head = tail = null;
    }

    void push(T element) {
        if (head == null) {
            head = new Element<>(element);
            tail = head;
        }
        else {
            tail.next = new Element<>(element);
            tail = tail.next;
        }

        ++size;
    }

    public int getSize() {
        return size;
    }

    boolean isEmpty() {
        return size == 0;
    }

    void pop() {
        if (isEmpty())
            return;

        head = head.next;
        --size;
    }

    T peek() {
        if (isEmpty())
            return null;

        return head.info;
    }

    T element(int pos) {
        if (isEmpty() || pos < 0 || pos > size - 1)
            return null;

        Element<T> aux = new Element<>(head);

        for (int i = 0; i < pos; ++i)
            aux = aux.next;

        return aux.info;
    }

    @Override
    public String toString() {
        String out = "";

        Element<T> aux = new Element<>(head);

        for (int i = 0; i < size; ++i) {
            out += aux.info.toString();

            if (i != size - 1)
                out += ", ";

            aux = aux.next;
        }

        return out;
    }
}

class UndirectedGraph {
    ArrayList<Queue<Integer>> adjacencyList;

    UndirectedGraph(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));

        adjacencyList = new ArrayList<>();

        int vertices = scanner.nextInt();
        for (int i = 0; i < vertices; ++i)
            adjacencyList.add(i, new Queue<Integer>());

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
        Queue<Integer> queue = new Queue<>();
        ArrayList<Integer> connectedComponent = new ArrayList<>();

        for (int i = 0; i < visited.length; ++i)
            visited[i] = false;

        queue.push(start);
        visited[start - 1] = true;
        connectedComponent.add(start);

        while (!queue.isEmpty()) {
            int top = queue.peek();

            for (int i = 0; i < adjacencyList.get(top - 1).getSize(); ++i){
                if (visited[adjacencyList.get(top - 1).element(i) - 1])
                    continue;

                queue.push(adjacencyList.get(top - 1).element(i));
                visited[adjacencyList.get(top - 1).element(i) - 1] = true;
                connectedComponent.add(adjacencyList.get(top - 1).element(i));
            }

            queue.pop();
        }

        return new ArrayList<>(connectedComponent);
    }
}

public class Main {

    public static void main(String[] args) {
        /*
	    Queue<Integer> queue = new Queue<>();
        queue.push(3);
        queue.push(4);
        queue.pop();
        System.out.println(queue.peek().toString());
        queue.push(2);
        queue.push(1);
        System.out.println(queue.toString());
        System.out.println(queue.element(2));
        */
        try {
            UndirectedGraph g = new UndirectedGraph("data.in");
            PrintWriter writer = new PrintWriter("data.out");

            writer.print(g.toString());
            ArrayList<Integer> cC = g.BreadthFirstSearch(8);

            for (int i = 0; i < cC.size(); ++i)
                writer.print(cC.get(i).toString() + " ");

            writer.flush();
            writer.close();
        }
        catch (FileNotFoundException exc) {
            System.out.println("Couldn't open the file.");
            System.exit(1);
        }
    }
}


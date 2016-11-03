import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

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

class Queue<T> implements Iterable<T>
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

    public boolean isEmpty() {
        return size == 0;
    }

    public void pop() {
        if (isEmpty())
            return;

        head = head.next;
        --size;
    }

    private void pop(Element<T> elem) {
        if (head == elem)
            pop();

        Element<T> cursor = new Element<>(head);

        while (cursor.next != elem && cursor != null)
            cursor = cursor.next;

        if (cursor == null)
            return;

        cursor.next = cursor.next.next;
        --size;
    }

    public T peek() {
        if (isEmpty())
            return null;

        return head.info;
    }

    public T element(int pos) {
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

    @Override
    public Iterator<T> iterator() {
        return new QueueItr();
    }

    private class QueueItr implements Iterator<T>
    {
        private Element<T> cursor = head;

        public boolean hasNext() {
            return cursor != null;
        }

        public T next() {
            if (cursor == null)
                throw new NoSuchElementException();

            T result = cursor.info;
            cursor = cursor.next;
            return result;
        }

        public void remove() {
            if (cursor != null)
                pop(cursor);
        }
    }
}

class UndirectedGraph {
    private ArrayList<Queue<Integer>> adjacencyList;

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
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        ArrayList<Integer> connectedComponent = new ArrayList<>();

        for (int i = 0; i < visited.length; ++i)
            visited[i] = false;

        queue.push(start);
        visited[start - 1] = true;
        connectedComponent.add(start);

        while (!queue.isEmpty()) {
            int top = queue.peek();

            for (int i = 0; i < adjacencyList.get(top - 1).getSize(); ++i) {
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


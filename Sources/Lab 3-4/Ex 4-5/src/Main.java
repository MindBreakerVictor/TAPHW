class Element<T extends Comparable<T>> {

    T info;
    Element<T> left;
    Element<T> right;

    Element() {
        info = null;
        left = null;
        right = null;
    }

    Element(T x) {
        info = x;
        left = null;
        right = null;
    }

    Element(Element<T> source) {
        info = source.info;
        left = source.left;
        right = source.right;
    }

    public boolean equals(Element<T> right) {
        return right.info == info;
    }

    @Override
    public String toString() {
        return info.toString();
    }

    @Override
    public int hashCode() {
        return info.hashCode();
    }
}

class BinarySearchTree<T extends Comparable<T>> {

    private Element<T> root;
    private int size;

    BinarySearchTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() { return size == 0; }

    public boolean insert(T value) {
        if (isEmpty()) {
            root = new Element<>(value);
            ++size;
            return true;
        }

        Element<T> current = root;
        Element<T> parent = null;

        while (true) {
            parent = current;

            if (value.compareTo(current.info) == 0)
                return false;
            else if (value.compareTo(current.info) < 0) {
                current = current.left;

                if (current == null) {
                    parent.left = new Element<>(value);
                    ++size;
                    return true;
                }
            }
            else {
                current = current.right;

                if (current == null) {
                    parent.right = new Element<>(value);
                    ++size;
                    return true;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        toString(root, strBuilder);
        return strBuilder.toString();
    }

    private void toString(Element<T> node, StringBuilder string) {
        if (node == null)
            return;

        toString(node.left, string);
        string.append(node.toString());
        string.append(" ");
        toString(node.right, string);
    }

    public boolean equals(BinarySearchTree right) {
        return toString().equals(right.toString());
    }

    @Override
    public int hashCode() {
        return getHashCode(root, 0);
    }

    private int getHashCode(Element<T> node, Integer hashCode) {
        if (node == null)
            return 0;

        hashCode += getHashCode(node.left, hashCode);
        hashCode += node.hashCode();
        hashCode += getHashCode(node.right, hashCode);
        return hashCode;
    }
}

class Pair implements Comparable<Pair> {
    int first;
    int second;

    Pair() {
        first = second = 0;
    }

    Pair(int fir, int sec) {
        first = fir;
        second = sec;
    }

    Pair(Pair source) {
        first = source.first;
        second = source.second;
    }

    void setFirst(int value) {
        first = value;
    }

    void setSecond(int value) {
        second = value;
    }

    int getFirst() {
        return first;
    }

    int getSecond() {
        return second;
    }

    public int compareTo(Pair value) {
        if (value.first == first && value.second == second)
            return 0;

        if (value.first == first) {
            if (value.second > second)
                return -1;
            else
                return 1;
        }

        if (value.first > first)
            return -1;
        else
            return 1;
    }

    public boolean equals(Pair right) {
        return first == right.first && second == right.second;
    }

    @Override
    public String toString() {
        return "(" + Integer.toString(first) + ", " + Integer.toString(second) + ")";
    }
}

class Point2D extends Pair {

    Point2D(int x, int y) {
        first = x;
        second = y;
    }

    Point2D(Point2D source) {
        first = source.first;
        second = source.second;
    }
}

public class Main {

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst1 = new BinarySearchTree<>();
        BinarySearchTree<Integer> bst2 = new BinarySearchTree<>();

        bst1.insert(3);
        bst1.insert(1);
        bst1.insert(2);
        bst1.insert(5);
        bst1.insert(4);
        bst1.insert(6);

        bst2.insert(3);
        bst2.insert(1);
        bst2.insert(2);
        bst2.insert(5);
        bst2.insert(4);
        bst2.insert(6);

        System.out.println(bst1.toString());
        System.out.println(bst2.toString());
        System.out.println(Integer.toString(bst1.hashCode()) + " is bst1 and " + Integer.toString(bst2.hashCode()) + " is bst2.");

        if (bst1.equals(bst2))
            System.out.println("They are equal.");
        else
            System.out.println("They aren't equal.");

        BinarySearchTree<String> bst3 = new BinarySearchTree<>();
        BinarySearchTree<String> bst4 = new BinarySearchTree<>();

        bst3.insert("Victor");
        bst3.insert("Stanescu");
        bst3.insert("FMI");
        bst3.insert("Laurentiu");
        bst3.insert("TAP");

        bst4.insert("Victor");
        bst4.insert("Stanescu");
        bst4.insert("FMI");
        bst4.insert("Laurentiu");
        bst4.insert("TAP");

        System.out.println(bst3.toString());
        System.out.println(bst4.toString());
        System.out.println(Integer.toString(bst3.hashCode()) + " is bst3 and " + Integer.toString(bst3.hashCode()) + " is bst3.");

        if (bst3.equals(bst4))
            System.out.println("They are equal");
        else
            System.out.println("They aren't equal");

        BinarySearchTree<Pair> bst5 = new BinarySearchTree<>();

        bst5.insert(new Point2D(1,2));
        bst5.insert(new Pair(1,2));
        bst5.insert(new Pair(1,3));
        bst5.insert(new Point2D(1,5));

        System.out.println(bst5.toString());
    }
}


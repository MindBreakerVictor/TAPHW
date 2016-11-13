import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {

    static class Text implements Comparable<Text> {
        int index;
        int length;
        int freq;

        Text(int index, int length, int freq) {
            this.index = index;
            this.length = length;
            this.freq = freq;
        }

        @Override
        public int compareTo(Text right) {
            if (length < right.length)
                return -1;

            if (length > right.length)
                return 1;

            if (length == right.length) {
                if (freq > right.freq)
                    return -1;

                if (freq < right.freq)
                    return 1;

                if (freq == right.freq)
                    return Integer.compare(index, right.index);
            }

            return 0;
        }
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("data.in"));
            PrintWriter writer = new PrintWriter("data.out");
            TreeSet<Text> texts = new TreeSet<>();

            if (!sc.hasNextInt())
                return;

            int index = 0;
            int textsNo = sc.nextInt();

            for (int i = 0; i < textsNo; ++i) {
                if (!sc.hasNextInt()) {
                    System.out.println("There are less texts than specified. INVALID DATA!");
                    return;
                }

                int length = sc.nextInt();

                if (!sc.hasNextInt()) {
                    System.out.println("Text without frequency. INVALID DATA!");
                    return;
                }

                int freq = sc.nextInt();
                texts.add(new Text(++index, length, freq));
            }

            for (Text text : texts) writer.printf("%d ", text.index);

            writer.close();
            sc.close();
        }
        catch (FileNotFoundException excep) {
            System.out.println(excep.getMessage());
        }
    }
}

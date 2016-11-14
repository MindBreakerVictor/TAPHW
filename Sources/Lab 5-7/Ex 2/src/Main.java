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
            double dif = ((double)length / (double)freq) - ((double)right.length / (double)right.freq);

            if (dif < 0)
                return -1;

            if (dif > 0)
                return 1;

            return 0;
        }
    }

    static class TextB implements Comparable<TextB> {
        int index;
        int length;
        int bandId;

        TextB(int index, int length) {
            this.index = index;
            this.length = length;
            this.bandId = 0;
        }

        @Override
        public int compareTo(TextB right) {
            return length - right.length;
        }
    }

    public static void main(String[] args) {
        // a)
        /*
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
        */

        // b)
        try {
            Scanner sc = new Scanner(new File("data.in"));
            PrintWriter writer = new PrintWriter("data.out");
            TreeSet<TextB> texts = new TreeSet<>();

            if (!sc.hasNextInt())
                return;

            int index = 0;
            int textsNo = sc.nextInt();

            if (!sc.hasNextInt())
                return;

            int bandsNo = sc.nextInt();

            for (int i = 0; i < textsNo; ++i) {
                if (!sc.hasNextInt()) {
                    System.out.println("There are less texts than specified. INVALID DATA!");
                    return;
                }

                int length = sc.nextInt();
                texts.add(new TextB(++index, length));
            }

            TextB[] txts = texts.toArray(new TextB[texts.size()]);

            for (int i = 0; i < txts.length; ++i) {
                int band = (i + 1) % bandsNo;

                txts[i].bandId = (band == 0) ? bandsNo : band;
            }

            int written = 0;
            int currentBand = 1;

            while (written < textsNo) {
                for (TextB text : txts)
                    if (text.bandId == currentBand) {
                        writer.printf("%d ", text.index);
                        ++written;
                    }

                writer.printf("\n");
                ++currentBand;
            }

            writer.close();
            sc.close();
        }
        catch (FileNotFoundException excep) {
            System.out.println(excep.getMessage());
        }
    }
}

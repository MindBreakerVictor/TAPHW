package apt.victor;

import java.io.*;
import java.util.*;

public class DPThree {
    private static void run(ArrayList<String> dictionary, String word, PrintWriter out) {
        int[] previous = new int[word.length()];
        int[] usedWords = new int[word.length()];

        previous[0] = -1;
        usedWords[0] = 1;

        for (int i = 1; i < word.length(); ++i) {
            usedWords[i] = word.length();

            for (String dictionaryWord : dictionary) {
                int wordOffset = i - dictionaryWord.length() + 1;

                if (wordOffset < 0)
                    continue;

                if (!word.regionMatches(wordOffset, dictionaryWord, 0, dictionaryWord.length()))
                    continue;

                if (wordOffset == 0) {
                    previous[i] = -1;
                    usedWords[i] = 1;
                    break;
                }
                else if (usedWords[i] > usedWords[wordOffset - 1] + 1) {
                    previous[i] = wordOffset - 1;
                    usedWords[i] = usedWords[wordOffset - 1] + 1;
                }
            }
        }

        buildSolution(word, previous, word.length() - 1, out);
    }

    private static void buildSolution(String word, int[] previous, int index, PrintWriter out) {
        if (index < 0)
            return;

        buildSolution(word, previous, previous[index], out);

        for (int i = previous[index] + 1; i <= index; ++i)
            out.print(word.charAt(i));

        out.print(" ");
    }

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("dpthree.in"));

            if (!in.hasNextInt())
                return;

            int dictionarySize = in.nextInt();

            ArrayList<String> dictionary = new ArrayList<>(dictionarySize);

            for (int i = 0; i < dictionarySize; ++i) {
                if (!in.hasNextLine())
                    return;

                dictionary.add(in.nextLine());
            }

            dictionary.add("0");
            dictionary.add("1");

            in.close();

            in = new Scanner(new File("cod.in"));

            if (!in.hasNextLine())
                return;

            String cod = in.nextLine();

            in.close();

            PrintWriter out = new PrintWriter("dpthree.out");

            run(dictionary, cod, out);
            out.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}


import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static class Freq {
        public int f1freq;
        public int f2freq;

        Freq() {
            f1freq = f2freq = 0;
        }

        Freq(int fr1, int fr2) {
            f1freq = fr1;
            f2freq = fr2;
        }
    }

    private static double dcos(String file1, String file2) {
        try {
            Scanner sf1 = new Scanner(new File(file1));
            Scanner sf2 = new Scanner(new File(file2));
            HashMap<String, Freq> freq = new HashMap<>();

            while (sf1.hasNext()) {
                String word = sf1.next();

                if (freq.containsKey(word))
                    freq.get(word).f1freq++;
                else
                    freq.put(word, new Freq(1, 0));
            }

            while (sf2.hasNext()) {
                String word = sf2.next();

                if (freq.containsKey(word))
                    freq.get(word).f2freq++;
                else
                    freq.put(word, new Freq(0, 1));
            }

            int sum = 0;
            int sfr1 = 0;
            int sfr2 = 0;

            for (Freq fr : freq.values()) {
                sum = sum + (fr.f1freq * fr.f2freq);
                sfr1 = sfr1 + (fr.f1freq * fr.f1freq);
                sfr2 = sfr2 + (fr.f2freq * fr.f2freq);
            }

            return (double)sum / (Math.sqrt(sfr1) * Math.sqrt(sfr2));
        }
        catch (FileNotFoundException excep){
            System.out.println(excep.getMessage());
        }

        return 0.0;
    }

    public static void main(String[] args) {
        System.out.println(dcos("in1.in", "in2.in"));
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    /*try {
            Scanner sc_input = new Scanner(System.in);
            Scanner sc_file = new Scanner(new File("data.in"));
            PrintWriter writer = new PrintWriter("data.out");

            String key = sc_input.next();
            sc_input.close();

            sc_file.useDelimiter("\r");
            String data = sc_file.next();

            while (sc_file.hasNext())
                data += sc_file.next();

            sc_file.close();

            for (int i = 0; i < data.length(); ++i)
                writer.print(((int)data.charAt(i) ^ (int)key.charAt(i % key.length())) + " ");

            writer.close();
        }
        catch (FileNotFoundException exc) {
            System.out.println("Couldn't open the file.");
            System.exit(1);
        }
*/
        try {
            Scanner sc_input = new Scanner(System.in);
            Scanner sc_file = new Scanner(new File("data2.in"));
            PrintWriter writer = new PrintWriter("data2.out");

            String key = sc_input.next();
            sc_input.close();

            int i = 0;

            while (sc_file.hasNextInt()) {
                if (i == key.length())
                    i = 0;

                writer.print((char)(sc_file.nextInt() ^ (int)key.charAt(i)));
                ++i;
            }

            sc_file.close();
            writer.close();
        }
        catch (FileNotFoundException exc){
            System.out.println("Couldn't open file.");
            System.exit(1);
        }
    }
}

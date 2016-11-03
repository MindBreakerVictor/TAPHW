import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("data.in"));
            PrintWriter writer = new PrintWriter("data.out");
            int v[] = new int[scanner.nextInt()];

            for (int i = 0; i < v.length; ++i)
                if (scanner.hasNextInt())
                    v[i] = scanner.nextInt();

            Arrays.sort(v);

            for (int i = 0; i <= v.length - 3; ++i) {
                int a = v[i];
                int start = i + 1;
                int end = v.length - 1;

                while (start < end) {
                    int b = v[start];
                    int c = v[end];
                    int sum = a + b + c;

                    if (sum == 0) {
                        writer.printf("(%d, %d, %d)\n", a, b, c);
                        writer.flush();
                        --end;
                    }
                    else if (sum > 0)
                        --end;
                    else
                        ++start;
                }
            }

            writer.close();
            scanner.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Couldn't open data.in");
            System.exit(1);
        }
        catch (SecurityException ex) {
            System.out.println("Couldn't create or overwrite data.out");
            System.exit(2);
        }
    }
}

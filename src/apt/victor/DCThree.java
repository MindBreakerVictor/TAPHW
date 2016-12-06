package apt.victor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DCThree {

    private static class WeightedMedian {
        private int size;
        private int vec[];
        private double weights[];

        WeightedMedian(int size, int vec[], double weights[]) {
            this.size = size;
            this.vec = new int[this.size];
            this.weights = new double[this.size];

            for (int i = 0; i < this.size; ++i) {
                this.vec[i] = vec[i];
                this.weights[i] = weights[i];
            }
        }

        private int pos(int vec[], int left, int right) {
            int difLeft = 0;
            int difRight = -1;

            while (left < right) {
                if (vec[left] > vec[right]) {
                    int iTemp = vec[left];
                    vec[left] = vec[right];
                    vec[right] = iTemp;

                    double dTemp = weights[left];
                    weights[left] = weights[right];
                    weights[right] = dTemp;

                    iTemp = difLeft;
                    difLeft = -difRight;
                    difRight = -iTemp;
                }

                left += difLeft;
                right += difRight;
            }

            return left;
        }

        int getWeightedMedian(int vec[], int left, int right) {
            if (left == right)
                return vec[left];

            int pivot = pos(vec, left, right);

            double weight1 = 0;

            for (int i = 0; i < pivot; ++i)
                weight1 += weights[i];

            double weightPos = 0;

            for (int i = pivot + 1; i < size; ++i)
                weightPos += weights[i];

            if (weight1 < 0.5 && weightPos <= 0.5)
                return vec[pivot];
            else if (weight1 > weightPos)
                return getWeightedMedian(vec, 1, pivot - 1);
            else if (weight1 <= weightPos)
                return getWeightedMedian(vec, pivot + 1, right);

            return 0;
        }
    }

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("data.in"));
            PrintWriter out = new PrintWriter("data.out");

            if (!in.hasNextInt())
                return;

            int size = in.nextInt();
            int vec[] = new int[size];
            double weights[] = new double[size];

            for (int i = 0; i < size; ++i) {
                if (!in.hasNextInt())
                    return;

                vec[i] = in.nextInt();
            }

            for (int i = 0; i < size; ++i) {
                if (!in.hasNextDouble())
                    return;

                weights[i] = in.nextDouble();
            }

            in.close();

            WeightedMedian med = new WeightedMedian(size, vec, weights);
            out.println(med.getWeightedMedian(vec, 1, size - 1));

            out.close();
        }
        catch (FileNotFoundException excep) {
            System.out.println(excep.getMessage());
        }
    }
}


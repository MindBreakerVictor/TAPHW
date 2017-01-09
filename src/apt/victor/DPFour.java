package apt.victor;

import java.io.*;
import java.util.*;

public class DPFour {
    private String firstSequence;
    private String secondSequence;
    private int grid[][];
    private int solution[][];

    private DPFour(String firstSequence, String secondSequence) {
        this.firstSequence = firstSequence;
        this.secondSequence = secondSequence;

        grid = new int[firstSequence.length() + 2][secondSequence.length() + 2];

        for (int i = 1; i < firstSequence.length() + 2; ++i)
            grid[i][0] = grid[i - 1][0] + 2;

        for (int i = 1; i < secondSequence.length() + 2; ++i)
            grid[0][i] = grid[0][i - 1] + 2;

        solution = new int[firstSequence.length() + 1][secondSequence.length() + 1];
    }

    private void align() {
        int letterCost;
        int spaceCost = 2;
        int costOne, costTwo, costThree;

        for (int i = 1; i < firstSequence.length() + 1; ++i)
            for (int j = 1; j < secondSequence.length() + 1; ++j) {
                if ((firstSequence.charAt(i - 1) == 'A' && secondSequence.charAt(j - 1) == 'C') ||
                    (firstSequence.charAt(i - 1) == 'C' && secondSequence.charAt(j - 1) == 'A') ||
                    (firstSequence.charAt(i - 1) == 'G' && secondSequence.charAt(j - 1) == 'T') ||
                    (firstSequence.charAt(i - 1) == 'T' && secondSequence.charAt(j - 1) == 'G'))
                    letterCost = 1;
                else if (firstSequence.charAt(i - 1) == secondSequence.charAt(j - 1))
                    letterCost = 0;
                else
                    letterCost = 3;

                costOne = grid[i - 1][j - 1] + letterCost;
                costTwo = grid[i - 1][j] + spaceCost;
                costThree = grid[i][j - 1] + spaceCost;

                if (costOne <= costTwo && costOne <= costThree) {
                    grid[i][j] = costOne;
                    solution[i][j] = 1;
                }
                else if (costTwo <= costOne && costTwo <= costThree) {
                    grid[i][j] = costTwo;
                    solution[i][j] = 2;
                }
                else if (costThree <= costOne && costThree <= costTwo) {
                    grid[i][j] = costThree;
                    solution[i][j] = 3;
                }
            }
    }

    private String getSolution() {
        int i = firstSequence.length();
        int j = secondSequence.length();

        String solutionFirstSequence = "";
        String solutionSecondSequence = "";

        while (i != 0 && j != 0) {
            switch (solution[i][j]) {
                case 1:
                    solutionFirstSequence = firstSequence.charAt(i - 1) + solutionFirstSequence;
                    solutionSecondSequence = secondSequence.charAt(j - 1) + solutionSecondSequence;
                    --i;
                    --j;
                    break;
                case 2:
                    solutionFirstSequence = firstSequence.charAt(i - 1) + solutionFirstSequence;
                    solutionSecondSequence = "-" + solutionSecondSequence;
                    --i;
                    break;
                case 3:
                    solutionFirstSequence = "-" + solutionFirstSequence;
                    solutionSecondSequence = secondSequence.charAt(j - 1) + solutionSecondSequence;
                    --j;
                    break;
                default:
                    break;
            }
        }

        return solutionFirstSequence + '\n' + solutionSecondSequence;
    }

    private int getPenalty() { return grid[firstSequence.length()][secondSequence.length()]; }

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("dpfour.in"));

            if (!in.hasNextLine())
                return;

            String firstSequence = in.nextLine();

            if (!in.hasNextLine())
                return;

            String secondSequence = in.nextLine();

            in.close();

            DPFour dpFour = new DPFour(firstSequence, secondSequence);

            dpFour.align();

            PrintWriter out = new PrintWriter("dpfour.out");
            out.println(dpFour.getPenalty());
            out.println(dpFour.getSolution());
            out.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}


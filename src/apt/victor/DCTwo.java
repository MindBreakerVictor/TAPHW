package apt.victor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DCTwo {
    private int holeRowIndex;
    private int holeColumnIndex;
    private int size;
    private int board[][];
    private int pieceCounter = 1;

    private DCTwo(int size, int holeRow, int holeColumn) {
        this.size = size;
        holeRowIndex = holeRow;
        holeColumnIndex = holeColumn;

        int dim = (int)Math.pow(2, size);

        board = new int[dim + 1][dim + 1];

        for (int i = 1; i < dim + 1; ++i) {
            board[i] = new int[dim + 1];

            for (int j = 1; j < dim + 1; ++j)
                board[i][j] = -1;
        }

        board[holeRowIndex][holeColumnIndex] = 0;
    }

    private void go(int row, int column, int size) {
        if (size <= 2)
            return;

        int dial = size / 2 + (int)Math.pow(2, this.size - 1);

        // The hole is on a line outside of the dial.
        if (holeRowIndex < row || holeRowIndex > row + size / 2 - 1)
        {
            // Dial 1 or 3, no hole.
            if (column + size/ 2 - 1 < dial)
            {
                // Dial 1, no hole.
                if (row + size / 2 - 1 < dial)
                {
                    for (int i = row; i <= row + size / 4 - 1; ++i)
                        for (int j = column; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;

                    for (int i = row + size / 4; i <= row + size / 2 - 1; i++)
                        for (int j = column; j <= column + size / 4 - 1; j++)
                            board[i][j] = pieceCounter;

                    ++pieceCounter;
                    go(size / 4 + row, size / 4 + column, size / 2);
                }
                // Dial 3, no hole.
                else
                {
                    for (int i = row; i <= row + size / 4 - 1; ++i)
                        for (int j = column; j <= column + size / 4 - 1; ++j)
                            board[i][j] = pieceCounter;

                    for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                        for (int j = column; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;

                    ++pieceCounter;
                    go(row, column + size / 4, size / 2);
                }
            }
            // Dial 2 or 4, no hole.
            else
            {
                // Dial 2, no hole.
                if (row + size / 2 - 1 < size)
                {
                    for (int i = row; i <= row + size / 4 - 1; ++i)
                        for (int j = column; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;

                    for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                        for (int j = column + size / 4; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;

                    ++pieceCounter;
                    go(row + size / 4, column, size / 2);
                }
                // Dial 4, no hole.
                else
                {
                    for (int i = row; i <= row + size / 4 - 1; ++i)
                        for (int j = column + size / 4; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;

                    for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                        for (int j = column; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;

                    ++pieceCounter;
                    go(row, column, size / 2);
                }
            }
        }
        // The hole is on a line inside the dial.
        else
        {
            // The hole isn't on dial's columns.
            if (holeColumnIndex < column || holeColumnIndex > column + size / 2 - 1)
            {
                // Dial 1 or 3, no hole.
                if (column + size / 2 - 1 < dial)
                {
                    // Dial 1, no hole.
                    if (row + size / 2 - 1 < dial)
                    {
                        for (int i = row; i <= row + size / 4 - 1; ++i)
                            for (int j = column; j <= column + size / 2 - 1; ++j)
                                board[i][j] = pieceCounter;

                        for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                            for (int j = column; j <= column + size / 4 - 1; ++j)
                                board[i][j] = pieceCounter;

                        ++pieceCounter;
                        go(size / 4 + row, size / 4 + column, size / 2);
                    }
                    // Dial 3, no hole.
                    else
                    {
                        for (int i = row; i <= row + size / 4 - 1; ++i)
                            for (int j = column; j <= column + size / 4 - 1; ++j)
                                board[i][j] = pieceCounter;

                        for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                            for (int j = column; j <= column + size / 2 - 1; ++j)
                                board[i][j] = pieceCounter;

                        ++pieceCounter;
                        go(row, column + size / 4, size / 2);
                    }
                }
                // Dial 2 or 4, no hole.
                else
                {
                    // Dial 2, no hole.
                    if (row + size / 2 - 1 < dial)
                    {
                        for (int i = row; i <= row + size / 4 - 1; ++i)
                            for (int j = column; j <= column + size / 2 - 1; ++j)
                                board[i][j] = pieceCounter;

                        for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                            for (int j = column + size / 4; j <= column + size / 2 - 1; ++j)
                                board[i][j] = pieceCounter;

                        ++pieceCounter;
                        go(row + size / 4, column, size / 2);
                    }
                    // Dial 4, no hole.
                    else
                    {
                        for (int i = row; i <= row + size / 4 - 1; ++i)
                            for (int j = column + size / 4; j <= column + size / 2 - 1; ++j)
                                board[i][j] = pieceCounter;

                        for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                            for (int j = column; j <= column + size / 2 - 1; ++j)
                                board[i][j] = pieceCounter;

                        ++pieceCounter;
                        go(row, column, size / 2);
                    }
                }
            }
            // Minidial = board quarter of current dial
            // Hole found.
            else
            {
                // The hole is in the first minidial.
                if (holeRowIndex >= row && holeRowIndex <= row + size / 4 - 1 && holeColumnIndex >= column && holeColumnIndex <= column + size / 4 - 1)
                {
                    for (int i = row; i <= row + size / 4 - 1; ++i)
                        for (int j = column + size / 4; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;
                    for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                        for (int j = column; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;

                    ++pieceCounter;
                    go(row, column, size / 2);
                }
                // The hole is in the second minidial.
                else if (holeRowIndex >= row && holeRowIndex <= row + size / 4 - 1 && holeColumnIndex >= column + size / 4 && holeColumnIndex <= column + size / 2 - 1)
                {
                    for (int i = row; i <= row + size / 4 - 1; ++i)
                        for (int j = column; j <= column + size / 4 - 1; ++j)
                            board[i][j] = pieceCounter;
                    for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                        for (int j = column; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;

                    ++pieceCounter;
                    go(row, column + size / 4, size / 2);
                }
                // The hole is in the third minidial.
                else if (holeRowIndex >= row + size / 4 && holeRowIndex <= row + size / 2 - 1 && holeColumnIndex >= column && holeColumnIndex <= column + size / 4 - 1)
                {
                    for (int i = row; i <= row + size / 4 - 1; ++i)
                        for (int j = column; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;
                    for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                        for (int j = column + size / 4; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;

                    ++pieceCounter;
                    go(row + size / 4, column, size / 2);
                }
                // The hole is in the fourth minidial.
                else if (holeRowIndex >= row + size / 4 && holeRowIndex <= row + size / 2 - 1 && holeColumnIndex >= column + size / 4 && holeColumnIndex <= column + size / 2 - 1)
                {
                    //setPiece(row, row + size / 4 - 1, column, column + size / 2 - 1, pieceCounter);
                    for (int i = row; i <= row + size / 4 - 1; ++i)
                        for (int j = column; j <= column + size / 2 - 1; ++j)
                            board[i][j] = pieceCounter;

                    //setPiece(row + size / 4, row + size / 2 - 1, column, column + size / 4 - 1, pieceCounter);
                    for (int i = row + size / 4; i <= row + size / 2 - 1; ++i)
                        for (int j = column; j <= column + size / 4 - 1; ++j)
                            board[i][j] = pieceCounter;

                    ++pieceCounter;
                    go(row + size / 4, column + size / 4, size / 2);
                }
            }
        }
    }

    private void completeBoard(int dim) {
        int i = dim / 2;

        if (board[i][i] == -1)
            board[i][i] = pieceCounter;

        if (board[i][i + 1] == -1)
            board[i][i + 1] = pieceCounter;

        if (board[i + 1][i] == -1)
            board[i + 1][i] = pieceCounter;

        if (board[i + 1][i + 1] == -1)
            board[i + 1][i + 1] = pieceCounter;
    }

    private void setPiece(int rowS, int rowE, int columnS, int columnE, int pieceCounter) {
        for (int i = rowS; i <= rowE; ++i)
            for (int j = columnS; j <= columnE; ++j)
                board[i][j] = pieceCounter;
    }

    private void outputMatrix(PrintWriter out) {
        int dim = (int)Math.pow(2, size);

        for (int i = 1; i <= dim; ++i) {
            for (int j = 1; j <= dim; ++j)
                out.print(board[i][j] + " ");

            out.println();
        }
    }

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("data.in"));

            int size, holeRow, holeColumn;

            if (!in.hasNextInt())
                return;

            size = in.nextInt();

            if (!in.hasNextInt())
                return;

            holeRow = in.nextInt();

            if (!in.hasNextInt())
                return;

            holeColumn = in.nextInt();

            in.close();

            DCTwo test = new DCTwo(size, holeRow, holeColumn);

            int dim = (int)Math.pow(2, size);

            test.go(1, 1, dim);
            test.go(1, dim / 2 + 1, dim);
            test.go(dim / 2 + 1, 1, dim);
            test.go(dim / 2 + 1, dim / 2 + 1, dim);
            test.completeBoard(dim);

            PrintWriter out = new PrintWriter("data.out");

            test.outputMatrix(out);
            out.close();
        }
        catch (FileNotFoundException excep) {
            System.out.println(excep.getMessage());
        }
    }
}


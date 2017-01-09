package apt.victor;

import java.io.*;
import java.util.*;

public class DPFive {
    private static class Activity implements Comparable<Activity> {
        int index;
        int profit;
        int deadline;
        int duration;

        Activity() {
            index = -1;
            deadline = -1;
        }

        Activity (int index, int profit, int deadline, int duration) {
            this.index = index;
            this.profit = profit;
            this.deadline = deadline;
            this.duration = duration;
        }

        @Override
        public int compareTo(Activity right) {
            return deadline - right.deadline;
        }
    }

    private int[][] M;
    private Activity[] activities;

    private DPFive(Activity[] activities) {
        this.activities = activities;
    }

    private void run(PrintWriter out) {
        int highestDeadline = activities[activities.length - 1].deadline;

        M = new int[activities.length][highestDeadline + 1];

        for (int i = 1; i < activities.length; ++i)
            for (int t = 0; t <= highestDeadline; ++t) {
                int time = Math.min(t, activities[i].deadline) - activities[i].duration;

                if (time < 0)
                    M[i][t] = M[i - 1][t];
                else
                    M[i][t] = Math.max(M[i - 1][t], activities[i].profit + M[i - 1][time]);
            }

        printSolution(out, activities.length - 1, highestDeadline);
    }

    private void printSolution(PrintWriter out, int i, int t) {
        if (i == 0)
            return;

        if (M[i][t] != M[i - 1][t]) {
            int time = Math.min(t, activities[i].deadline) - activities[i].duration;
            printSolution(out, i - 1, time);
            out.println("Schedule job " + Integer.toString(activities[i].index) + " at time " + Integer.toString(time));
            return;
        }

        printSolution(out, i - 1, t);
    }

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("dpfive.in"));

            if (!in.hasNextInt())
                return;

            int activitiesNo = in.nextInt();
            Activity[] activities = new Activity[activitiesNo + 1];
            activities[0] = new Activity();

            for (int i = 1; i <= activitiesNo; ++i) {
                if (!in.hasNextInt())
                    return;

                int profit = in.nextInt();

                if (!in.hasNextInt())
                    return;

                int deadline = in.nextInt();

                if (!in.hasNextInt())
                    return;

                int duration = in.nextInt();

                activities[i] = new Activity(i, profit, deadline, duration);
            }

            in.close();
            Arrays.sort(activities);

            DPFive dpFive = new DPFive(activities);
            PrintWriter out = new PrintWriter("dpfive.out");

            dpFive.run(out);
            out.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}


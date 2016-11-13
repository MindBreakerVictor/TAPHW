import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

public class Main {

    static class Activity implements Comparable<Activity> {
        int index;
        int profit;
        int deadline;

        Activity(int index, int profit, int deadline) {
            this.index = index;
            this.profit = profit;
            this.deadline = deadline;
        }

        @Override
        public int compareTo(Activity right) {
            if (deadline < right.deadline)
                return -1;

            if (deadline > right.deadline)
                return 1;

            if (deadline == right.deadline) {
                if (profit > right.profit)
                    return -1;

                if (profit < right.profit)
                    return 1;
            }

            return Integer.compare(index, right.index);
        }
    }

    public static void main(String[] args) {
	    try {
            Scanner scanner = new Scanner(new File("data.in"));
            PrintWriter writer = new PrintWriter("data.out");
            TreeSet<Activity> activities = new TreeSet<>();

            if (!scanner.hasNextInt())
                return;

            int index = 0;
            int activitiesNo = scanner.nextInt();

            for (int i = 0; i < activitiesNo; ++i) {
                if (!scanner.hasNextInt()) {
                    System.out.println("There are less activities then specified. INVALID DATA!");
                    return;
                }

                int profit = scanner.nextInt();

                if (!scanner.hasNextInt()) {
                    System.out.println("Activity without deadline. INVALID DATA!");
                    return;
                }

                activities.add(new Activity(++index, profit, scanner.nextInt()));
            }

            int profit = 0;
            Stack<Activity> jobs = new Stack<>();

            while (!activities.isEmpty()) {
                Activity activity = activities.pollFirst();

                if (activity.deadline > jobs.size()) {
                    jobs.push(activity);
                    profit += activity.profit;
                }
            }

            writer.println(profit);

            Activity[] act = jobs.toArray(new Activity[jobs.size()]);

            for (Activity anAct : act)
                writer.printf("%d ", anAct.index);

            writer.close();
            scanner.close();
        }
        catch (FileNotFoundException excep) {
            System.out.println(excep.getMessage());
        }
    }
}

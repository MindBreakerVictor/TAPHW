import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

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
            if (deadline > right.deadline)
                return -1;

            if (deadline < right.deadline)
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

    static class ActivityComparator implements Comparator<Activity> {
        public int compare(Activity left, Activity right) {
            return right.profit - left.profit;
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
            int currentDeadline = activities.first().deadline;
            PriorityQueue<Activity> jobs = new PriorityQueue<>(activitiesNo, new ActivityComparator());
            Stack<Activity> todo = new Stack<>();

            while ((!activities.isEmpty() || !jobs.isEmpty()) && currentDeadline > 0) {
                while (!activities.isEmpty() && activities.first().deadline == currentDeadline)
                    jobs.add(activities.pollFirst());

                --currentDeadline;

                if (jobs.isEmpty()) {
                    profit += activities.first().profit;
                    todo.push(activities.pollFirst());
                    continue;
                }

                profit += jobs.peek().profit;
                todo.push(jobs.poll());
            }

            writer.println(profit);

            Activity[] act = todo.toArray(new Activity[jobs.size()]);

            for (int i = act.length - 1; i >= 0; --i)
                writer.printf("%d ", act[i].index);

            writer.close();
            scanner.close();
        }
        catch (FileNotFoundException excep) {
            System.out.println(excep.getMessage());
        }
    }
}

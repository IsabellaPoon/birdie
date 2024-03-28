import javax.swing.*;
import java.util.ArrayList;

public class  Main {
    public static void main(String[] args) throws Exception {
        int boardWidth = 640;
        int boardHeight = 640;

        JFrame frame = new JFrame("FlappingBird");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BirdFlapping flaps = new BirdFlapping();
        frame.add(flaps);
        frame.pack();
        flaps.requestFocus();
        frame.setVisible(true);
    ArrayList<Integer> nums = new ArrayList<>();
        for(int i = 0; i < 10; i++) System.out.println(nums.add(i));
        System.out.println(nums.size());
        nums.add(2, 3);
        System.out.println(nums.get(2));
        System.out.println(nums.set(4, 5));
        System.out.println(nums.remove(8));
        System.out.println(numsdeler(nums, 3));
    ArrayList<Integer> num2 = new ArrayList<>();
        num2.add(23);
        num2.add(34);
        num2.add(65);
        num2.add(11);
        num2.add(98);
    printerer(num2);
    selectionSorterer(num2);
        System.out.println();
    ArrayList<Integer> num5 = new ArrayList<>();
        num5.add(23);
        num5.add(654);
        num5.add(32);
        num5.add(65);
        num5.add(1);
    printerer(num5);
    insertionSorterer(num5);
        System.out.println();

    int[][] numbers = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        for (int r = 0; r < numbers.length; r++) {
        for (int c = 0; c < numbers[0].length; c++) {
            System.out.println(numbers[r][c]);
        }
    }

        for (int c = 0; c < numbers[0].length; c++) {
        for (int r = 0; r < numbers.length; r++) {
            System.out.println(numbers[r][c]);
        }
    }

        for (int[] x : numbers) {
        for (int y : x) {
            System.out.println(y);
        }
    }

    Football footy = new Football(5, "Adri");
    Football foots = new Football();
        footy.inflate(4);
        footy.drop(-2);
    Ball footer = new Football();
    pop(footy);
    Ball[] list = {footer, new Football()};
    ArrayList<Ball> baller = new ArrayList<Ball>();
        baller.add(footer);
        baller.add(new Football());
}

    public static Ball pop(Ball b) {
        b.faster();
        b.inflate(2);
        return b; // Added return statement
    }

    public static ArrayList<Integer> numsdeler(ArrayList<Integer> x, int m) {
        for (int i = x.size() - 1; i >= 0; i--) {
            if (x.get(i) == m) {
                x.remove(i);
            }
        }
        return x;
    }

    public static void printerer(ArrayList<Integer> percy) {
        String printer = "[";
        for (int i = 0; i < percy.size() - 1; i++) {
            printer += percy.get(i) + ", ";
        }
        printer += percy.get(percy.size() - 1);
        System.out.println(printer + "]");
    }

    public static int findMinyer(int index, ArrayList<Integer> percy) {
        int numChanged = 0;
        int min = percy.get(index);
        for (int i = index; i < percy.size(); i++) {
            if (min > percy.get(i)) {
                numChanged++;
                min = percy.get(i);
                index = i;
            }
        }
        System.out.println("Minimum Updates: " + numChanged);
        return index;
    }

    public static void switcharoonyer(ArrayList<Integer> percy, int p, int a) {
        int pause = percy.get(p);
        percy.set(p, percy.get(a));
        percy.set(a, pause);
    }
    public static void selectionSorterer(ArrayList<Integer> percy) {
        int swapperupper = 0;
        for (int i = 0; i < percy.size(); i++) {
            int mini = findMinyer(i, percy);
            if (mini != i) {
                switcharoonyer(percy, i, mini);
                swapperupper++;
            }
        }
        System.out.println("Swaps: " + swapperupper);
    }

    public static void insertionSorterer(ArrayList<Integer> percy) {
        int swapperupper = 0;
        for (int i = 1; i < percy.size(); i++) {
            int pause = percy.get(i);
            int index = i;
            while (index > 0 && pause <= percy.get(index - 1)) {
                switcharoonyer(percy, index, index - 1);
                swapperupper++;
                index--;
            }
        }
        System.out.println("Swaps: " + swapperupper);
    }
}
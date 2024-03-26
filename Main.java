import javax.swing.*;

public class Main {
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
    }
}
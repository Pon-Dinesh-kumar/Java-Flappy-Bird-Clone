import javax.swing.*;
public class App {
    public static void main(String[] args) throws  Exception {
        int boardWidth = 460;
        int boardHeight = 720;

        JFrame frame = new JFrame("Endgame");
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Endgame endgame = new Endgame();
        frame.add(endgame);
        frame.pack();
        endgame.requestFocus();
        frame.setVisible(true);
    }
}

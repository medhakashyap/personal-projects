import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int board_width = 500;
        int board_height = board_width;

        JFrame frame = new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(board_width, board_height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame game = new SnakeGame(board_width, board_height);
        frame.add(game);
        frame.pack();
        game.requestFocus();
    }
}

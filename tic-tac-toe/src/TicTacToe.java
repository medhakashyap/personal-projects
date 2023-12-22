import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

class Game implements ActionListener{

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel text_field = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;

    public Game(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(Color.white);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        text_field.setBackground(Color.white);
        text_field.setForeground(Color.black);
        text_field.setFont(new Font("Exo 2", Font.BOLD, 70));
        text_field.setHorizontalAlignment(JLabel.CENTER);
        text_field.setText("Tic-Tac-Toe");
        text_field.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 500, 100);

        button_panel.setLayout(new GridLayout(3,3));
        button_panel.setBackground(Color.white);

        for(int i = 0; i < 9; i++){
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setBackground(Color.white);
            buttons[i].setFont(new Font("Exo 2", Font.BOLD, 80));
            buttons[i].setFocusable(false);
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
            buttons[i].addActionListener(this);
        }

        title_panel.add(text_field);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);

        first_turn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]){
                if (player1_turn){
                    if (buttons[i].getText().equals("")){
                        buttons[i].setText("X");
                        player1_turn = false;
                        buttons[i].setForeground(Color.blue);
                        text_field.setText("O turn");
                        check_wins();
                    }
                } else{
                    if (buttons[i].getText() == ""){
                        buttons[i].setText("O");
                        player1_turn = true;
                        buttons[i].setForeground(Color.red);
                        text_field.setText("X turn");
                        check_wins();
                    }
                }
            }
        }
    }

    public void first_turn(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (random.nextInt(2) == 0){
            player1_turn = true;
            text_field.setText("X turn");
        } else{
            player1_turn = false;
            text_field.setText("O turn");
        }
    }

    public void check_wins(){
        // check X winning conditions
        // row
        if ((buttons[0].getText() == "X") && 
            (buttons[1].getText() == "X") &&
            (buttons[2].getText() == "X")){
                x_wins(0, 1, 2);
        }
        
        if ((buttons[3].getText() == "X") && 
            (buttons[4].getText() == "X") &&
            (buttons[5].getText() == "X")){
                x_wins(3, 4, 5);
        }

        if ((buttons[6].getText() == "X") && 
            (buttons[7].getText() == "X") &&
            (buttons[8].getText() == "X")){
                x_wins(6, 7, 8);
        }
        // col
        if ((buttons[0].getText() == "X") && 
            (buttons[3].getText() == "X") &&
            (buttons[6].getText() == "X")){
                x_wins(0, 3, 6);
        }

        if ((buttons[1].getText() == "X") && 
            (buttons[4].getText() == "X") &&
            (buttons[7].getText() == "X")){
                x_wins(1, 4, 7);
        }

        if ((buttons[2].getText() == "X") && 
            (buttons[5].getText() == "X") &&
            (buttons[8].getText() == "X")){
                x_wins(2, 5, 8);
        }
        // diagonal
        if ((buttons[0].getText() == "X") && 
            (buttons[4].getText() == "X") &&
            (buttons[8].getText() == "X")){
                x_wins(0, 4, 8);
        }

        if ((buttons[2].getText() == "X") && 
            (buttons[4].getText() == "X") &&
            (buttons[6].getText() == "X")){
                x_wins(2, 4, 6);
        }
        // check O winning conditions
        // row
        if ((buttons[0].getText() == "O") && 
            (buttons[1].getText() == "O") &&
            (buttons[2].getText() == "O")){
                o_wins(0, 1, 2);
        }
        
        if ((buttons[3].getText() == "O") && 
            (buttons[4].getText() == "O") &&
            (buttons[5].getText() == "O")){
                o_wins(3, 4, 5);
        }

        if ((buttons[6].getText() == "O") && 
            (buttons[7].getText() == "O") &&
            (buttons[8].getText() == "O")){
                o_wins(6, 7, 8);
        }
        // col
        if ((buttons[0].getText() == "O") && 
            (buttons[3].getText() == "O") &&
            (buttons[6].getText() == "O")){
                o_wins(0, 3, 6);
        }

        if ((buttons[1].getText() == "O") && 
            (buttons[4].getText() == "O") &&
            (buttons[7].getText() == "O")){
                o_wins(1, 4, 7);
        }

        if ((buttons[2].getText() == "O") && 
            (buttons[5].getText() == "O") &&
            (buttons[8].getText() == "O")){
                o_wins(2, 5, 8);
        }
        // diagonal
        if ((buttons[0].getText() == "O") && 
            (buttons[4].getText() == "O") &&
            (buttons[8].getText() == "O")){
                o_wins(0, 4, 8);
        }

        if ((buttons[2].getText() == "O") && 
            (buttons[4].getText() == "O") &&
            (buttons[6].getText() == "O")){
                o_wins(2, 4, 6);
        }
    }

    public void x_wins(int a, int b, int c){
        buttons[a].setBackground(Color.BLUE);
        buttons[b].setBackground(Color.BLUE);
        buttons[c].setBackground(Color.BLUE);
        // buttons[a].setForeground(Color.BLUE);
        // buttons[b].setForeground(Color.BLUE);
        // buttons[c].setForeground(Color.BLUE);
        for (int i = 0; i < 9; i++){
            buttons[i].setEnabled(false);
        }
        text_field.setText("X Wins!!");
    }

    public void o_wins(int a, int b, int c){
        buttons[a].setBackground(Color.red);
        buttons[b].setBackground(Color.red);
        buttons[c].setBackground(Color.red);
        // buttons[a].setForeground(Color.RED);
        // buttons[b].setForeground(Color.RED);
        // buttons[c].setForeground(Color.RED);
        for (int i = 0; i < 9; i++){
            buttons[i].setEnabled(false);
        }
        text_field.setText("O Wins!!");
    }
}

public class TicTacToe{
    public static void main(String[] args) {
        
        Game tictactoe = new Game();

    }
}
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener{

    private boolean play = false;
    private int total_bricks = 21;
    private Timer timer;
    private int delay = 8;
    private int ball_x_position = 120;
    private int ball_y_position = 350;
    private int ball_x_direction = -1;
    private int ball_y_direction = -2;
    private int player_x = 350;
    private Brick map;
    private int score = 0;

    public Game(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        map = new Brick(3, 7);

        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // clear background
        g.setColor(Color.black);
        g.fillRect(0, 0, 692, 692);

        // black canvas
        g.setColor(Color.black);
        g.fillRect(1, 1,692, 692);
        
        // borders
        g.setColor(Color.green);
        g.fillRect(0, 0, 692, 5); // top
        g.fillRect(0, 3, 4, 570); // left
        g.fillRect(683, 4, 3, 592); // right

        // paddle
        g.setColor(Color.cyan);
        g.fill3DRect(player_x, 545, 100, 8, true);

        // ball
        g.setColor(Color.red);
        int width = 20, height = 20;

        Graphics2D g2d = (Graphics2D) g;

        Point2D center = new Point2D.Float(ball_x_position + width/2, ball_y_position + height/2);
        float[] dist = {0.0f, 0.3f, 1.0f};
        Color[] colors = {Color.decode("#FF2400"), Color.decode("#E0115F"), Color.decode("#A91B0D")};
        RadialGradientPaint gradient = new RadialGradientPaint(center, width/2, dist, colors);
        
        g2d.setPaint(gradient);
        g2d.fill(new Ellipse2D.Double(ball_x_position, ball_y_position, width, height));
        
        // brick
        map.draw((Graphics2D) g);

        // score
        g.setFont(new Font("Serif", Font.BOLD, 18));
        g.setColor(Color.white);
        g.drawString("Score: " + score, 550, 30);

        // game over
        if (ball_y_position >= 570){
            play = false;
            ball_x_direction = 0;
            ball_y_direction = 0;

            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over!!", 260, 250);
            
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Final Score: " + score, 250, 300);

            g.setColor(Color.pink);
            g.setFont(new Font("Serif", Font.BOLD, 18));
            g.drawString("Press 'enter' to restart", 260, 330);
        }
        if (total_bricks <= 0){
            play = false;
            ball_x_direction = 0;
            ball_y_direction = 0;

            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("You Won", 280, 250);
            
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Final Score: " + score, 250, 300);

            g.setColor(Color.pink);
            g.setFont(new Font("Serif", Font.BOLD, 18));
            g.drawString("Press 'enter' to restart", 260, 330);
        }
    }

    private void move_left(){
        play = true;
        player_x -= 20;
    }

    private void move_right(){
        play = true;
        player_x += 20;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (player_x > 0){
                move_left();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (player_x < 600){
                move_right();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                score = 0;
                total_bricks = 21;
                ball_x_position = 120;
                ball_y_position = 350;
                ball_x_direction = -1;
                ball_y_direction = -2;
                player_x = 320;

                map = new Brick(3,7);
            }
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play){
            if (ball_x_position <= 0){ // left
                ball_x_direction =- ball_x_direction;
            }
            if (ball_y_position <= 0){ // top
                ball_y_direction =- ball_y_direction;
            }
            if (ball_x_position >= 670){ // right
                ball_x_direction =- ball_x_direction;
            }

            Rectangle ball_rectangle = new Rectangle(ball_x_position, ball_y_position, 20, 20);
            Rectangle paddle_rectangle = new Rectangle(player_x, 550, 100, 8);

            if (ball_rectangle.intersects(paddle_rectangle)){
                ball_y_direction =- ball_y_direction;
            }

            A : for (int i = 0; i < map.map.length; i++){
                for (int j = 0; j < map.map[0].length; j++){
                    if (map.map[i][j] > 0){
                        int width = map.brick_width;
                        int height = map.brick_height;
                        int brick_x_position = 80 + j*width;
                        int brick_y_position = 50 + i*height;

                        Rectangle brick_rectangle = new Rectangle(brick_x_position, brick_y_position, width, height);
                        
                        if (ball_rectangle.intersects(brick_rectangle)){
                            map.set_brick(0, i, j);
                            total_bricks--;
                            score += 5; // 5 points increased while breaking one brick

                            if (ball_x_position + 19 <= brick_x_position || ball_x_position + 1 >= brick_x_position + width){
                                // left intersection                        // right intersection
                                ball_x_direction = -ball_x_direction;
                            }
                            else{
                                ball_y_direction = -ball_y_direction;
                            }
                            break A;
                        }
                    }
                }
            }

            ball_x_position += ball_x_direction;
            ball_y_position += ball_y_direction;
        }
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

}

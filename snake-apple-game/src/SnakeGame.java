import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    private class Tile{
        int x;
        int y;

        public Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    int board_width;
    int board_height;
    int tile_size = 25;

    Tile snake_head;
    Tile food;
    ArrayList<Tile> snake_body;

    Random random;

    // game logic
    Timer game_loop;
    int velocity_x;
    int velocity_y;
    boolean game_over = false;

    public SnakeGame(int board_width, int board_height){
        this.board_width = board_width;
        this.board_height = board_height;
        setPreferredSize(new Dimension(this.board_width, this.board_height));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        
        snake_head = new Tile(5, 5);
        snake_body = new ArrayList<Tile>();
        food = new Tile(10, 10);
        random = new Random();
        place_food();

        velocity_x = 0;
        velocity_y = 1;
        game_loop = new Timer(400, this);
        game_loop.start();

    }

    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
        graphic.setColor(Color.black);
        graphic.fillRect(0, 0, board_width, board_height);
        if (game_over){
            // game over message
            graphic.setColor(Color.white);
            graphic.setFont(new Font("Arial", Font.PLAIN, 30));
            String over = "Game Over";
            int over_width = graphic.getFontMetrics().stringWidth(over);
            graphic.drawString(over, (board_width - over_width)/2, (board_height/2)-20);

            // final score message
            graphic.setFont(new Font("Arial", Font.PLAIN, 25));
            graphic.setColor(Color.white);
            String final_score = "Final Score: " + String.valueOf(snake_body.size());
            int final_width = graphic.getFontMetrics().stringWidth(final_score);
            graphic.drawString(final_score, (board_width - final_width)/2, (board_height/2)+20);
        } else{
            draw(graphic);
        }
    }

    public void draw(Graphics graphic){
        // snake head
        graphic.setColor(Color.decode("#028A0F"));
        graphic.fill3DRect(snake_head.x * tile_size, snake_head.y * tile_size, tile_size, tile_size, true);

        // snake body
        for (int i = 0; i < snake_body.size(); i++) {
            Tile snake_part = snake_body.get(i);
            graphic.setColor(Color.decode("#03AC13"));
            graphic.fill3DRect(snake_part.x * tile_size, snake_part.y * tile_size, tile_size, tile_size, true);
        }

        // grid
        // graphic.setColor(new Color(85, 85, 85));
        // for (int i = 0; i < board_width/tile_size; i++) {
        //     graphic.drawLine(i * tile_size, 0, i * tile_size, board_height); // vertical lines
        //     graphic.drawLine(0, i * tile_size, board_height, i * tile_size); // horizontal lines
        // }

        // food
        graphic.setColor(Color.red);
        graphic.fill3DRect(food.x * tile_size, food.y * tile_size, tile_size, tile_size, true);

        // score
        graphic.setFont(new Font("Arial", Font.PLAIN, 20));
        if (game_over){
            graphic.setColor(Color.white);
            graphic.drawString("Game Over: " + String.valueOf(snake_body.size()), tile_size - 16, tile_size);
        } else{
            graphic.setColor(Color.white);
            graphic.drawString("Score: " + String.valueOf(snake_body.size()), tile_size - 16, tile_size);
        }
    }

    public void place_food(){
        // random number from 0 to 24
        food.x = random.nextInt(board_width/tile_size); // 600/25 = 24 
        food.y = random.nextInt(board_height/tile_size); // 600/25 = 24
    }

    public boolean collision(Tile t1, Tile t2){
        return t1.x == t2.x && t1.y == t2.y;
    }

    public void move(){
        // eat food
        if (collision(snake_head, food)){
            snake_body.add(new Tile(food.x, food.y));
            place_food();
        }

        // snake body
        for (int i = snake_body.size() - 1; i >= 0; i--) {
            Tile snake_part = snake_body.get(i);
            if (i == 0){
                snake_part.x = snake_head.x;
                snake_part.y = snake_head.y;
            } else{
                Tile prev_snake_part = snake_body.get(i-1);
                snake_part.x = prev_snake_part.x;
                snake_part.y = prev_snake_part.y;
            }
        }

        // snake head
        snake_head.x += velocity_x;
        snake_head.y += velocity_y;

        // game over
        for (int i = 0; i < snake_body.size(); i++){
            Tile snake_part = snake_body.get(i);
            // when it collides with snake head
            if (collision(snake_head, snake_part)){
                game_over = true;
            }
        }

        if (snake_head.x*tile_size < 0 || snake_head.x*tile_size > board_width
            || snake_head.y*tile_size < 0 || snake_head.y*tile_size > board_height){
                game_over = true;
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // updates x and y position of snake
        repaint();
        if (game_over){
            game_loop.stop();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocity_y != 1){
            velocity_x = 0;
            velocity_y = -1;
        } 
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocity_y != -1){
            velocity_x = 0;
            velocity_y = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocity_x != 1){
            velocity_x = -1;
            velocity_y = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocity_x != -1){
            velocity_x = 1;
            velocity_y = 0;
        }
    }

    // we do not need these methods
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}

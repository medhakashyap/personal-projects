import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Brick {
    int map[][];
    int brick_width, brick_height;

    public Brick(int row, int col){
        map = new int[row][col];

        for (int i= 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = 1;
            }
        }

        brick_width = 540/col;
        brick_height = 150/row;
    }

    public void set_brick(int val, int r, int c){
        map[r][c] = val;
    }

    public void draw(Graphics2D g){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0){
                    g.setColor(Color.decode("#faa307"));
                    g.fill3DRect(j*brick_width + 80, i*brick_height + 50, brick_width, brick_height, true);

                    g.setColor(Color.black);
                    g.setStroke(new BasicStroke(1));
                    g.drawRect(j*brick_width + 80, i*brick_height + 50, brick_width, brick_height);
                }
            }
        }
    }
}

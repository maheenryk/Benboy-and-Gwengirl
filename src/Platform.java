import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Platform {
    
    int x, y;
    int width, height;

    Rectangle hitbox;

    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        hitbox = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D gtd){
        gtd.setColor(Color.BLACK);
        gtd.drawRect(x, y, width, height);
        gtd.setColor(Color.WHITE);
        gtd.fillRect(x+1, y+1, width-2, height-2);
    }
}

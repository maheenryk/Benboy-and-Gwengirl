package util;
import java.awt.Rectangle;

// Student ID: 22305226
// Student Name: Maheen Ahmed

public class StaticObject extends GameObject {
    
    int x, y;
    int width, height;

    public StaticObject(int x, int y, int width, int height, String imagePath) {
        super(imagePath, x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle hitbox() {
        return this.getHitbox();
    }
}

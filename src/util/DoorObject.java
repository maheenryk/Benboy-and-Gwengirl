package util;
import java.awt.Rectangle;

public class DoorObject extends GameObject{
    int x, y;
    int width, height;

    public DoorObject(int x, int y, int width, int height, String imagePath) {
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

import java.awt.Rectangle;

import util.GameObject;
import util.Point3f;

public class Platform extends GameObject {
    
    int x, y;
    int width, height;

    public Platform(int x, int y, int width, int height) {
        super("res/grass.png", 50, 50, new Point3f(x, y, 0));
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle hitbox() {
        return this.getHitbox();
    }
}

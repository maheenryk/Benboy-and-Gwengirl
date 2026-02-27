import java.awt.Rectangle;

import util.PlayerObject;

public class Platform extends PlayerObject {
    
    int x, y;
    int width, height;

    public Platform(int x, int y, int width, int height) {
        super("res/grass.png", x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle hitbox() {
        return this.getHitbox();
    }
}

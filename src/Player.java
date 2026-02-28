import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Rectangle;

import util.PlayerObject;
import util.GameObject;
import util.Point3f;
import util.StaticObject;
import util.Vector3f;

public class Player extends PlayerObject {

    MainWindow mainWindow;

    int jumpSpeed = -12;

    float xspeed;
    float yspeed;

    Rectangle hitbox = this.getHitbox();

    int playerNumber;

	private CopyOnWriteArrayList<GameObject> BulletListP1 = new CopyOnWriteArrayList<GameObject>();
	private CopyOnWriteArrayList<GameObject> BulletListP2 = new CopyOnWriteArrayList<GameObject>();

    public Player(int x, int y, int width, int height, MainWindow mainWindow, String imagePath, int playerNumber) {
        super(imagePath, x, y, width, height);
        this.mainWindow = mainWindow;
        this.playerNumber = playerNumber;
    }

    void playerLogic() {

        // Player 1 controls
        if (playerNumber == 1) {

            if (!Controller.getInstance().isKeyAPressed() && !Controller.getInstance().isKeyDPressed()) {
                xspeed *= 0.8;
            } else if (Controller.getInstance().isKeyAPressed()) {
                xspeed--;
            } else if (Controller.getInstance().isKeyDPressed()) {
                xspeed++;
            }

            // jump logic
            if (Controller.getInstance().isKeyWPressed()) {
                hitbox = this.getHitbox();
                hitbox.y++;
                for (StaticObject object : mainWindow.getModel().getPlatforms()) {
                    if (hitbox.intersects(object.hitbox())) {
                        yspeed = jumpSpeed;
                    }
                }

                hitbox.y--;
                Controller.getInstance().setKeyWPressed(false);
            }

            if (Controller.getInstance().isKeySPressed()) {
                CreateBullet(1);
                Controller.getInstance().setKeySPressed(false);
            }

        } 
        
        // Player 2 controls
        else if (playerNumber == 2) {

            if (!Controller.getInstance().isKeyJPressed() && !Controller.getInstance().isKeyLPressed()) {
                xspeed *= 0.8;
            } else if (Controller.getInstance().isKeyJPressed()) {
                xspeed--;
            } else if (Controller.getInstance().isKeyLPressed()) {
                xspeed++;
            }

            // jump logic
            if (Controller.getInstance().isKeyIPressed()) {
                hitbox = this.getHitbox();
                hitbox.y++;
                for (StaticObject object : mainWindow.getModel().getPlatforms()) {
                    if (hitbox.intersects(object.hitbox())) {
                        yspeed = jumpSpeed;
                    }
                }

                hitbox.y--;
                Controller.getInstance().setKeyIPressed(false);
            }

            if (Controller.getInstance().isKeyKPressed()) {
                CreateBullet(2);
                Controller.getInstance().setKeyKPressed(false);
            }
        }

        if (xspeed > 0 && xspeed < 0.75) xspeed = 0;
        if (xspeed < 0 && xspeed > -0.75) xspeed = 0;

        if (xspeed > 5) xspeed = 5;
        if (xspeed < -5) xspeed = -5;

        // gravity
        yspeed += 0.4f;

        // horizontal collision
        hitbox = this.getHitbox();
        hitbox.x += xspeed;
        for (StaticObject object : mainWindow.getModel().getPlatforms()) {
            if (hitbox.intersects(object.hitbox())) {
                hitbox.x -= xspeed;
                while (!hitbox.intersects(object.hitbox())) {
                    hitbox.x += Math.signum(xspeed);
                }
                hitbox.x -= Math.signum(xspeed);
                xspeed = 0;
                this.setX(hitbox.x);
            }
        }

        // vertical collision
        hitbox = this.getHitbox();
        hitbox.y += yspeed;
        for (StaticObject object : mainWindow.getModel().getPlatforms()) {
            if (hitbox.intersects(object.hitbox())) {
                hitbox.y -= yspeed;
                while (!hitbox.intersects(object.hitbox())) {
                    hitbox.y += Math.signum(yspeed);
                }
                hitbox.y -= Math.signum(yspeed);
                yspeed = 0;
                this.setY(hitbox.y);
            }
        }

        
        this.setX((int)(this.getX() + xspeed));
        this.setY((int)(this.getY() + yspeed));

        hitbox.x = this.getX();
        hitbox.y = this.getY();  
    }

    
    private void CreateBullet(int playerNumber) {
        
        if (playerNumber == 1) {
            BulletListP1.add(new GameObject("res/ben_bullet.png", 62, 48, new Point3f(this.getX(), this.getY(), 0.0f)));
        } else if (playerNumber == 2) {
            BulletListP2.add(new GameObject("res/gwen_bullet.png", 60, 47, new Point3f(this.getX(), this.getY(), 0.0f)));
            // System.out.println("Bullet created for Player 2 at: " + this.getX());

            // for (GameObject bullet : BulletListP2) {
            //     System.out.println("Current bullet position for Player 2: " + bullet.getCentre().getX());
            // }
        }
    }

    public void bulletLogic() {
        // Move and remove bullets safely using CopyOnWriteArrayList
        for (GameObject bullet : getBulletListP1()) {
            bullet.getCentre().ApplyVector(new Vector3f(3f, 0, 0));
            if (bullet.getCentre().getX() > 1500) {
                BulletListP1.remove(bullet);
            }
        }

        for (GameObject bullet : getBulletListP2()) {
            bullet.getCentre().ApplyVector(new Vector3f(-3f, 0, 0));
            //System.out.println("Bullet position for Player 2: " + bullet.getCentre().getX());
            if (bullet.getCentre().getX() < 50) {
                BulletListP2.remove(bullet);
            }
        }
    }

    public CopyOnWriteArrayList<GameObject> getBulletListP1() {
		return BulletListP1;
	}

    public CopyOnWriteArrayList<GameObject> getBulletListP2() {
		return BulletListP2;
	}

}

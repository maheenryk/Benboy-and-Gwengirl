import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Rectangle;

import util.PlayerObject;
import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Player extends PlayerObject {

    MainWindow mainWindow;

    int x, y;

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
        this.x = x;
        this.y = y;
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
                for (Platform platform : mainWindow.getModel().getPlatforms()) {
                    if (hitbox.intersects(platform.hitbox())) {
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
                for (Platform platform : mainWindow.getModel().getPlatforms()) {
                    if (hitbox.intersects(platform.hitbox())) {
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
        for (Platform platform : mainWindow.getModel().getPlatforms()) {
            if (hitbox.intersects(platform.hitbox())) {
                hitbox.x -= xspeed;
                while (!hitbox.intersects(platform.hitbox())) {
                    hitbox.x += Math.signum(xspeed);
                }
                hitbox.x -= Math.signum(xspeed);
                xspeed = 0;
                x = hitbox.x;
                this.setX(x);
            }
        }

        // vertical collision
        hitbox = this.getHitbox();
        hitbox.y += yspeed;
        for (Platform platform : mainWindow.getModel().getPlatforms()) {
            if (hitbox.intersects(platform.hitbox())) {
                hitbox.y -= yspeed;
                while (!hitbox.intersects(platform.hitbox())) {
                    hitbox.y += Math.signum(yspeed);
                }
                hitbox.y -= Math.signum(yspeed);
                yspeed = 0;
                y = hitbox.y;
                this.setY(y);
            }
        }

        x += xspeed;
        y += yspeed;
        this.setX(x);
        this.setY(y);

        hitbox.x = x;
        hitbox.y = y;  
    }

    
	private void CreateBullet(int playerNumber) {
		if (playerNumber == 1) {
			BulletListP1.add(new GameObject("res/Bullet.png", 64, 32,
					new Point3f(this.getX(), this.getY(), 0.0f)));
		} else if (playerNumber == 2) {
			BulletListP2.add(new GameObject("res/Bullet.png", 64, 32,
					new Point3f(this.getX(), this.getY(), 0.0f)));
		}
	}

    public void bulletLogic() {
		// TODO Auto-generated method stub
		// move bullets

		for (GameObject bullet : getBulletListP1()) {
			// check to move them

			bullet.getCentre().ApplyVector(new Vector3f(2, 0, 0));
			// see if they hit anything

			// see if they get to the top of the screen ( remember 0 is the top
			if (bullet.getCentre().getX() > 1550) {
				getBulletListP1().remove(bullet);
			}
		}

        for (GameObject bullet : getBulletListP2()) {
            // check to move them

            bullet.getCentre().ApplyVector(new Vector3f(-2, 0, 0));
            // see if they hit anything

            // see if they get to the top of the screen ( remember 0 is the top
            if (bullet.getCentre().getX() < 50) {
                getBulletListP2().remove(bullet);
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

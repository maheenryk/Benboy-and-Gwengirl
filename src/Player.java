import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Rectangle;

import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Player extends GameObject {

    MainWindow mainWindow;

    int x, y;

    float xspeed;
    float yspeed;

    Rectangle hitbox = this.getHitbox();

    int playerNumber;

	private CopyOnWriteArrayList<GameObject> BulletListP1 = new CopyOnWriteArrayList<GameObject>();
	private CopyOnWriteArrayList<GameObject> BulletListP2 = new CopyOnWriteArrayList<GameObject>();

    public Player(int x, int y, int width, int height, MainWindow mainWindow, String imagePath, int playerNumber) {
        super(imagePath, width, height, new Point3f(x, y, 0));
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
            // if (Controller.getInstance().isKeyWPressed()) {
            //     hitbox = this.getHitbox();
            //     hitbox.y += yspeed;
            //     for (Platform platform : mainWindow.getModel().getPlatforms()) {
            //         if (hitbox.intersects(platform.hitbox())) {
            //             System.out.println("Jump!");
            //             yspeed = 6;
            //         }
            //     }
            //     hitbox.y--;
            //     Controller.getInstance().setKeyWPressed(false);
            // }

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
            // if (Controller.getInstance().isKeyIPressed()) {
            //     hitbox = this.getHitbox();
            //     hitbox.y += yspeed;
            //     for (Platform platform : mainWindow.getModel().getPlatforms()) {
            //         if (hitbox.intersects(platform.hitbox())) {
            //             System.out.println("Jump!");
            //             yspeed = 6;
            //         }
            //     }
            //     hitbox.y--;
            //     Controller.getInstance().setKeyIPressed(false);
            // }

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
        yspeed -= 0.3f;

        // vertical collision
        hitbox = this.getHitbox();
        hitbox.y += yspeed;
        for (Platform platform : mainWindow.getModel().getPlatforms()) {
            if (hitbox.intersects(platform.hitbox())) { 
                // while (!hitbox.intersects(platform.hitbox())) {
                //     hitbox.y += Math.signum(yspeed);
                // }
                // hitbox.y -= Math.signum(yspeed);
                // yspeed = 0;

                if ((Controller.getInstance().isKeyWPressed() && playerNumber == 1) || (Controller.getInstance().isKeyIPressed() && playerNumber == 2)) {
                    System.out.println("Jump!");
                    yspeed = 6;
                    hitbox.y--;
                    Controller.getInstance().setKeyWPressed(false);
                    Controller.getInstance().setKeyIPressed(false);
                }
            }
        }

        this.getCentre().ApplyVector(new Vector3f(xspeed, 0, 0));
        this.getCentre().ApplyVector(new Vector3f(0, yspeed, 0));
    
    }

    
	private void CreateBullet(int playerNumber) {
		if (playerNumber == 1) {
			BulletListP1.add(new GameObject("res/Bullet.png", 64, 32,
					new Point3f(this.getCentre().getX(), this.getCentre().getY(), 0.0f)));
		} else if (playerNumber == 2) {
			BulletListP2.add(new GameObject("res/Bullet.png", 64, 32,
					new Point3f(this.getCentre().getX(), this.getCentre().getY(), 0.0f)));
		}
	}

    public void bulletLogic() {
		// TODO Auto-generated method stub
		// move bullets

		for (GameObject temp : getBulletListP1()) {
			// check to move them

			temp.getCentre().ApplyVector(new Vector3f(1, 0, 0));
			// see if they hit anything

			// see if they get to the top of the screen ( remember 0 is the top
			if (temp.getCentre().getX() > 850) {
				getBulletListP1().remove(temp);
			}
		}

        for (GameObject temp : getBulletListP2()) {
            // check to move them

            temp.getCentre().ApplyVector(new Vector3f(-1, 0, 0));
            // see if they hit anything

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getX() < -50) {
                getBulletListP2().remove(temp);
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

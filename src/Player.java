import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Rectangle;

import util.GameObject;
import util.MovingObject;
import util.Point3f;
import util.StaticObject;

public class Player extends GameObject{

    MainWindow mainWindow;

    int jumpSpeed = -12;

    float xspeed;
    float yspeed;

    Rectangle hitbox = this.getPlayerHitbox();

    int playerNumber;

	private CopyOnWriteArrayList<MovingObject> BulletListP1 = new CopyOnWriteArrayList<MovingObject>();
	private CopyOnWriteArrayList<MovingObject> BulletListP2 = new CopyOnWriteArrayList<MovingObject>();

    public Player(int x, int y, int width, int height, MainWindow mainWindow, String imagePath, int playerNumber) {
        super(imagePath, x, y, width, height);
        this.mainWindow = mainWindow;
        this.playerNumber = playerNumber;

        hitbox = this.getPlayerHitbox();
    }

    void playerLogic() {

        playerControls();

        playerMovementLogic();

    }


    private void playerControls() {
        
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
                hitbox = this.getPlayerHitbox();
                hitbox.y++;
                for (StaticObject object : mainWindow.getModel().getStaticObjects()) {
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
                hitbox = this.getPlayerHitbox();
                hitbox.y++;
                for (StaticObject object : mainWindow.getModel().getStaticObjects()) {
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
    }

    private void playerMovementLogic() {

        if (xspeed > 0 && xspeed < 0.75) xspeed = 0;
        if (xspeed < 0 && xspeed > -0.75) xspeed = 0;

        if (xspeed > 5) xspeed = 5;
        if (xspeed < -5) xspeed = -5;

        // gravity
        yspeed += 0.4f;

        // horizontal collision
        hitbox = this.getPlayerHitbox();
        hitbox.x += xspeed;
        for (StaticObject object : mainWindow.getModel().getStaticObjects()) {
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
        hitbox = this.getPlayerHitbox();
        hitbox.y += yspeed;
        for (StaticObject object : mainWindow.getModel().getStaticObjects()) {
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
            BulletListP1.add(new MovingObject("res/ben_bullet.png", 62, 48, new Point3f(this.getX(), this.getY(), 0.0f)));
        } else if (playerNumber == 2) {
            BulletListP2.add(new MovingObject("res/gwen_bullet.png", 60, 47, new Point3f(this.getX(), this.getY()+15, 0.0f)));
            // System.out.println("Bullet created for Player 2 at: " + this.getX());

            // for (GameObject bullet : BulletListP2) {
            //     System.out.println("Current bullet position for Player 2: " + bullet.getCentre().getX());
            // }
        }
    }

    public CopyOnWriteArrayList<MovingObject> getBulletListP1() {
		return BulletListP1;
	}

    public CopyOnWriteArrayList<MovingObject> getBulletListP2() {
		return BulletListP2;
	}

}

import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Rectangle;

import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Player extends GameObject {

    Rectangle hitbox = this.getHitbox();

	private CopyOnWriteArrayList<GameObject> BulletList = new CopyOnWriteArrayList<GameObject>();

    public Player(int x, int y, MainWindow mainWindow) {
        super("res/ben_still.png", 60, 80, new Point3f(x, y, 0));
    }

    void playerLogic() {

		// smoother animation is possible if we make a target position // done but may
		// try to change things for students

		// check for movement and if you fired a bullet

		if (Controller.getInstance().isKeyAPressed()) {
            this.getCentre().ApplyVector(new Vector3f(-2, 0, 0));
		}

		if (Controller.getInstance().isKeyDPressed()) {
			this.getCentre().ApplyVector(new Vector3f(2, 0, 0));
		}

		if (Controller.getInstance().isKeyWPressed()) {
			this.getCentre().ApplyVector(new Vector3f(0, 2, 0));
		}

		if (Controller.getInstance().isKeySPressed()) {
			this.getCentre().ApplyVector(new Vector3f(0, -2, 0));
		}

		if (Controller.getInstance().isKeySpacePressed()) {
			CreateBullet();
			Controller.getInstance().setKeySpacePressed(false);
		}

	}

    
	private void CreateBullet() {
		BulletList.add(new GameObject("res/Bullet.png", 64, 32,
				new Point3f(this.getCentre().getX(), this.getCentre().getY(), 0.0f)));

	}

    public void bulletLogic() {
		// TODO Auto-generated method stub
		// move bullets

		for (GameObject temp : getBulletList()) {
			// check to move them

			temp.getCentre().ApplyVector(new Vector3f(1, 0, 0));
			// see if they hit anything

			// see if they get to the top of the screen ( remember 0 is the top
			if (temp.getCentre().getX() > 850) {
				getBulletList().remove(temp);
			}
		}

	}

    public CopyOnWriteArrayList<GameObject> getBulletList() {
		return BulletList;
	}

}

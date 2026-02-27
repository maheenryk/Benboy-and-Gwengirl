package util;

import java.awt.Rectangle;

public class PlayerObject {
    
    private int x, y;
	private int width=10;
	private int height=10;
	private boolean hasTextured=false;
	private String textureLocation; 
	private String blanktexture="res/blankSprite.png";
	
    public PlayerObject(String textureLocation, int x, int y, int width, int height) { 
    	 hasTextured=true;
    	 this.textureLocation=textureLocation;
    	 this.x=x;
		 this.y=y;
    	 this.width=width;
		 this.height=height;
	}

	public int setX(int x) {
		this.x = x;
		return x;
	}

	public int setY(int y) {
		this.y = y;
		return y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle getHitbox() {
		int hitboxWidth = width - 10; 
		return new Rectangle(x, y, hitboxWidth, height);
	}

	public String getTexture() {
		if(hasTextured) 
			{
			return textureLocation;
			}
		 
		return blanktexture; 
	}

	public boolean isCollidingWith(PlayerObject other) {
		return this.getHitbox().intersects(other.getHitbox());
	}
}

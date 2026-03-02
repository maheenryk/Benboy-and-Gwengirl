import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Color;

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 
 * Credits: Kelly Charles (2020)
 */
public class Viewer extends JPanel {
	private long CurrentAnimationTime = 0;

	Model gameworld;

	public Viewer(Model World) {
		this.gameworld = World;
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Viewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void updateview() {

		this.repaint();
		// TODO Auto-generated method stub

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		CurrentAnimationTime++; // runs animation time step

		// Draw background
		drawBackground(g);

		// draw hitbox 
		//g.drawRect(gameworld.getPlayer1().getPlayerHitbox().x, gameworld.getPlayer1().getPlayerHitbox().y, gameworld.getPlayer1().getPlayerHitbox().width, gameworld.getPlayer1().getPlayerHitbox().height);
		//g.drawRect(gameworld.getPlayer2().getPlayerHitbox().x, gameworld.getPlayer2().getPlayerHitbox().y, gameworld.getPlayer2().getPlayerHitbox().width, gameworld.getPlayer2().getPlayerHitbox().height);
		
		// for (Platform platform : gameworld.getPlatforms()) {
		// 	g.drawRect(platform.hitbox().x, platform.hitbox().y, platform.hitbox().width, platform.hitbox().height);
		// }

		drawStats(g);

		if (!gameworld.mainWindow.isGameVersus()){
			// Draw Enemies
			gameworld.getBEnemies().forEach((Benemy) -> {
				drawEnemies((int) Benemy.getCentre().getX(), (int) Benemy.getCentre().getY(), (int) Benemy.getWidth(),
						(int) Benemy.getHeight(), Benemy.getTexture(), g);

			});

			gameworld.getGwEnemiesList().forEach((Gwenemy) -> {
				drawEnemies((int) Gwenemy.getCentre().getX(), (int) Gwenemy.getCentre().getY(), (int) Gwenemy.getWidth(),
						(int) Gwenemy.getHeight(), Gwenemy.getTexture(), g);

			});

			gameworld.getDoorObjectList().forEach((door) -> {
				drawDoors((int) door.getX(), (int) door.getY(), (int) door.getWidth(),
						(int) door.getHeight(), door.getTexture(), g);
				//g.drawRect(door.hitbox().x, door.hitbox().y, door.hitbox().width, door.hitbox().height);
			});
		}

		// Draw player 1 Game Object
		int x = (int) gameworld.getPlayer1().getX();
		int y = (int) gameworld.getPlayer1().getY();
		int width = (int) gameworld.getPlayer1().getWidth();
		int height = (int) gameworld.getPlayer1().getHeight();
		boolean movingLeft = gameworld.getPlayer1().getMovingLeft();
		String texture = gameworld.getPlayer1().getTexture();

		// Draw player
		drawPlayer(x, y, width, height, movingLeft, texture, g);
		//g.drawRect(gameworld.getPlayer1().getPlayerHitbox().x, gameworld.getPlayer1().getPlayerHitbox().y, gameworld.getPlayer1().getPlayerHitbox().width, gameworld.getPlayer1().getPlayerHitbox().height);

		x = (int) gameworld.getPlayer2().getX();
		y = (int) gameworld.getPlayer2().getY();
		width = (int) gameworld.getPlayer2().getWidth();
		height = (int) gameworld.getPlayer2().getHeight();
		movingLeft = gameworld.getPlayer2().getMovingLeft();
		texture = gameworld.getPlayer2().getTexture();

		drawPlayer(x, y, width, height, movingLeft, texture, g);
		//g.drawRect(gameworld.getPlayer2().getPlayerHitbox().x, gameworld.getPlayer2().getPlayerHitbox().y, gameworld.getPlayer2().getPlayerHitbox().width, gameworld.getPlayer2().getPlayerHitbox().height);


		drawStaticObjects(g);

		// Draw Bullets
		// change back
		gameworld.getPlayer1().getBulletListP1().forEach((bullet) -> {
			drawBullet((int) bullet.getCentre().getX(), (int) bullet.getCentre().getY(), (int) bullet.getWidth(),
					(int) bullet.getHeight(), bullet.getTexture(), g);
			g.drawRect(bullet.getHitbox().x, bullet.getHitbox().y, bullet.getHitbox().width, bullet.getHitbox().height);
		});

		gameworld.getPlayer2().getBulletListP2().forEach((bullet) -> {
			drawBullet((int) bullet.getCentre().getX(), (int) bullet.getCentre().getY(), (int) bullet.getWidth(),
					(int) bullet.getHeight(), bullet.getTexture(), g);
			g.drawRect(bullet.getHitbox().x, bullet.getHitbox().y, bullet.getHitbox().width, bullet.getHitbox().height);
			//System.out.println("Drawing bullet for Player 2 at: " + bullet.getCentre().getX());
		});


	}

	private void drawEnemies(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			BufferedImage myImage = ImageIO.read(TextureToLoad);
			// Draw the entire bullet sprite without cropping
			int imgWidth = myImage.getWidth();
			int imgHeight = myImage.getHeight();

			g.drawImage(myImage, x, y, x + width, y + height, 0, 0, imgWidth, imgHeight, null);

			// draw bullet hitbox
			// g.drawRect(x, y, width, height);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void drawDoors(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			BufferedImage myImage = ImageIO.read(TextureToLoad);
			// Draw the entire bullet sprite without cropping
			int imgWidth = myImage.getWidth();
			int imgHeight = myImage.getHeight();

			g.drawImage(myImage, x, y, x + width, y + height, 0, 0, imgWidth, imgHeight, null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBackground(Graphics g) {
		File TextureToLoad = new File("res/background.png"); // should work okay on OSX and Linux but check if you
																	// have issues depending your eclipse install or if
																	// your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			g.drawImage(myImage, 0, 0, 1600, 900, 0, 0, 1600, 900, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBullet(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			BufferedImage myImage = ImageIO.read(TextureToLoad);
			// Draw the entire bullet sprite without cropping
			int imgWidth = myImage.getWidth();
			int imgHeight = myImage.getHeight();

			g.drawImage(myImage, x, y, x + width, y + height, 0, 0, imgWidth, imgHeight, null);

			// draw bullet hitbox
			// g.drawRect(x, y, width, height);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawPlayer(int x, int y, int width, int height, boolean movingLeft, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			BufferedImage myImage = ImageIO.read(TextureToLoad);
			int imgWidth = myImage.getWidth();
			int imgHeight = myImage.getHeight();

			if (movingLeft){
				g.drawImage(myImage, x, y, x + (width/2), y + height, 0, 0, imgWidth/2, imgHeight, null);
			} else {
				g.drawImage(myImage, x, y, x + (width/2), y + height, imgWidth/2, 0, imgWidth, imgHeight, null);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer));
		// Lighnting Png from https://opengameart.org/content/animated-spaceships its
		// 32x32 thats why I know to increament by 32 each time
		// Bullets from https://opengameart.org/forumtopic/tatermands-art
		// background image from
		// https://www.needpix.com/photo/download/677346/space-stars-nebula-background-galaxy-universe-free-pictures-free-photos-free-images

	}

	private void drawStaticObjects(Graphics g) {
		gameworld.getStaticObjects().forEach((object) -> {
			File TextureToLoad = new File(object.getTexture()); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
			try {
				BufferedImage myImage = ImageIO.read(TextureToLoad);
				// Draw the entire sprite without cropping
				int imgWidth = myImage.getWidth();
				int imgHeight = myImage.getHeight();

				g.drawImage(myImage, object.getX(), object.getY(), object.getX() + object.getWidth(), object.getY() + object.getHeight(), 0, 0,
						imgWidth, imgHeight, null);
				
				// draw hitbox
				//g.drawRect(object.getHitbox().x, object.getHitbox().y, object.getHitbox().width, object.getHitbox().height);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	public void drawStats(Graphics g) {
		// Draw player scores
		// g.drawString("Player 1 Score: " + gameworld.getPlayer1Scores().getPlayerScore(), 100, 50);
		// g.drawString("Player 2 Score: " + gameworld.getPlayer2Scores().getPlayerScore(), 1300, 50);

		// Player 1 health bar
		g.setColor(Color.BLACK);
		g.fillRect(45, 15, 610, 30); // background of health bar
		g.setColor(Color.GREEN);
        g.fillRect(50, 20, gameworld.getPlayer1Scores().getPlayerHealth() * 6, 20);

		// Player 2 health bar
		g.setColor(Color.BLACK);
		g.fillRect(940, 15, 610, 30); // background of health bar
		g.setColor(Color.GREEN);
        g.fillRect(945 + (600 - gameworld.getPlayer2Scores().getPlayerHealth() * 6), 20, gameworld.getPlayer2Scores().getPlayerHealth() * 6, 20);
	}

	public void drawGameOver(String winner) {

		Graphics g = this.getGraphics();
		String imagePath = "";

		switch (winner) {
			case "p1":
				imagePath = "res/p1win.png";
				break;
			case "p2":
				imagePath = "res/p2win.png";
				break;
			case "players":
				imagePath = "res/win.png";
				break;
			case "computer":
				imagePath = "res/loss.png";
				break;
			default:
				break;
		}

		File TextureToLoad = new File(imagePath);

		try {
			Image myImage = ImageIO.read(TextureToLoad);
			g.drawImage(myImage, 0, 0, 1600, 900, 0, 0, 1600, 900, null);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawTutorialScreen(){

		Graphics g = this.getGraphics();
		File TextureToLoad = null;

		if(gameworld.mainWindow.isGameVersus()){
			TextureToLoad = new File("res/tutorial-vs.png");
		} else {
			TextureToLoad = new File("res/tutorial-coop.png");
		}

		try {
			Image myImage = ImageIO.read(TextureToLoad);
			g.drawImage(myImage, 400, 100, 1200, 800, 0, 0, 800, 600, null);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


/*
 * 
 * 
 *              VIEWER HMD into the world                                                             
                                                                                
                                      .                                         
                                         .                                      
                                             .  ..                              
                               .........~++++.. .  .                            
                 .   . ....,++??+++?+??+++?++?7ZZ7..   .                        
         .   . . .+?+???++++???D7I????Z8Z8N8MD7I?=+O$..                         
      .. ........ZOZZ$7ZZNZZDNODDOMMMMND8$$77I??I?+?+=O .     .                 
      .. ...7$OZZ?788DDNDDDDD8ZZ7$$$7I7III7??I?????+++=+~.                      
       ...8OZII?III7II77777I$I7II???7I??+?I?I?+?+IDNN8??++=...                  
     ....OOIIIII????II?I??II?I????I?????=?+Z88O77ZZO8888OO?++,......            
      ..OZI7III??II??I??I?7ODM8NN8O8OZO8DDDDDDDDD8DDDDDDDDNNNOZ= ......   ..    
     ..OZI?II7I?????+????+IIO8O8DDDDD8DNMMNNNNNDDNNDDDNDDNNNNNNDD$,.........    
      ,ZII77II?III??????DO8DDD8DNNNNNDDMDDDDDNNDDDNNNDNNNNDNNNNDDNDD+.......   .
      7Z??II7??II??I??IOMDDNMNNNNNDDDDDMDDDDNDDNNNNNDNNNNDNNDMNNNNNDDD,......   
 .  ..IZ??IIIII777?I?8NNNNNNNNNDDDDDDDDNDDDDDNNMMMDNDMMNNDNNDMNNNNNNDDDD.....   
      .$???I7IIIIIIINNNNNNNNNNNDDNDDDDDD8DDDDNM888888888DNNNNNNDNNNNNNDDO.....  
       $+??IIII?II?NNNNNMMMMMDN8DNNNDDDDZDDNN?D88I==INNDDDNNDNMNNMNNNNND8:..... 
   ....$+??III??I+NNNNNMMM88D88D88888DDDZDDMND88==+=NNNNMDDNNNNNNMMNNNNND8......
.......8=+????III8NNNNMMMDD8I=~+ONN8D8NDODNMN8DNDNNNNNNNM8DNNNNNNMNNNNDDD8..... 
. ......O=??IIIIIMNNNMMMDDD?+=?ONNNN888NMDDM88MNNNNNNNNNMDDNNNMNNNMMNDNND8......
........,+++???IINNNNNMMDDMDNMNDNMNNM8ONMDDM88NNNNNN+==ND8NNNDMNMNNNNNDDD8......
......,,,:++??I?ONNNNNMDDDMNNNNNNNNMM88NMDDNN88MNDN==~MD8DNNNNNMNMNNNDND8O......
....,,,,:::+??IIONNNNNNNDDMNNNNNO+?MN88DN8DDD888DNMMM888DNDNNNNMMMNNDDDD8,.... .
...,,,,::::~+?+?NNNNNNNMD8DNNN++++MNO8D88NNMODD8O88888DDDDDDNNMMMNNNDDD8........
..,,,,:::~~~=+??MNNNNNNNND88MNMMMD888NNNNNNNMODDDDDDDDND8DDDNNNNNNDDD8,.........
..,,,,:::~~~=++?NMNNNNNNND8888888O8DNNNNNNMMMNDDDDDDNMMNDDDOO+~~::,,,.......... 
..,,,:::~~~~==+?NNNDDNDNDDNDDDDDDDDNNND88OOZZ$8DDMNDZNZDZ7I?++~::,,,............
..,,,::::~~~~==7DDNNDDD8DDDDDDDD8DD888OOOZZ$$$7777OOZZZ$7I?++=~~:,,,.........   
..,,,,::::~~~~=+8NNNNNDDDMMMNNNNNDOOOOZZZ$$$77777777777II?++==~::,,,......  . ..
...,,,,::::~~~~=I8DNNN8DDNZOM$ZDOOZZZZ$$$7777IIIIIIIII???++==~~::,,........  .  
....,,,,:::::~~~~+=++?I$$ZZOZZZZZ$$$$$777IIII?????????+++==~~:::,,,...... ..    
.....,,,,:::::~~~~~==+?II777$$$$77777IIII????+++++++=====~~~:::,,,........      
......,,,,,:::::~~~~==++??IIIIIIIII?????++++=======~~~~~~:::,,,,,,.......       
.......,,,,,,,::::~~~~==+++???????+++++=====~~~~~~::::::::,,,,,..........       
.........,,,,,,,,::::~~~======+======~~~~~~:::::::::,,,,,,,,............        
  .........,.,,,,,,,,::::~~~~~~~~~~:::::::::,,,,,,,,,,,...............          
   ..........,..,,,,,,,,,,::::::::::,,,,,,,,,.,....................             
     .................,,,,,,,,,,,,,,,,.......................                   
       .................................................                        
           ....................................                                 
               ....................   .                                         
                                                                                
                                                                                
                                                                 GlassGiant.com
                                                                 */

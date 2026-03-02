import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
 */

public class MainWindow {
	private static JFrame frame = new JFrame("Game"); // Change to the name of your game
	private static Model gameworld;
	private static Viewer canvas;
	private Controller Controller;
	private static boolean startGame = false;
	private static JLabel BackgroundImageForStartMenu;
	private static JLabel BackgroundImageForGameOver;
	private static JButton vsMenuButton;
	private static JButton coopMenuButton;
	//private static JButton restartButton;

	private boolean tutorialScreen = true;

	private boolean versus;

	Player player;

	public MainWindow() {

		gameworld = new Model(this);
		canvas = new Viewer(gameworld);
		Controller = new Controller();

		frame.setSize(1600, 900); // you can customise this later and adapt it to change on size.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		// If exit you can modify with your way of quitting, just is a template.

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) (screenSize.getWidth() / 2 - 800), (int) (screenSize.getHeight() / 2 - 450)); 
		// center the window 

		frame.setLayout(null);
		frame.setResizable(false);
		frame.setTitle("BenBoy and GwenGirl");
		frame.add(canvas);
		canvas.setBounds(0, 0, 1600, 900);
		canvas.setBackground(new Color(255, 255, 255)); 
		// white background replaced by Space background but if you
		// remove the background method this will draw a white screen

		canvas.setVisible(false); // this will become visible after you press the key.
		
		File GameOver = new File("res/loss.png");

		try {
			BufferedImage myPicture = ImageIO.read(GameOver);
			BackgroundImageForGameOver = new JLabel(new ImageIcon(myPicture));
			BackgroundImageForGameOver.setBounds(0, 0, 1600, 900);
			frame.add(BackgroundImageForGameOver);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BackgroundImageForGameOver.setVisible(false);
		frame.add(BackgroundImageForGameOver);

		//vsMenuButton = new JButton(); // start button
		BufferedImage buttonIcon = null;
		try {
			buttonIcon = ImageIO.read(new File("res/vs.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		vsMenuButton = new JButton(new ImageIcon(buttonIcon));
		//vsMenuButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
		//vsMenuButton.setContentAreaFilled(false);
		vsMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vsMenuButton.setVisible(false);
				coopMenuButton.setVisible(false);
				BackgroundImageForStartMenu.setVisible(false);
				canvas.setVisible(true);
				canvas.addKeyListener(Controller); // adding the controller to the Canvas
				canvas.requestFocusInWindow(); // making sure that the Canvas is in focus so keyboard input will be taking in
				startGame = true;
				versus = true;
				gameworld.setGameStart(true);
			}
		});

		vsMenuButton.setBounds(200, 700, 340, 110);

		BufferedImage buttonIcon2 = null;
		try {
			buttonIcon2 = ImageIO.read(new File("res/coop.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		coopMenuButton = new JButton(new ImageIcon(buttonIcon2));
		//vsMenuButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
		//vsMenuButton.setContentAreaFilled(false);
		coopMenuButton.setVisible(true);
		vsMenuButton.setVisible(true);
		coopMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				coopMenuButton.setVisible(false);
				vsMenuButton.setVisible(false);
				BackgroundImageForStartMenu.setVisible(false);
				canvas.setVisible(true);
				canvas.addKeyListener(Controller); // adding the controller to the Canvas
				canvas.requestFocusInWindow(); // making sure that the Canvas is in focus so keyboard input will be taking in
				startGame = true;
				versus = false;
				gameworld.setGameStart(true);
			}
		});

		coopMenuButton.setBounds(1100, 700, 310, 112);

		// loading background image
		File BackroundToLoad = new File("res/start.png"); 
		// should work okay on OSX and Linux but check if you
		// have issues depending your eclipse install or if your
		// running this without an IDE

		try {
			BufferedImage myPicture = ImageIO.read(BackroundToLoad);
			BackgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
			BackgroundImageForStartMenu.setBounds(0, 0, 1600, 900);
			frame.add(BackgroundImageForStartMenu);
		} catch (IOException e) {
			e.printStackTrace();
		}

		frame.add(vsMenuButton);
		frame.add(coopMenuButton);

		// restartButton = new JButton("Restart Game");
		// restartButton.setBounds(700, 750, 200, 40);
		// restartButton.setVisible(false);
		// restartButton.addActionListener(new ActionListener() {
		// 	@Override
		// 	public void actionPerformed(ActionEvent e) {
		// 		restartButton.setVisible(false);
		// 		setStartGame(true);
		// 	}
		// });

		frame.setVisible(true);
	}

	// public static void main(String[] args) {
	// 	MainWindow window = new MainWindow(); // sets up environment
	// 	while (true) {
	// 		// not nice but remember we do just want to keep looping till the end. this
	// 		// could be replaced by a thread but again we want to keep things simple
			
	// 		// swing has timer class to help us time this but I'm writing my own, you can of
	// 		// course use the timer, but I want to set FPS and display it
	// 		int TimeBetweenFrames = 1000 / TargetFPS;
	// 		long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames;

	// 		// wait till next time step
	// 		while (FrameCheck > System.currentTimeMillis()) {
	// 		}

	// 		if (startGame && !gameworld.isGameOver()) {
	// 			gameloop();
	// 		}

	// 		else if (gameworld.isGameOver()) {
	// 			startGame = false;
	// 			gameOverScreen();
	// 		}

	// 		// UNIT test to see if framerate matches
	// 		UnitTests.CheckFrameRate(System.currentTimeMillis(), FrameCheck, TargetFPS);

	// 	}

	// }

	// Basic Model-View-Controller pattern
	public void gameloop() {
		// GAMELOOP

		// controller input will happen on its own thread
		// So no need to call it explicitly

		// model update
		if (isGameVersus()) {
			gameworld.gamelogicVS();
		}
		else if (!isGameVersus()){
			gameworld.gamelogicCOOP();
		}
		// view update

		//System.out.println("Game Status: " + gameworld.isGameOver());
		// while(!gameworld.isGameOver()) {
		// 	canvas.updateview();
		// }

		canvas.updateview();

		// Both these calls could be setup as a thread but we want to simplify the game
		// logic for you.
		// score update
		//frame.setTitle("Score =  " + gameworld.getScore());

	}

	public void drawTutorialScreen(){
		while (tutorialScreen){
			canvas.drawTutorialScreen();
			if (Controller.getInstance().isSpacePressed()){
				System.out.print("end tutorial");
				tutorialScreen = false;
			}
		}
	}

	// private static void gameOverScreen() {
	// 	while (!startGame){
	// 		canvas.setVisible(false);
	// 		drawGameOver(gameworld.getWinner());
	// 		restartButton.setVisible(true);
	// 	}
	// }

	public Model getModel() {
		return gameworld;
	}

	public void drawGameOver(String winner) {

		//canvas.setVisible(false);

		vsMenuButton.setVisible(false);
		coopMenuButton.setVisible(false);

		//restartButton.setVisible(false);

		canvas.drawGameOver(winner);

		// String imagePath = "";

		// switch (winner) {
		// 	case "p1":
		// 		imagePath = "res/p1win.png";
		// 		break;
		// 	case "p2":
		// 		imagePath = "res/p2win.png";
		// 		break;
		// 	case "players":
		// 		imagePath = "res/win.png";
		// 		break;
		// 	case "computer":
		// 		imagePath = "res/loss.png";
		// 		break;
		// 	default:
		// 		break;
		// }

		// File GameOver = new File(imagePath);

		// try {
		// 	BufferedImage myPicture = ImageIO.read(GameOver);
		// 	BackgroundImageForGameOver = new JLabel(new ImageIcon(myPicture));
		// 	BackgroundImageForGameOver.setBounds(0, 0, 1600, 900);
		// 	frame.add(BackgroundImageForGameOver);
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
		
		// BackgroundImageForGameOver.setVisible(true);

	}

	public Viewer getCanvas() {
		return canvas;
	}

	public boolean isGameRunning() {
		return startGame;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JLabel getBackgroundImageForGameOver() {
		return BackgroundImageForGameOver;
	}

	public void setStartGame(boolean b) {
		startGame = b;
	}

	public boolean isGameVersus() {
		return versus;
	}

}

/*
 * 
 * 

Hand shake agreement 
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,=+++
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,:::::,=+++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,:++++????+??
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,:,,:,:,,,,,,,,,,,,,,,,,,,,++++++?+++++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,=++?+++++++++++??????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++?+++?++?++++++++++?????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++++++++++++++????+++++++???????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,:===+=++++++++++++++++++++?+++????????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,~=~~~======++++++++++++++++++++++++++????????????????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,::::,,,,,,=~.,,,,,,,+===~~~~~~====++++++++++++++++++++++++++++???????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,~~.~??++~.,~~~~~======~=======++++++++++++++++++++++++++????????????????II
:::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,:=+++??=====~~~~~~====================+++++++++++++++++++++?????????????????III
:::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,++~~~=+=~~~~~~==~~~::::~~==+++++++==++++++++++++++++++++++++++?????????????????IIIII
::::::::::::::::::::::::::::::::::::::::::::::::,:,,,:++++==+??+=======~~~~=~::~~===++=+??++++++++++++++++++++++++?????????????????I?IIIIIII
::::::::::::::::::::::::::::::::::::::::::::::::,,:+????+==??+++++?++====~~~~~:~~~++??+=+++++++++?++++++++++??+???????????????I?IIIIIIII7I77
::::::::::::::::::::::::::::::::::::::::::::,,,,+???????++?+?+++???7?++======~~+=====??+???++++++??+?+++???????????????????IIIIIIIIIIIIIII77
:::::::::::::::::::::::::::::::::::::::,,,,,,=??????IIII7???+?+II$Z77??+++?+=+++++=~==?++?+?++?????????????III?II?IIIIIIIIIIIIIIIIIIIIIIIIII
::::::::::::::::::::::::::::::,,,,,,~=======++++???III7$???+++++Z77ZDZI?????I?777I+~~+=7+?II??????????????IIIIIIIIIIIIIIIIIIIIII??=:,,,,,,,,
::::::::,:,:,,,,,,,:::~==+=++++++++++++=+=+++++++???I7$7I?+~~~I$I??++??I78DDDO$7?++==~I+7I7IIIIIIIIIIIIIIIIII777I?=:,,,,,,,,,,,,,,,,,,,,,,,,
++=++=++++++++++++++?+????+??????????+===+++++????I7$$ZZ$I+=~$7I???++++++===~~==7??++==7II?~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+++++++++++++?+++?++????????????IIIII?I+??I???????I7$ZOOZ7+=~7II?+++?II?I?+++=+=~~~7?++:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+?+++++????????????????I?I??I??IIIIIIII???II7II??I77$ZO8ZZ?~~7I?+==++?O7II??+??+=====.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
?????????????III?II?????I?????IIIII???????II777IIII7$ZOO7?+~+7I?+=~~+???7NNN7II?+=+=++,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
????????????IIIIIIIIII?IIIIIIIIIIII????II?III7I7777$ZZOO7++=$77I???==+++????7ZDN87I??=~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIII?II??IIIIIIIIIIIIIIIIIIIIIIIIIII???+??II7777II7$$OZZI?+$$$$77IIII?????????++=+.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII?+++?IIIII7777$$$$$$7$$$$7IIII7I$IIIIII???I+=,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII???????IIIIII77I7777$7$$$II????I??I7Z87IIII?=,,,,,,,,,,,:,,::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777777777777I7I777777777~,,,,,,,+77IIIIIIIIIII7II7$$$Z$?I????III???II?,,,,,,,,,,::,::::::::,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777$77777777777+::::::::::::::,,,,,,,=7IIIII78ZI?II78$7++D7?7O777II??:,,,:,,,::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$$$$$$$$$$$77=:,:::::::::::::::::::::::::::,,7II$,,8ZZI++$8ZZ?+=ZI==IIII,+7:,,,,:::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$I~::::::::::::::::::::::::::::::::::::::::::II+,,,OOO7?$DOZII$I$I7=77?,,,,,,:::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::+ZZ?,$ZZ$77ZZ$?,,,,,::::::::::::::::::::::::::,::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::I$:::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
                                                                                                                             GlassGiant.com
 * 
 * 
 */

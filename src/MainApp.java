import util.UnitTests;

public class MainApp {
    
	private static int TargetFPS = 100;
    private static MainWindow window;
    
	static Sound soundEffects = new Sound();

    public MainApp(){
        window = new MainWindow();
    }

	public static void main(String[] args) {

        while (true){
            soundEffects.themeMusic();
            play();
            drawGameOver();
            window.getFrame().remove(window.getBackgroundImageForGameOver());
        }
	}


    private static void drawGameOver() {

        while (true){
            window.drawGameOver(window.getModel().getWinner());

            //System.out.println("Is game running? " + window.isGameRunning());
            if (window.isGameRunning()) {
                break;
            }
        }
    }

    private static void play(){

        window = new MainWindow(); // sets up environment
		while (true) {
			// not nice but remember we do just want to keep looping till the end. this
			// could be replaced by a thread but again we want to keep things simple
			
			// swing has timer class to help us time this but I'm writing my own, you can of
			// course use the timer, but I want to set FPS and display it
			int TimeBetweenFrames = 1000 / TargetFPS;
			long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames;

			// wait till next time step
			while (FrameCheck > System.currentTimeMillis()) {
			}

			if (!window.getModel().isGameOver() && window.getModel().isGameStart()) {
				window.gameloop();
                window.drawTutorialScreen();
			}

            else if (window.getModel().isGameOver()) {
                window.setStartGame(false);
                break;
            }

			// else if (gameworld.isGameOver()) {
			// 	startGame = false;
			// 	gameOverScreen();
			// }

			// UNIT test to see if framerate matches
			UnitTests.CheckFrameRate(System.currentTimeMillis(), FrameCheck, TargetFPS);

		}

    }
    
}

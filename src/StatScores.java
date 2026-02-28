public class StatScores {

    private int playerScore;
    private int playerHealth;

    private int dmgAmount = 20;
    private int scoreAmount = 1;

    public StatScores() {
        this.playerScore = 0;
        this.playerHealth = 100;
    }

    public int getPlayerScore() {
        return playerScore;
    } 

    public int getPlayerHealth() {
        return playerHealth;
    }

    public void increasePlayerScore() {
        playerScore += scoreAmount;
    }

    public void decreasePlayerHealth() {
        playerHealth -= dmgAmount;
        if (playerHealth < 0) {
            playerHealth = 0;
        }
    }

    public void displayStats(int playerNumber) {
        System.out.println("\n-------------------------- Player " + playerNumber + " Stats -----------------------------");
        System.out.println("Score: " + playerScore);
        System.out.println("Health: " + playerHealth);
        System.out.println("-----------------------------------------------------------------------");
    }

}

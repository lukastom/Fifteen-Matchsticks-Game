import java.util.Random;

public class Game {
    static int lot;
    Board board;
    boolean firstRun, pcPlaysFirst, pcPlaysNow, userWantsExitRound;
    int pcScore = 0, userScore = 0;

    Game (){
        Random random = new Random();
        lot = random.nextInt(2); //0 - computer, 1 - player
        if (lot == 0) {
            System.out.println("A random draw decided that the first turn is mine this game.");
            pcPlaysFirst = true;
            pcPlaysNow = true;
        } else {
            System.out.println("A random draw decided that the first turn is yours this game.");
            pcPlaysFirst = false;
            pcPlaysNow = false;
        }
        firstRun = true;
        board = new Board();
    }

    public void letsPlay(){

        if (!firstRun){
            if (pcPlaysFirst){
                System.out.println("The first turn is mine this game.");
                pcPlaysNow = true;
            } else {
                System.out.println("The first turn is yours this game.");
                pcPlaysNow = false;
            }
        }

        while (board.getMatchesOnBoard() > 0) {
            board.drawBoard();
            if (pcPlaysNow){
                board.computersTurn();
            } else {
                board.usersTurn();
            }
            pcPlaysNow = !pcPlaysNow;
        }

        if (pcPlaysNow){
            pcScore += 1;
            System.out.println("YOU LOST!");
        } else {
            userScore += 1;
            System.out.println("YOU WON!");
        }

        System.out.println("Score you vs. me is: " + userScore + ":" + pcScore + ".");
        System.out.println("(To continue, press Enter.)");
        ScannerSystemInSingleton.getInstance().nextLine();

        //players take turns in starting a new round
        pcPlaysFirst = !pcPlaysFirst;
        if (firstRun) {
            firstRun = false;
        }

        //Putting the matchsticks back on the board
        board.setMatchesOnBoard(15);

    }
}

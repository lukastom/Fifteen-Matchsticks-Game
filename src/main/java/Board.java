import java.util.Random;

public class Board {
    private int matchesOnBoard;

    //Constructor
    public Board() {
        matchesOnBoard = 15;
    }

    //Setter
    public void setMatchesOnBoard(int matchesOnBoard) {
        this.matchesOnBoard = matchesOnBoard;
    }

    //Getter
    public int getMatchesOnBoard() {
        return matchesOnBoard;
    }

    //Drawing matchsticks on the board
    public void drawBoard() {
        System.out.println("There are " + matchesOnBoard + " matchsticks on the board now.");
        if (matchesOnBoard > 0) {
            for (int i = 0; i < (matchesOnBoard - 1); i++) {
                System.out.print("| ");
            }
            System.out.println("|");
        } else if (matchesOnBoard < 0) {
            System.out.println("Number of matchsticks on the board can not be less then zero.");
        }
    }

    //User's turn
    public void usersTurn() {
        UsersInput usersInput = new UsersInput();
        matchesOnBoard -= usersInput.MinMaxNumber("How many matchsticks do you want to take? (You can take 1 or 2 or 3. Enter the number and press Enter.)", 1, 3, matchesOnBoard);
    }

    //Computer's turn
    public void computersTurn(){
        int computerTakes;
        if (matchesOnBoard == 1) {  //if this is true, the rest else if/else statements will be skipped
            computerTakes = 1;
            //If computer goes first, it takes random number to obscure the winning strategy. It also takes random number in non-winnable situation.
        } else if ((matchesOnBoard == 15) || ((matchesOnBoard -1)%4 == 0)) {
            Random randomNumber = new Random();
            computerTakes = randomNumber.nextInt(3)+1;   //nextInt(x) returns random int from 0 to (x-1)
            //This is the optimal winning strategy
        } else {
            computerTakes = (matchesOnBoard -1)%4;
        }
        if (computerTakes == 1) {
            System.out.println("I have taken " + computerTakes + " matchstick.");
        } else {
            System.out.println("I have taken " + computerTakes + " matchsticks.");
        }
        matchesOnBoard -= computerTakes;
    }
}

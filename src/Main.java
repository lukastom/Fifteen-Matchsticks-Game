/*  15 Matchsticks Game
 *  ©2023 Lukáš Tomek
 */

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.welcome();

        boolean userWantsExitGame = false;
        int userChoseEnd;

        while (!userWantsExitGame){
            int userChose = menu.beginningMenu();
            switch (userChose) {
                case 1 -> {
                    Game game = new Game();
                    while (!game.userWantsExitRound){
                        game.letsPlay();
                        userChoseEnd = menu.endMenu();
                        switch (userChoseEnd){
                            case 2 -> game.userWantsExitRound = true;
                            case 3 -> {
                                menu.bye();
                                game.userWantsExitRound = true;
                                userWantsExitGame = true;
                            }
                        }
                    }
                }
                case 2 -> WriteText.rules();
                case 3 -> WriteText.winningStrategy();
                case 4 -> {
                    menu.bye();
                    /*Exiting with status 0 = normal exit. Non-zero status (e.g. 1) = abnormal exit. Status will be passed to OS.
                      //System.exit(0);
                      No need tu use this here. The program just runs to the end.
                     */
                    userWantsExitGame = true;
                }
            }
        }
    }
}

class Menu {

    String menuItemsToString(String[] menuItems){
        /* • StringBuilder = mutable vs String = immutable.
            • With every String variable reassign, it actually does not overwrite the value, but creates a new object!
              (which takes some time and takes more memory)
              (these objects are created in STRING CONSTANT POOL in HEAP)
              (In a loop, modern Java compiler converts internally all concatenations for StringBuilder, but creates a new StringBuilder object for every loop run.
               This is also not good for performance, so it is recommended to use directly 1 StringBuilder object for concatenations in a loop.)
              • if we assign two String variables to the same string, they will reference the same object. (This saves memory.)
                • this is also implicitly thread-safe
              • advantage: security - database passwords etc. are in immutable object, can not be changed
                • String class is declared final, so it can not be overridden
            • String is immutable = "it can not be mutated" = unmodifiable, unchangeable
            • mutable version of String is: 1) StringBuilder, 2) StringBuffer
          */
        StringBuilder menuItemsStringBuilder = new StringBuilder();
        for (int i = 0; i < menuItems.length; i++){
            if (i==(menuItems.length-1)){
                menuItemsStringBuilder.append(menuItems[i]);
            } else {
                menuItemsStringBuilder.append(menuItems[i]).append(System.lineSeparator());
            }
        }
        //toString() converts StringBuilder to String
        return menuItemsStringBuilder.toString();
    }

    void welcome() {
        System.out.println("━━━━━━━━━━━━━━━ 1 5  M A T C H S T I C K S  G A M E  W E L C O M E S  Y O U ━━━━━━━━━━━━━━━");
    }

    int beginningMenu() {
        String[] menuItems = {
                "┎────────── MENU ──────────┒",
                "┃  1 - PLAY NEW GAME       ┃",
                "┃  2 - RULES               ┃",
                "┃  3 - WINNING STRATEGY    ┃",
                "┃  4 - EXIT                ┃",
                "┖──────────────────────────┚",
                "(Enter a number and press Enter.)"
        };
        String menuItemsString = menuItemsToString(menuItems);

        UsersInput menu = new UsersInput();
        return menu.MinMaxNumber(menuItemsString, 1, 4);
    }

    int endMenu() {
        String[] menuItems = {
                "┎──── CONTINUE TO PLAY? ────┒",
                "┃  1 - CONTINUE TO PLAY     ┃",
                "┃  2 - BACK TO MAIN MENU    ┃",
                "┃  3 - EXIT                 ┃",
                "┖───────────────────────────┚",
                "(Enter a number and press Enter.)"
        };
        String menuItemsString = menuItemsToString(menuItems);
        UsersInput menu = new UsersInput();
        return menu.MinMaxNumber(menuItemsString, 1, 4);
    }

    void bye() {
        System.out.println("━━━━━━━━━━━━━━━ T H A N K  Y O U  F O R  P L A Y I N G  A N D  H A V E  A  N I C E  D A Y! ━━━━━━━━━━━━━━━");
        System.out.println("Exiting the program...");
    }

}

class WriteText{

    static void rules() {
        System.out.println("───── RULES ─────");
        System.out.println("There are 15 matchsticks on the table. A lot is drawn to see who will make the first turn. Players take turns. A player can take out 1 or 2 or 3 matchsticks. The player that takes the last matchstick lost. Players take turns to start the next game.");
        System.out.println("(To continue, press Enter.)");
        ScannerSystemInSingleton.getInstance().nextLine();
    }

    //TODO: This seems repetitive (System.out.println many times). I could use the same method when printing the menu lines from a string. (menuItemsToString)
    static void winningStrategy() {
        System.out.println("───── WINNING STRATEGY ─────");
        System.out.println("• How to win this game? It helps to visualize the 15 matchsticks like this:");
        System.out.println("|| - floor 4 (the remainder)");
        System.out.println("|||| - floor 3");
        System.out.println("|||| - floor 2");
        System.out.println("|||| - floor 1");
        System.out.println("| - we want to push our opponent to take this last matchstick");
        System.out.println("• Imagine the game as a building demolition. Every time when it is your turn, demolish 1 complete floor.");
        System.out.println("  • When you demolish a complete floor, you know for certain that you will be able to demolish the next complete floor and push your opponent to take the last matchstick at the end.");
        System.out.println("  • Note that whatever you opponent does, you can always demolish one complete floor.");
        System.out.println("  • 1 floor consists of 4 matchsticks. If your opponent takes 1, you take 3. If he takes 2, you take 2. If he takes 3, you take 1. In any case, a floor of 4 matches is gone.");
        System.out.println("• Of course, problem are these 2 remaining matches at the start of the game.");
        System.out.println("  • If it is a player's turn, he takes 2 matchsticks and continue the winning strategy, he will always win. This is called first-player-wins in combinatorial game theory.");
        System.out.println("  • If you are performing this as a party trick, do not quickly tell them the rules and crush them first. Let them find the winning strategy. If you are the first to play, do not always take 2 matches. Obscure the strategy by taking a different number of matchsticks and demolish the complete floor next turn.");
        System.out.println("(To continue, press Enter.)");
        ScannerSystemInSingleton.getInstance().nextLine();
    }

}

class Game {

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

        while (board.matchesOnBoard > 0) {
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
        board.matchesOnBoard = 15;

    }

}

class Board {

    int matchesOnBoard;

    //Constructor
    public Board() {
        matchesOnBoard = 15;
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

class UsersInput {

    /*2 polymorph methods, overloading
      TODO: overloading always goes against DRY principle. So it is always a dilemma if use it or not. I am still thinking about this.
     */
    public int MinMaxNumber (String prompt, int min, int max){
        System.out.println(prompt);
        int userTakes = 0;
        boolean inputIsInt;
        do {
            do {
                try {
                    inputIsInt = true;
                    userTakes = ScannerSystemInSingleton.getInstance().nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You have to enter a number, try again!");
                    inputIsInt = false;
                    /* When a scanner throws an InputMismatchException, the scanner will not skip the token
                       that caused the exception, so it must be skipped using other method, like nextLine(),
                       which skips the whole wrong input line. */
                    ScannerSystemInSingleton.getInstance().nextLine();
                }
            } while (!inputIsInt);
            if (userTakes < min) {
                System.out.println("You can not take less then 1 matchsticks, try again!");
            } else if (userTakes > max){
                System.out.println("You can not take more than 3 matchsticks, try again!");
            }
        } while (userTakes < min || userTakes > max);
        //Scanner nextInt() consumes only the number, not the newline created by pressing Enter. This consumes the newline.
        ScannerSystemInSingleton.getInstance().nextLine();
        /* return in if statements: 2 options
           1) it must be in all if/else statements (so the compiler is sure the method will certainly return in any case)
           2) we will use it only at the end of the method
        */
        return userTakes;
    }

    public int MinMaxNumber (String prompt, int min, int max, int matchesOnBoard){
        System.out.println(prompt);
        int userTakes = 0;
        boolean inputIsInt;
        boolean lastTurnWrong;
        do {
            lastTurnWrong = false;
            do {
                try {
                    inputIsInt = true;
                    userTakes = ScannerSystemInSingleton.getInstance().nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You have to enter a number, try again!");
                    inputIsInt = false;
                    ScannerSystemInSingleton.getInstance().nextLine();
                }
            } while (!inputIsInt);
            if (userTakes < min) {
                System.out.println("You can not take less then 1 matchsticks, try again!");
            } else if (userTakes > max){
                System.out.println("You can not take more than 3 matchsticks, try again!");
            } else if (matchesOnBoard==3 && userTakes>2) {
                lastTurnWrong = true;
                System.out.println("You can not take more than 2 matchsticks now, try again!");
            } else if (matchesOnBoard==2 && userTakes>1) {
                lastTurnWrong = true;
                System.out.println("You can not take more than 1 matchstick now, try again!");
            } else if (matchesOnBoard==1 && userTakes>1) {
                lastTurnWrong = true;
                System.out.println("You can not take more than 1 matchstick now, try again!");
            }
        } while (userTakes < min || userTakes > max || lastTurnWrong);
        ScannerSystemInSingleton.getInstance().nextLine();
        return userTakes;
    }
}

/* ----- SINGLETON CLASS -----
   • Having multiple scanners on the same stream is a bad practice, because scanners would share and consume that 1 stream.
   • There are less elegant solutions, like global variable (e.g. in Main and called like Main.scanner by other classes) or
     pass scanner as a parameter in methods called like board.usersTurn(scanner).
     The truly right OOP solution is to declare a SINGLETON CLASS (=class, from which only 1 object can be instantiated).
   • Singletons are often considered as bad practice in OOP (it is similar to global variable), but this is an example of a good use.
   • Here, we see LAZY INITIALIZATION implementation (memory effective, but not thread-safe)
 */
final class ScannerSystemInSingleton {    //final = can not be extended
    // Static = this variable will have only one instance + will be initialized only once when the class is first loaded
    private static Scanner scanner = null;

    /* CONSTRUCTOR
       Why empty? We want to prevent instantiating just by creating variable of ScannerSystemInSingleton type in the main program (to save memory).
       We want to instantiate the object only if a method getInstance() is called.
    */
    private ScannerSystemInSingleton() {
    }

    /* • static = can be called without creating an object
       • This is preventing multiple instantiation. Only one "scanner" object is permitted.
       • Only if the method is called, the object will be instantiated for the first time.
       • This condition is not thread safe. If you use threads, this part will need some more conditions.
     */
    public static Scanner getInstance() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;  //ScannerSystemInSingleton.getInstance() will: 1) only 1st time called - create new object, 2) return scanner object
    }
}

/*----- SINGLETON CLASS: EAGER INITIALIZATION -----
class MyScanner{
    //Disadvantage: The instance of the singleton class is created at the time of loading the class - before any method is used (can take memory).
    //Better is to use LAZY INITIALIZATION.
    private static final Scanner instance = new Scanner(System.in);
    private MyScanner(){
    }
    public static Scanner getInstance(){
        return instance;
    }
}

----- SHOULD WE USE scanner.close()? NO! -----
• If we close it, we can not use system.in for the rest of the program!
• Closing Scanner will automatically close the underlying stream with which it was created (e.g. System.in).
• Close only resources you have opened yourself. System.in is managed by the JVM, you don't need to close it. The JVM will close it.
*/
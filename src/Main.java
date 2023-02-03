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

        boolean user_wants_exit_game = false;
        int user_chose_end;

        while (!user_wants_exit_game){
            int user_chose = menu.beginning_menu();
            switch (user_chose) {
                case 1 -> {
                    Game game = new Game();
                    while (!game.user_wants_exit_round){
                        game.lets_play();
                        user_chose_end = menu.end_menu();
                        switch (user_chose_end){
                            case 2 -> game.user_wants_exit_round = true;
                            case 3 -> {
                                menu.bye();
                                game.user_wants_exit_round = true;
                                user_wants_exit_game = true;
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
                    user_wants_exit_game = true;
                }
            }
        }
    }
}

class Menu {

    String menuItemsToString(String[] menu_items){
        /*In modern Java compiler, every String concatenation is converted from + to StringBuilder and append()
          If we use + in a loop, it will create StringBuilder for every iteration, with a heavy performance penalty.
          So, it is recommended to use StringBuilder and append() when concatenating in a loop.
         */
        StringBuilder menu_items_string_builder = new StringBuilder();
        for (int i = 0; i < menu_items.length; i++){
            if (i==(menu_items.length-1)){
                menu_items_string_builder.append(menu_items[i]);
            } else {
                menu_items_string_builder.append(menu_items[i]).append(System.lineSeparator());
            }
        }
        //toString() converts StringBuilder to String
        return menu_items_string_builder.toString();
    }

    void welcome() {
        System.out.println("━━━━━━━━━━━━━━━ 1 5  M A T C H S T I C K S  G A M E  W E L C O M E S  Y O U ━━━━━━━━━━━━━━━");
    }

    int beginning_menu () {
        String[] menu_items = {
                "───── MENU ─────",
                "1 - PLAY NEW GAME",
                "2 - RULES",
                "3 - WINNING STRATEGY",
                "4 - EXIT",
                "(Enter a number and press Enter.)"
        };
        String menu_items_string = menuItemsToString(menu_items);

        UsersInput menu = new UsersInput();
        return menu.MinMaxNumber(menu_items_string, 1, 4);
    }

    int end_menu () {
        String[] menu_items = {
                "───── CONTINUE TO PLAY? ─────",
                "1 - CONTINUE TO PLAY",
                "2 - BACK TO MAIN MENU",
                "3 - EXIT",
                "(Enter a number and press Enter.)"
        };
        String menu_items_string = menuItemsToString(menu_items);
        UsersInput menu = new UsersInput();
        return menu.MinMaxNumber(menu_items_string, 1, 4);
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
        System.out.println("• Of course, problem are these 2 remaining matches.");
        System.out.println("  • If it is a player's turn, he takes 2 matchsticks and continue the winning strategy, he will always win. This is called first-player-wins in combinatorial game theory.");
        System.out.println("  • If you are performing this as a party trick, do not quickly tell them the rules and crush them first. Let them find the winning strategy. If you are the first to play, do not always take 2 matches. Obscure the strategy by taking a different number of matchsticks and demolish the complete floor next turn.");
        System.out.println("(To continue, press Enter.)");
        ScannerSystemInSingleton.getInstance().nextLine();
    }

}

class Game {

    static int lot;
    Board board;
    boolean first_run;
    boolean pc_plays_first;
    boolean pc_plays_now;
    boolean user_wants_exit_round;
    int pc_score = 0;
    int user_score = 0;

    Game (){
        Random random = new Random();
        lot = random.nextInt(2); //0 - computer, 1 - player
        if (lot == 0) {
            System.out.println("A random draw decided that the first turn is mine this game.");
            pc_plays_first = true;
            pc_plays_now = true;
        } else {
            System.out.println("A random draw decided that the first turn is yours this game.");
            pc_plays_first = false;
            pc_plays_now = false;
        }
        first_run = true;
        board = new Board();
    }

    public void lets_play(){

        if (!first_run){
            if (pc_plays_first){
                System.out.println("The first turn is mine this game.");
                pc_plays_now = true;
            } else {
                System.out.println("The first turn is yours this game.");
                pc_plays_now = false;
            }
        }

        while (board.matches_on_board > 0) {
            board.draw_board();
            if (pc_plays_now){
                board.computers_turn();
            } else {
                board.users_turn();
            }
            pc_plays_now = !pc_plays_now;
        }

        if (pc_plays_now){
            pc_score += 1;
            System.out.println("YOU LOST!");
        } else {
            user_score += 1;
            System.out.println("YOU WON!");
        }

        System.out.println("Score you vs. me is: " + user_score + ":" + pc_score + ".");
        System.out.println("(To continue, press Enter.)");
        ScannerSystemInSingleton.getInstance().nextLine();

        //players take turns in starting a new round
        pc_plays_first = !pc_plays_first;
        if (first_run) {
            first_run = false;
        }

        //Putting the matchsticks back on the board
        board.matches_on_board = 15;

    }

}

class Board {

    int matches_on_board;

    //Constructor
    public Board() {
        matches_on_board = 15;
    }

    //Drawing matchsticks on the board
    public void draw_board() {
        System.out.println("There are " + matches_on_board + " matchsticks on the board now.");
        if (matches_on_board > 0) {
            for (int i = 0; i < (matches_on_board - 1); i++) {
                System.out.print("| ");
            }
            System.out.println("|");
        } else if (matches_on_board < 0) {
            System.out.println("Number of matchsticks on the board can not be less then zero.");
        }
    }

    //User's turn
    public void users_turn() {
        UsersInput users_input = new UsersInput();
        matches_on_board -= users_input.MinMaxNumber("How many matchsticks do you want to take? (You can take 1 or 2 or 3. Enter the number and press Enter.)", 1, 3, matches_on_board);
    }

    //Computer's turn
    public void computers_turn(){
        int computer_takes;
        if (matches_on_board == 1) {  //if this is true, the rest else if/else statements will be skipped
            computer_takes = 1;
        //If computer goes first, it takes random number to obscure the winning strategy. It also takes random number in non-winnable situation.
        } else if ((matches_on_board == 15) || ((matches_on_board-1)%4 == 0)) {
            Random random_number = new Random();
            computer_takes = random_number.nextInt(3)+1;   //nextInt(x) returns random int from 0 to (x-1)
        //This is the optimal winning strategy
        } else {
            computer_takes = (matches_on_board-1)%4;
        }
        if (computer_takes == 1) {
            System.out.println("I have taken " + computer_takes + " matchstick.");
        } else {
            System.out.println("I have taken " + computer_takes + " matchsticks.");
        }
        matches_on_board -= computer_takes;
    }

}

class UsersInput {

    //2 polymorph methods
    public int MinMaxNumber (String prompt, int min, int max){
        System.out.println(prompt);
        int user_takes = 0;
        boolean input_is_int;
        do {
            do {
                try {
                    input_is_int = true;
                    user_takes = ScannerSystemInSingleton.getInstance().nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You have to enter a number, try again!");
                    input_is_int = false;
                    /* When a scanner throws an InputMismatchException, the scanner will not skip the token
                       that caused the exception, so it must be skipped using other method, like nextLine(),
                       which skips the whole wrong input line. */
                    ScannerSystemInSingleton.getInstance().nextLine();
                }
            } while (!input_is_int);
            if (user_takes < min) {
                System.out.println("You can not take less then 1 matchsticks, try again!");
            } else if (user_takes > max){
                System.out.println("You can not take more than 3 matchsticks, try again!");
            }
        } while (user_takes < min || user_takes > max);
        //Scanner nextInt() consumes only the number, not the newline created by pressing Enter. This consumes the newline.
        ScannerSystemInSingleton.getInstance().nextLine();
        /* return in if statements: 2 options
           1) it must be in all if/else statements (so the compiler is sure the method will certainly return in any case)
           2) we will use it only at the end of the method
        */
        return user_takes;
    }

    public int MinMaxNumber (String prompt, int min, int max, int matches_on_board){
        System.out.println(prompt);
        int user_takes = 0;
        boolean input_is_int;
        boolean last_turn_wrong;
        do {
            last_turn_wrong = false;
            do {
                try {
                    input_is_int = true;
                    user_takes = ScannerSystemInSingleton.getInstance().nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You have to enter a number, try again!");
                    input_is_int = false;
                    /* When a scanner throws an InputMismatchException, the scanner will not skip the token
                       that caused the exception, so it must be skipped using other method, like nextLine(),
                       which skips the whole wrong input line. */
                    ScannerSystemInSingleton.getInstance().nextLine();
                }
            } while (!input_is_int);
            if (user_takes < min) {
                System.out.println("You can not take less then 1 matchsticks, try again!");
            } else if (user_takes > max){
                System.out.println("You can not take more than 3 matchsticks, try again!");
            } else if (matches_on_board==3 && user_takes>2) {
                last_turn_wrong = true;
                System.out.println("You can not take more than 2 matchsticks now, try again!");
            } else if (matches_on_board==2 && user_takes>1) {
                last_turn_wrong = true;
                System.out.println("You can not take more than 1 matchstick now, try again!");
            } else if (matches_on_board==1 && user_takes>1) {
                last_turn_wrong = true;
                System.out.println("You can not take more than 1 matchstick now, try again!");
            }
        } while (user_takes < min || user_takes > max || last_turn_wrong);
        ScannerSystemInSingleton.getInstance().nextLine();
        return user_takes;
    }
}

/* ----- SINGLETON CLASS -----
   • Having multiple scanners on the same stream is a bad practice, because scanners would share and consume that 1 stream.
   • There are less elegant solutions, like global variable (e.g. in Main and called like Main.scanner by other classes) or
     pass scanner as a parameter in methods called like board.users_turn(scanner).
     The truly right OOP solution is to declare a SINGLETON CLASS (=class, from which only 1 object can be instantiated).
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
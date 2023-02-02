/*  15 Matchsticks Game
 *  ©2023 Lukáš Tomek
 */

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.lets_play();
    }
}

class Game {
    static int lot;
    Board board;
    boolean first_run;
    boolean pc_plays_first;
    boolean pc_plays_now;

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
            System.out.println("YOU LOST!");
        } else {
            System.out.println("YOU WON!");
        }

        //players take turns in starting a new game
        pc_plays_first = !pc_plays_first;
        if (first_run) {
            first_run = false;
        }

    }

    /* TODO • !pokud pretahnu posledni tah do minusu, sice me to varuje, ale vyhodi, ze jsem prohral - proc?
            • enkapsulovat fields a methods - nastavit access aby zbytecne nebyl pristup zvenku
              (napovidac v IDE ukazuje, co lze z objektu/tridy vytahnout (po napsani tecky)
            • napsat zaverecne vyhlaseni, jestli chce hrac pokracovat ve hre
     */
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
        matches_on_board -= users_input.MinMaxNumber("How many matchsticks do you want to take? (You can take 1 or 2 or 3.) Enter the number and press Enter.", 1, 3, matches_on_board);
    }

    //Computer's turn
    public void computers_turn(){
        int computer_takes;
        //If computer goes first, it takes random number to obscure the winning strategy. It also takes random number in non-winnable situation.
        if ((matches_on_board == 15) || ((matches_on_board-1)%4 == 0)) {
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
                    System.out.println("You have to enter a number, try again.");
                    input_is_int = false;
                    /* When a scanner throws an InputMismatchException, the scanner will not skip the token
                       that caused the exception, so it must be skipped using other method, like nextLine(),
                       which skips the whole wrong input line. */
                    ScannerSystemInSingleton.getInstance().nextLine();
                }
            } while (!input_is_int);
            if (user_takes < min) {
                System.out.println("You can not take less then 1 matchsticks, try again.");
            } else if (user_takes > max){
                System.out.println("You can not take more than 3 matchsticks, try again.");
            }
        } while (user_takes < min || user_takes > max);
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
        do {
            do {
                try {
                    input_is_int = true;
                    user_takes = ScannerSystemInSingleton.getInstance().nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You have to enter a number, try again.");
                    input_is_int = false;
                    /* When a scanner throws an InputMismatchException, the scanner will not skip the token
                       that caused the exception, so it must be skipped using other method, like nextLine(),
                       which skips the whole wrong input line. */
                    ScannerSystemInSingleton.getInstance().nextLine();
                }
            } while (!input_is_int);
            if (user_takes < min) {
                System.out.println("You can not take less then 1 matchsticks, try again.");
            } else if (user_takes > max){
                System.out.println("You can not take more than 3 matchsticks, try again.");
            } else if (matches_on_board==3 && user_takes>2) {
                System.out.println("You can not take more than 2 matchsticks now, try again.");
            } else if (matches_on_board==2 && user_takes>1) {
                System.out.println("You can not take more than 1 matchstick now, try again.");
            } else if (matches_on_board==1 && user_takes>1) {
                System.out.println("You can not take more than 1 matchstick now, try again.");
            }
        } while (user_takes < min || user_takes > max);
        /* return in if statements: 2 options
           1) it must be in all if/else statements (so the compiler is sure the method will certainly return in any case)
           2) we will use it only at the end of the method
        */
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
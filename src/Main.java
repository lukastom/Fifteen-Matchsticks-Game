/*  15 Matchsticks Game
 *  ©2023 Lukáš Tomek
 */

/* TODO • write a class with polymorph methods to take input from user using the singleton ScannerSystemInSingleton class
          (because also when using a menu, user will need to give an input)
          • method with string input
          • method with int input (parameters: from-to)
   FIXME
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------------");
        Board board = new Board();
        board.draw();
        board.users_turn();
        board.draw();
        board.users_turn();
        board.draw();
        board.users_turn();
        board.draw();

        System.out.println("-----------------------------------------------------------");
    }
}



class Board {
    int matches_on_board;

    //Constructor
    public Board() {
        matches_on_board = 15;
    }

    //Drawing matchsticks on the board
    public void draw() {
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
        System.out.println("How many matchsticks do you want to take? (You can take 1 or 2 or 3.) Enter the number and press Enter.");
        int user_takes = 0;
        boolean input_is_int;
        do {
            do {
                input_is_int = true;
                try {
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
            if (user_takes >= 1 && user_takes <= 3) {
                matches_on_board -= user_takes;
            } else if (user_takes < 1) {
                System.out.println("You can not take less then 1 matchsticks, try again.");
            } else {
                System.out.println("You can not take more than 3 matchsticks, try again.");
            }
        } while (user_takes < 1 || user_takes > 3);
        /* scanner.close(); - If we close it, we can not use system.in for the rest of the program!
                              Closing Scanner will automatically close the underlying stream with which it was created (here System.in).
                              Close only resources you have opened yourself. System.in is managed
                              by the JVM, you don't need to close it. The JVM will close it. */
    }

}

/* ----- SINGLETON CLASS -----
   • Having multiple scanners on the same stream is a bad practice, because scanners would share and consume that 1 stream.
   • There are less elegant solutions, like global variable (e.g. in Main and called like Main.scanner by other classes).
     Other less elegant solution is to pass scanner as a parameter in methods called like board.users_turn(scanner).
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
    //Disadvantage: The instance of the singleton class is created at the time of class loading before any method is used (can take memory).
    private static final Scanner instance = new Scanner(System.in);
    private MyScanner(){
    }
    public static Scanner getInstance(){
        return instance;
    }
}*/
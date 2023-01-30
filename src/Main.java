/*  15 Matchsticks Game
 *  ©2023 Lukáš Tomek
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
    public void draw (){
        System.out.println("There are " + matches_on_board + " matchsticks on the board now.");
        if (matches_on_board>0) {
            for (int i = 0; i < (matches_on_board - 1); i++) {
                System.out.print("| ");
            }
            System.out.println("|");
        } else if (matches_on_board<0) {
            System.out.println("Number of matchsticks on the board can not be less then zero.");
        }
    }

    //User's turn
    public void users_turn (){
        System.out.println("How many matchsticks do you want to take? (You can take 1 or 2 or 3.) Enter the number and press Enter.");
        Scanner scanner = new Scanner (System.in);
        int user_takes = 0;
        boolean input_is_int;
        do {
            do {
                input_is_int = true;
                try {
                    user_takes = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You have to enter a number, try again.");
                    input_is_int = false;
                    /* When a scanner throws an InputMismatchException, the scanner will not skip the token
                       that caused the exception, so it must be skipped using other method, like nextLine(),
                       which skips the whole wrong input line.
                       */
                    scanner.nextLine();
                }
            } while (!input_is_int);
            if (user_takes>0 && user_takes<4) {
                matches_on_board -= user_takes;
            } else if (user_takes<1) {
                System.out.println("You can not take less then 1 matchsticks, try again.");
            } else {
                System.out.println("You can not take more than 3 matchsticks, try again.");
            }
        } while (user_takes<1 || user_takes>3);
        //scanner.close();
    }


}
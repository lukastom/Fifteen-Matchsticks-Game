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
import java.util.InputMismatchException;

public class UsersInput {
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

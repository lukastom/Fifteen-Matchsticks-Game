import java.util.Scanner;

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

    //This is here just because of JUnit testing, so we can null the singleton between tests and reset Scanner(System.in) state this way.
    public static void setInstance(Scanner scanner) {
        ScannerSystemInSingleton.scanner = scanner;
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

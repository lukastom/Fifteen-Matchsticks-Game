public class WriteText {
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

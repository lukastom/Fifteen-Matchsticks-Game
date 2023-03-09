public class Menu {
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

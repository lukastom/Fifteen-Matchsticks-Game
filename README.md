# 15 Matchsticks Game

• used: Maven 3.9 building

• note: when you run jar in a Windows terminal, set the terminal to UTF-8 first (chcp 65001)

This program also demonstrates and explains by extended comments:

• What are singleton classes and how to implement the eager initialization and the lazy initialization.

• Behaviour of Scanner when InputMismatchException is thrown.

• Why we do not close a Scanner(System.in) object.

• Behavior of Scanner nextLine() called after nextInt().

• Concatenating strings in a loop - StringBuilder and append(), immutability of String class.

Game rules:

There are 15 matchsticks on the table. Players take turns. A player can take out 1 or 2 or 3 matchsticks. The player that takes the last matchstick lost. The PC player is programmed to win, but it starts the game randomly, so the human player does not guess what is the winning strategy easily after several rounds.
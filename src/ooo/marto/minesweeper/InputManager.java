package ooo.marto.minesweeper;

import java.util.Scanner;

public class InputManager {

    private Scanner scanner = new Scanner(System.in);
    private Minefield minefield;

    public InputManager(Minefield minefield) {
        this.minefield = minefield;
    }

    public void promptPlayer() {
        System.out.println("\nWould you like to dig or flag?\nEnter D to dig.\nEnter F to flag.");
        String input = scanner.nextLine();

        System.out.println("Enter the column number of the tile (leftmost = 1).");
        int x = scanner.nextInt() - 1;

        System.out.println("Enter the row number of the tile (topmost = 1).");
        int y = scanner.nextInt() - 1;

        System.out.println();
        scanner.nextLine();

        if (input.equalsIgnoreCase("d")) {
            minefield.selectTile(x, y);
        } else if (input.equalsIgnoreCase("f")) {
            if (!minefield.markFlag(x, y)) {
                System.out.println("There was an error marking your flag.");
            }
        } else {
            System.out.println("You did not enter a valid option.");
        }

        promptPlayer();
    }

}

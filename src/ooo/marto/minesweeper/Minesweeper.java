package ooo.marto.minesweeper;

public class Minesweeper {

    public static void main(String[] args) {
        Minefield minefield = new Minefield(3, 3, 1);
        minefield.printFullField(true);

        InputManager inputManager = new InputManager(minefield);
        inputManager.promptPlayer();
    }

}

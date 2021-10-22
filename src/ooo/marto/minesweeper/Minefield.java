package ooo.marto.minesweeper;

public class Minefield {

    private final int WIDTH, HEIGHT;
    private int totalMines, minesLeft;
    private boolean[][] mines, discoveredTiles, flagTiles;

    public Minefield(int width, int height, int totalMines) {
        WIDTH = width;
        HEIGHT = height;
        this.totalMines = totalMines;
        this.minesLeft = totalMines;
        mines  = new boolean[HEIGHT][WIDTH];
        discoveredTiles = new boolean[HEIGHT][WIDTH];
        flagTiles = new boolean[HEIGHT][WIDTH];

        populateField();
    }

    private void populateField() {
        for (int i = 0; i < totalMines; i++) {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);

            if (isMine(x, y)) i--;

            mines[y][x] = true;
        }
    }

    private boolean isMine(int x, int y) {
        return mines[y][x];
    }

    private boolean isDiscovered(int x, int y) {
        return discoveredTiles[y][x];
    }

    private boolean isFlag(int x, int y) {
        return flagTiles[y][x];
    }

    public void printFullField(boolean playing) {
        boolean won = true;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (isMine(x, y) != isFlag(x, y)) won = false;
                int adjacentMines = getAdjacentMines(x, y);
                System.out.print(playing ? isFlag(x, y) ? "F" : isDiscovered(x, y) ? adjacentMines == 0 ? "-" : adjacentMines : "?" : isMine(x, y) ? "X" : adjacentMines == 0 ? "-" : adjacentMines);
            }
            System.out.println();
        }

        if (won) {
            System.out.println("\nYou found all the flags and won the game! Congratulations!");
            System.exit(1);
        }

        System.out.println("\nYou have " + minesLeft + (minesLeft == 1 ? " flag" : " flags") + " remaining.");
    }

    private int getAdjacentMines(int x, int y) {
        int mines = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int effX = x + i, effY = y + j;
                if (effX < 0 || effX >= WIDTH || effY < 0 || effY >= HEIGHT) continue;

                if (isMine(effX, effY)) mines++;
            }
        }

        return mines;
    }

    public void selectTile(int x, int y) {
        if (isDiscovered(x, y)) {
            System.out.println("\nThis square has already been discovered.\n");
            printFullField(true);
            return;
        }

        if (isMine(x, y)) {
            printFullField(false);
            System.out.println("\nUh oh! Looks like you dug a mine! You lose!");
            System.exit(1);
            return;
        }

        recursiveDiscover(x, y);
        printFullField(true);
    }

    private void recursiveDiscover(int x, int y) {
        discoveredTiles[y][x] = true;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int effX = x + i, effY = y + j;

                if (effX < 0 || effX >= WIDTH || effY < 0 || effY >= HEIGHT || isDiscovered(effX, effY) || isFlag(effX, effY))
                    continue;

                if (getAdjacentMines(effX, effY) == 0) {
                    recursiveDiscover(effX, effY);
                } else if (!isMine(x, y) && getAdjacentMines(x, y) == 0) {
                    discoveredTiles[effY][effX] = true;
                }
            }
        }
    }

    public boolean markFlag(int x, int y) {
        if (isDiscovered(x, y) || x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return false;

        flagTiles[y][x] = true;
        minesLeft--;

        printFullField(true);

        return true;
    }


}

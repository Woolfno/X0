package Player;

import GameBoard.GameBoard;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 02.08.13
 */
public class Human extends Player {

    public Human(int mark) {
        super(mark);
    }

    @Override
    public void play(int[][] board) {
        int x, y;
        do {
            char[] step = sc.nextLine().toCharArray();
            x = step[0] - '0';
            y = step[1] - '0';
        } while (board[y][x] != GameBoard.EMPTY);
        this.x = x;
        this.y = y;
    }
}

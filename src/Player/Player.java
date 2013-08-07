package Player;

import GameBoard.GameBoard;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 09.07.13
 */
public abstract class Player {
    protected Scanner sc = new Scanner(System.in);

    protected final int MARK;
    protected final int EMEMY_MARK;

    protected int x;
    protected int y;

    public Player(int mark) {
        MARK = mark;
        if (mark == GameBoard.CROSS)
            EMEMY_MARK = GameBoard.ZERO;
        else
            EMEMY_MARK = GameBoard.CROSS;
        this.x = -1;
        this.y = -1;
    }

    abstract public void play(int[][] board);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}

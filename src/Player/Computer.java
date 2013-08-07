package Player;

import GameBoard.GameBoard;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 09.07.13
 */
public class Computer extends Player {

    private Random rand = new Random();

    public Computer(int mark) {
        super(mark);
    }

    @Override
    public void play(int[][] board) {
        int[] xy;
        xy = step(board);
        this.x = xy[0];
        this.y = xy[1];
    }

    private int[] step(int[][] board) {
        int x, y;
        //линия с двумя своими "фишками" и пустым полем
        for (int i = 0; i < board.length; i++) {
            y = i;
            x = checkHorizontalLine(board, i, MARK);
            if (x != -1)
                return new int[]{x, y};
            y = checkVerticalLine(board, i, MARK);
            x = i;
            if (y != -1)
                return new int[]{x, y};
        }
        x = checkDiagonal(board, 0, MARK);
        y = x;
        if (x != -1)
            return new int[]{x, y};
        x = checkDiagonal(board, 2, MARK);
        y = 2 - x;
        if (x != -1)
            return new int[]{x, y};

        //линия с двумя "фишками" противника и пустым полем
        for (int i = 0; i < board.length; i++) {
            y = i;
            x = checkHorizontalLine(board, i, EMEMY_MARK);
            if (x != -1)
                return new int[]{x, y};
            y = checkVerticalLine(board, i, EMEMY_MARK);
            x = i;
            if (y != -1)
                return new int[]{x, y};
        }
        x = checkDiagonal(board, 0, EMEMY_MARK);
        y = x;
        if (x != -1)
            return new int[]{x, y};
        x = checkDiagonal(board, 2, EMEMY_MARK);
        y = 2 - x;
        if (x != -1)
            return new int[]{x, y};

        if (board[1][1] == GameBoard.EMPTY) {
            return new int[]{1, 1};
        } else if (board[1][1] == MARK) {
            Random rand = new Random(); //поставить фишку в центральную вертикаль или горизонталь
            if (rand.nextInt(2) == 0) {
                for (int i = 0; i < board.length; i++) {
                    if (board[1][i] == GameBoard.EMPTY)
                        return new int[]{i, 1};
                }
            }
            for (int i = 0; i < board.length; i++) {
                if (board[i][1] == GameBoard.EMPTY)
                    return new int[]{1, i};
            }
        }

        //ставим фишку в любое свободное поле
        int[] t = null;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == GameBoard.EMPTY) {
                    t = new int[]{i, j};
                    break;
                }
            }
            if (t != null)
                break;
        }

        return t;
    }

    private int checkDiagonal(int[][] arr, int startIndex, int mark) {
        int countOfEmptyField = 0;
        int countOfBusyField = 0;
        int emptyIndex = -1;
        int j;
        for (int i = 0; i < arr.length; i++) {
            if (startIndex == 0) {
                j = i;
            } else {
                j = startIndex - i;
            }
            if (arr[i][j] == mark)
                countOfBusyField++;
            else if (arr[i][j] == GameBoard.EMPTY) {
                countOfEmptyField++;
                emptyIndex = i;
            }
        }
        if (countOfEmptyField == 1 && countOfBusyField == 2)
            return emptyIndex;
        return -1;
    }

    private int checkHorizontalLine(int[][] arr, int row, int mark) {
        int countOfEmptyField = 0;
        int countOfBusyField = 0;
        int emptyIndex = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[row][i] == mark)
                countOfBusyField++;
            else if (arr[row][i] == GameBoard.EMPTY) {
                countOfEmptyField++;
                emptyIndex = i;
            }
        }
        if (countOfEmptyField == 1 && countOfBusyField == 2)
            return emptyIndex;
        return -1;
    }

    private int checkVerticalLine(int[][] arr, int col, int mark) {
        int countOfEmptyField = 0;
        int countOfBusyField = 0;
        int emptyIndex = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][col] == mark)
                countOfBusyField++;
            else if (arr[i][col] == GameBoard.EMPTY) {
                countOfEmptyField++;
                emptyIndex = i;
            }
        }
        if (countOfEmptyField == 1 && countOfBusyField == 2)
            return emptyIndex;
        return -1;
    }
}


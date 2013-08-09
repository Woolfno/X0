/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 09.07.13
 */

package Main;

import GameBoard.GameBoard;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(GameBoard.NETWORKING, true);
        gameBoard.printBoard();
        while (!gameBoard.gameEnd()) {
            gameBoard.nextStep();
        }
    }
}

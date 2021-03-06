package GameBoard;

import Player.*;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 08.07.13
 */
public class GameBoard {

    public static final byte ZERO = 0;
    public static final byte CROSS = 1;
    public static final byte EMPTY = -1;

    public static final byte DEADHEAD = 2;

    private int emptyField;

    private History history = new History();
    private boolean historyON = true;

    public static final byte SINGLEPLAYER = 0;
    public static final byte MULTIPLAYER = 1;
    public static final byte NETWORKING = 2;

    private boolean isServer = false;

    private Player player;
    private Player enemy;

    private boolean endGame = false;
    private byte[][] board = new byte[3][3];
    private Scanner sc;

    public GameBoard(int typeOfGame) {
        init();
        setTypeOfGame(typeOfGame);
    }

    public GameBoard(int typeOfGame, String serverIP) {
        init();
        setTypeOfGame(typeOfGame);
        if (typeOfGame == NETWORKING) {
            ((ClientPlayer) (enemy)).setServerIP(serverIP);
            ((ClientPlayer) (enemy)).startClient();
        }
    }

    public GameBoard(int typeOfGame, boolean isServer) {
        init();
        this.isServer = isServer;
        setTypeOfGame(typeOfGame);
    }

    private void init() {
        emptyField = board.length * board.length;
        Arrays.fill(board[0], EMPTY);
        Arrays.fill(board[1], EMPTY);
        Arrays.fill(board[2], EMPTY);

        sc = new Scanner(System.in);
    }

    private void setTypeOfGame(int typeOfGame) {
        switch (typeOfGame) {
            case SINGLEPLAYER:
                player = new Human(CROSS);
                enemy = new Computer(ZERO);
                break;
            case MULTIPLAYER:
                player = new Human(CROSS);
                enemy = new Human(ZERO);
                break;
            case NETWORKING:
                if (isServer) {
                    player = new ServerPlayer(CROSS);
                    enemy = ((ServerPlayer) (player)).getRemotePlayer(ZERO);
                    ((ServerPlayer) (player)).startServer();
                } else {
                    enemy = new ClientPlayer(ZERO);
                    player = ((ClientPlayer) (enemy)).getRemotePlayer(CROSS);
                }
                historyON = false;
                break;
        }
    }

    public void nextStep() {
        if (!endGame) {
            player.play(board);
            printStep('X', player.getX(), player.getY());
            board[player.getY()][player.getX()] = CROSS;
            history.append(player.getX(), player.getY(), CROSS);
            emptyField--;
            printBoard();
            if (win()) return;

            enemy.play(board);
            printStep('0', enemy.getX(), enemy.getY());
            board[enemy.getY()][enemy.getX()] = ZERO;
            history.append(enemy.getX(), enemy.getY(), ZERO);
            emptyField--;
            printBoard();
            if (win()) return;

            history.print();
            if (historyON) {
                System.out.println("Go to back Step Y/N");
                if (sc.next().toUpperCase().equals("Y")) {
                    int s = sc.nextInt();
                    history.goBackToStep(s, board);
                    emptyField = board.length * board.length - s * 2;
                    System.out.print(emptyField);
                    printBoard();
                }
            }
        }
    }

    private boolean win() {
        switch (checkState()) {
            case ZERO:
                System.out.print("Win 0");
                endGame = true;
                return true;
            case CROSS:
                System.out.print("Win x");
                endGame = true;
                return true;
            case DEADHEAD:
                System.out.print("Dead Head");
                endGame = true;
                return true;
        }
        return false;
    }

    private int checkState() {
        int win = -1;

        if (checkDiagonal(0)) {
            win = board[0][0];
        } else if (checkDiagonal(2)) {
            win = board[0][2];
        }

        //проверка остальных линий
        if (win == -1)
            for (int i = 0; i < board.length; i++) {
                if (checkHorizontalLine(i)) {
                    win = board[i][0];
                    break;
                }
                if (checkVerticalLine(i)) {
                    win = board[0][i];
                    break;
                }
            }
        if (win == -1 && emptyField == 0)
            win = DEADHEAD;
        return win;
    }

    private boolean checkHorizontalLine(int row) {
        int countOfCross = 0;
        int countOfZero = 0;
        for (int i = 0; i < board.length; i++) {
            switch (board[row][i]) {
                case CROSS:
                    countOfCross++;
                    break;
                case ZERO:
                    countOfZero++;
                    break;
            }
        }
        if (countOfCross == 3 || countOfZero == 3)
            return true;
        return false;
    }

    private boolean checkVerticalLine(int col) {
        int countOfCross = 0;
        int countOfZero = 0;
        for (int i = 0; i < board.length; i++) {
            switch (board[i][col]) {
                case CROSS:
                    countOfCross++;
                    break;
                case ZERO:
                    countOfZero++;
                    break;
            }
        }
        if (countOfCross == 3 || countOfZero == 3)
            return true;
        return false;
    }

    private boolean checkDiagonal(int startIndex) {
        int countOfCross = 0;
        int countOfZero = 0;
        int j;
        for (int i = 0; i < board.length; i++) {
            if (startIndex == 0) {
                j = i;
            } else {
                j = 2 - i;
            }
            switch (board[i][j]) {
                case CROSS:
                    countOfCross++;
                    break;
                case ZERO:
                    countOfZero++;
                    break;
            }
        }
        if (countOfCross == 3 || countOfZero == 3)
            return true;
        return false;
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == CROSS) {
                    System.out.print("[x]");
                } else if (board[i][j] == ZERO) {
                    System.out.print("[0]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }

    private void printStep(char ch, int x, int y) {
        System.out.printf("\t%s: %d %d\n", ch, x, y);
    }

    public boolean gameEnd() {
        return endGame;
    }

}

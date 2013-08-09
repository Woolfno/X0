package GameBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 05.08.13
 */
public class History {
    private class Step {
        public int x;
        public int y;
        public int mark;

        public Step(int x, int y, int mark) {
            this.x = x;
            this.y = y;
            this.mark = mark;
        }
    }

    private List<Step> steps = new ArrayList<Step>();

    public void append(int x, int y, int mark) {
        steps.add(new Step(x, y, mark));
    }

    public void goBackToStep(int numStep, byte[][] board) {
        if (numStep < steps.size() && numStep >= 0) {
            for (int i = numStep + 2; i < steps.size(); i++) {
                board[steps.get(i).y][steps.get(i).x] = GameBoard.EMPTY;
                steps.remove(i);
            }
        }
    }

    public void print() {
        int st = 1;
        for (int i = 0; i < steps.size(); i++) {
            if (i % 2 == 0) {
                System.out.printf("%d:\n", st);
                st++;
            }
            System.out.printf(
                    "\t%c: %d,%d\n",
                    (steps.get(i).mark == GameBoard.CROSS) ? 'X' : '0',
                    steps.get(i).x,
                    steps.get(i).y
            );
        }
    }
}

package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class TicTacToePage {
    public void action(HttpServletRequest request, Map<String, Object> view) {
        HttpSession session = request.getSession();
        if (session.getAttribute("state") == null) {
            session.setAttribute("state", new State());
        }
        view.put("state", session.getAttribute("state"));
    }

    public void newGame(HttpServletRequest request, Map<String, Object> view) {
        request.getSession().setAttribute("state", new State());
        action(request, view);
    }

    public void onMove(HttpServletRequest request, Map<String, Object> view) {
        int moveX = -1;
        int moveY = -1;
        for (String attribute : request.getParameterMap().keySet()) {
            if (attribute.startsWith("cell_")) {
                if (moveX == -1) {
                    moveX = attribute.charAt(5) - '0';
                    moveY = attribute.charAt(6) - '0';
                } else {
                    action(request, view);
                    return;
                }
            }
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("state") == null) {
            session.setAttribute("state", new State());
        }
        State state = (State) session.getAttribute("state");
        state.makeMove(moveX, moveY);
        view.put("state", session.getAttribute("state"));
    }

    public static class State {
        private final int size = 3;
        private final Character[][] cells = new Character[size][size];
        private int freeCellsCount = size * size;
        private boolean crossesMove = true;
        private String phase = "RUNNING";

        public int getSize() {
            return size;
        }

        public Character[][] getCells() {
            return cells;
        }

        public String getPhase() {
            return phase;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        private void makeMove(int moveX, int moveY) {
            if (! phase.equals("RUNNING")) {
                return;
            }
            if (moveX < 0 || moveX >= size || moveY < 0 || moveY >= size) {
                return;
            }
            if (cells[moveX][moveY] != null) {
                return;
            }
            cells[moveX][moveY] = crossesMove ? 'x' : 'O';
            freeCellsCount--;
            boolean won = false;
            for (int i = 0; i < 3; i++) {
                won = won || isEqual(i, 0, i, 1, i, 2);
                won = won || isEqual(0, i, 1, i, 2, i);
            }
            won = won || isEqual(0, 0, 1, 1, 2, 2);
            won = won || isEqual(0, 2, 1, 1, 2, 0);
            if (won) {
                phase = crossesMove ? "WON_X" : "WON_O";
            } else if (freeCellsCount == 0) {
                phase = "DRAW";
            }
            crossesMove = ! crossesMove;
        }

        private boolean isEqual(int row1, int col1, int row2, int col2, int row3, int col3) {
            Character c1 = cells[row1][col1];
            Character c2 = cells[row2][col2];
            Character c3 = cells[row3][col3];
            return c1 != null && c1 == c2 && c1 == c3;
        }
    }
}

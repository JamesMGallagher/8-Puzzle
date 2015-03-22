
/**
 * The board class represent a position in the 8 puzzle.
 *
 * @author James Gallagher <james.gallagher@gmx.us>
 * @version 1.0
 * @since 2014-10-08
 */
public class Board {

    /**
     * An NxN array indexed by i(0to N-1) and j(0-1) representing the position
     * of the blocks on the board.
     */
    private int[][] board;
    /**
     * The dimension of the board
     */
    private int N;

    /**
     * Initialises a Board from an N-by-N array of blocks, (where blocks[i][j] =
     * block in row i, column j, with i,j indexed from 0 to N-1)
     *
     * @param blocks NxN array
     */
    public Board(int[][] blocks)//
    {
        N = blocks.length;
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = blocks[i][j];
            }
        }

    }

    /**
     * return the dimension of the board
     *
     * @return integer the dimension N
     */
    public int dimension() {
        return N;
    }

    /**
     * return boolean does the board represent the goal board
     *
     * @return boolean true if board is goal, false otherwise
     */
    public boolean isGoal() {
        int row, col;
        for (int i = 0; i < (N * N) - 1; i++) {
            row = i / N;
            col = i % N;

            if (board[row][col] != i + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds a twin of the current board. A twin of a board is obtained by
     * swapping the position of any two adjacent tiles
     *
     * @return a twin if the current board
     */
    public Board twin() {
        Board twin = new Board(board);

        //deal with degenerate cases
        if (twin == null || twin.dimension() == 1) {
            return twin;
        }

        // We can find a twin by exchanging the leftmost two tiles of a row,
        // provided that they are both non-empty. We try the first row, if this
        // does not have the first two tiels non-empty, we use the second row
        int row;

        for (row = 0; row < N; row++) {

            if (board[row][0] != 0 && board[row][1] != 0) {
                twin.board[row][0] = board[row][1];
                twin.board[row][1] = board[row][0];

                return twin;

            }

        }
        return twin;
    }

    /**
     * The hamming distance is the number of tiles out of position with respect
     * to the goal board
     *
     * @return the hamming distance from the goal board
     */
    public int hamming() {
        int ham = 0;
        int row, col;
        for (int i = 0; i < (N * N); i++) {
            row = i / N;
            col = i % N;

            // the ith element should be i+1 (we have 1 in position 0 etc.)
            if (board[row][col] != i + 1 && board[row][col] != 0) {
                ham++;
            }

        }
        return ham;
    }

    /**
     * Returns the Manhattan number.
     *
     * <p>
     * The Manhattan number of a tile is the sum of its horizontal and vertical
     * distance of that tile from its position on the goal board. The Manhattan
     * number of the board is the sum of Manhattan numbers of its tiles position
     * on the goal board
     * <p>
     * @return the Manhattan distance from the goal board
     */
    public int manhattan() {
        int man = 0;
        int row, col, brow, bcol;

        for (int i = 0; i < (N * N); i++) {
            row = i / N;
            col = i % N;

            if (board[row][col] == 0) {
                continue;
            }
            brow = (board[row][col] - 1) / N;
            bcol = (board[row][col] - 1) % N;

            man += Math.abs(brow - row) + Math.abs(bcol - col);

        }

        return man;
    }

    /**
     * Creates a string representation of the board for display
     *
     * @return string representation of the board
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Returns true if the board is equal to the argument, false otherwise.
     *
     * ***Best practice deviation - in order to implement API, this class does
     * not implement hash table.***
     *
     * Two boards are considered equal if every tile is in the same position on
     * each.
     *
     * @return boolean ture if equal to the argument board, false otherwise
     *
     * {@inheritDoc}
     *
     */
    @Override

    public boolean equals(Object Y) {

        if (Y == this) {
            return true;
        }
        if (Y == null) {
            return false;
        }
        if (this.getClass() != Y.getClass()) {
            return false;
        }

        Board that = (Board) Y;

        // If the hamming and manhattan values are cached, the perfomance of
        // can be improved in the case where hamming or manhattan values differ
        if (this.hamming()
                != that.hamming()) {
            return false;
        }

        if (this.manhattan()
                != that.manhattan()) {
            return false;
        }

        for (int i = 0;
                i < (this.dimension()); i++) {
            for (int j = 0; j < (this.dimension()); j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns a stack of the neighbours of the board.
     *
     * Neighbours are boards that can be reach within on move from the current
     * board. Each board has 2,3 or 4 neighbours.
     *
     * The stack used in this method is LinkedStack, an implementation of a
     * stack using a linked list.
     *
     * @return A stack containing the neighbours of the board
     *
     *
     */
    public Iterable<Board> neighbors() {
        LinkedStack<Board> Stack = new LinkedStack<Board>();
        Board neighbor;
        int row, col;
        for (int i = 0; i < (N * N); i++) {
            row = i / N;
            col = i % N;

            if (board[row][col] == 0) {
                if (row != N - 1) {
                    neighbor = new Board(board);
                    neighbor.board[row][col] = neighbor.board[row + 1][col];
                    neighbor.board[row + 1][col] = 0;
                    Stack.push(neighbor);
                }

                if (row != 0) {
                    neighbor = new Board(board);
                    neighbor.board[row][col] = neighbor.board[row - 1][col];
                    neighbor.board[row - 1][col] = 0;
                    Stack.push(neighbor);
                }

                if (col != N - 1) {
                    neighbor = new Board(board);
                    neighbor.board[row][col] = neighbor.board[row][col + 1];
                    neighbor.board[row][col + 1] = 0;
                    Stack.push(neighbor);
                }

                if (col != 0) {
                    neighbor = new Board(board);
                    neighbor.board[row][col] = neighbor.board[row][col - 1];
                    neighbor.board[row][col - 1] = 0;
                    Stack.push(neighbor);
                }

                return Stack;

            }
        }
        return Stack;

    }

}

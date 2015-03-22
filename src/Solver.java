
public class Solver {

    private int moves = 0;
    private Node finalNode;
    private Boolean solverRan, solvable;

    public Iterable<Board> solution() {
        Node printBoard = finalNode;
        LinkedStack<Board> solutionStack = new LinkedStack<Board>();

        while (printBoard != null) {
            solutionStack.push(printBoard.board);
            printBoard = printBoard.prev;
        }
        if (this.solvable) {
            return solutionStack;
        } else {
            return null;
        }

    }

    public Solver(Board initial) // find a solution to the initial board (using the A* algorithm)
    {

        Board twin;
        Board orig;
        Node prev;
        Node newNode;

        orig = initial;
        twin = initial.twin();

        Node origNode = new Node(initial, null, moves);
        Node twinNode = new Node(twin, null, moves);

        MinPQ<Node> PQ = new MinPQ<Node>();
        PQ.insert(origNode);
        MinPQ<Node> twinPQ = new MinPQ<Node>();
        twinPQ.insert(twinNode);

        while (true) {
            moves++;

            origNode = PQ.delMin();

            if (origNode.board.isGoal()) {
                solverRan = true;
                solvable = true;
                finalNode = origNode;
                break;
            }

            prev = origNode;
            Iterable<Board> neighbors = origNode.board.neighbors();
            for (Board neighbor : neighbors) {

                newNode = new Node(neighbor, prev, prev.moves + 1);

                if (origNode.prev == null || !newNode.board.equals(origNode.prev.board)) {
                    PQ.insert(newNode);
                }
            }

            twinNode = twinPQ.delMin();

            if (twinNode.board.isGoal()) {
                solverRan = true;
                solvable = false;
                finalNode = twinNode;
                break;
            }

            prev = twinNode;
            Iterable<Board> twinNeighbors = twinNode.board.neighbors();
            for (Board neigbor : twinNeighbors) {
                newNode = new Node(neigbor, prev, prev.moves + 1);
                if (twinNode.prev == null || !newNode.board.equals(twinNode.prev.board)) {
                    twinPQ.insert(newNode);
                }

            }

        }

    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (this.solvable) {
            return finalNode.moves;
        } else {
            return -1;
        }
    }

    private class Node implements Comparable<Node> {

        public Board board;
        Node prev;
        public int priority, moves;

        public Node(Board board, Node prev, int moves) {

            this.board = board;
            this.prev = prev;
            this.moves = moves;

            this.priority = moves + board.manhattan();

        }

        public int compareTo(Node that) {
            if (this.priority > that.priority) {
                return 1;
            }
            if (that.priority > this.priority) {
                return -1;
            }

            return 0;
        }

        /* public boolean boardEquals(Node that) {

         if (this.board.hamming() != that.board.hamming()) {
         return false;
         }

         if (this.board.manhattan() != that.board.manhattan()) {
         return false;
         }

         for (int i = 0; i < (this.board.dimension()); i++) {
         for (int j = 0; j < (this.board.dimension()); j++) {
         if (this.board.board[i][j] != that.board.board[i][j]) {
         return false;
         }
         }
         }

         return true;
         }
         */
    }

}

import java.io.Serializable;

public class Board implements Serializable {
	
	private static final long serialVersionUID = 1L; // used for object deep duplication
	
	private int[] board;
	
	public static int EMPTY = 0;
	public static int X = 1;
	public static int O = 2;

	public static int X_WINNER = -1;
	public static int O_WINNER = 1;
	
	public Board() {
		board = new int[9];
		for(int i = 0; i < board.length; i++)
			board[i] = EMPTY;
	}
	
	/**
	 * @return integer arrangement of the board's current state
	 */
	public int[] getBoard() {
		return board;
	}
	
	/**
	 * @return a formated string representation of the current board state
	 */
	public String toString() {
		String displayString = "";
		for(int i = 0; i < board.length; i++) {
			if(i % 3 == 0)
				displayString += "\n";
			displayString += " ";
			if(board[i] == EMPTY)
				displayString += "_";
			else if(board[i] == X)
				displayString += "X";
			else if(board[i] == O)
				displayString += "O";
			else
				displayString += "ERROR";
		}
		return displayString;
	}
	/**
	 * puts an 'X' or 'O' onto the game board
	 * @param xo - either 1 or 2 (corresponding with 'X' or 'O')
	 * @param index - box number 1 to 9 (RMO) to place the 'X' or 'O' 
	 * @return whether insertion was successful
	 */
	public boolean put(int xo, int index)  {
		if(index >= 0 && index < board.length && board[index] == EMPTY) {
			board[index] = xo;
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if the given character has won the game
	 * @param xo - either 1 or 2 (corresponding with 'X' or 'O')
	 * @return if the given character has won
	 */
	private boolean hasWon(int xo) {
		if(board[4] == xo) {
			if((board[1] == xo && board[7] == xo)
					|| (board[3] == xo && board[5] == xo)
					|| (board[2] == xo && board[6] == xo)
					|| (board[0] == xo && board[8] == xo))
				return true;
		}
		else if((board[0] == xo) && ((board[1] == xo && board[2] == xo) || (board[3] == xo && board[6] == xo)))
				return true;
		else if((board[8] == xo) && ((board[2] == xo && board[5] == xo) || board[6] == xo && board[7] == xo))
				return true;
		return false;
	}
	/**
	 * Determines if the current board is full
	 * @return true if full
	 */
	public boolean isFull() {
		for(int i : board)
			if(i == EMPTY)
				return false;
		return true;
	}
	/**
	 * @return true if the game is completed (win, loss, or tie)
	 */
	public boolean terminalTest() {
		return isFull() || utility() != 0;
	}
	/**
	 * Determines the state of the board
	 * @return -1 if X won, 1 if O won, 0 otherwise
	 */
	public int utility() {
		if(hasWon(X))
			return X_WINNER;
		else if(hasWon(O)) 
			return O_WINNER;
		else
			return 0;
	}
	/**
	 * Board setter method for testing purposes
	 * @param board new board to be set
	 */
	public void set(int[] board) {
		this.board = board;
	}
}

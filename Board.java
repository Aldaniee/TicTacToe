import java.io.Serializable;

public class Board implements Serializable {
	private static final long serialVersionUID = 1L; // allows object to be deep copied
	
	private int[] board;
	public static int EMPTY = 0;
	public static int X = 1;
	public static int O = 2;
	public static int HIGHEST = 1;
	public static int LOWEST = -1;
	public Board() {
		board = new int[9];
		for(int i = 0; i < 9; i++) {
			board[i] = EMPTY;
		}
	}
	/**
	 * 
	 * @return integer arrangement of the board's current state
	 */
	public int[] getBoard() {
		return board;
	}
	public String toString() {
		String displayString = "";
		for(int i = 0; i < board.length; i++) {
			if((i) % 3 == 0)
				displayString += "\n";
			displayString += " ";
			if(board[i] == EMPTY) {
				displayString += "_";
			}
			else if(board[i] == X) {
				displayString += "X";
			}
			else if(board[i] == O) {
				displayString += "O";
			}
			else
				displayString += "ERROR";
		}
		return displayString;
	}
	public boolean put(char xo, int index)  {
		if(board[index-1] == 0) {
			board[index-1] = convert(xo);
			return true;
		}
		else
			return false;
	}
	public int convert(char xo) {
		if(xo == 'X')
			return X;
		else 
			return O;
	}
	public boolean hasWon(char cxo) {
		int xo = convert(cxo);
		if(board[0] == xo && board[1] == xo && board[2] == xo)
			return true;
		else if(board[3] == xo && board[4] == xo && board[5] == xo)
			return true;
		else if(board[6] == xo && board[7] == xo && board[8] == xo)
			return true;
		else if(board[0] == xo && board[3] == xo && board[6] == xo)
			return true;
		else if(board[1] == xo && board[4] == xo && board[7] == xo)
			return true;
		else if(board[2] == xo && board[5] == xo && board[8] == xo)
			return true;
		else if(board[0] == xo && board[4] == xo && board[8] == xo)
			return true;
		else if(board[2] == xo && board[4] == xo && board[6] == xo)
			return true;
		return false;
	}
	public boolean isTie() {
		for(int i : board) {
			if(i == EMPTY)
				return false;
		}
		return true;
	}
	public boolean terminalTest() {
		if (isTie())
			return true;
		else if(hasWon('X'))
			return true;
		else if(hasWon('O'))
			return true;
		else
			return false;
	}
	public int utility() {
		if(hasWon('X'))
			return -1;
		else if(hasWon('O')) 
			return 1;
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

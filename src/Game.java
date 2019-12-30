import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	private static Scanner s; // allows for user input
	private static Board b; // stores the current state of the game
	
	/** Runs the interactive Tic-Tac-Toe game */
	public static void main(String[] args) {
		s = new Scanner(System.in);
		b = new Board();
		System.out.println("Welcome to Tic-Tac-Toe! You will be 'X' and the computer will be 'O'.");
		System.out.println("Type the index of your move (row-major order):");
		System.out.println(" _   _   _\n 1   2   3\n _   _   _\n 4   5   6\n _   _   _\n 7   8   9\n");
		do {
			if(b.put(Board.X, s.nextInt() - 1)) { // returns true if the placement is valid
				if(!b.isFull())
					b.set(miniMaxDecision(b).getBoard());
			}
			else
				System.out.println("Invalid input. Try again.");
			System.out.println(b);
		} while(b.terminalTest() == false);
		
		int result = b.utility();
		if(result == Board.X_WINNER)
			System.out.println("X won!");
		else if (result == Board.O_WINNER)
			System.out.println("O won!");
		else
			System.out.println("Tie game.");
		s.close();
	}
	
	/**
	 * Returns the next board state after the algorithm's optimal move
	 * @param bb a board with one more X then O on the board
	 * @return with an O added to the optimal location
	 */
	private static Board miniMaxDecision(Board bb) {
		ArrayList<Board> boards = makeMoveArray(Board.O, bb);
		int best = Board.X_WINNER; // starts at worst case scenario for algorithm
		Board bestBoard = null; // this will store running best board state after an the optimal move
		for(int n = 0; n < boards.size(); n++) {
			if(boards.get(n).terminalTest()) {
				int utility = boards.get(n).utility();
				if(utility >= best) {
					best = utility;
					bestBoard = boards.get(n);
				}
			}
			else {
				int min = min(boards.get(n));
				if(min >= best) {
					best = min;
					bestBoard = boards.get(n);
				}
			}
		}
		return bestBoard;
	}
	
	/**
	 * Returns the max outcome for the current position - used for algorithm moves
	 * @param bb a board with one more X then O
	 * @return the value of the optimal outcome from this position
	 */
	private static int max(Board bb) {
		ArrayList<Board> boards = makeMoveArray(Board.O, bb);
		int best = Board.X_WINNER; // starts at worst case scenario for algorithm
		for(Board bn: boards) {
			if(bn.terminalTest()) {
				int utility = bn.utility();
				if(utility >= best)
					best = utility;
			}
			else {
				int min = min(bn);
				if(min >= best)
					best = min;
			}
		}		
		return best;
	}
	
	/**
	 * Returns the min outcome for the current position - used for human moves
	 * @param bb a board with the same amount of Xs and Os
	 * @return the value of the optimal outcome from this position
	 */
	private static int min(Board bb) {
		ArrayList<Board> boards = makeMoveArray(Board.X, bb);
		int best = Board.O_WINNER; // starts at worst case scenario for human
		for(Board bn: boards) {
			if(bn.terminalTest()) {
				int utility = bn.utility();
				if(utility <= best)
					best = utility;
			}
			else {
				int max = max(bn);
				if(max <= best)
					best = max;
			}
		}
		return best;
	}
	/**
	 * Makes an array of all possible states of the board after the char xo is placed
	 * @param xo either X or O to be placed on the board
	 * @param bb the board to be moved on
	 * @return an array of all possible next states after xo's move
	 */
	private static ArrayList<Board> makeMoveArray(int xo, Board bb) {
		ArrayList<Board> boards = new ArrayList<Board>();
		for(int i = 0; i < bb.getBoard().length; i++) {
			if(bb.getBoard()[i] == Board.EMPTY) {
				Board temp = (Board) deepClone(bb);
				temp.put(xo, i);
				boards.add(temp);
			}
		}
		return boards;
	}
	
	/**
	 * This method makes a "deep clone" of any Java object it is given.
	 * @author Alvin Alexander, http://alvinalexander.com
	 */
	private static Object deepClone(Object object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

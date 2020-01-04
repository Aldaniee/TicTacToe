import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JFrame implements WindowListener,ActionListener {

	private static final long serialVersionUID = 1L;
	private static Board b; // stores the current state of the game
	private JButton buttons[] = new JButton[9];
	/** Runs the interactive Tic-Tac-Toe game */
	public static void main(String[] args) {
		Game game = new Game("Tic-Tac-Toe");
		game.setPreferredSize(new Dimension(600, 600));
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Display the window.
		game.pack();
		game.setVisible(true);
	}
	public Game(String title) {
		b = new Board();
		
		//Set up the content pane.
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;   //1 column wide
		c.weightx = 0.5;
		c.weighty = 0.5;
		for(int i = 0; i < 9; i++) {
			buttons[i] = new JButton(b.get(i));
			buttons[i].addActionListener(this);
		    buttons[i].setActionCommand(i + new String());
			c.gridx = i % 3;
			c.gridy = i / 3;
			add(buttons[i], c);
		}
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
	/**
	 * updates the button text to reflect the current board state
	 */
	public void updateButtons() {
		for(int i = 0; i < 9; i++)
			buttons[i].setText(b.get(i));
	}
	/**
	 * Communicates a message on the board
	 * @param message to send
	 */
	public void boardMessage(String message) {
		for(int i = 0; i < 9; i++)
			buttons[i].setText("");
		buttons[4].setText(message);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int i = Integer.parseInt(e.getActionCommand());
		b.put(Board.X, i); // returns true if the placement is valid
		updateButtons();
		if(!b.isFull()) {
			b.set(miniMaxDecision(b).getBoard());
			updateButtons();
		}
		if(b.terminalTest()) {
			int result = b.utility();
			b = new Board();
			if(result == Board.X_WINNER)
				boardMessage("X won! Well, this shouldn't be possible...");
			else if (result == Board.O_WINNER)
				boardMessage("You lose! Don't feel bad, you can't win.");
			else
				boardMessage("Tie game. It's really the best you can do.");
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}

package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Timer;
import java.util.TimerTask;

public class Board {

	private BooleanProperty field[][];
	private boolean buffer[][];
	private int width, height;
	private boolean running;

	private Timer timer;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		running = false;
		field = new BooleanProperty[width][height];
		buffer = new boolean[width][height];
		running = false;

		for (int i = 0; i < field.length; i++)
			for (int j = 0; j < field.length; j++)
				field[i][j] = new SimpleBooleanProperty();
	}

	/**
	 * This will toggle whether or not the GameOfLife is playing the
	 * way it should.
	 */
	public void playOrPause() {
		running = !running;
		if (running) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					calculateNextStep();
				}
			}, 0, 80);
		} else
			timer.cancel();
	}

	/**
	 * Calculates the state of the entire board for the next step and then
	 * stores refreshes the board with the new values.
	 */
	private void calculateNextStep() {
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				buffer[i][j] = isAlive(i, j);
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				field[i][j].set(buffer[i][j]);
		buffer = new boolean[width][height];
	}

	/**
	 * returns a boolean value representing whether the cell at the
	 * provided coordinates is alive or dead.
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean isAlive(int i, int j) {
		int neighborCount = liveNeighbors(i, j);
		if (field[i][j].get())
			return neighborCount >= 2 && neighborCount <= 3;
		return neighborCount == 3;
	}

	/**
	 * returns an integer representing the number of neighbors this
	 * cell has that are currently alive. Wrapping is built in.
	 * @param i
	 * @param j
	 * @return
	 */
	private int liveNeighbors(int i, int j) {
		int liveNeighborCount = 0;
//		System.out.println("(" + i + ", " + j + ")");
//		System.out.println("(" + (i - 1 + height) % height + ", " + j + 1 % width + ")");


		if (field[(i - 1 + height) % height][(j - 1 + width) % width].get())
			liveNeighborCount++;
		if (field[(i - 1 + height) % height][j].get())
			liveNeighborCount++;
		if (field[(i - 1 + height) % height][(j + 1) % width].get())
			liveNeighborCount++;
		if (field[i][(j - 1 + width) % width].get())
			liveNeighborCount++;
		if (field[i][(j + 1) % width].get())
			liveNeighborCount++;
		if (field[(i + 1) % height][(j - 1 + width) % width].get())
			liveNeighborCount++;
		if (field[(i + 1) % height][j].get())
			liveNeighborCount++;
		if (field[(i + 1) % height][(j + 1) % width].get())
			liveNeighborCount++;
		return liveNeighborCount;
	}

	/**
	 * Will bind the given BooleanProperty with the state of the cell
	 * at the given coordinate.
	 * @param stateProperty
	 * @param i
	 * @param j
	 */
	public void bindToCell(BooleanProperty stateProperty, int i, int j) {
		field[i][j].bindBidirectional(stateProperty);
	}

	/**
	 * Will unbind the given BooleanProperty from the state of the cell
	 * at the given coordinate.
	 * @param stateProperty
	 * @param i
	 * @param j
	 */
	public void unBindToCell(BooleanProperty stateProperty, int i, int j) {
		field[i][j].unbindBidirectional(stateProperty);
	}

	public boolean isPlaying() {
		return running;
	}

}

package model;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Board {

	private CellModel[][] field;
	private CellModel[][] buffer;
	private int width, height;
	private boolean running;
	private int turnCounter;

	private Timer timer;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		running = false;
		turnCounter = 0;
		field = new CellModel[width][height];
		buffer = new CellModel[width][height];
		running = false;
		for (int i = 0; i < field.length; i++)
			for (int j = 0; j < field.length; j++) {
				field[i][j] = new CellModel();
				buffer[i][j] = new CellModel();
			}
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
	public void calculateNextStep() {
		turnCounter++;
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				buffer[i][j] = futureState(i, j);
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				field[i][j].clone(buffer[i][j]);
	}

	/**
	 * returns a boolean value representing whether the cell at the
	 * provided coordinates is alive or dead.
	 * @param i
	 * @param j
	 * @return TODO bug should probably around here!
	 */
	private CellModel futureState(int i, int j) {
		int neighborCount = liveNeighbors(i, j);
		if (field[i][j].isAlive()) {
			if (neighborCount == 2 || neighborCount == 3) {
				return new CellModel(field[i][j]);
			} else {
				return new CellModel(Constants.DEAD, Constants.DEAD);
			}
		} else {
			if (neighborCount == 3) {
				return new CellModel(turnCounter, -1); // TODO change to correct Color for new cells
			} else {
				return new CellModel(Constants.DEAD, Constants.DEAD);
			}
		}
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


		if (field[(i - 1 + height) % height][(j - 1 + width) % width].isAlive())
			liveNeighborCount++;
		if (field[(i - 1 + height) % height][j].isAlive())
			liveNeighborCount++;
		if (field[(i - 1 + height) % height][(j + 1) % width].isAlive())
			liveNeighborCount++;
		if (field[i][(j - 1 + width) % width].isAlive())
			liveNeighborCount++;
		if (field[i][(j + 1) % width].isAlive())
			liveNeighborCount++;
		if (field[(i + 1) % height][(j - 1 + width) % width].isAlive())
			liveNeighborCount++;
		if (field[(i + 1) % height][j].isAlive())
			liveNeighborCount++;
		if (field[(i + 1) % height][(j + 1) % width].isAlive())
			liveNeighborCount++;
		return liveNeighborCount;
	}

	/**
	 * Will bind the given BooleanProperty with the state of the cell
	 * at the given coordinate. TODO Update this comment
	 * @param ageProperty
	 * @param i
	 * @param j
	 */
	public void bindToCell(IntegerProperty ageProperty, int i, int j) {
		field[i][j].ageProperty().bindBidirectional(ageProperty);
	}

	public boolean isPlaying() {
		return running;
	}

	public CellModel getCellModel(int i, int j) {
		return field[i][j];
	}

}

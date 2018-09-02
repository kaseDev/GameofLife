package model;

import javafx.beans.property.IntegerProperty;

import java.util.*;

public class Board {

	private CellModel[][] field;
	private CellModel[][] buffer;
	private int width, height;
	private boolean running;
	private int turnCount;

	private Timer timer;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		running = false;
		turnCount = 0;
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
		turnCount++;
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
	 * @return
	 */
	private CellModel futureState(int i, int j) {
		CellModel[] neighbors = liveNeighbors(i, j);
		if (field[i][j].isAlive()) {
			if (neighbors.length == 2 || neighbors.length == 3) {
				return new CellModel(field[i][j]);
			} else {
				return new CellModel(Constants.DEAD, Constants.DEAD);
			}
		} else {
			if (neighbors.length == 3) {
				return new CellModel(turnCount, calculateColor(neighbors));
//				return new CellModel(turnCount, 0);
			} else {
				return new CellModel(Constants.DEAD, Constants.DEAD);
			}
		}
	}

	public int calculateColor(CellModel[] neighbors) {
		Map<Integer, Integer> colorCount = new HashMap<>();
		List<Integer> mostOccurred = new ArrayList<>();
		int largestYet = 0;
		for (CellModel neighbor : neighbors) {
			Integer val = colorCount.get(neighbor.getColor());
			if (val == null)
				colorCount.put(neighbor.getColor(), 1);
			else
				colorCount.put(neighbor.getColor(), val+1);
		}
		for (Map.Entry<Integer, Integer> color : colorCount.entrySet()) {
			if (color.getValue() == largestYet)
				mostOccurred.add(color.getKey());
			else if (color.getValue() > largestYet) {
				mostOccurred.clear();
				mostOccurred.add(color.getKey());
			}
		}
		return mostOccurred.get(new Random().nextInt(mostOccurred.size()));
	}

	/**
	 * returns an integer representing the number of neighbors this
	 * cell has that are currently alive. Wrapping is built in.
	 * @param i
	 * @param j
	 * @return
	 */
	private CellModel[] liveNeighbors(int i, int j) {
		List<CellModel> neighborList = new ArrayList<>();
		if (field[(i - 1 + height) % height][(j - 1 + width) % width].isAlive())
			neighborList.add(field[(i - 1 + height) % height][(j - 1 + width) % width]);
		if (field[(i - 1 + height) % height][j].isAlive())
			neighborList.add(field[(i - 1 + height) % height][j]);
		if (field[(i - 1 + height) % height][(j + 1) % width].isAlive())
			neighborList.add(field[(i - 1 + height) % height][(j + 1) % width]);
		if (field[i][(j - 1 + width) % width].isAlive())
			neighborList.add(field[i][(j - 1 + width) % width]);
		if (field[i][(j + 1) % width].isAlive())
			neighborList.add(field[i][(j + 1) % width]);
		if (field[(i + 1) % height][(j - 1 + width) % width].isAlive())
			neighborList.add(field[(i + 1) % height][(j - 1 + width) % width]);
		if (field[(i + 1) % height][j].isAlive())
			neighborList.add(field[(i + 1) % height][j]);
		if (field[(i + 1) % height][(j + 1) % width].isAlive())
			neighborList.add(field[(i + 1) % height][(j + 1) % width]);
		return neighborList.toArray(new CellModel[0]);
	}

	public boolean isPlaying() {
		return running;
	}

	public CellModel getCellModel(int i, int j) {
		return field[i][j];
	}

	public int getTurnCount() {
		return turnCount;
	}

}

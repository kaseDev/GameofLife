package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import model.Board;
import model.Constants;
import view.CellView;

public class GameController {

	@FXML private GridPane pane;
	@FXML private Button playButton;
	@FXML private Rectangle colorIndicator;

	private int curColor = 3;
	private Board board = new Board(45, 45);

	@FXML
	public void initialize() {
		for (int i = 0; i < 45; i++) {
			for (int j = 0; j < 45; j++) {
				CellView cellView = new CellView(i, j, this);
				board.getCellModel(i, j).bind(cellView.getCellModel());
				pane.add(cellView, i, j);
			}
		}
//		colorIndicator.setFill(Constants.getColor(curColor));
	}

	@FXML
	public void playOrPause() {
		board.playOrPause();
		if (board.isPlaying())
			playButton.setText("Pause");
		else
			playButton.setText("Play");
	}

	@FXML
	public void oneStep() {
		board.calculateNextStep();
	}

	@FXML
	public void changeColor() {
		curColor = (curColor + 1) % 8;
		colorIndicator.setFill(Constants.getColor(curColor));
	}

	public int getColor() {
		return curColor;
	}

	public int getTurnCount() {
		return board.getTurnCount();
	}

}

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.Board;
import view.Cell;

public class MainController {

	@FXML private GridPane pane;
	@FXML private Button playButton;

	private Board board = new Board(45, 45);

	@FXML
	public void initialize() {
		for (int i = 0; i < 45; i++) {
			for (int j = 0; j < 45; j++) {
				Cell cell = new Cell(i, j);
				board.bindToCell(cell.getStateProperty(), i, j);
				pane.add(cell, i, j);
			}
		}
	}

	@FXML
	public void playOrPause() {
		board.playOrPause();
		if (board.isPlaying())
			playButton.setText("Pause");
		else
			playButton.setText("Play");
	}

}

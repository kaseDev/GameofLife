package view;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.CellModel;
import model.Constants;

import java.io.IOException;

public class CellView extends AnchorPane {

	@FXML private Rectangle rect;

	private CellModel cellModel;
	private GameController controller;
	private int x, y;

	public CellView(int x, int y, GameController controller) {
		uglySetUpCode();
		this.cellModel = new CellModel();
		this.x = x;
		this.y = y;
		this.controller = controller;
		setOnMouseClicked(e -> reactToClick());
		this.cellModel.ageProperty().addListener(
				(e, oldVal, newVal) -> reactToStateChange((int) oldVal, (int) newVal));
	}

	private void reactToClick() {
		if (cellModel.isAlive()) {
			cellModel.setColor(Constants.DEAD);
			cellModel.setAge(Constants.DEAD);
		} else {
			cellModel.setColor(controller.getColor());
			cellModel.setAge(controller.getTurnCount());
		}
	}

	private void reactToStateChange(int oldVal, int newVal) {
		if (cellModel.isAlive()) {
			rect.setFill(Constants.getColor(cellModel.getColor()));
		} else
			rect.setFill(Color.BLACK);
	}

	private void uglySetUpCode() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getClassLoader().getResource("cell.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		rect = (javafx.scene.shape.Rectangle) getChildren().get(0);
	}

	public CellModel getCellModel() {
		return cellModel;
	}
}

package view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.CellModel;

import java.io.IOException;

public class CellView extends AnchorPane {

	@FXML private Rectangle rect;
//	private BooleanProperty state = new SimpleBooleanProperty(false);

	private CellModel cellModel;
	private int x,y;

	public CellView(int i, int j, CellModel cellModel) {
		uglySetUpCode();
		this.cellModel = new CellModel();
		this.cellModel.bind(cellModel);
		setOnMouseClicked(e -> reactToClick());
		this.cellModel.ageProperty().addListener((e, oldVal, newVal) -> reactToStateChange(e, (int) oldVal, (int) newVal));
		x = i;
		y = j;
	}

	private void reactToClick() {

//		state.set(!state.get());

		if (cellModel.isAlive()) {
			cellModel.setAge(-1);
		} else {
			cellModel.setAge(1);
		}
	}

	private void reactToStateChange(ObservableValue e, int oldVal, int newVal) {
		if (cellModel.isAlive())
			rect.setFill(Color.YELLOW);
		else
			rect.setFill(Color.BLACK);
		if ((newVal == -1 && oldVal != -1) || (newVal != -1 && oldVal == -1))
			System.out.println("(" + x + ", " + y + "): " + oldVal + " -> " + newVal);
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

}

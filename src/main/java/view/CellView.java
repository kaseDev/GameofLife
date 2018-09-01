package view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class CellView extends AnchorPane {

	@FXML private Rectangle rect;
	private BooleanProperty state = new SimpleBooleanProperty(false);
	private int x,y;

	public CellView(int i, int j) {
		uglySetUpCode();
		setOnMouseClicked(e -> reactToClick());
		state.addListener(e -> reactToStateChange());
		x = i;
		y = j;
	}

	private void reactToClick() {
		state.set(!state.get());
	}

	private void reactToStateChange() {
		if (state.get())
			rect.setFill(Color.YELLOW);
		else
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

	public BooleanProperty getStateProperty() {
		return state;
	}

}

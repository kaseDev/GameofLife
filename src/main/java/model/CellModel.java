package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CellModel {

	private BooleanProperty state;

	public CellModel() {
		state = new SimpleBooleanProperty(false);
	}

	public CellModel(boolean state) {
		this.state = new SimpleBooleanProperty(state);
	}

	public boolean getState() {
		return state.get();
	}

	public BooleanProperty stateProperty() {
		return state;
	}

	public void setState(boolean state) {
		this.state.set(state);
	}
}

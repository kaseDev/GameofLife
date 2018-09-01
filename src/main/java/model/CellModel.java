package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CellModel {

	private IntegerProperty age;
	private IntegerProperty color;

	public CellModel() {
		age = new SimpleIntegerProperty(-1);
		color = new SimpleIntegerProperty(-1);
	}

	public CellModel(int age, int color) {
		this.age = new SimpleIntegerProperty(age);
		this.color = new SimpleIntegerProperty(color);
	}

	public CellModel(CellModel copy) {
		this.age = new SimpleIntegerProperty(copy.getAge());
		this.color = new SimpleIntegerProperty(copy.getColor());
	}

	public int getAge() {
		return age.get();
	}

	public IntegerProperty ageProperty() {
		return age;
	}

	public void setAge(int age) {
		this.age.set(age);
	}

	public int getColor() {
		return color.get();
	}

	public IntegerProperty colorProperty() {
		return color;
	}

	public void setColor(int color) {
		this.color.set(color);
	}

	public void clone(CellModel model) {
		this.age.set(model.age.get());
		this.color.set(model.color.get());
	}

	public boolean isAlive() {
		return age.get() != -1;
	}

	public void bind(CellModel cellModel) {
		this.age.bindBidirectional(cellModel.age);
		this.color.bindBidirectional(cellModel.color);
	}
}

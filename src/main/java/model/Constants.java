package model;

import javafx.scene.paint.Color;

public class Constants {

	public static final int DEAD = -1;
	public static final int RED = 0;
	public static final int BLUE = 1;
	public static final int GREEN = 2;
	public static final int YELLOW = 3;
	public static final int ORANGE = 4;
	public static final int PURPLE = 5;
	public static final int PINK = 6;
	public static final int CYAN = 7;

	public static Color getColor(int colorCode) {
		Color color = Color.BROWN; // in case something goes horribly wrong
		switch (colorCode) {
			case Constants.RED:
				color = Color.RED;
				break;
			case Constants.BLUE:
				color = Color.BLUE;
				break;
			case Constants.GREEN:
				color = Color.GREEN;
				break;
			case Constants.YELLOW:
				color = Color.YELLOW;
				break;
			case Constants.ORANGE:
				color = Color.ORANGE;
				break;
			case Constants.PURPLE:
				color = Color.PURPLE;
				break;
			case Constants.PINK:
				color = Color.PINK;
				break;
			case Constants.CYAN:
				color = Color.CYAN;
				break;
		}
		return color;
	}
}

package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;

public class TitleScreenController {

	private static final String MAIN_FXML = "game.fxml";
	private static final String SETTINGS_FXML = "settings.fxml";

	private Stage primaryStage;

	@FXML
	public void launchGame() {
		launch(MAIN_FXML);
	}

	public void goToSettings() {

	}

	public void exit() {
		System.exit(0);
	}

	private void launch(String fxml) {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(getClass().getClassLoader().getResource(fxml));
			Parent parent = loader.load();
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);
	}

	public void setPrimaryStage(Stage priaryStage) {
		this.primaryStage = priaryStage;
	}

}

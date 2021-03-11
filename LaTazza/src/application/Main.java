package application;
	
import java.io.IOException;

import application.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	Stage primaryStage;
	Scene scene, scene2;
	Controller contr;
	
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setResizable(true);
		this.primaryStage.setMinHeight(450);
		this.primaryStage.setMinWidth(600);
		primaryStage.getIcons().add(new Image("file:res/icon.png"));
		FXMLLoader loader = new FXMLLoader(
					getClass().getResource("view/MainView.fxml"));
		try {
				scene = new Scene(loader.load(),800,600);
		} catch (IOException e) {
				e.printStackTrace();		
		}
		contr=loader.getController();
		contr.init();
		primaryStage.setScene(scene);
		primaryStage.setTitle("LaTazza");
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void stop() throws Exception {
		contr.saveAllInFile();
	}
}

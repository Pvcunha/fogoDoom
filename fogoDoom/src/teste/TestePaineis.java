package teste;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestePaineis extends Application implements EventHandler<ActionEvent>{
	
	private Button botao;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane root = new StackPane();
		Pane painelfogo = new Pane();
		botao = new Button("teste");
		botao.setMaxSize(50,20);
		painelfogo.getChildren().add(botao);
		painelfogo.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
		painelfogo.setMaxSize(200,200);
		
		botao.setTranslateX(80);
		botao.setTranslateY(90);
		botao.setOnAction(this);
		
		
		
		root.getChildren().add(painelfogo);
		StackPane.setAlignment(painelfogo, Pos.CENTER);
		
		
		Scene cena = new Scene(root, 400, 300);
		primaryStage.setScene(cena);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == botao) {
			System.out.println("puta merda!");
		}
		
	}

	
}

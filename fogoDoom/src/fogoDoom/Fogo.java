package fogoDoom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import static fogoDoom.Celula.PIXEL_SIZE;

import java.util.Random;

public class Fogo extends Application implements EventHandler<ActionEvent> {

	private static final int LARGURA_TELA = 400;
	private static final int ALTURA_TELA = 450;

	private static final int LARGURA_FOGO = 80;
	private static final int ALTURA_FOGO = 50;

	private Celula[] arrayCelulasFogo;

	private Scene cena;

	private StackPane root;

	private Pane painelFogo;

	private Timeline timeline = new Timeline();

	private Button botao;

	private KeyFrame frame;

	private static boolean FLAG = true;



	private void criaEstruturaDado() {
		int numeroPixels = LARGURA_FOGO * ALTURA_FOGO;

		arrayCelulasFogo = new Celula[numeroPixels];

		for (int i = 0; i < numeroPixels; i++) {
			arrayCelulasFogo[i] = new Celula(0);
		}
	}

	public void criaFonte() {

		int pixelBase = ALTURA_FOGO * LARGURA_FOGO;
		for (int coluna = 0; coluna < LARGURA_FOGO; coluna++) { //
			int pixelFonte = (pixelBase - LARGURA_FOGO) + coluna;
			arrayCelulasFogo[pixelFonte].setIntensidadeFogo(36);
			arrayCelulasFogo[pixelFonte].determinaCorCelula();
		}
	}

	private void defineFogo() {

		for (int i = 0; i < arrayCelulasFogo.length; i++) {
			Celula cel = arrayCelulasFogo[i];
			System.out.println(i + ": " + arrayCelulasFogo[i].getIntensidadeFogo());
			painelFogo.getChildren().add(cel);
		}
		renderizaFogo();
	}

	private void renderizaFogo() {
		for (int i = 0; i < arrayCelulasFogo.length; i++) {
			Celula cel = arrayCelulasFogo[i];
			System.out.println(i + ": " + arrayCelulasFogo[i].getIntensidadeFogo());
			cel.setTranslateX(PIXEL_SIZE * (i % LARGURA_FOGO));
			cel.setTranslateY(PIXEL_SIZE * (i / LARGURA_FOGO));

		}
	}

	private void calculaPropagacao() {
		for (int linha = 0; linha < ALTURA_FOGO; linha++) {
			for (int coluna = 0; coluna < LARGURA_FOGO; coluna++) {
				int indexPixel = coluna + (LARGURA_FOGO * linha);
				atualizaIntensidade(indexPixel);
			}
		}

		renderizaFogo();
	}

	private void atualizaIntensidade(int indexAtual) {
		Random rand = new Random();
		int numeroPixels = LARGURA_FOGO * ALTURA_FOGO;
		int pixelAbaixo = indexAtual + LARGURA_FOGO;

		if (pixelAbaixo >= numeroPixels)
			return;

		int decaimento = rand.nextInt(3);
		int intensidadePixelAbaixo = arrayCelulasFogo[pixelAbaixo].getIntensidadeFogo();
		int novaIntensidade = intensidadePixelAbaixo - decaimento >= 0 ? intensidadePixelAbaixo - decaimento : 0;
		int pixelComVento = indexAtual - decaimento >= 0 ? indexAtual - decaimento : 0;
		arrayCelulasFogo[pixelComVento].setIntensidadeFogo(novaIntensidade);
		arrayCelulasFogo[pixelComVento].determinaCorCelula();
	}

	public void propriedadesPainelFogo() {
		painelFogo = new Pane();
		painelFogo.setMaxSize(LARGURA_FOGO * PIXEL_SIZE, ALTURA_FOGO * PIXEL_SIZE);
		painelFogo.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		painelFogo.setTranslateY(-40);

	}
	
	public void propriedadesPlanoFundo(){
		
		root = new StackPane();
		root.setPrefSize(LARGURA_TELA, ALTURA_TELA);
		root.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		botao = new Button("Liga/Desliga");
		botao.setTranslateX(painelFogo.getWidth() - painelFogo.getWidth());
		botao.setTranslateY((painelFogo.getHeight() * PIXEL_SIZE) + 85);
		botao.setOnAction(this);
	}
	
	public void updateFrame() {
		frame = new KeyFrame(Duration.millis(50), e -> calculaPropagacao());

		timeline.getKeyFrames().add(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);

		timeline.play();
	}
	
	private void createContent() {

		propriedadesPainelFogo();
		propriedadesPlanoFundo();
		
		Canvas canvas = new Canvas(500, 300);
	         
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	         
	    gc.setFill( Color.RED );
	    gc.setStroke( Color.BLACK );
	    gc.setLineWidth(2);
	    Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 28 );
	    gc.setFont( theFont );
	    gc.fillText( "TA PEGANDO FOGO BICHO!", 60, 50 );
	    gc.strokeText( "TA PEGANDO FOGO BICHO!", 60, 50 );
		
		StackPane.setAlignment(canvas, Pos.TOP_CENTER);
		StackPane.setAlignment(painelFogo, Pos.CENTER);
		root.getChildren().addAll(painelFogo, botao, canvas);

		criaEstruturaDado();

		criaFonte();
		defineFogo();
		updateFrame();
	}

	public void apagaFogo() {
		Fogo.FLAG = false;
		for (int linha = 0; linha < ALTURA_FOGO; linha++) {
			for (int coluna = 0; coluna < LARGURA_FOGO; coluna++) {
				int indexPixel = coluna + (LARGURA_FOGO * linha);
				arrayCelulasFogo[indexPixel].setIntensidadeFogo(0);
			}
		}
		renderizaFogo();

	}

	public void ligaFogo() {
		Fogo.FLAG = true;
		criaFonte();
		calculaPropagacao();
		timeline.play();

	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == botao) {
			if (Fogo.FLAG) {
				apagaFogo();
			} else {
				ligaFogo();
			}
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		createContent();
		cena = new Scene(this.root);
		
		stage.setTitle("FOGO DO DOOM");
		stage.setScene(cena);
		stage.show();
	}
	
}

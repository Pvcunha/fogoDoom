package fogoDoom;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static fogoDoom.PaletaCores.paletaCores;

public class Celula extends Pane {
	
	private int intensidadeFogo;
	private Rectangle formato;
	public static final int PIXEL_SIZE = 4;
	
	public Celula(int intensidadeFogo) {
		this.intensidadeFogo = intensidadeFogo;
		formato = new Rectangle(PIXEL_SIZE, PIXEL_SIZE);
		formato.setFill(null);
		getChildren().addAll(formato);
	}
	
	public int getIntensidadeFogo() {
		return intensidadeFogo;
	}

	public void setIntensidadeFogo(int intensidadeFogo) {
		this.intensidadeFogo = intensidadeFogo;
	}
	
	public void determinaCorCelula() {
		formato.setFill(paletaCores[this.intensidadeFogo]);
	}
}

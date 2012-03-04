package img;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;

import javax.imageio.ImageIO;

public class Imagem {
	
	public static Raster carregaImagem(String filePath) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filePath));
		} catch (Exception e) {
		}

		return img.getData();
	}
}

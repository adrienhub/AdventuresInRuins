package com.lerthal.graficos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.lerthal.tools.Tool;

public class Spritesheet {

	private BufferedImage spritesheet;
	
	public Spritesheet(String path){
		try {
			//prblema procurando arquivos, provavelmente o arquivo de conf do ECLIPSE seta o folder como, ao exportar no cliente isso não funciona
			//tambem não sei como isso se comporta ao empacotar tudo com 
			//pegando arquivo absoluto
			File file = Tool.loadFilefromName(path);
			spritesheet = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x , int y , int width , int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
}

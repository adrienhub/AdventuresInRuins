package com.lerthal.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lerthal.main.Game;

public class Tile {
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(16, 32, 16, 16); //Chao limpo
	public static BufferedImage TILE_FLOOR2 = Game.spritesheet.getSprite(16, 0, 16, 16); //Rachadura 1
	public static BufferedImage TILE_FLOOR3 = Game.spritesheet.getSprite(16, 16, 16, 16); //Rachadura 2
	public static BufferedImage TILE_FLOOR4 = Game.spritesheet.getSprite(128, 64, 16, 16); //cHAO COM FLOR
	public static BufferedImage TILE_FLOOR5 = Game.spritesheet.getSprite(112 , 64 , 16 ,16); //Chao com coisinha
	
	public static BufferedImage TILE_WALL1 = Game.spritesheet.getSprite(0, 0, 16, 16); //Meio
	public static BufferedImage TILE_WALL2 = Game.spritesheet.getSprite(0, 16, 16, 16); //Baixo
	public static BufferedImage TILE_WALL3 = Game.spritesheet.getSprite(0, 32, 16, 16); //Esquerda
	public static BufferedImage TILE_WALL4 = Game.spritesheet.getSprite(0, 48, 16, 16); //Direita
	public static BufferedImage TILE_WALL5 = Game.spritesheet.getSprite(0, 64, 16, 16); //Cima
	
	
	 private BufferedImage sprite;
		private int x,y;
	
	public Tile(int x , int y ,  BufferedImage sprite){
			this.x = x;
			this.y = y;
			this.sprite = sprite;
	 }
	
	public void render(Graphics g) {
		g.drawImage(sprite , x - Camera.x , y - Camera.y,null);
	}
}

package com.lerthal.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.lerthal.entities.Ammo;
import com.lerthal.entities.Enemy;
import com.lerthal.entities.EnergyPotion;
import com.lerthal.entities.Entity;
import com.lerthal.entities.LifePotion;
import com.lerthal.entities.Particle;
import com.lerthal.entities.Player;
import com.lerthal.entities.Sign;
import com.lerthal.entities.Weapon;
import com.lerthal.graficos.Spritesheet;
import com.lerthal.main.Game;

public class World {

	private BufferedImage map;
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

	public World(String path) {
		try {
			map = ImageIO.read(getClass().getResource(path));

			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for (int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {

					tiles[xx + (yy * map.getWidth())] = new floorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);

					int pixelAtual = pixels[xx + (yy * map.getWidth())];

					if (pixelAtual == 0xFF000000) {
						// floor liso
						tiles[xx + (yy * map.getWidth())] = new floorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					} else if (pixelAtual == 0xFF7a7a7a) {
						// floor rachadura 1
						tiles[xx + (yy * map.getWidth())] = new floorTile(xx * 16, yy * 16, Tile.TILE_FLOOR2);
					} else if (pixelAtual == 0xFF585858) {
						// floor rachadura 2
						tiles[xx + (yy * map.getWidth())] = new floorTile(xx * 16, yy * 16, Tile.TILE_FLOOR3);
					} else if (pixelAtual == 0xFF195858) {
						// floor com matinho
						tiles[xx + (yy * map.getWidth())] = new floorTile(xx * 16, yy * 16, Tile.TILE_FLOOR5);
					} else if (pixelAtual == 0xFFc9e9d8) {
						// floor com flor
						tiles[xx + (yy * map.getWidth())] = new floorTile(xx * 16, yy * 16, Tile.TILE_FLOOR4);
					} else if (pixelAtual == 0xFFffffff) {
						// wall centro
						tiles[xx + (yy * map.getWidth())] = new wallTile(xx * 16, yy * 16, Tile.TILE_WALL1);
					} else if (pixelAtual == 0xFFcfb4b4) {
						// wall direita
						tiles[xx + (yy * map.getWidth())] = new wallTile(xx * 16, yy * 16, Tile.TILE_WALL4);
					} else if (pixelAtual == 0xFFb4b8cf) {
						// wall esquerda
						tiles[xx + (yy * map.getWidth())] = new wallTile(xx * 16, yy * 16, Tile.TILE_WALL3);
					} else if (pixelAtual == 0xFFb4cfb7) {
						// wall cima
						tiles[xx + (yy * map.getWidth())] = new wallTile(xx * 16, yy * 16, Tile.TILE_WALL5);
					} else if (pixelAtual == 0xFFcdcfb4) {
						// wall baixo
						tiles[xx + (yy * map.getWidth())] = new wallTile(xx * 16, yy * 16, Tile.TILE_WALL2);
					} else if (pixelAtual == 0xFF6238e4) {
						// Placa
						Sign sign = new Sign(xx * 16, yy * 16, 16, 16, Entity.placa);
						Game.entities.add(sign);
					} else if (pixelAtual == 0xFFdf7126) {
						// player
						Game.player.setMask(2, 2, 11, 14);
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);

					} else if (pixelAtual == 0xFFac3232) {
						// enemy
						Enemy en = new Enemy(xx * 16, yy * 16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);

					} else if (pixelAtual == 0xFF639bff) {
						// Weapon
						Game.entities.add(new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPON_EN));
					} else if (pixelAtual == 0xFF99e550) {
						// LifePotion
						LifePotion lifePotion = new LifePotion(xx * 16, yy * 16, 16, 16, Entity.LIFEPOTION_EN);
						lifePotion.setMask(0, 0, 12, 15);
						Game.entities.add(lifePotion);
					} else if (pixelAtual == 0xFFdf7126) {
						// WEAPON
						Game.entities.add(new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPON_EN));
					} else if (pixelAtual == 0xFF76428a) {
						// EnergyPotion
						EnergyPotion energyPotion = new EnergyPotion(xx * 16, yy * 16, 16, 16, Entity.ENERGYPOTION_EN);
						energyPotion.setMask(0, 0, 12, 15);
						Game.entities.add(energyPotion);
					} else if (pixelAtual == 0xFFfbf236) {
						// Ammo
						Game.entities.add(new Ammo(xx * 16, yy * 16, 16, 16, Entity.AMMO_EN));
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE; // Pegando a proxima posição e convertendo pro formato de Tile;
		int y1 = yNext / TILE_SIZE;

		int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;

		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof wallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof wallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof wallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof wallTile));

	}
	
	public static void generateParticles(int amount , int x , int y){
		for(int i = 0; i < amount; i++){
			Game.entities.add(new Particle(x,y,1,1,null));
		}
	}
	
	
	public static boolean isFreeDynamic(int xNext, int yNext , int width , int height) {
		int x1 = xNext / TILE_SIZE; // Pegando a proxima posição e convertendo pro formato de Tile;
		int y1 = yNext / TILE_SIZE;

		int x2 = (xNext + width - 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;

		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + height - 1) / TILE_SIZE;

		int x4 = (xNext + width - 1) /  TILE_SIZE;
		int y4 = (yNext + height - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof wallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof wallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof wallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof wallTile));

	}

	public static void restartGame(String lvl) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(16, 16, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/" + lvl);
		Game.player.ammo = 0;
		Game.player.wisdomPoints = 0;
		Entity.life_bar3 = Game.spritesheet.getSprite(32, 112, 16, 16);
		Entity.life_bar4 = Game.spritesheet.getSprite(48, 112, 16, 16);
		Entity.speedIcon = Game.spritesheet.getSprite(64, 80, 16, 16);
		Entity.ammoIcon = Game.spritesheet.getSprite(80, 80, 16, 16);
		return;
	}

	public void render(Graphics g) {
		int xStart = Camera.x >> 4;
		int yStart = Camera.y >> 4;

		int xFinal = xStart + (Game.WIDTH >> 4);
		int yFinal = yStart + (Game.HEIGHT >> 4);

		for (int xx = xStart; xx <= xFinal; xx++) {
			for (int yy = yStart; yy <= yFinal; yy++) {
				if ((xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)) {
					continue;
				}

				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}

	}

}

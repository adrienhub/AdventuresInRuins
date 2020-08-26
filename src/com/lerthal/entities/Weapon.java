package com.lerthal.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lerthal.main.Game;
import com.lerthal.world.Camera;

public class Weapon extends Entity {

	private BufferedImage[] sprites;
	private int frames = 0, maxFrames = 23, index = 0, maxIndex = 1;

	public Weapon(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(16, 64, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(32, 64, 16, 16);
	}

	public void tick() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;

		}
		if (index > maxIndex) {
			index = 0;
		}
	}

	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}

package com.lerthal.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lerthal.main.Game;
import com.lerthal.world.Camera;

public class Ammo extends Entity {

	private BufferedImage[] sprites;
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 5;

	public Ammo(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		sprites = new BufferedImage[6];
		sprites[0] = Game.spritesheet.getSprite(0, 96, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(16, 96, 16, 16);
		sprites[2] = Game.spritesheet.getSprite(32, 96, 16, 16);
		sprites[3] = Game.spritesheet.getSprite(48, 96, 16, 16);
		sprites[4] = Game.spritesheet.getSprite(64, 96, 16, 16);
		sprites[5] = Game.spritesheet.getSprite(80, 96, 16, 16);
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

package com.lerthal.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lerthal.main.Game;
import com.lerthal.world.Camera;
import com.lerthal.world.World;

public class Shoot extends Entity {

	private double dx;
	private double dy;
	private int spd = 4;
	private int life = 35, curLife = 0;

	public Shoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);

		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		if (World.isFreeDynamic((int) (x + (dx * spd)), (int) (y + (dy * spd)), 3, 3)) {
			x += dx * spd;
			y += dy * spd;
		} else {
			Game.bullets.remove(this);
			World.generateParticles(5, (int) x ,(int) y);
			return;
		}
		curLife++;
		if (curLife == life) {
			Game.bullets.remove(this);
			return;
		}

	}

	public void render(Graphics g) {
		g.setColor(new Color(47, 86, 226));
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);

		if (Player.wisdomPoints >= 5) {
			g.setColor(new Color(255, 216, 0));
			g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
		}
		if (Player.wisdomPoints >= 10) {
			g.setColor(new Color(0, 255, 136));
			g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
			g.fillOval(this.getX() + 6 - Camera.x, this.getY() + 6 - Camera.y, width, height);
		}
	}
}

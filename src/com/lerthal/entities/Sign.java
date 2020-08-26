package com.lerthal.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Collections;

import com.lerthal.main.Game;

public class Sign extends Entity {

	public String[] frases = new String[3];
	public static boolean showMessage = false;
	public boolean show = false;
	public int curIndexMsg = 0;
	public int fraseIndex = 0;
	public int time = 0;
	public int maxTime = 3;
	private int separador = 15;
	private int inicio = 25 * Game.SCALE;

	public Sign(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		frases[0] = "Olá viajante,";
		frases[1] = "obrigada por jogar até aqui!";
		frases[2] = "Derrote todos os inimigos para vencer :)";

	}

	public void tick() {
		depth = 2;
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();

		int xSign = (int) x;
		int ySign = (int) y;

		if (Math.abs(xPlayer - xSign) < 15 && Math.abs(yPlayer - ySign) < 15) {
			if (show == false) {
				showMessage = true;

			}
		}

		else {
			showMessage = false;
		}

		this.time++;
		if (showMessage) {
			if (time >= maxTime) {
				time = 0;
				if (curIndexMsg < frases[fraseIndex].length()) {
					curIndexMsg++;
				} else {
					if (fraseIndex < frases.length - 1) {
						fraseIndex++;
						curIndexMsg = 0;
					}
				}

			}
		}

	}

	public void render(Graphics g) {

		super.render(g);
		if (showMessage) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

			g.setColor(new Color(195, 80, 60));
			g.fillRect(25, 25, 200, 120);

			g.setColor(new Color(238, 138, 69));
			g.fillRect(30, 30, 190, 110 );

			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 9));

			for (int i = 0; i < fraseIndex; i++) {
				g.drawString(frases[i],  33, 60  + separador * i);
			}

			g.drawString(frases[fraseIndex].substring(0, curIndexMsg), 33 , 60 + separador * fraseIndex);

			// g.drawString(frases[1], 35, 65);
			// g.drawString(frases[2], 32, 80);
			// g.drawString(frases[3], 33, 115);

		}
	}

}

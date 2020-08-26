package com.lerthal.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.lerthal.entities.Entity;
import com.lerthal.entities.Player;
import com.lerthal.main.Game;

public class UI {

	private int saveIconFrames = 0 , maxSaveFrames = 150;
	
	
	/*
	 	public void showSavingIcon(Graphics g){
		{
            saveIconFrames++;
            
            if(saveIconFrames >= maxSaveFrames)
            {
                saveIconFrames = 0;
               Game.showSaveIcon = false;
            }
            
            g.drawImage(Entity.savingIcon, Game.WIDTH - 20, Game.HEIGHT - 20, null);
        }
	}
	
	*/
	
	public void render(Graphics g){
		g.setColor(Color.red);
		g.fillRect(14, 7, 35, 4);
		g.setColor(Color.green);
		g.fillRect(14, 7, (int)((Game.player.life / Game.player.maxLife)*35), 4);
		g.setColor(Color.white);
		//g.drawString((int)Player.life+"/"+(int)Player.maxLife,10,10);
		
		g.drawImage(Entity.life_bar2, 13, 1, null);
		g.drawImage(Entity.life_bar3, 29, 1, null);
		g.drawImage(Entity.life_bar4, 40, 1, null);
		g.drawImage(Entity.life_bar, 0, 0, null);
		g.drawImage(Entity.ammoIcon, 203, 2, null);
		g.setFont((new Font("arial", Font.BOLD, 10)));
		g.drawString("" + Game.player.ammo, 220, 14);
		// g.drawString(""+player.speed, 10, 10);
		
		/*if(Game.showSaveIcon)
        {
            showSavingIcon(g);
        }
        */
	}
	
}

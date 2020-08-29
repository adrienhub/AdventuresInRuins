package com.lerthal.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lerthal.main.Game;
import com.lerthal.main.Sound;
import com.lerthal.world.Camera;
import com.lerthal.world.World;

public class Player extends Entity {

	public boolean right, left, up, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 1.8;
	public int ammo = 0;
	public static int wisdomPoints = 0;
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 7;
	private boolean moved = false;
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	private int levelLimit = 5;	
	private int level = 1;

	private BufferedImage[] rightPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;

	private BufferedImage playerDamageLeft, playerDamageRight, playerDamageUp, playerDamageDown;

	public double life = 100, maxLife = 100;
	public int mx, my;

	public boolean isDamaged = false;
	private int damageFrames = 0;
	private boolean Gun = false;
	public boolean isShooting = false, mouseShoot = false;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[8];
		leftPlayer = new BufferedImage[8];
		upPlayer = new BufferedImage[8];
		downPlayer = new BufferedImage[8];

		playerDamageRight = Game.spritesheet.getSprite(16, 80, 16, 16);
		playerDamageLeft = Game.spritesheet.getSprite(0, 80, 16, 16);
		playerDamageUp = Game.spritesheet.getSprite(32, 80, 16, 16);
		playerDamageDown = Game.spritesheet.getSprite(48, 80, 16, 16);

		for (int i = 0; i < 8; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
		}
		for (int i = 0; i < 8; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
		}
		for (int i = 0; i < 8; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 32, 16, 16);
		}
		for (int i = 0; i < 8; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 48, 16, 16);
		}

	}

	public void tick() {
		depth = 1;
		moved = false;
		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;

		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;

		}
		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			dir = up_dir;
			y -= speed;

		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			moved = true;
			dir = down_dir;
			y += speed;
		}

		if (moved) {
			frames++;

		}
		if (frames == maxFrames) {
			frames = 0;
			index++;

		}
		if (index > maxIndex) {
			index = 0;
		}

		checkCollisionWithEnergy();
		checkCollisionWithLife();
		checkCollisionWithAmmo();
		checkCollisionWithGun();

		if (isDamaged) {
			this.damageFrames++;
			if (this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}

		if (isShooting) {
			isShooting = false;
			if (Gun && ammo > 0) {

				ammo--;
				int dx = 0;
				int dy = 0;
				int px = 0;
				int py = 0;

				if (dir == right_dir) {
					px = 10;
					py = 4;
					dx = 1;

				} else if (dir == left_dir) {
					px = 2;
					py = 3;
					dx = -1;

				} else if (dir == up_dir) {
					dy = -1;
					px = -1;
					py = 3;
				} else {
					dy = 1;
					px = 14;
					py = 2;
				}

				Shoot shoot = new Shoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
				Game.bullets.add(shoot);
			}
		}

		if (mouseShoot) {
			mouseShoot = false;
			if (Gun && ammo > 0) {
				Sound.shoot.play();
				ammo--;

				int px = 0;
				int py = 0;
				double angle = 0;

				if (dir == right_dir) {
					px = 10;
					py = 2;
					angle = (Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x)));

				} else if (dir == left_dir) {
					px = 2;
					py = 3;
					angle = (Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x)));
				} else if (dir == up_dir) {

					px = -1;
					py = 3;
					angle = (Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x)));

				} else {
					px = 14;
					py = 2;
					angle = (Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x)));

				}

				double dx = Math.cos(angle);
				double dy = Math.sin(angle);

				Shoot shoot = new Shoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
				Game.bullets.add(shoot);

			}

		}

		// Game over
		if (life <= 0) {
			Game.gameState = "Game_Over";
		}

		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);

		if (wisdomPoints >= levelLimit) {
			Sound.powerUp.play();
			level = level + 1;			
			levelLimit += 5;
		}

	}

	public void checkCollisionWithLife() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof LifePotion) {
				if (Entity.isColliding(this, atual)) {
					life += 10;
					if (life >= 100) {
						life = 100;
						continue;
					}
					Game.entities.remove(atual);
				}
			}
		}

	}

	public void checkCollisionWithEnergy() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof EnergyPotion) {
				if (Entity.isColliding(this, atual)) {
					speed += 0.5;
					if (speed > 2.3) {
						speed = 2.3;
						Game.player.setMask(2, 2, 11, 14);
						continue;
					}
					Game.entities.remove(atual);

				}
			}
		}
	}

	public void checkCollisionWithAmmo() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Ammo) {
				if (Entity.isColliding(this, atual)) {
					ammo += 100;
					Game.entities.remove(atual);
				}
			}
		}

	}

	public void checkCollisionWithGun() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Weapon) {
				if (Entity.isColliding(this, atual)) {
					Gun = true;
					Game.entities.remove(atual);
					// System.out.println("Peguei a arma");
				}
			}
		}

	}

	public void render(Graphics g) {
		if (!isDamaged) {
			if (dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (Gun) {
					// drawImage RIGHT
					g.drawImage(Entity.gunRight, this.getX() + 2 - Camera.x, this.getY() - Camera.y, null);
				}
				if (Gun && wisdomPoints >= 5) {
					// drawImage RightYellow
					g.drawImage(Entity.gunRightYellow, this.getX() + 2 - Camera.x, this.getY() - Camera.y, null);
				}
				if (Gun && wisdomPoints >= 10) {
					g.drawImage(Entity.gunRightGreen, this.getX() + 2 - Camera.x, this.getY() - Camera.y, null);
				}

			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (Gun) {
					// DrawImage LEFT
					g.drawImage(Entity.gunLeft, this.getX() - 2 - Camera.x, this.getY() - Camera.y, null);
				}
				if (Gun && wisdomPoints >= 5) {
					// drawImage leftYellow
					g.drawImage(Entity.gunLeftYellow, this.getX() - 2 - Camera.x, this.getY() - Camera.y, null);
				}
				if (Gun && wisdomPoints >= 10) {
					g.drawImage(Entity.gunLeftGreen, this.getX() - 2 - Camera.x, this.getY() - Camera.y, null);
				}

			} else if (dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (Gun) {
					g.drawImage(Entity.gunUp, this.getX() - 2 - Camera.x, this.getY() + 1 - Camera.y, null);
				}
				if (Gun && wisdomPoints >= 5) {
					// drawImage upYellow
					g.drawImage(Entity.gunUpYellow, this.getX() - 2 - Camera.x, this.getY() + 1 - Camera.y, null);
				}
				if (Gun && wisdomPoints >= 10) {
					g.drawImage(Entity.gunUpGreen, this.getX() - 2 - Camera.x, this.getY() + 1 - Camera.y, null);
				}

			} else if (dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (Gun) {
					g.drawImage(gunDown, this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
				if (Gun && wisdomPoints >= 5) {
					// drawImage downYellow
					g.drawImage(Entity.gunDownYellow, this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
				if (Gun && wisdomPoints >= 10) {
					g.drawImage(Entity.gunDownGreen, this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
			}
			// Feedback do ataque
		} else if (isDamaged = true && dir == right_dir) {
			g.drawImage(playerDamageRight, this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (isDamaged = true && dir == left_dir) {
			g.drawImage(playerDamageLeft, this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (isDamaged = true && dir == up_dir) {
			g.drawImage(playerDamageUp, this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (isDamaged = true && dir == down_dir) {
			g.drawImage(playerDamageDown, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

		if (speed >= 1.9) {
			g.drawImage(Entity.speedIcon, 1, 13, null);
		}

		if (wisdomPoints >= 5) {
			g.drawImage(Entity.wisdomIcon, 17, 13, null);
		}

		//g.drawString("" + wisdomPoints, 20, 20);

		/* MASK */
		// g.setColor(Color.red);
		// g.fillRect(this.getX()+ maskX- Camera.x, this.getY()+ maskY - Camera.y
		// ,mwidth , mheight);
	}

}

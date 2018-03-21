package com.nincha.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.nincha.main.Game;

/**
 * EntityProjectileIA est le projectile lancé par l'IA
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class EntityProjectileIA extends Entity{

	public EntityProjectileIA(float x, float y, double atk, EntityType entityType, float velox, float veloy) {
		super(x, y, atk, entityType);
		this.setVeloX(velox);
		this.setVeloY(veloy);
	}

	@Override
	public void tick(LinkedList<Entity> entity) {
		if (this.getX() <= 75 || this.getX() >= 938 || this.getY() <= 95 ||this.getY() >= 675) {
			this.die();
		}
		this.setX(this.getX()+this.getVeloX());
		this.setY(this.getY()+this.getVeloY());
		this.collisionBounds();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.drawOval((int)this.getX(), (int)this.getY(), 12, 12);
		g.fillOval((int)this.getX(), (int)this.getY(), 12, 12);
	}

	
	private void collisionBounds(){
		
		EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();
		if (this.getBounds().intersects(nincha.getBounds())){
			nincha.setPv(nincha.getPv() - this.getAtk());
			this.die();
			return;
		}
		for (int i = 728; i < Game.getHandlerEntity().getTextureInPremiereCoucheList().size(); i++) {
			Entity tempObject = Game.getHandlerEntity().getTextureInPremiereCoucheList().get(i);
			if (tempObject.getType() == EntityType.EntityObstacle) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					this.die();
				}
			}
		}
	}
	
	
	@Override
	public void die() {
		Game.getHandlerEntity().removeProjectile(this);		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)this.getX(), (int)this.getY(), 12, 12);
	}

	@Override
	public Rectangle getBoundsBottom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getBoundsFloor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int pourcentageHP() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPostion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPosition(int col, int line) {
		// TODO Auto-generated method stub
		
	}

}

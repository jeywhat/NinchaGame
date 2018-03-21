package com.nincha.entity;

import java.awt.Graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.nincha.main.Game;
import com.nincha.utils.SpriteEntity;

/**
 * EntityDoor définit les portes pour passer dans différents salles/étages 
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class EntityDoor extends Entity{

	private SpriteEntity se;
	private EntityAnimation animation;
	private Boolean check;
	private int i = 0;
	private Boolean open = false;
	
	public EntityDoor(float x, float y, EntityType entityType, boolean lorr) {
		super(x,y,entityType);
		se = new SpriteEntity(Game.getHandlerSprite().getTerrainSprite(18));
		if (lorr == true) {
			this.animation = new EntityAnimation(10, getSprite(1, 1), getSprite(1, 2), getSprite(1, 3),getSprite(1, 4));
			this.check = false;
		}
		else {
			this.animation = new EntityAnimation(10, getSprite(1, 4),getSprite(1, 3), getSprite(1, 2), getSprite(1, 1));
			this.check = true;
		}
	}

	public void tick(LinkedList<Entity> entity) {
		if (i < 51){
			if (check == true){
				this.animation.runAnimation();
				i++;
			}
			else if (check == false){
					if (i < 11){
						this.animation.runAnimation();
						i++;
					}
					else if (Game.getHandlerEntity().getEntityList().isEmpty()){
						this.animation.runAnimation();
						open = true;
						i++;
					}
				}
		}
		else if (check == true)
				this.check = false;
	}

	public BufferedImage getSprite(int col, int line){
		return se.takeSprite(col, line, Game.getHandlerSprite().getTerrainSprite(18).getWidth(),Game.getHandlerSprite().getTerrainSprite(18).getHeight()/4);
	}
	
	public void render(Graphics g) {
		this.animation.drawAnimation(g, (int) coord.getX(), (int) coord.getY());
	}
	
	public boolean getOpen(){
		return this.open;
	}
	
	public void die() {}

	public Rectangle getBounds() {
		return new Rectangle((int)((int)coord.getX()), (int) ((int)coord.getY()),Game.getHandlerSprite().getTerrainSprite(18).getWidth(),(Game.getHandlerSprite().getTerrainSprite(18).getHeight()/4)+6);
	}

	public Rectangle getBoundsBottom() {return null;}
	public Rectangle getBoundsFloor() {return null;}
	public int pourcentageHP() {return 0;}
	public int getPostion() {return 0;}
	public void setPosition(int col, int line) {}

}

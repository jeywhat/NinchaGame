package com.nincha.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.nincha.main.Game;

/**
 * EntityObjectTerrain convertit chaque texture du terrain en un objet avec un affichage (render) / des coordonnées dans le jeu.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class EntityObjectTerrain extends Entity{
	
	private BufferedImage texture = null;
	
	public EntityObjectTerrain(float x, float y, EntityType id) {
		super(x, y, id);
		this.setTexture();
	}

	public void tick(LinkedList<Entity> entity) {
			
	}

	public void render(Graphics g) {
		if(this.getType() == EntityType.EntityFloor)
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		if(this.getType() == EntityType.EntityWall)
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		if(this.getType() == EntityType.EntityWallTop)
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		if(this.getType() == EntityType.EntityWallRight)
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		if(this.getType() == EntityType.EntityWallBottom)
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		if(this.getType() == EntityType.EntityWallLeft)
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		if(this.getType() == EntityType.EntityCornerTopG)
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		if(this.getType() == EntityType.EntityCornerTopD)
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		if(this.getType() == EntityType.EntityCornerBotD)
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		if(this.getType() == EntityType.EntityCornerBotG)
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		if(this.getType() == EntityType.EntityObstacle){
			g.drawImage(texture, (int)coord.getX(), (int)coord.getY(), null);
		}
		if(this.getType()== EntityType.EntityDoor)
			this.render(g);
	}

	private void setTexture(){
		if(this.getType() == EntityType.EntityFloor)
			texture = Game.getHandlerSprite().getTerrainSprite(0);
		if(this.getType() == EntityType.EntityWall)
			texture = Game.getHandlerSprite().getTerrainSprite(1);
		if(this.getType() == EntityType.EntityWallTop)
			texture = Game.getHandlerSprite().getTerrainSprite(2);
		if(this.getType() == EntityType.EntityWallRight)
			texture = Game.getHandlerSprite().getTerrainSprite(3);
		if(this.getType() == EntityType.EntityWallBottom)
			texture = Game.getHandlerSprite().getTerrainSprite(4);
		if(this.getType() == EntityType.EntityWallLeft)
			texture = Game.getHandlerSprite().getTerrainSprite(5);
		if(this.getType() == EntityType.EntityCornerTopG)
			texture = Game.getHandlerSprite().getTerrainSprite(6);
		if(this.getType() == EntityType.EntityCornerTopD)
			texture = Game.getHandlerSprite().getTerrainSprite(7);
		if(this.getType() == EntityType.EntityCornerBotD)
			texture = Game.getHandlerSprite().getTerrainSprite(8);
		if(this.getType() == EntityType.EntityCornerBotG)
			texture = Game.getHandlerSprite().getTerrainSprite(9);
		if(this.getType() == EntityType.EntityObstacle){
			int randGlobal = (int) (1+Math.random()*100);
			if(randGlobal < 85){
				int rand = (int) (Math.random()*5);
				texture = Game.getHandlerSprite().getTerrainSprite(10+rand);
			}else{
				int rand = (int) (Math.random()*3);
				texture = Game.getHandlerSprite().getTerrainSprite(15+rand);
			}
			
		}
	}

	public Rectangle getBounds() {
		 return new Rectangle((int)((int)coord.getX()), (int) ((int)coord.getY()), texture.getWidth(), texture.getHeight());
	}

	public Rectangle getBoundsBottom() {
		return new Rectangle((int)((int)coord.getX()), (int)coord.getY()+(int)texture.getWidth()/2, texture.getWidth(), (int)texture.getHeight()/2);
	}
	
	
	public int getPostion() {return 0;}
	public void setPosition(int col, int line) {}
	public Rectangle getBoundsFloor() {return null;}
	public int pourcentageHP() {return 0;}
	public void die() {}

}


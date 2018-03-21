package com.nincha.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.nincha.main.Game;

/**
 * EntityLoot permet de transformer un Item, en un objet ramassable sur la carte
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class EntityLoot extends Entity{
	
	/**
	 * Objet ciblé
	 */
	private Item item;

	/**
	 * Créer un objet ramassable grâce au bestiaire du jeu (Item.java)
	 * @param x : Position x sur la carte
	 * @param y : Position y sur la carte
	 * @param eT : Type d'entity
	 * @param item : L'objet demandé (à afficher) [Item.java]
	 */
	public EntityLoot(float x, float y, EntityType eT, Item item){
		super(x,y,eT);
		this.item = item;
	}
	
	public void tick(LinkedList<Entity> entity) {
	 // Possibilité d'avenir : Faire scintiller l'objet
	}
	public void render(Graphics g) {
		g.drawImage(item.getIcon(), (int)coord.getX(), (int)coord.getY(), null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)((int)coord.getX()), (int) ((int)coord.getY()), item.getWidth(), item.getHeight());
	}
	
	/**
	 * Retourne l'objet définit dans le constructeur
	 * @return Item
	 */
	public Item getItem(){
		return item;
	}
	
	public Rectangle getBoundsBottom() {return null;}
	public Rectangle getBoundsFloor() {return null;}
	public int pourcentageHP() {return 0;}
	public int getPostion() {return 0;}
	public void setPosition(int col, int line) {}
	
	public void die() {
		Game.getHandlerEntity().removeEntityLoot(this);
	}
}

package com.nincha.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
/**
 * EntityAnimation définit l'animation des sprites de l'entity ciblé.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class EntityAnimation {

	private int speed;
	private int frames;
	
	private int index = 0;
	private int count = 0;
	
	private BufferedImage[] images;
	private BufferedImage currentImg;
	
	/**
	 * Ce constructeur est utilisé pour l'EntityPlayer, l'EntityDoor, l'EntityDoor
	 * @param speed : Vitesse d'animation du sprite
	 * @param args : Un nombre de sprite à la suite
	 */
	public EntityAnimation(int speed, BufferedImage... args){
		this.speed = speed;
		images = new BufferedImage[args.length];
		for(int i=0; i < args.length; i++){
			images[i] = args[i];
		}
		frames = args.length;
	}
	
	/**
	 * Ce constructeur est utilisé pour l'entity Projectile Joueur, car celui ci possède une liste de sprite initialisé au début du jeu
	 * @param speed : Vitesse d'animation du sprite
	 * @param args : Liste de sprite
	 */
	public EntityAnimation(int speed, LinkedList<BufferedImage> args){
		this.speed = speed;
		images = new BufferedImage[args.size()];
		for(int i=0; i < args.size(); i++){
			images[i] = args.get(i);
		}
		frames = args.size();
	}
	
	/**
	 * Déclanche l'animation (utilisé dans une méthode tick)
	 */
	public void runAnimation(){
		index++;
		if(index > speed){
			index = 0;
			nextFrame();
		}
	}

	/**
	 * Utilisé par la méthode runAnimation, permettant de passé au sprite suivant
	 */
	private void nextFrame() {
		currentImg = images[count%frames];
		count++;
	}
	
	/**
	 * Affiche l'animation (utilisé dans une méthode render) 
	 * @param g : zone de canvas
	 * @param x : position x de l'entity
	 * @param y : position y de l'entity
	 */
	public void drawAnimation(Graphics g, int x, int y){
		g.drawImage(currentImg, x, y, null);
	}
}

package com.nincha.utils;

import java.awt.image.BufferedImage;

/**
 * SpriteEntity Nous permet de récupérer une planche de sprite (largeur x hauteur), puis de la découper avec la méthode adéquate, ici takeSprite.
 * @author Nincha Corporation
 * @version 1.0
 */
public class SpriteEntity {

	/**
	 * La planche de sprite type BufferedImage
	 */
	private BufferedImage image;
	
	/**
	 * Crée un objet SpriteEntity, pouvant être par la suite découpé en plusieurs images.
	 * @param img : Récupère une planche de sprite type BufferedImage
	 */
	public SpriteEntity(BufferedImage img){
		this.image = img;
	}
	
	/**
	 * 
	 * @param col : Récupère le numéro de colonne du sprite (image)
	 * @param line : Récupère le numéro de ligne du sprite (image)
	 * @param width : Récupère la largeur de la portion de l'image à prendre
	 * @param height : Récupère la hauteur de la portion de l'image à prendre
	 * @return BufferedImage
	 */
	public BufferedImage takeSprite(int col, int line, int width, int height){
		return image.getSubimage((col*width)-width, (line*height)-height, width, height);
	}
}

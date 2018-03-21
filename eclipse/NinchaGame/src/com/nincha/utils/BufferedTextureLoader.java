package com.nincha.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * BufferedTextureLoader, comme son nom l'indique, permet d'aller charger des
 * fichiers ici nous l'utilisons pour charger essentiellement les textures du
 * jeu. Utilisé en parallèle avec SpriteEntity.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class BufferedTextureLoader {

	/**
	 * Récupère une image de type BufferedImage
	 */
	private BufferedImage image;

	/**
	 * Cette méthode s'occupe de charger les différentes textures du jeu (sprites)
	 * @param file : Récupère le chemin du fichier à charger
	 * @return : une image du type BufferedImage
	 * @throws IOException : Erreur si le fichier n'existe pas
	 */
	public BufferedImage loadTexture(File file) throws IOException {
		image = ImageIO.read(file);
		return image;
	}
}

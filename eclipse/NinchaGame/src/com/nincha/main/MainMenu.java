package com.nincha.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nincha.utils.HandlerSprite;

/**
 * MainMenu est le menu principal du jeu.
 * @author Nincha Corporation
 * @version 1.0
 */
public class MainMenu {
	
	private static BufferedImage boutonJouer;
	private static BufferedImage boutonAide;
	private static BufferedImage boutonQuitter;
	private static BufferedImage boutonRetour;
	
	private HandlerSprite hs = Game.getHandlerSprite();
	
	public MainMenu(){
		boutonJouer = hs.getUtilsImage(1);
		boutonAide = hs.getUtilsImage(3);
		boutonQuitter = hs.getUtilsImage(5);
		boutonRetour = hs.getUtilsImage(8);
	}
	
	/**
	 * Affiche les différents composant du menu principal ou du menu aide
	 * @param g : Graphics
	 */
	public void render(Graphics g){
		if(Game.getGameState() == GameState.MainMenu){
			g.drawImage(hs.getUtilsImage(0), 0, 0, null);
			g.drawImage(boutonJouer, 576, 350, null);
			g.drawImage(boutonAide, 576, 350+(hs.getUtilsImage(3).getHeight()+15), null);
			g.drawImage(boutonQuitter, 576, 350+(hs.getUtilsImage(5).getHeight()+15)*2, null);
		}else if(Game.getGameState() == GameState.AideMenu){
			g.drawImage(hs.getUtilsImage(7), 0, 0, null);
			g.drawImage(boutonRetour, 860, 625, null);
		}
	}

	public static BufferedImage getBoutonJouer() {
		return boutonJouer;
	}

	public static void setBoutonJouer(BufferedImage boutonJouer) {
		MainMenu.boutonJouer = boutonJouer;
	}

	public static BufferedImage getBoutonAide() {
		return boutonAide;
	}

	public static void setBoutonAide(BufferedImage boutonAide) {
		MainMenu.boutonAide = boutonAide;
	}

	public static BufferedImage getBoutonQuitter() {
		return boutonQuitter;
	}

	public static void setBoutonQuitter(BufferedImage boutonQuitter) {
		MainMenu.boutonQuitter = boutonQuitter;
	}
	
	public static BufferedImage getBoutonRetour() {
		return boutonRetour;
	}

	public static void setBoutonRetour(BufferedImage boutonRetour) {
		MainMenu.boutonRetour = boutonRetour;
	}


}

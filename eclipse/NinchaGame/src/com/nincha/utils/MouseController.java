package com.nincha.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import com.nincha.entity.Entity;
import com.nincha.entity.EntityProjectile;
import com.nincha.entity.EntityType;
import com.nincha.main.EndGame;
import com.nincha.main.Game;
import com.nincha.main.GameState;
import com.nincha.main.MainMenu;

/**
 * MouseController permet de récupérer les déplacements / cliques souris
 * quand l'on est en jeu.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class MouseController extends MouseAdapter {

	private Entity nincha = Game.getHandlerEntity().getNincha();
	private LinkedList<Entity> containerProjectile = Game.getHandlerEntity().getProjectileList();
	private long timeNow = 0;
	private long timer = 0;

	public MouseController() {
	}

	public void mousePressed(MouseEvent e) {
		//System.out.println(e.getX() + " | " + e.getY());
		if (Game.getGameState() == GameState.InGameMenu) {
			timeNow = System.currentTimeMillis();
			if (timer - timeNow <= 0) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					// 2-5-8-11
					containerProjectile.add(new EntityProjectile(nincha.getX() + 16, nincha.getY() + 32,
							nincha.getAtk(), EntityType.EntityProjectile, e));
					timeNow = System.currentTimeMillis();
					timer = timeNow + 250;
				}
			}
			//System.out.println(Game.getGuiInGame().isArtefactCollected());
			if (e.getButton() == MouseEvent.BUTTON1 && ((e.getX() >= 317 && e.getY() >= 735) && (e.getX() <= 317+114 && e.getY() <= 735+31) && Game.getGuiInGame().isArtefactCollected())){
				Game.setGameState(GameState.VictoryMenu);
				EndGame.setTimeStop(Game.getCompteurStats().realTimeGame());
				EndGame.setTimeLongStop(Game.getCompteurStats().timeGame());
			}
		} else if (Game.getGameState() == GameState.MainMenu) {
			// Cliquer sur le bouton JOUER
			if (isBoutonClicked(e, 576, 350, 1, 0))
				MainMenu.setBoutonJouer(Game.getHandlerSprite().getUtilsImage(2));
			// Cliquer sur le bouton AIDE
			else if (isBoutonClicked(e, 576, 350, 3, 1))
				MainMenu.setBoutonAide(Game.getHandlerSprite().getUtilsImage(4));
			// Cliquer sur le bouton QUITTER
			else if (isBoutonClicked(e, 576, 350, 5, 2))
				MainMenu.setBoutonQuitter(Game.getHandlerSprite().getUtilsImage(6));
		} else if (Game.getGameState() == GameState.AideMenu){
			// Cliquer sur le bouton RETOUR
			if (isBoutonClicked(e, 860, 625, 8, 0)){
				MainMenu.setBoutonRetour(Game.getHandlerSprite().getUtilsImage(9));
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (Game.getGameState() == GameState.MainMenu) {
			if (MainMenu.getBoutonJouer() == Game.getHandlerSprite().getUtilsImage(2)
					&& (isBoutonClicked(e, 576, 350, 1, 0))) {
				//Executé si le curseur a bien été relaché sur le bouton
				MainMenu.setBoutonJouer(Game.getHandlerSprite().getUtilsImage(1));
				Game.setGameState(GameState.InGameMenu);
			}else if(MainMenu.getBoutonJouer() == Game.getHandlerSprite().getUtilsImage(2)) {
				//Executé si le curseur a été relaché autre part que sur le bouton
				MainMenu.setBoutonJouer(Game.getHandlerSprite().getUtilsImage(1));
			}
			
			if (MainMenu.getBoutonAide() == Game.getHandlerSprite().getUtilsImage(4) && (isBoutonClicked(e, 576, 350, 3, 1))) {
				MainMenu.setBoutonAide(Game.getHandlerSprite().getUtilsImage(3));
				Game.setGameState(GameState.AideMenu);
			}else if (MainMenu.getBoutonAide() == Game.getHandlerSprite().getUtilsImage(4)) {
				MainMenu.setBoutonAide(Game.getHandlerSprite().getUtilsImage(3));
			} 
			
			if (MainMenu.getBoutonQuitter() == Game.getHandlerSprite().getUtilsImage(6) && (isBoutonClicked(e, 576, 350, 5, 2))) {
				System.exit(1);
			}else if (MainMenu.getBoutonQuitter() == Game.getHandlerSprite().getUtilsImage(6)) {
				MainMenu.setBoutonQuitter(Game.getHandlerSprite().getUtilsImage(5));
			}
			
		}else if(Game.getGameState() == GameState.AideMenu){
			if (MainMenu.getBoutonRetour() == Game.getHandlerSprite().getUtilsImage(9) && (isBoutonClicked(e, 860, 625, 9, 0))) {
				MainMenu.setBoutonRetour(Game.getHandlerSprite().getUtilsImage(8));
				Game.setGameState(GameState.MainMenu);
			}else if(MainMenu.getBoutonRetour() == Game.getHandlerSprite().getUtilsImage(9)) {
				//Executé si le curseur a été relaché autre part que sur le bouton
				MainMenu.setBoutonRetour(Game.getHandlerSprite().getUtilsImage(8));
			}
		}
		

	}

	private boolean isBoutonClicked(MouseEvent e, int x, int y, int id, int i) {
		boolean isClicked = false;
		if (e.getX() >= x && e.getY() >= y && e.getX() <= x + Game.getHandlerSprite().getUtilsImage(id).getWidth()
				&& e.getY() <= y + ((Game.getHandlerSprite().getUtilsImage(id).getHeight() + 15) * i)
						+ Game.getHandlerSprite().getUtilsImage(id).getHeight()) {
			isClicked = true;
		}
		return isClicked;
	}

}

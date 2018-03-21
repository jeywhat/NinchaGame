package com.nincha.main;

/**
 * GameState permet de savoir notre positionnement dans le jeu.
 * Savoir si l'on est dans le Menu Principal ou dans le jeu par exemple.
 * Ainsi cela permet d'afficher ce que l'on veut pour chaque situation.
 *
 * @author Nincha Corporation
 * @version 1.0
 */
public enum GameState {

	/**
	 * Le Menu Principal
	 */
	MainMenu(),
	/**
	 * En jeu
	 */
	InGameMenu(),
	/**
	 * Menu Principal : Accès au Menu d'Aide
	 */
	AideMenu(),
	/**
	 * Lors d'une victoire
	 */
	VictoryMenu(),
	/**
	 * Lors d'une defaite
	 */
	DefeatMenu();
	
}

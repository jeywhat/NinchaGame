package com.nincha.entity;

/**
 * ItemType permet de différencier les item (objet) entre eux.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public enum ItemType {
	/**
	 * Objet généralement inexistant dans l'inventaire, (pièce, morceau d'armure,...)
	 */
	BASIC(),
	/**
	 * Objet de quête inexistant dans l'inventaire
	 */
	ARTEFACT(),
	/**
	 * Objet permettant de soigner le joueur
	 */
	POTION_HEAL(),
	/**
	 * Objet permettant de renforcer le joueur (++attaque) {Inutilisé dans cette version}
	 */
	POTION_STRONG(),
	
}

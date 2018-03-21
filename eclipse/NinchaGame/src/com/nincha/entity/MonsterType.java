package com.nincha.entity;

/**
 * MonsterType permet de différencier les monstres (EntityMob) entre eux.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public enum MonsterType {

	/**
	 * Les monstres communs
	 */
	BASIC(),
	/**
	 * Les mini-boss trouvable uniquement à la salle 5 
	 */
	MINI_BOSS(),
	/**
	 * Classe : Corps à Corps
	 */
	CAC(),
	/**
	 * Classe : Distance
	 */
	DISTANCE(),
	/**
	 * Le boss final (Pas encore implémenté)
	 */
	BOSS();
	
}

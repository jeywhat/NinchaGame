package com.nincha.entity;

/**
 * EntityType permet de différencier les entity entre eux.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public enum EntityType {
	
	//LES ENTITES "VIVANTE" (Forte Interraction)
	/**
	 * Le joueur principal
	 */
	EntityPlayer(), 
	/**
	 * Le projectile lancé par une entity
	 */
	EntityProjectile(),
	/**
	 *  Le projectile lancé par un Monstre
	*/
	EntityProjectileIA(),
	/**
	/**
	 * Le monstre
	 */
	EntityMonster(),
	/**
	 * Le Boss final (Prochaine Mise à jour)
	 */
	EntityBoss(),
	/**
	 * Un objet ramassable
	 */
	EntityLoot(),
	
	//LE TERRAIN
	/**
	 * Le sol du jeu
	 */
	EntityFloor(),
	/**
	 * Les murs du jeu
	 */
	EntityWall(),
	/**
	 * Les corniches (du haut)
	 */
	EntityWallTop(),
	/**
	 * Les corniches (de droite)
	 */
	EntityWallRight(),
	/**
	 * Les corniches (du bas)
	 */
	EntityWallBottom(),
	/**
	 * Les corniches (de gauche)
	 */
	EntityWallLeft(),
	/**
	 * Les intersections de corniches (haut à gauche)
	 */
	EntityCornerTopG(),
	/**
	 * Les intersections de corniches (haut à droite)
	 */
	EntityCornerTopD(),
	/**
	 * Les intersections de corniches (bas à droite)
	 */
	EntityCornerBotD(),
	/**
	 * Les intersections de corniches (bas à gauche)
	 */
	EntityCornerBotG(),
	/**
	 * Les obstacles sur la carte
	 */
	EntityObstacle(),
	/**
	 * Les portes menant à des salles / étages différents
	 */
	EntityDoor();
	
}


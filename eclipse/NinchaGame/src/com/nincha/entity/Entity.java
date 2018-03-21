package com.nincha.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.nincha.utils.Coord;
/**
 * Entity est la classe représentant une entité.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public abstract class Entity {
	
	/**
	 *  Les coordonnées x et y de l'entité. Elles sont modifiables.
	 */
	protected Coord coord;
	/**
	 * La vélocité sur l'axe des X de l'entité
	 * Initialisé à 0. Cette velocité est modifiable
	 */
	protected float velociteX = 0;
	/**
	 * La vélocité sur l'axe des Y de l'entité
	 * Initialisé à 0. Cette velocité est modifiable
	 */
	protected float velociteY = 0;
	/**
	 * Le nombre de point de vie de l'entité
	 * Initialisé à 0. Ce nombre de point de vie est modifiable
	 */
	protected double pv = 0;
	/**
	 * L'armure de l'entité.
	 * Initialisé à 0. Cette armure est modifiable
	 */
	protected double armor = 0;
	/**
	 * L'attaque de l'entité.
	 * Initialisé à 0. Cette attaque est modifiable.
	 */
	protected double atk = 0;
	/**
	 * Le type de l'entité. Elle est modifiable.
	 */
	protected EntityType entityType;

	/**
	 * Créer un type d'entité avec sa position sur la fenêtre.
	 * @param x : Position x sur la fenêtre.
	 * @param y : Position y sur la fenêtre.
	 * @param entityType : Défini le type de l'objet
	 */
	public Entity(float x, float y, EntityType entityType){
		this.coord = new Coord(x,y);
		this.entityType = entityType;
	}
	/**
	 * Créer un type d'entité avec sa position sur la fenêtre et son attaque.
	 * @param x : Position x sur la fenêtre
	 * @param y : Position y sur la fenêtre
	 * @param atk : Défini l'attaque de l'entité
	 * @param entityType : Défini le type de l'objet
	 */
	public Entity(float x, float y, double atk, EntityType entityType){
		this.coord = new Coord(x,y);
		this.atk = atk;
		this.entityType = entityType;
	}
	/**
	 * Créer un type d'entité avec sa position sur la fenêtre, son attaque, sa vie et son armure.
	 * @param x : Position x sur la fenêtre
	 * @param y : Position y sur la fenêtre
	 * @param pv : Défini le nombre de point de vie de l'entité
	 * @param atk : Défini l'attaque de l'entité
	 * @param armor : Défini l'armure de l'entité
	 * @param entityType : Défini le type de l'objet
	 */
	public Entity(float x, float y, double pv, double atk, double armor, EntityType entityType){
		this.coord = new Coord(x,y);
		this.pv = pv;
		this.atk = atk;
		this.armor = armor;
		this.entityType = entityType;
	}
	
	/**
	 * Méthode compliqué à expliquer, mais le tick est la partie calcul (le
	 * déplacement des ennemies, le calcul de l'AStar, les evènement de
	 * ramassage d'item sur le sol, la génération de map, etc...). Elle est
	 * executée toutes les 50ms (environ, si mes calculs sont exactes).
	 * 
	 * @param entity : Une liste d'entity permettant un calcul entre l'entity présente et les différente entity de la liste
	 */
	public abstract void tick(LinkedList<Entity> entity);
	
	/**
	 * Methode compliqué à expliquer, mais le render s'occupe de dessiner un
	 * nombre d'images par seconde. Elle est executée toutes les images Par
	 * seconde (Si vous avez un bon PC c'est 60FPS, sinon si c'est inférieur à
	 * 60, FPS = TICK;
	 * @param g : La Zone de Canvas, là où il faut dessiner
	 */
	public abstract void render(Graphics g);
	
	/**
	 * Cette méthode s'exécute lorsque l'entity meurt.
	 */
	public abstract void die();
	
	/**
	 * Dessine un rectangle tout autour de l'entity (HitBox).
	 * @return Rectangle
	 */
	public abstract Rectangle getBounds();
	
	/**
	 * Dessine un rectangle tout autour de l'entity (HitBox) mais plus précisément que la moitié du personnage (en partant du bas).
	 * @return Rectangle
	 */
	public abstract Rectangle getBoundsBottom();
	
	/**
	 * Dessine un rectangle tout autour des "pieds" de l'entity (HitBox).
	 * @return Rectangle
	 */
	public abstract Rectangle getBoundsFloor();
	
	/**
	 * Le pourcentage de vie restant de l'entity.
	 * @return : Le pourcentage de vie restant de l'entity
	 */
	public abstract int pourcentageHP();
	
	/**
	 * Une entity de manière général possède une plaquette de sprite (3x4)
	 * Cette méthode permet de récupérer la position qu'il tient dans cette plaquette.
	 * @return la position de l'entity tenu dans cette plaquette de sprite.
	 */
	public abstract int getPostion();
	
	/**
	 * Une entity de manière général possède une plaquette de sprite (3x4)
	 * Cette méthode permet de récupérer la position qu'il tient dans cette plaquette.
	 * @param col : Colonne dans le sprite (1~3)
	 * @param line : Ligne dans le sprite (1~4)
	 */
	public abstract void setPosition(int col, int line);
	/**
	 * Retourne la coordonnée X de l'entité
	 * @return la coordonnée X de l'entité sous forme d'un float
	 */
	public float getX() {
		return coord.getX();
	}
	/**
	 * Retourne la coordonnée Y de l'entité
	 * @return la coordonnée Y de l'entité sous forme d'un float
	 */
	public float getY() {
		return coord.getY();
	}
	/**
	 * Met à jour la coordonnée X de l'entité 
	 * @param x la nouvelle coordonnée X de l'entité sous forme d'un float
	 */
	public void setX(float x) {
		coord.setX(x);
	}
	/**
	 * Met à jour la coordonnée Y de l'entité
	 * @param y la nouvelle coordonnée Y de l'entité sous forme d'un float
	 */
	public void setY(float y) {
		coord.setY(y);
	}
	/**
	 * Retourne la vélocité parcouru de l'entité par tick sur l'axe X
	 * @return la vélocité ,sur l'axe X, parcouru de l'entitité par tick sous forme d'un float
	 */
	public float getVeloX() {
		return this.velociteX;
	}
	/**
	 * Retourne la vélocité parcouru de l'entité par tick sur l'axe Y
	 * @return la vélocité, sur l'axe Y , parcouru de l'entité par tick sous forme d'un float
	 */
	public float getVeloY() {
		return this.velociteY;
	}
	/**
	 * Met a jour la vélocité parcouru de l'entité par tick sur l'axe X	
	 * @param x la nouvelle vélocité, sur l'axe X, parcouru de l'entité par tick sous forme d'un float
	 */
	public void setVeloX(float x) {
		this.velociteX = x;
	}
	/**
	 * Met a jour la vélocité parcouru de l'entité par tick sur l'axe Y
	 * @param y la nouvelle vélocité, sur l'axe Y, parcouru de l'entité par tick sous forme d'un float
	 */
	public void setVeloY(float y) {
		this.velociteY = y;
	}
	/**
	 * Retourne le type de l'entité
	 * @return le type de l'entité
	 */
	public EntityType getType() {
		return this.entityType;
	}
	/**
	 * Met à jour le type de l'entité
	 * @param entityType le nouveau type de l'entité
	 */
	public void setType(EntityType entityType) {
		this.entityType = entityType;
	}
	/**
	 * Retourne le nombre de point de vie de l'entité
	 * @return les points de vie de l'entité sous forme d'un double
	 */
	public double getPv() {
		return this.pv;
	}
	/**
	 * Retourne l'armure de l'entité
	 * @return l'armure de l'entité sous forme d'un double
	 */
	public double getArmor() {
		return this.armor;
	}
	/**
	 * Met à jour le nombre de point de vie de l'entité
	 * @param d les nouveaux points de vie de l'entité sous forme d'un double
	 */
	public void setPv(double d) {
		this.pv = d;
	}
	/**
	 * Met à jour l'armure de l'entité
	 * @param d les nouveaux points d'armure de l'entité sous forme d'un double
	 */
	public void setArmor(double d) {
		this.armor = d;
	}
	/**
	 * Retourne l'attaque de l'entité
	 * @return l'attaque de l'entité sous forme d'un double
	 */
	public double getAtk() {
		return this.atk;
	}
	/**
	 * Met à jour l'attaque de l'entité
	 * @param atk la nouvelle attaque de l'entité sous forme d'un double
	 */
	public void setAtk(double atk) {
		this.atk = atk;
	}
}

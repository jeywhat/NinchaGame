package com.nincha.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.nincha.generation.GenerationMap;
import com.nincha.main.EndGame;
import com.nincha.main.Game;
import com.nincha.main.GameState;
import com.nincha.utils.SpriteEntity;

/**
 * EntityPlayer, comme son nom l'indique cette classe est le joueur principal du jeu, ici le nincha.
 * 
 * @author NinchaCorporation
 * @version 1.0
 */
public class EntityPlayer extends Entity {

	/**
	 *  Defini l'experience du personnage principal a 0
	 */
	private int experience = 0;
	/**
	 * Defini le niveau du personnage principal a 1
	 */
	private int niveau = 1;
	/**
	 * Recupere le sprite 3x4 du personnage principal
	 */
	private SpriteEntity se;
	/**
	 * Position dans le sprite
	 */
	private int col = 2, line = 1;
	/**
	 * Recupere la position 1x1 du personnage principal
	 */
	private BufferedImage img;
	/**
	 * Defini la taille du personnage
	 */
	private int width = 64, height = 64;
	/**
	 * Defini les points de vie initiales
	 */
	private double pvInit = 0;
	/**
	 * Animation du personnage
	 */
	private LinkedList<EntityAnimation> animationNincha = new LinkedList<EntityAnimation>();
	/**
	 * Inventaire Personnage
	 */
	private LinkedList<Item> inventory = new LinkedList<Item>();
	/**
	 * Pièces récoltées par le joueur
	 */
	private int pieces = 0;

	/**
	 * Définit le joueur principal (Nincha)
	 * @param x : Position en x
	 * @param y : Position en y
	 * @param pv : Son nombre de point de vie
	 * @param atk : Son attaque au niveau 1
	 * @param armor : Son armure
	 * @param id : Le type d'entité 
	 */
	public EntityPlayer(float x, float y, double pv, double atk, double armor, EntityType id) {
		super(x, y, pv, atk, armor, id);
		this.pvInit = pv;
		this.setInventory();
		se = new SpriteEntity(Game.getHandlerSprite().getSpriteEntity(0));
		this.setPosition(col, line);
		for (int i = 1; i <= 4; ++i)
			animationNincha.add(new EntityAnimation(6, getSprite(1, i), getSprite(2, i), getSprite(3, i)));
	}

	public void tick(LinkedList<Entity> entity) {
		if (this.pv <= 0) {
			die();
		}
		
		if (armor > 0.50){
			this.armor = 0.50;
		}
		
		if (coord.getX() + velociteX <= 0 + 32 * 2 || coord.getX() + velociteX >= 1024 - 20 - 32 * 3
				|| coord.getY() + velociteY >= 768 - 32 * 4 || coord.getY() + velociteY <= 0 + 24 * 5) {
			velociteX = 0;
			velociteY = 0;
		}
		coord.setX(coord.getX() + velociteX);
		coord.setY(coord.getY() + velociteY);
		collisionBounds(entity);
		int pos = 2;
		for (int i = 0; i < 4; ++i) {
			if (getPostion() == pos)
				animationNincha.get(i).runAnimation();
			pos += 3;
		}
	}

	public void render(Graphics g) {
		if ((velociteX != 0 || velociteY != 0) && getPostion() == 2)
			animationNincha.get(0).drawAnimation(g, (int) coord.getX(), (int) coord.getY());
		else if ((velociteX != 0 || velociteY != 0) && getPostion() == 5)
			animationNincha.get(1).drawAnimation(g, (int) coord.getX(), (int) coord.getY());
		else if ((velociteX != 0 || velociteY != 0) && getPostion() == 8)
			animationNincha.get(2).drawAnimation(g, (int) coord.getX(), (int) coord.getY());
		else if ((velociteX != 0 || velociteY != 0) && getPostion() == 11)
			animationNincha.get(3).drawAnimation(g, (int) coord.getX(), (int) coord.getY());
		else
			g.drawImage(img, (int) coord.getX(), (int) coord.getY(), null);
	}

	/**
	 * Cette methode permet de tester comme son nom l'indique, les colisions avec les autres entity
	 * @param object : une liste d'entity à tester
	 */
	private void collisionBounds(LinkedList<Entity> object) {
		for (int i = 0; i < Game.getHandlerEntity().getEntityList().size(); i++) {
			Entity tempObject = Game.getHandlerEntity().getEntityList().get(i);

			if (tempObject.getType() == EntityType.EntityMonster) {
				if (getBoundsBottom().intersects(tempObject.getBounds())) {
					coord.setX(coord.getX() - velociteX);
					coord.setY(coord.getY() - velociteY);
					velociteX = 0;
					velociteY = 0;
				}

			}
		}

		for (int i = 0; i < Game.getHandlerEntity().getTextureInPremiereCoucheList().size(); i++) {
			Entity tempObject = Game.getHandlerEntity().getTextureInPremiereCoucheList().get(i);

			if (tempObject.getType() == EntityType.EntityObstacle) {
				if (getBoundsObstacle().intersects(tempObject.getBounds())) {
					coord.setX(coord.getX() - velociteX);
					coord.setY(coord.getY() - velociteY);
					velociteX = 0;
					velociteY = 0;
				}
			} else if (tempObject.getType() == EntityType.EntityDoor) {
				if (((EntityDoor) tempObject).getOpen())
					if (getBoundsFloor().intersects(tempObject.getBounds()) && this.getPostion() == 11) {
						Game.getHandlerEntity().clearEntity();
						Game.getHandlerEntity().clearTerrainPremiereCouche();
						Game.getHandlerEntity().clearTerrainSecondeCouche();
						Game.getHandlerEntity().clearEntityLoot();
						Game.getHandlerEntity().clearProjectile();
						this.setX(200);
						this.setY(140);
						this.setPosition(2, 1);
						new GenerationMap();
						Game.setTransitionSalle(true);

					}
			}
		}

		for (int i = 0; i < Game.getHandlerEntity().getEntityLootList().size(); i++) {
			EntityLoot tempObject = (EntityLoot) Game.getHandlerEntity().getEntityLootList().get(i);

			if (getBounds().intersects(tempObject.getBounds())) {
				if (tempObject.getItem().getId() == 0)
					this.addPiece(1);
				else if (tempObject.getItem().getId() == 1)
					this.setArmor(this.getArmor()+0.005);
				else if (tempObject.getItem().getId() == 2)
					this.setPv(this.getPv()-this.getPv()*0.25);
				else
					this.getInventory().add(tempObject.getItem());
				tempObject.die();
			}
		}
	}

	public int pourcentageHP() {
		if (pv <= 0) {
			pv = 0;
		}
		return (int) ((pv * 100) / pvInit);
	}

	public void setPosition(int col, int line) {
		this.col = col;
		this.line = line;
		img = se.takeSprite(col, line, 64, 64);
	}

	public BufferedImage getSprite(int col, int line) {
		return se.takeSprite(col, line, 64, 64);
	}

	/**
	 * Définit l'inventaire initial du joueur
	 */
	private void setInventory() {
		inventory.add(Item.potionVieI);
		//POUR LA DEMO
		/*inventory.add(Item.calisseHarmonie);
		inventory.add(Item.clefUniversel);
		inventory.add(Item.collierMagique);
		inventory.add(Item.livreAncestral);
		inventory.add(Item.masqueVaudou);
		inventory.add(Item.oeilDuSerpent);
		inventory.add(Item.peluche);*/
	}

	public int getPostion() {
		return col + (line - 1) * 3;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) ((int) coord.getX() + (width / 2) - ((width / 2) / 2) - 3),
				(int) ((int) coord.getY()), (int) width / 2 + 6, (int) height);
	}

	public Rectangle getBoundsObstacle() {
		return new Rectangle((int) ((int) coord.getX() + (width / 2) - ((width / 4) / 2) - 3),
				(int) coord.getY() + height - (int) height / 10, (int) width / 4 + 6, (int) height / 10);
	}

	public Rectangle getBoundsZoneDamage() {
		return new Rectangle((int) ((int) coord.getX() + (width / 2) - ((width / 2) / 2) - 3) - 8,
				(int) ((int) coord.getY()) - 4, (int) width / 3 + 32, (int) height + 8);
	}

	public Rectangle getBoundsBottom() {
		return new Rectangle((int) ((int) coord.getX() + (width / 2) - ((width / 2) / 2) - 3),
				(int) coord.getY() + (int) height / 2 + (int) height / 6, (int) width / 2 + 6, (int) height / 3);
	}

	public Rectangle getBoundsFloor() {
		return new Rectangle((int) ((int) coord.getX() + (width / 2) - ((width / 2) / 2) - 3),
				(int) coord.getY() + height - (int) height / 10, (int) width / 2 + 6, (int) height / 10);
	}

	public void die() {
		Game.setGameState(GameState.DefeatMenu);
		EndGame.setTimeStop(Game.getCompteurStats().realTimeGame());
		EndGame.setTimeLongStop(Game.getCompteurStats().timeGame());
	}

	/**
	 * Ajoute de l'expérience au personnage principal
	 * @param i : Nombre d'expérience à rajouter au nincha
	 */
	public void addExp(double i) {
		this.experience += i;
	}

	/**
	 * Donne l'expérience du joueur
	 * @return : l'expérience du joueur accumulé pendant ce niveau
	 */
	public int getExp() {
		return this.experience;
	}

	/**
	 * Retourne les points de vie initaux (c'est à dire, lors de la création de l'entité), ici 100 
	 * @return : les Points de Vie du personnage principal
	 */
	public double getPvInitial() {
		return this.pvInit;
	}
	
	/**
	 * Définit son expérience, utile pour remettre son expérience à zéro lors d'un passage de niveau
	 * @param exp : Expérience à définir
	 */
	public void setExp(double exp) {
		this.experience = (int) exp;
	}

	/**
	 * Récupère le niveau du personnage principal (nincha)
	 * @return : le niveau du personnage principal
	 */
	public int getLevel() {
		return this.niveau;
	}
	
	/**
	 * Définit le niveau du personnage principal
	 * @param lvl : Le nouveau niveau à définir
	 */
	public void setLevel(int lvl) {
		this.niveau = lvl;
	}

	/**
	 * Ajoute un nombre de pièce, dans son inventaire (compteur)
	 * @param i : Nombre de pièce(s)
	 */
	public void addPiece(int i) {
		this.pieces += i;
	}

	/**
	 * Retire un nombre de pièce bien précis (inutilisé à ce jour)
	 * @param i : Nombre de pièce
	 */
	public void removePiece(int i) {
		this.pieces -= i;
	}

	/**
	 * Permet de récupérer le nombre de pièces du joueur
	 * @return : Le nombre de pièce(s) qu'a accumulé le joueur.
	 */
	public int getPiece() {
		return this.pieces;
	}

	/**
	 * Permet de récupérer l'inventraire du joueur
	 * @return : l'inventaire du joueur
	 */
	public LinkedList<Item> getInventory() {
		return inventory;
	}

	/**
	 * Courbe d'expérience du personnage principal
	 * @param level : Récupère le niveau du personnage
	 * @return : l'expérience a avoir pour passer de niveau
	 */
	public int getExpToLevelUp(int level) {
		return (int)(100*((Math.log(level)+level)/5));
	}

	/**
	 * Méthode utilisé essentiellement pour les tests
	 */
	public String toString() {
		String phrase = "Je suis un Nincha de niveau : " + niveau + " avec " + experience + " points d'expérience avec "
				+ pieces + "  pièces et dans mon Inventaire j'ai : ";
		for (int i = 0; i < inventory.size(); ++i) {
			phrase = phrase + inventory.get(i).getName() + " | ";
		}
		return phrase;
	}
}

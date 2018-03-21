package com.nincha.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import com.nincha.entity.MonsterType;
import com.nincha.generation.SpawningEntity;
import com.nincha.main.Game;
import com.nincha.utils.Astar;
import com.nincha.utils.Noeud;
import com.nincha.utils.SpriteEntity;

/**
 * EntityMob permet de convertir un monstre du bestiaire en une entity vivante 
 * (coordonnée, point de vie, vitesse, attaque, armure, inventaire etc...)
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class EntityMob extends Entity {

	/**
	 * Monstre ciblé
	 */
	private Monster monster;
	/**
	 * Taille du sprite de taille 1x1
	 */
	private int width = 0, height = 0;
	/**
	 * Point(s) de vie de l'entity
	 */
	private double pv;
	/**
	 * Point(s) de vie de l'entity définit dans le constructeur
	 */
	private double pvInit;
	/**
	 *Armure de l'entity
	 */
	private double armor;
	/**
	 * Expérience(s) donnée(s) par le monstre
	 */
	private double experienceGive;

	/**
	 * Nombre de test effectés par la méthode récurssive, lors du spawn d'item
	 */
	private int testSpawnInventoryOnFloor = 0;
	/**
	 * Vitesse des EntityMob (impossible de bouger à cause de l'AStar)
	 */
	private float maxVelo = 2F;
	/**
	 * Inventaire du monstre
	 */
	private LinkedList<Item> inventoryToDrop = new LinkedList<Item>();
	/**
	 * Recupere le sprite 3x4 du monstre (executé dans le constructeur)
	 */
	private SpriteEntity se;
	/**
	 * Position dans le sprite 2x1
	 */
	private int col = 2, line = 1;
	/**
	 *  Recupere le sprite a afficher en fonction de sa position.
	 */
	private BufferedImage img;
	/**
	 * Les Sprites de l'animation du monstre
	 */
	private LinkedList<EntityAnimation> animationMonster = new LinkedList<EntityAnimation>();

	private LinkedList<Noeud> noeuds = new LinkedList<Noeud>();
	/**
	 * L'AStar : Algorithme de PathFinding
	 */
	Astar a;
	int ticka = 30;

	float directionx = 0;
	float directiony = 0;
	
	float projx = 0;
	float projy = 0;

	/**
	 * Créer un monstre grâce au bestiaire du jeu (Monster.java)
	 * @param x : Position x du monstre
	 * @param y : Position y du monstre
	 * @param id : Type d'entity
	 * @param monster : Monstre ciblé dans le bestiaire (Monster.java)
	 */
	public EntityMob(float x, float y, EntityType id, Monster monster) {
		super(x, y, id);
		this.monster = monster;
		this.width = monster.getIcon().getWidth() / 3;
		this.height = monster.getIcon().getHeight() / 4;

		this.pv = monster.getPv();
		this.pvInit = pv;
		this.armor = monster.getArmor();
		this.experienceGive = monster.getExp();
		this.setAtk(monster.getAtk());

		this.a = new Astar(this, (EntityPlayer) Game.getHandlerEntity().getNincha());
		this.setInventory();
		se = new SpriteEntity(monster.getIcon());
		this.setPosition(col, (int) (line + Math.random() * 4));
		for (int i = 1; i <= 4; ++i)
			animationMonster.add(new EntityAnimation(5, getSprite(1, i), getSprite(2, i), getSprite(3, i)));
	}

	public void tick(LinkedList<Entity> entity) {
		if (pv <= 0)
			this.die();
		
		if (this.testproj() == true && this.monster.getClasse() == MonsterType.DISTANCE){
			if (ticka == 40)
			 Game.getHandlerEntity().getProjectileList().add(new EntityProjectileIA((float)this.getBounds().getCenterX(),(float)this.getBounds().getCenterY(), this.getAtk(),EntityType.EntityProjectileIA, this.projx, this.projy));
		}
		else {
			Noeud n;
		if (!noeuds.isEmpty()) {
			n = noeuds.getFirst();
			if (directionx == 0 && directiony == 0) {
				this.directionx = (n.getCoord().getX() - (float) this.getBoundsBottom().getX());
				this.directiony = (n.getCoord().getY() - (float) this.getBoundsBottom().getY());
				noeuds.removeFirst();
			} else {
				if (directionx != 0) {
					velociteX = maxVelo;
					if (directionx < 0) {
						velociteX = -maxVelo;
						directionx += maxVelo;
					} else
						directionx -= maxVelo;
				} else if (directiony != 0) {
					velociteY = maxVelo;
					if (directiony < 0) {
						velociteY = -maxVelo;
						directiony += maxVelo;
					} else
						directiony -= maxVelo;
				}
				coord.setX(coord.getX() + velociteX);
				coord.setY(coord.getY() + velociteY);
				//
				if (velociteX > 0 && velociteY == 0) {
					setPosition(2, 3);
				} else if (velociteX < 0 && velociteY == 0) {
					setPosition(2, 2);
				} else if (velociteY > 0 && velociteX == 0) {
					setPosition(2, 1);
				} else if (velociteY < 0 && velociteX == 0) {
					setPosition(2, 4);
				}
				//
				velociteX = 0;
				velociteY = 0;
			}
		} else {
			this.noeuds = a.newpath(this, (EntityPlayer) Game.getHandlerEntity().getNincha());
		//	ticka = 0;
		}
		collisionBounds(entity);
		int pos = 2;
		for (int i = 0; i < 4; ++i) {
			if (getPostion() == pos)
				animationMonster.get(i).runAnimation();
			pos += 3;
		}
		}
		if (directionx == 0 && directiony == 0 && ticka >=30) {
			this.noeuds = a.newpath(this, (EntityPlayer) Game.getHandlerEntity().getNincha());
		//	ticka = 0;
		}
		if (ticka == 60)
			ticka = 0;
		else {
			ticka++;
		}
	}

	public void render(Graphics g) {
		if ((directionx != 0 || directiony != 0) && getPostion() == 2)
			animationMonster.get(0).drawAnimation(g, (int) coord.getX(), (int) coord.getY());
		else if ((directionx != 0 || directiony != 0) && getPostion() == 5)
			animationMonster.get(1).drawAnimation(g, (int) coord.getX(), (int) coord.getY());
		else if ((directionx != 0 || directiony != 0) && getPostion() == 8)
			animationMonster.get(2).drawAnimation(g, (int) coord.getX(), (int) coord.getY());
		else if ((directionx != 0 || directiony != 0) && getPostion() == 11)
			animationMonster.get(3).drawAnimation(g, (int) coord.getX(), (int) coord.getY());
		else
			g.drawImage(img, (int) coord.getX(), (int) coord.getY(), null);

		if (this.pv < this.pvInit) {
			if (monster.getType() == MonsterType.BASIC)
				Game.getGuiInGame().renderHudMonster(coord.getX(), coord.getY(), Color.red, this, g);
			if (monster.getType() == MonsterType.MINI_BOSS)
				Game.getGuiInGame().renderHudMonster(coord.getX(), coord.getY(), Color.orange, this, g);
		}
	}

	private void collisionBounds(LinkedList<Entity> object) {
		EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();

		if (getBounds().intersects(nincha.getBoundsZoneDamage())) {
			nincha.setPv(nincha.getPv() - ((monster.getAtk() / 10) * (1 - nincha.getArmor())));
			this.directionx = 0;
			this.directiony = 0;
		}

	}

	/**
	 * Récupère sa vélocité maximal
	 * 
	 * @return
	 */
	public float getVelo() {
		return this.maxVelo;
	}

	public int getPostion() {
		return col + (line - 1) * 3;
	}

	public void setPosition(int col, int line) {
		this.col = col;
		this.line = line;
		img = se.takeSprite(col, line, monster.getIcon().getWidth() / 3, monster.getIcon().getHeight() / 4);
	}

	public BufferedImage getSprite(int col, int line) {
		return se.takeSprite(col, line, monster.getIcon().getWidth() / 3, monster.getIcon().getHeight() / 4);
	}

	public Rectangle getBounds() {
		if (monster.getGlobalBounds() != null)
			return new Rectangle((int) ((int) coord.getX() + monster.getGlobalBounds()[0]),
					(int) ((int) coord.getY() + monster.getGlobalBounds()[1]), width - monster.getGlobalBounds()[2],
					height - monster.getGlobalBounds()[1]);
		else
			return new Rectangle((int) ((int) coord.getX()), (int) coord.getY(), width, height);
	}

	public Rectangle getBoundsBottom() {
		return new Rectangle((int) ((int) coord.getX()), (int) coord.getY() + (int) height / 2, (int) width,
				(int) height / 2);
	}

	public Rectangle getBoundsFloor() {
		return new Rectangle((int) ((int) coord.getX()), (int) coord.getY() + height - (int) height / 10, (int) width,
				(int) height / 10);
	}

	public int pourcentageHP() {
		if (pv <= 0) {
			pv = 0;
		}
		// Impossible de diviser par zéro
		if (pvInit == 0)
			pvInit = 1;
		return (int) ((pv * 100) / pvInit);
	}

	/**
	 * Définit l'inventaire du monstre
	 */
	private void setInventory() {
		Item itemTemp = null;
		for (int i = 0; i < setPiecesToinventoryToDrop(); ++i)
			inventoryToDrop.add(Item.coin);
		if (monster.getType() == MonsterType.BASIC) {
			itemTemp = getItemToBasicMonster();
			if (itemTemp != null)
				inventoryToDrop.add(itemTemp);
			int rand = (int) (Math.random() * 100 + 1);
			if (rand >= 0 && rand <= 16)
				inventoryToDrop.add(Item.potionPoison);
		}
		if (monster.getType() == MonsterType.MINI_BOSS) {
			itemTemp = getItemToMiniBoss();
			if (itemTemp != null)
				inventoryToDrop.add(itemTemp);
			inventoryToDrop.add(lootArtefact());
		}
	}

	/**
	 * Définit le nombre de pièces à faire apparaitre à l'écran, une fois
	 * celui-ci mort.
	 * 
	 * @return un nombre de pièces.
	 */
	private int setPiecesToinventoryToDrop() {
		int pieces = 0;
		if (monster.getType() == MonsterType.BASIC)
			pieces = (int) (Math.random() * monster.getCoinToGive());
		else if (monster.getType() == MonsterType.MINI_BOSS)
			pieces = (int) (15 + Math.random() * monster.getCoinToGive());
		else if (monster.getType() == MonsterType.BOSS)
			pieces = (int) (100 + Math.random() * monster.getCoinToGive());
		return pieces;

	}

	/**
	 * Méthode utilisé dans le cadre d'un monstre commun. Elle permet de lui
	 * définir un objet dans son inventaire.
	 * 
	 * @return un objet.
	 */
	private Item getItemToBasicMonster() {
		int random = (int) (Math.random() * 100 + 1);
		Item itemToLoot = null;
		if (random <= monster.getProbaToLoot()) {
			int randomItem = (int) (Math.random() * 100 + 1);
			if (randomItem >= 0 && randomItem <= 60) {
				itemToLoot = Item.potionVieI;
			} else if (randomItem > 60 && randomItem <= 86) {
				itemToLoot = Item.potionVieII;
			} else if (randomItem > 86 && randomItem <= 93) {
				itemToLoot = Item.potionVieMAX;
			} else {
				itemToLoot = Item.morceauArmure;
			}
		}
		return itemToLoot;

	}

	/**
	 * Méthode utilisé dans le cadre d'un monstre supérieur (mini-boss). Elle
	 * permet de lui définir un objet dans son inventaire.
	 * 
	 * @return un objet (Item).
	 */
	private Item getItemToMiniBoss() {
		int random = (int) (Math.random() * 100 + 1);
		Item itemToLoot = null;
		if (random <= monster.getProbaToLoot()) {
			int randomItem = (int) (Math.random() * 100 + 1);
			if (randomItem >= 0 && randomItem <= 40) {
				itemToLoot = Item.potionVieI;
			} else if (randomItem > 40 && randomItem <= 65) {
				itemToLoot = Item.potionVieII;
			} else if (randomItem > 65 && randomItem <= 75) {
				itemToLoot = Item.potionVieMAX;
			} else {
				itemToLoot = Item.morceauArmure;
			}
		}
		return itemToLoot;

	}

	/**
	 * Méthode utilisé dans le cadre d'un mini-boss
	 * 
	 * @return un artefact (objet Item)
	 */
	private Item lootArtefact() {
		int test = 501 + (int) (Math.random() * 7);
		return Item.itemsList[test];
	}

	public void die() {
		EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();

		if ((nincha.getExp() + this.experienceGive) >= nincha.getExpToLevelUp(nincha.getLevel())) {
			double expTemp = this.experienceGive - (nincha.getExpToLevelUp(nincha.getLevel()) - nincha.getExp());
			nincha.setLevel(nincha.getLevel() + 1);
			nincha.setAtk(11 + (nincha.getLevel() * 2));
			nincha.setExp(expTemp);
		} else {
			nincha.addExp(this.experienceGive);
		}

		if(monster.getType() == MonsterType.BASIC)
			Game.getCompteurStats().addNbrEnnemisBasicTues();
		else if(monster.getType() == MonsterType.MINI_BOSS)
			Game.getCompteurStats().addNbrEnnemisMiniBossTues();
		Game.getHandlerEntity().removeEntity(this);

		for (int i = 0; i < this.inventoryToDrop.size(); ++i) {
			lootItemSecure(i, 1);
		}

	}

	/**
	 * Méthode permettant de faire apparaître un objet (item) sur la carte, plus
	 * précisément sur le sol (floor).
	 * ( /!\ Méthode récursive /!\)
	 * @param i : Emplacement dans l'inventaire du monstre
	 * @param rayon : Pour la méthode récursive, permet d'élargir ses coordonnées
	 */
	private void lootItemSecure(int i, double rayon) {
		int factor = 1;
		int rand = (int) (Math.random() * 100 + 1);

		// System.out.println(rand);
		if (rand > 50) {
			factor = -1;
		}

		SpawningEntity spawnELoot = new SpawningEntity(
				new EntityLoot((float) (coord.getX() + (Math.random() * (7 * rayon)) * factor),
						(float) (coord.getY() + (Math.random() * (7 * rayon)) * factor), EntityType.EntityLoot,
						this.inventoryToDrop.get(i)));

		if (spawnELoot.spawning() != null) {
			++testSpawnInventoryOnFloor;
			Game.getHandlerEntity().addEntityLoot(spawnELoot.spawning());
			//System.out.println("Nbr Test" + testSpawnInventoryOnFloor);
		} else if (spawnELoot.spawning() == null && testSpawnInventoryOnFloor < 250) {
			++testSpawnInventoryOnFloor;
			rayon += 0.1;
			lootItemSecure(i, ++rayon);
		}

	}
	
	private boolean testproj(){
		Rectangle r = new Rectangle((int)this.getBounds().getCenterX()+12, (int)this.getBounds().getCenterY()+12, 24, 24);
		EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();
		this.projx = (float)nincha.getBounds().getCenterX() - (float)this.getBounds().getCenterX();
		this.projy = (float)nincha.getBounds().getCenterY() - (float)this.getBounds().getCenterY();
		float savex = (float)Math.abs((double)projx);
		projx = projx /((float)Math.abs((double)projx)+(float)Math.abs((double)projy));
		projy = projy /(savex+(float)Math.abs((double)projy));
		projx = projx * 5.5F;
		projy = 5.5F * projy;
		
		int j = 0;
		while (r.intersects(nincha.getBounds()) == false && j < 100){
			for (int i = 728; i < Game.getHandlerEntity().getTextureInPremiereCoucheList().size(); i++) {
				Entity tempObject = Game.getHandlerEntity().getTextureInPremiereCoucheList().get(i);
				if (tempObject.getType() == EntityType.EntityObstacle) {
					if (r.intersects(tempObject.getBounds())) {
						return false;
					}
				}
			}
			j++;
			r.setLocation((int)(r.getX()+projx), (int)(r.getY()+projy));
		}
		return true;
	}

	public void setPv(double pv) {
		this.pv = pv;
	}

	public double getPv() {
		return this.pv;
	}

	public double getArmor() {
		return this.armor;
	}

	/**
	 * Retourne l'experience donné par le monstre
	 * @return double
	 */
	public double getExp() {
		return this.experienceGive;
	}

	/**
	 * Retourne le monstre assigné dans le constructeur
	 * @return Monster : Monstre
	 */
	public Monster getMonster() {
		return monster;
	}
}

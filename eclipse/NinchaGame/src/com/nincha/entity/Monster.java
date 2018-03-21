package com.nincha.entity;

import java.awt.image.BufferedImage;

import com.nincha.main.Game;

/**
 * Le Bestiaire des monstres du jeu.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class Monster {
	
	/**
	 * Emplacement du monstre dans le bestiaire (identifiant unique)
	 */
	private int id;
	/**
	 * Le type de monstre (BASIC / MINI_BOSS)
	 */
	private MonsterType type;
	/**
	 * La classe du monstre (CAC / DISTANCE)
	 */
	private MonsterType classe;
	/**
	 * Le nom du monstre (à titre indicatif pour le moment)
	 */
	private String name;
	/**
	 * Le sprite 3x4 du monstre
	 */
	private BufferedImage icon;
	/**
	 * la hitbox personnalisée du monstre 
	 */
	private int[] globalBounds;
	/**
	 * La vitesse de déplacement du monstre
	 */
	private float vitesseDepla = 1.0F;
	/**
	 * Les points de vie de base du monstre
	 */
	private double pv = 0.0;
	/**
	 * L'amure du monstre
	 */
	private double armor = 0.0;
	/**
	 * l'attaque de base du monstre
	 */
	private double atk = 0.0;
	/**
	 * L'expérience de base donné au joueur en cas de mort
	 */
	private double experienceGive = 0.0;
	/**
	 * Le nombre de pièce maximum donné au joueur 
	 */
	private int coinGive = 0;
	/**
	 * La probabilité qu'il donnera un objet en cas de mort
	 */
	private int probaToLoot = 0;
	
	/**
	 * La liste, contenant tout les monstre du jeu, classée en fonction de leur ID
	 */
	public static Monster[] monsterList = new Monster[20];
	
	/**
	 * Créer un monstre avec :
	 * @param id : Identifiant unique
	 * @param type : Le type de monstre
	 */
	protected Monster(int id, MonsterType type){
		this.id = id;
		this.type = type;
		monsterList[id] = this;
	}
	
	/*
	 * On considere ici que le nincha a une force d'attaque infligeant 11 PV a chaque coup de shuriken et
	 * qu'il gagne 2 en force par niveau soit ( 11 + ( 2 x 6 ) ) = 23 pour le niveau 6
	 */ 

	 
	 /*
	 * Tous les monstres du jeu Lv.1 sont répertoriés ici :
	 */
	

	 /*							[	Le SLIME	]
	 * PV = 16 + ( niveau du joueur * 8 ) 
	 * ARMURE = Robuste : 0.25
	 * RAPIDITE = Assez lent : 0.80
	 * ATTAQUE = 1.8 + ( 0.2 x niveau du joueur )
	 * PROBABILITÉ QUE LE MONSTRE LOOT UN OBJET : 0.25
	 * LISTE D'OBJET LOOTABLE : 
	 *  - Potion de vie I : 45%
	 *  - Potion de vie II : 10%
	 *  - Potion de vie Max : 4%
	 *  - Potion de force I : 26%
	 *  - Potion de force II : 10%
	 *  - Pièce d'armure : 5%
	 * NOMBRE D'EXP DONNE = 5 + niveau du joueur 
	 * NOMBRE DE PIECE DONNE = random entre [0 ~ 5]
	 */
	 
	public static Monster slime = (new Monster(0, MonsterType.BASIC))
			.setName("Slime")
			.setIcon(Game.getHandlerSprite().getSpriteEntity(1))
			.setGlobalBounds(0,4,5)
			.setPv(24)
			.setArmor(0.25)
			.setVitesseDeplacement(1F)
			.setAtk(6)
			.setExp(6)
			.setCoin(5)
			.setProbaToLoot(25)
			.setClasse(MonsterType.DISTANCE);
			
	 /*								[	Le Zombie	]
	 * PV = 14 + ( niveau du joueur * 7 ) 
	 * ARMURE = Normale : 0.20
	 * RAPIDITE = Rapide : 1.2
	 * ATTAQUE = 1.9 + ( 0.3 x niveau du joueur )
	 * PROBABILITÉ QUE LE MONSTRE LOOT UN OBJET : 0.18
	 * LISTE D'OBJET LOOTABLE : 
	 *  - Potion de vie I : 40%
	 *  - Potion de vie II : 15%
	 *  - Potion de vie Max : 6%
	 *  - Potion de force I : 23%
	 *  - Potion de force II : 13%
	 *  - Pièce d'armure : 3%
	 * NOMBRE D'EXP DONNE = 3 + niveau du joueur 
	 * NOMBRE DE PIECE DONNE = random entre [0 ~ 4]
	 */
	 
	public static Monster zombie = (new Monster(1, MonsterType.BASIC))
			.setName("Zombie")
			.setIcon(Game.getHandlerSprite().getSpriteEntity(2))
			.setPv(21)
			.setArmor(0.20)
			.setVitesseDeplacement(1.2F)
			.setAtk(2.2)
			.setExp(5)
			.setCoin(4)
			.setProbaToLoot(18)
			.setClasse(MonsterType.CAC);
			
	 /*								[	Le Mage		]
	 * PV = 13.5 + ( niveau du joueur * 6.5 ) 
	 * ARMURE = Tres Faible : 0.01
	 * RAPIDITE = Tres Rapide : 1.6
	 * ATTAQUE = 2.7 + ( 0.3 x niveau du joueur )
	 * PROBABILITÉ QUE LE MONSTRE LOOT UN OBJET : 0.35
	 * LISTE D'OBJET LOOTABLE : 
	 *  - Potion de vie I : 18%
	 *  - Potion de vie II : 7%
	 *  - Potion de vie Max : 2% 
	 *  - Potion de force I : 43%
	 *  - Potion de force II : 28%
	 *  - Pièce d'armure : 2%
	 * NOMBRE D'EXP DONNE = 6 + niveau du joueur  
	 * NOMBRE DE PIECE DONNE = random entre [0 ~ 8]
	 */
	 
	public static Monster mage = (new Monster(2, MonsterType.BASIC))
			.setName("Mage")
			.setIcon(Game.getHandlerSprite().getSpriteEntity(3))
			.setPv(20)
			.setArmor(0.01)
			.setVitesseDeplacement(1F)
			.setAtk(12)
			.setExp(8)
			.setCoin(7)
			.setProbaToLoot(35)
			.setClasse(MonsterType.DISTANCE);
			
	 /*								[	La Souris   	]
	 * PV = 5 + ( niveau du joueur * 4.2 ) 
	 * ARMURE = Faible : 0.05
	 * RAPIDITE = Tres Rapide : 1.6
	 * ATTAQUE = 1.3 + ( 0.25 x niveau du joueur )
	 * PROBABILITÉ QUE LE MONSTRE LOOT UN OBJET : 0.30
	 * LISTE D'OBJET LOOTABLE : 
	 *  - Potion de vie I : 85%
	 *  - Potion de vie II : 8%
	 *  - Potion de vie Max : 0% 
	 *  - Potion de force I : 0%
	 *  - Potion de force II : 0%
	 *  - Pièce d'armure : 7%
	 * NOMBRE D'EXP DONNE = 2 + niveau du joueur  
	 * NOMBRE DE PIECE DONNE = random entre [0 ~ 2]
	 */
	 
	public static Monster souris = (new Monster(3, MonsterType.BASIC))
			.setName("Souris")
			.setIcon(Game.getHandlerSprite().getSpriteEntity(4))
			.setPv(17.2)
			.setArmor(0.05)
			.setVitesseDeplacement(1F)
			.setAtk(1.55)
			.setExp(3)
			.setCoin(2)
			.setProbaToLoot(30)
			.setClasse(MonsterType.CAC);
	
	 /*								[	Le Goblin   	]
	 * PV = 12 + ( niveau du joueur * 7.0 ) 
	 * ARMURE = Normal : 0.10
	 * RAPIDITE = Normal : 1
	 * ATTAQUE = 1.4 + ( 0.25 x niveau du joueur )
	 * PROBABILITÉ QUE LE MONSTRE LOOT UN OBJET : 0.65
	 * LISTE D'OBJET LOOTABLE : 
	 *  - Potion de vie I : 25%
	 *  - Potion de vie II : 20%
	 *  - Potion de vie Max : 15% 
	 *  - Potion de force I : 20%
	 *  - Potion de force II : 15%
	 *  - Pièce d'armure : 5%
	 * NOMBRE D'EXP DONNE = 4 + niveau du joueur  
	 * NOMBRE DE PIECE DONNE = random entre [0 ~ 6]
	 */
	 
	public static Monster goblin = (new Monster(4, MonsterType.BASIC))
			.setName("Le Goblin")
			.setIcon(Game.getHandlerSprite().getSpriteEntity(5))
			.setPv(19)
			.setArmor(0.10)
			.setVitesseDeplacement(1.4F)
			.setAtk(1.65)
			.setExp(5)
			.setCoin(6)
			.setProbaToLoot(65)
			.setClasse(MonsterType.CAC);
			
	 /*								[	Le Guerrier Maudit   	]
	 * PV = 75 + ( niveau du joueur * 9.5 ) 
	 * ARMURE = Colosse : 0.50
	 * RAPIDITE = Trés lent : 0.5
	 * ATTAQUE = 4.0 + ( 0.35 x niveau du joueur )
	 * PROBABILITÉ QUE LE MONSTRE LOOT UN OBJET : 0.70
	 * LISTE D'OBJET LOOTABLE : 
	 *  - Potion de vie I : 0%
	 *  - Potion de vie II : 10%
	 *  - Potion de vie Max : 15% 
	 *  - Potion de force I : 15%
	 *  - Potion de force II : 10%
	 *  - Pièce d'armure : 50%
	 * NOMBRE D'EXP DONNE = 11 + niveau du joueur  
	 * NOMBRE DE PIECE DONNE = random entre [0 ~ 10]
	 */
	 
	public static Monster war = (new Monster(5, MonsterType.BASIC))
			.setName("Le Guerrier Maudit")
			.setIcon(Game.getHandlerSprite().getSpriteEntity(6))
			.setPv(84.5)
			.setArmor(0.50)
			.setVitesseDeplacement(0.5F)
			.setAtk(4.0)
			.setExp(12)
			.setCoin(10)
			.setProbaToLoot(70)
			.setClasse(MonsterType.CAC);
			
	 /*								[	Le PIG MAN Mini-Boss   	]
	 * PV = 412 + ( niveau du joueur * 12 ) 
	 * ARMURE = Robuste : 0.250
	 * RAPIDITE = Assez Rapide : 1.55
	 * ATTAQUE = 8.0 + ( 0.45 x niveau du joueur )
	 * PROBABILITÉ QUE LE MONSTRE LOOT UN OBJET : 100%
	 * LISTE D'OBJET LOOTABLE : 
	 *  - Potion de vie I : 0%
	 *  - Potion de vie II : 15%
	 *  - Potion de vie Max : 35% 
	 *  - Potion de force I : 15%
	 *  - Potion de force II : 10%
	 *  - Pièce d'armure : 25%
	 * NOMBRE D'EXP DONNE = 25 + niveau du joueur  
	 * NOMBRE DE PIECE DONNE = random entre [15 ~ 40]
	 */
	 
	public static Monster pigman = (new Monster(6, MonsterType.MINI_BOSS))
			.setName("Le Pig Man")
			.setIcon(Game.getHandlerSprite().getSpriteEntity(7))
			.setPv(424)
			.setArmor(0.25)
			.setVitesseDeplacement(1.1F)
			.setAtk(20)
			.setExp(26)
			.setCoin(25)
			.setProbaToLoot(100)
			.setClasse(MonsterType.DISTANCE);
			
	 /*								[	Le SHARK MAN Mini-Boss   	]
	 * PV = 642 + ( niveau du joueur * 10 ) 
	 * ARMURE = Très Robuste (Peau de requin) : 0.42
	 * RAPIDITE = Rapide : 2
	 * ATTAQUE = 9.5 + ( 0.52 x niveau du joueur )
	 * PROBABILITÉ QUE LE MONSTRE LOOT UN OBJET : 100%
	 * LISTE D'OBJET LOOTABLE : 
	 *  - Potion de vie I : 0%
	 *  - Potion de vie II : 10%
	 *  - Potion de vie Max : 25% 
	 *  - Potion de force I : 15%
	 *  - Potion de force II : 20%
	 *  - Pièce d'armure : 35%
	 * NOMBRE D'EXP DONNE = 25 + niveau du joueur  
	 * NOMBRE DE PIECE DONNE = random entre [15 ~ 40]
	 */
	 
	public static Monster sharkman = (new Monster(7, MonsterType.MINI_BOSS))
			.setName("Le Shark Man")
			.setIcon(Game.getHandlerSprite().getSpriteEntity(8))
			.setPv(652)
			.setArmor(0.42)
			.setVitesseDeplacement(2F)
			.setAtk(5.85)
			.setExp(26)
			.setCoin(25)
			.setProbaToLoot(100)
			.setClasse(MonsterType.CAC);
	/*
	 * Fin du bestiaire
	 */
	
	public Monster setType(MonsterType type) {
		this.type = type;
		return this;
	}
	
	public Monster setClasse(MonsterType classe) {
		this.classe = classe;
		return this;
	}
	
	public Monster setIcon(BufferedImage icon) {
		this.icon = icon;
		return this;
	}
	
	public Monster setName(String name) {
		this.name = name;
		return this;
	}
	
	public Monster setGlobalBounds(int width, int height, int widthEnd) {
		this.globalBounds = new int[3];
		this.globalBounds[0] = width;
		this.globalBounds[1] = height;
		this.globalBounds[1] = widthEnd;
		return this;
	}
	
	public Monster setPv(double pv) {
		this.pv = pv;
		return this;
	}
	
	public Monster setVitesseDeplacement(float vit) {
		this.vitesseDepla = vit;
		return this;
	}
	
	public Monster setAtk(double atk) {
		this.atk = atk;
		return this;
	}
	
	public Monster setArmor(double armor) {
		this.armor = armor;
		return this;
	}
	
	public Monster setExp(double exp) {
		this.experienceGive = exp;
		return this;
	}
	
	public Monster setCoin(int Nbrcoin) {
		this.coinGive = Nbrcoin;
		return this;
	}
	
	public Monster setProbaToLoot(int proba) {
		this.probaToLoot = proba;
		return this;
	}

	public int getId() {
		return id;
	}
	
	public MonsterType getType() {
		return type;
	}
	
	public MonsterType getClasse() {
		return classe;
	}
	
	public BufferedImage getIcon() {
		return icon;
	}
	
	public String getName() {
		return name;
	}
	
	public int[] getGlobalBounds() {
		return globalBounds;
	}
	
	public double getPv() {
		return pv;
	}
	
	public float getVitesse() {
		return vitesseDepla;
	}
	
	public double getAtk() {
		return atk;
	}
	
	public double getArmor() {
		return armor;
	}
	
	public double getExp() {
		return experienceGive;
	}
	
	public double getCoinToGive() {
		return coinGive;
	}
	
	public double getProbaToLoot() {
		return probaToLoot;
	}
}

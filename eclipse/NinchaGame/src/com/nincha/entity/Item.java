package com.nincha.entity;

import java.awt.image.BufferedImage;

import com.nincha.main.Game;

/**
 * Le Bestiaire des objets du jeu
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class Item {
	
	/**
	 * Emplacement dans la hiérarchie du bestaire (Identifiant unique)
	 */
	private int id;
	/**
	 * Effet de l'objet en question sur le personnage ou sur l'environnement
	 */
	private double effects;
	/**
	 * La catégorie de l'objet
	 */
	private ItemType type;
	/**
	 * Son nom
	 */
	private String name;
	/**
	 * Sa desccription (inutilisé dans cette version)
	 */
	private String description = "";
	/**
	 * Sa valeur dans la boutique (inutilisé dans cette version)
	 */
	private double valeur = -1;
	/**
	 * Le sprite lui correspondant (render sur la map)
	 */
	private BufferedImage icon;
	
	/**
	 * Liste de l'ensemble des objets du bestaire classée par ordre d'ID
	 */
	public static Item[] itemsList = new Item[550];
	
	/**
	 * Définit un objet dont :
	 * @param id : Emplacement dans le bestaire (identifiant unique)
	 * @param type : Le type d'objet auquel il appartient
	 */
	protected Item(int id, ItemType type){
		this.id = id;
		this.type = type;
		itemsList[id] = this;
	}

	/*
	 * Tous les objets du jeu sont répertoriés ici
	 */
	
	public static Item coin = (new Item(0, ItemType.BASIC))
			.setName("Pièce")
			.setValeur(1)
			.setIcon(Game.getHandlerSprite().getItemSprite(0));
	
	public static Item morceauArmure = (new Item(1, ItemType.BASIC))
			.setName("Morceau d'Armure")
			.setIcon(Game.getHandlerSprite().getItemSprite(1));
	
	public static Item potionPoison = (new Item(2, ItemType.BASIC))
			.setName("Potion de Poison")
			.setIcon(Game.getHandlerSprite().getItemSprite(2));
	
	//OBJETS BASICS - ID commence à partir de 100
	
	public static Item potionVieI = (new Item(100, ItemType.POTION_HEAL))
			.setName("Potion Vie Lv. 1")
			.setValeur(5)
			.setEffects(25)
			.setIcon(Game.getHandlerSprite().getItemSprite(100));
	
	//PROCHAINE MISE A JOUR
	/*public static Item potionStrengthI = (new Item(101, ItemType.POTION_STRONG))
			.setName("Potion Force Lv. 1")
			.setValeur(10)
			.setEffects(15);
			//.setIcon(Game.getHandlerSprite().getItemSprite(11));*/
	
	//OBJETS INTERMEDIAIRES - ID commence à partir de 200
	
	public static Item potionVieII = (new Item(200, ItemType.POTION_HEAL))
			.setName("Potion Vie Lv. 2")
			.setValeur(10)
			.setEffects(50)
			.setIcon(Game.getHandlerSprite().getItemSprite(200));
	
	//PROCHAINE MISE A JOUR
	/*public static Item potionStrengthII = (new Item(201, ItemType.POTION_STRONG))
			.setName("Potion Force Lv. 2")
			.setValeur(20);
			//.setIcon(Game.getHandlerSprite().getItemSprite(12));*/
	
	//OBJETS ULTIMES - ID commence à partir de 300
	
	public static Item potionVieMAX = (new Item(300, ItemType.POTION_HEAL))
			.setName("Potion Vie Max")
			.setValeur(20)
			.setEffects(100)
			.setIcon(Game.getHandlerSprite().getItemSprite(300));
	
	//OBJETS DE QUETES (Artefacts) - ID commence à partir de 501
	
	public static Item peluche = (new Item(501, ItemType.ARTEFACT))
			.setName("[Artefact n°1] Peluche")
			.setDescription("Peluche ayant appartenu au terrible Boss de ce donjon !")
			.setIcon(Game.getHandlerSprite().getItemSprite(501));
	
	public static Item livreAncestral = (new Item(502, ItemType.ARTEFACT))
			.setName("[Artefact n°2] Livre Ancestral")
			.setDescription("Livre contenant l'incantation pour l'ouverture de la porte du Boss.")
			.setIcon(Game.getHandlerSprite().getItemSprite(502));
	
	public static Item clefUniversel = (new Item(503, ItemType.ARTEFACT))
			.setName("[Artefact n°3] Clef Universel")
			.setDescription("Possède un pouvoir encore inconnu par les mortel.")
			.setIcon(Game.getHandlerSprite().getItemSprite(503));
	
	public static Item oeilDuSerpent = (new Item(504, ItemType.ARTEFACT))
			.setName("[Artefact n°4] Oeil du Serpent")
			.setDescription("Artefact permettant d'enlever l'immunité au boss et ainsi lui infliger des dégats.")
			.setIcon(Game.getHandlerSprite().getItemSprite(504));
	
	public static Item calisseHarmonie = (new Item(505, ItemType.ARTEFACT))
			.setName("[Artefact n°5] Câlisse d'Harmonie")
			.setDescription("Possède un pouvoir encore inconnu par les mortel.")
			.setIcon(Game.getHandlerSprite().getItemSprite(505));
	
	public static Item collierMagique = (new Item(506, ItemType.ARTEFACT))
			.setName("[Artefact n°6] Collier Magique")
			.setDescription("Possède un pouvoir encore inconnu par les mortel.")
			.setIcon(Game.getHandlerSprite().getItemSprite(506));
	
	public static Item masqueVaudou = (new Item(507, ItemType.ARTEFACT))
			.setName("[Artefact n°7] Masque Vaudou")
			.setDescription("Possède un pouvoir encore inconnu par les mortel.")
			.setIcon(Game.getHandlerSprite().getItemSprite(507));
	
	/*
	 * Fin du bestiaire
	 */
	
	public Item setIcon(ItemType type) {
		this.type = type;
		return this;
	}
	
	public Item setType(ItemType type) {
		this.type = type;
		return this;
	}
	
	public Item setValeur(double valeur) {
		this.valeur = valeur;
		return this;
	}
	
	public Item setIcon(BufferedImage icon) {
		this.icon = icon;
		return this;
	}
	
	public Item setName(String name) {
		this.name = name;
		return this;
	}
	
	public Item setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public Item setEffects(double effects) {
		this.effects = effects;
		return this;
	}
	
	public int getId() {
		return id;
	}
	
	public ItemType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getValeur() {
		return valeur;
	}
	
	public BufferedImage getIcon() {
		return icon;
	}
	
	/**
	 * Largeur du sprite
	 * @return Récupère la largeur du sprite
	 */
	public int getWidth() {
		return getIcon().getWidth();
	}
	
	/**
	 * Longueur du sprite
	 * @return Récupère la longueur du sprite
	 */
	public int getHeight() {
		return getIcon().getHeight();
	}
	
	public double getEffects() {
		return effects;
	}
}

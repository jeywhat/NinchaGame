package com.nincha.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.nincha.entity.EntityMob;
import com.nincha.entity.EntityPlayer;
import com.nincha.entity.Item;
import com.nincha.generation.GenerationMap;
import com.nincha.main.Game;
import com.nincha.utils.BufferedTextureLoader;

/**
 * GuiInGame est l'interface du jeu.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class GuiInGame {

	private EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();
	private LinkedList<BufferedImage> hud_health = new LinkedList<BufferedImage>();
	private BufferedImage hud_monster;
	private BufferedImage hud_artefact;
	private BufferedImage bouton_sortir_donjon;
	
	/**
	 * Font utilisé pour les grosses informations
	 */
	private Font fontG = Game.getFontLoading().getFont(0);
	/**
	 * Font utilisé pour les information en tout petit
	 */
	private Font fontM = Game.getFontLoading().getFont(1);
	
	private int nbrArtefactColected = 0;
	private boolean artefactsCollected = false;
	
	/**
	 * Créer l'interface du jeu
	 */
	public GuiInGame(){
		interfaceImg();
	}
	
	/**
	 * S'occupe d'afficher ce qui doit l'être.
	 * 
	 * @param g : Récupère l'endroit où il faut dessiner.
	 */
	public void render(Graphics g){
		hud(g);
		levelExp(g);
		afficherStatistiques(g);
		locationDonjon(g);
		artefactCollected(g);
	}
	
	/**
	 * Affiche le HUD (l'interface de barre de vie/expérience) - EN HAUT A GAUCHE
	 * 
	 * @param g : Récupère l'endroit où il faut dessiner.
	 */
	
	private void hud(Graphics g){
		int pourcentageVie = nincha.pourcentageHP();
		if(pourcentageVie >=60)
			etatHud(Color.green, pourcentageVie, 0, g);
		else if(pourcentageVie >=30 && pourcentageVie < 60)
			etatHud(Color.orange, pourcentageVie, 1, g);
		else
			etatHud(Color.red, pourcentageVie, 2, g);
	}
	
	/**
	 * Gère le HUD (l'interface de barre de vie/expérience) à afficher en fonction des points de vie.
	 * 
	 * @param c : Défini la couleur de la barre de vie.
	 * @param pourcentageVie : Récupère le pourcentage de vie du Nincha (Joueur Principal)
	 * @param etat : 100% Vie => Etat : 0; 60% Vie => Etat : 1; 30% Vie => Etat : 2.
	 * @param g : Récupère l'endroit où il faut dessiner.
	 */
	private void etatHud(Color c, int pourcentageVie, int etat, Graphics g){
		g.drawImage(hud_health.get(etat), 0, 0, null);
		g.setColor(c);
		g.fillRect(87, 22, (243*pourcentageVie)/100, 14);
		
		Color grey = new Color(48,48,48);
		g.setColor(grey);
		g.setFont(fontM.deriveFont(8F));
		String pourcentageHUD = pourcentageVie+"%";
		int widthPourcentage = g.getFontMetrics().stringWidth(pourcentageHUD);
		g.drawString(pourcentageVie+"%", hud_health.get(etat).getWidth()/2 - widthPourcentage  + 40, 38);
	}
	
	/**
	 * Affiche l'expérience obtenu à l'aide d'une barre progressive (en cyan)
	 * 
	 * @param g : Récupère l'endroit où il faut dessiner.
	 */
	private void levelExp(Graphics g){
		int pourcentageExp = (int) ((nincha.getExp() * 100)/nincha.getExpToLevelUp(nincha.getLevel()));
		
		int pourcentageBar = (172*pourcentageExp)/100;
		
		g.setColor(Color.cyan);
		g.fillRect(104, 42,pourcentageBar, 4);
	}
	
	/**
	 * Affiche les statistiques du joueur (Niveau) 
	 * 
	 * @param g : Récupère l'endroit où il faut dessiner.
	 */
	private void afficherStatistiques(Graphics g){
		//LE NIVEAU DU PERSONNAGE
		g.setFont(fontG.deriveFont(22F));
		g.setColor(Color.white);
		String levelText = "Niv. "+nincha.getLevel();
		g.drawString(levelText, 105, 68);
	}
	
	/**
	 * Affiche notre localisation dans le donjon (Salle(s)/Etage(s)) - EN HAUT A DROITE
	 * 
	 * @param g : Récupère l'endroit où il faut dessiner.
	 */
	
	private void locationDonjon(Graphics g){
		g.setFont(fontG.deriveFont(40F));
		g.setColor(Color.white);
		String levelText = "SALLE "+ GenerationMap.getNumSalle() +" | ETAGE "+ GenerationMap.getNumEtage();
		g.drawString(levelText, 610, 54);
	}
	
	/**
	 * Affiche les artefacts collectés - EN BAS A GAUCHE
	 * 
	 * @param g : Récupère l'endroit où il faut dessiner.
	 */
	
	private void artefactCollected(Graphics g){
		g.drawImage(hud_artefact, 0, 710, null);
		nbrArtefactColected = 0;
		int y = 0;
		for(int i = 1; i <= 7; ++i){
			g.drawImage(ifArtefactCollected(i), 15+y, 739, null);
			y += 39;
		}
		if(nbrArtefactColected >= 7){
			artefactsCollected = true;
			g.drawImage(bouton_sortir_donjon, 317, 735, null);
		}
	}
	
	/**
	 * Vérifie que l'on possède l'artefact ciblé dans notre inventaire.
	 * @param i : ID de l'artefact ciblé
	 * @return : l'image de l'artefact ou sinon retourne l'image de l'artefact inconnu.
	 */
	private BufferedImage ifArtefactCollected(int i){
		EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();
		boolean find = false;
		BufferedImage icon = null;
		for (int j = 0; j < nincha.getInventory().size() && !find; j++) {
			Item tempObject = nincha.getInventory().get(j);
			if(tempObject.getId() == 500+i){
				icon = Game.getHandlerSprite().getItemSprite(500+i);
				nbrArtefactColected++;
				find = true;
			}
		}
		if(!find)
			icon = Game.getHandlerSprite().getItemSprite(500);
		return icon;
	}
	
	/**
	 * Charge les images utilisé par l'interface du jeu (HUD)
	 */
	private void interfaceImg(){
		BufferedTextureLoader bft = new BufferedTextureLoader();
		File file;
		try{
			file = new File("ressources/hud/health_bar_100.png");
			this.hud_health.add(bft.loadTexture(file));
			file = new File("ressources/hud/health_bar_60.png");
			this.hud_health.add(bft.loadTexture(file));
			file = new File("ressources/hud/health_bar_30.png");
			this.hud_health.add(bft.loadTexture(file));
			file = new File("ressources/hud/hud_ennemi.png");
			this.hud_monster = bft.loadTexture(file);
			file = new File("ressources/hud/hud_artefact.png");
			this.hud_artefact = bft.loadTexture(file);			
			file = new File("ressources/hud/bouton_sortir_donjon.png");
			this.bouton_sortir_donjon = bft.loadTexture(file);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	

	//FONCTION EXTERNE :
	/**
	 * Affiche une barre de vie au dessus des monstres afin de voir les points de vie restants.
	 * 
	 * @param x : Coordonnée x du monstre.
	 * @param y : Coordonnée y du monstre.
	 * @param c : Couleur de la barre de vie.
	 * @param e : L'entity monstre ciblé.
	 * @param g : Récupère l'endroit où il faut dessiner.
	 */
	public void renderHudMonster(float x, float y, Color c, EntityMob e, Graphics g){
		g.drawImage(hud_monster, (int)(x+((e.getMonster().getIcon().getWidth()/3)/2 - hud_monster.getWidth()/2)), (int)(y-8), null);
		g.setColor(c);
		g.fillRect((int)(x+2+((e.getMonster().getIcon().getWidth()/3)/2 - hud_monster.getWidth()/2)), (int)(y-7), (28*e.pourcentageHP())/100, 3);
	}
	
	/**
	 * Est-ce que l'objectif du jeu a été rempli ?
	 * Si les artefacts ont tous été collectés = vrai; sinon = faux
	 * @return : boolean
	 */
	public boolean isArtefactCollected() {
		return artefactsCollected;
	}
	
	public int getArtefactCollected() {
		return this.nbrArtefactColected;
	}
	
}

package com.nincha.generation;

import java.util.LinkedList;

import com.nincha.entity.EntityDoor;
import com.nincha.entity.EntityMob;
import com.nincha.entity.EntityObjectTerrain;
import com.nincha.entity.EntityPlayer;
import com.nincha.entity.EntityType;
import com.nincha.entity.Monster;
import com.nincha.entity.MonsterType;
import com.nincha.main.Game;
import com.nincha.utils.HandlerEntity;

/**
 * La Génération de la carte du jeu avec la génération des monstres, ce fait ici.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class GenerationMap {
	
	private HandlerEntity handlerE;
	/**
	 * Nombre de test récursif effectué.
	 */
	private int test = 0;
	private int testOb = 0;
	private int nbrMob = 0;
	/**
	 * Densité des obstacles présent sur la carte
	 */
	private double density = 0.1;
	
	/**
	 * La salle de départ
	 */
	private static int numSalle = 0;
	/**
	 * L'étage de départ
	 */
	private static int numEtage = 1;
	
	private LinkedList<Monster> monsterBasic = new LinkedList<Monster>();
	private LinkedList<Monster> monsterMiniBoss = new LinkedList<Monster>();
	
	/**
	 * Génère la carte du jeu
	 */
	public GenerationMap(){
		numSalle++;
		if(numSalle > 5){
			numSalle = 1;
			numEtage++;
		}
		handlerE = Game.getHandlerEntity();
		genePremiereCouche();
		geneSecondeCouche();
		geneGates();
		geneObstacle();
		setCategorieMonster();
		updateMob();
		for(int i=0; i<(2+numSalle);++i){
			generateMonster(monsterBasic);
		}
		if(numSalle == 5){
			generateMonster(monsterMiniBoss);
		}
	}
	
	/**
	 * Génère la première couche (Terrain) à une position x et y précise
	 */
	private void genePremiereCouche(){
		//1ere Couche
		for(int i =0; i<28; ++i){
			for(int j=0; j<26; ++j){
				if((i >= 0 && i < 28) && (j >= 0 && j <= 3)){
					handlerE.getTextureInPremiereCoucheList().add(new EntityObjectTerrain(i*32+69, j*24+80, EntityType.EntityWall));
					
				}else
					handlerE.getTextureInPremiereCoucheList().add(new EntityObjectTerrain(i*32+69, j*24+80, EntityType.EntityFloor));
			}
		}
	}
	
	/**
	 * Génère la seconde couche de texture (Terrain) à une position x et y précise
	 */
	private void geneSecondeCouche(){
		//2eme Couche
		for(int i =0; i<28; ++i){
			for(int j=0; j<26; ++j){
				if(i == 0 && j == 0){
					handlerE.getTextureInSecondeCoucheList().add(new EntityObjectTerrain(i*32+69, j*24+80, EntityType.EntityCornerTopG));
				}else if(i == 27 && j == 0){
					handlerE.getTextureInSecondeCoucheList().add(new EntityObjectTerrain(i*32+69, j*24+80, EntityType.EntityCornerTopD));
				}else if(i == 0 && j == 25){
					handlerE.getTextureInSecondeCoucheList().add(new EntityObjectTerrain(i*32+69, j*24+80, EntityType.EntityCornerBotG));
				}else if(i == 27 && j == 25){
					handlerE.getTextureInSecondeCoucheList().add(new EntityObjectTerrain(i*32+69, j*24+80, EntityType.EntityCornerBotD));
				}else if(i == 0 && (j >= 1 && j <= 25)){
					handlerE.getTextureInSecondeCoucheList().add(new EntityObjectTerrain(i*32+69, j*24+80, EntityType.EntityWallLeft));
				}else if(i == 27 && (j >= 1 && j <= 25)){
					handlerE.getTextureInSecondeCoucheList().add(new EntityObjectTerrain(i*32+69, j*24+80, EntityType.EntityWallRight));
				}else if((i >= 1 && i <= 26) && j == 0){
					handlerE.getTextureInSecondeCoucheList().add(new EntityObjectTerrain(i*32+69, j*24+80, EntityType.EntityWallTop));
				}else if((i >= 1 && i <= 26) && j == 25){
					handlerE.getTextureInSecondeCoucheList().add(new EntityObjectTerrain(i*32+69, j*24+80, EntityType.EntityWallBottom));
				}
			}
		}
	}
	
	/**
	 * Génère les portes à une position x et y précise
	 */
	private void geneGates(){
		handlerE.getTextureInPremiereCoucheList().add(new EntityDoor(32*4+69, 100, EntityType.EntityDoor, false));
		handlerE.getTextureInPremiereCoucheList().add(new EntityDoor(32*22+69, 100, EntityType.EntityDoor, true));
	}
	
	/**
	 * Génère les obstacles en quantités contrôlable et à une position x et y précise
	 * (/!\ Méthode Récursive /!\)
	 */
	private void geneObstacle() {
		int den = (int) (415 * this.density);
		/*while (den > 0){
			SpawningEntity spawnE = new SpawningEntity(new EntityObjectTerrain((float)((int)(Math.random() * 22.0 + 3.0) * 32 + 69), (float)((int)(Math.random() * 16.0 + 6.0) * 24 + 80), EntityType.EntityObstacle));
			if (spawnE.spawning() != null) {
				handlerE.getTextureInPremiereCoucheList().add(spawnE.spawning());
				++testOb;
				den--;
				//System.out.println("Nbr Test" + testOb);
			}else if(spawnE.spawning() == null && testOb < 450){
				++testOb;
			}else{
				den = -1;
			}
		}*/
				
	}
	
	/**
	 * Sépare dans 2 listes distincts les monstres BASIC / MINI_BOSS 
	 */
	private void setCategorieMonster(){
		for(int i = 0; i < Monster.monsterList.length; ++i){
			if(Monster.monsterList[i] != null && Monster.monsterList[i].getType() == MonsterType.BASIC){
				monsterBasic.add(Monster.monsterList[i]);
			}
			if(Monster.monsterList[i] != null && Monster.monsterList[i].getType() == MonsterType.MINI_BOSS){
				monsterMiniBoss.add(Monster.monsterList[i]);
			}
		}
	}
	
	/**
	 * Génère un monstre
	 * (/!\ Méthode Récursive /!\)
	 * @param listMonster : Récupère la liste des monstres (soit BASIC, soit MINI-BOSS)
	 */
	private void generateMonster(LinkedList<Monster> listMonster){
		int randomMonster = (int) (Math.random()*listMonster.size());
		SpawningEntity spawnE = new SpawningEntity(new EntityMob((int) (Math.random() * 1024+ 400),
				(int) (Math.random() * 768 +300 ), EntityType.EntityMonster, listMonster.get(randomMonster)));
		if (spawnE.spawning() != null) {
			++nbrMob;
			//System.out.println("Nombre Mob Spawn : " + nbrMob);
			handlerE.addEntity(spawnE.spawning());
			++test;
			//System.out.println("Nbr Test" + test);
		}else if(spawnE.spawning() == null && test < 8000){
			generateMonster(listMonster);
			++test;
		}
	}
	
	/**
	 * Level Design :
	 * Methode permettant une mise à jour des monstres (Point de vie/ Attaque / Expérience donnée) 
	 */
	private void updateMob(){
		EntityPlayer nincha = (EntityPlayer)(Game.getHandlerEntity().getNincha());
		//Slime
		Monster.slime.setPv(16+(nincha.getLevel()*8));
		Monster.slime.setAtk(1.8+(nincha.getLevel()*0.2));
		Monster.slime.setExp((nincha.getLevel() + 5));
		//Le Zombie
		Monster.zombie.setPv(14+(nincha.getLevel()*7));
		Monster.zombie.setAtk(1.9+(nincha.getLevel()*0.3));
		Monster.zombie.setExp((nincha.getLevel() + 4));
		//Mage
		Monster.mage.setPv(13.5+(nincha.getLevel()*6.5));
		Monster.mage.setAtk(2.7+(nincha.getLevel()*0.3));
		Monster.mage.setExp((nincha.getLevel() + 7));
		//Souris/Rat
		Monster.souris.setPv(5+(nincha.getLevel()*4.2));
		Monster.souris.setAtk(1.3+(nincha.getLevel()*0.25));
		Monster.souris.setExp((nincha.getLevel() + 2));
		//Goblin
		Monster.goblin.setPv(12+(nincha.getLevel()*7));
		Monster.goblin.setAtk(1.4+(nincha.getLevel()*0.25));
		Monster.goblin.setExp((nincha.getLevel() + 4));
		//Le Guerrier Maudit
		Monster.war.setPv(75+(nincha.getLevel()*9.5));
		Monster.war.setAtk(4.0+(nincha.getLevel()*0.35));
		Monster.war.setExp((nincha.getLevel() + 11));
		//Le PigMan - Mini-Boss
		Monster.pigman.setPv(312+(nincha.getLevel()*12));
		Monster.pigman.setAtk(8.0+(nincha.getLevel()*0.45));
		Monster.pigman.setExp((nincha.getLevel() + 25));
		//Le SharkMan - Mini-Boss
		Monster.sharkman.setPv(265+(nincha.getLevel()*10));
		Monster.sharkman.setAtk(9.5+(nincha.getLevel()*0.52));
		Monster.sharkman.setExp((nincha.getLevel() + 25));
	}

	/**
	 * Récupère le numéro de la salle
	 * @return int
	 */
	public static int getNumSalle() {
		return numSalle;
	}

	/**
	 * Définit le numéro de la salle
	 * @param int
	 */
	public static void setNumSalle(int numSalle) {
		GenerationMap.numSalle = numSalle;
	}

	/**
	 * Récupère le numero de l'étage
	 * @return int
	 */
	public static int getNumEtage() {
		return numEtage;
	}
	
	/**
	 * Définit le numéro de l'étage actuel
	 * @param int
	 */
	public static void setNumEtage(int numEtage) {
		GenerationMap.numEtage = numEtage;
	}
	
}

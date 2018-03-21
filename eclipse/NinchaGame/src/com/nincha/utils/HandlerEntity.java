package com.nincha.utils;

import java.awt.Graphics;
import java.util.LinkedList;

import com.nincha.entity.Entity;

/**
 * HandlerEntity comme son nom l'indique sert de contenir toutes les entity du jeu.
 * Sous formes de différentes listes (Plusieurs couches dans le render)
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class HandlerEntity {

	//PERSONNAGE PRINCIPAL
	private LinkedList<Entity> ninchaEntity = new LinkedList<Entity>();
	
	//LISTE DES MONSTRES
	private LinkedList<Entity> entityList = new LinkedList<Entity>();
	
	//LISTE DES LOOTS
	private LinkedList<Entity> entityLootList = new LinkedList<Entity>();
	
	//LISTES DES TEXTURES DU TERRAIN GENERE
	private LinkedList<Entity> terrainPremiereCoucheList = new LinkedList<Entity>();
	private LinkedList<Entity> terrainSecondeCoucheList = new LinkedList<Entity>();
	
	//LISTE DES PROJECTILES TEMPORAIRES
	private LinkedList<Entity> projectileList = new LinkedList<Entity>();
	
	private Entity entityTemp;
	
	/**
	 * Execute tous les tick cette methode
	 * Methode qui sert au différents calculs entre les entity. 
	 */
	public void tick(){
		for(int i = 0; i < terrainPremiereCoucheList.size(); ++i){
			entityTemp = terrainPremiereCoucheList.get(i);
			entityTemp.tick(terrainPremiereCoucheList);
		}
		for(int i = 0; i < entityLootList.size(); ++i){
			entityTemp = entityLootList.get(i);
			entityTemp.tick(entityLootList);
		}
		for(int i = 0; i < projectileList.size(); ++i){
			entityTemp = projectileList.get(i);
			entityTemp.tick(projectileList);
		}
		for(int i = 0; i < entityList.size(); ++i){
			entityTemp = entityList.get(i);
			entityTemp.tick(entityList);
		}
		for(int i = 0; i < ninchaEntity.size(); ++i){
			entityTemp = ninchaEntity.get(i);
			entityTemp.tick(ninchaEntity);
		}
		for(int i = 0; i < terrainSecondeCoucheList.size(); ++i){
			entityTemp = terrainSecondeCoucheList.get(i);
			entityTemp.tick(terrainSecondeCoucheList);
		}
	}
	
	/**
	 * Execute tous les X FPS (IPS) cette methode
	 * @param g : Graphics
	 */
	public void render(Graphics g){
		for(int i = 0; i < terrainPremiereCoucheList.size(); ++i){
			entityTemp = terrainPremiereCoucheList.get(i);
			entityTemp.render(g);
		}
		for(int i = 0; i < entityLootList.size(); ++i){
			entityTemp = entityLootList.get(i);
			entityTemp.render(g);
		}
		for(int i = 0; i < projectileList.size(); ++i){
			entityTemp = projectileList.get(i);
			entityTemp.render(g);
		}
		for(int i = 0; i < entityList.size(); ++i){
			entityTemp = entityList.get(i);
			entityTemp.render(g);
		}
		for(int i = 0; i < ninchaEntity.size(); ++i){
			entityTemp = ninchaEntity.get(i);
			entityTemp.render(g);
		}
		for(int i = 0; i < terrainSecondeCoucheList.size(); ++i){
			entityTemp = terrainSecondeCoucheList.get(i);
			entityTemp.render(g);
		}
	}
	
	/**
	 * Defini le personnage principal (Nincha)
	 * @param e : Entity
	 */
	public void setNincha(Entity e){
		this.ninchaEntity.add(e);
	}
	
	/**
	 * Récupère le personnage principal (Nincha)
	 * @return Entity
	 */
	public Entity getNincha(){
		return ninchaEntity.get(0);
	}
	
	/**
	 * Supprime le personnage principal (Nincha)
	 * @param e : Entity
	 */
	public void removeNincha(Entity e){
		this.ninchaEntity.remove(e);
	}
	
	/**
	 * Ajoute un EntityMob (Monstre) dans cette liste
	 * @param e : Entity
	 */
	public void addEntity(Entity e){
		this.entityList.add(e);
	}
	
	/**
	 * Retire l'Entity demandé de la liste des monstres.
	 * @param e : Entity
	 */
	public void removeEntity(Entity e){
		this.entityList.remove(e);
	}
	
	/**
	 * Vide la liste des monstres.
	 */
	public void clearEntity(){
		this.entityList.clear();
	}
	
	/**
	 * Récupère la liste contenant tout les monstres
	 * @return LinkedList
	 */
	public LinkedList<Entity> getEntityList(){
		return entityList;
	}
	
	/**
	 * Ajoute un EntityLoot (Objet pouvant être ramassé) dans cette liste
	 * @param e : Entity
	 */
	public void addEntityLoot(Entity e){
		this.entityLootList.add(e);
	}
	
	/**
	 * Retire l'Entity demandé de la liste des objets pouvant être ramassé sur la map.
	 * @param e : Entity
	 */
	public void removeEntityLoot(Entity e){
		this.entityLootList.remove(e);
	}
	
	/**
	 * Vide la liste des objets pouvant être ramassé sur la map.
	 */
	public void clearEntityLoot(){
		this.entityLootList.clear();
	}
	
	/**
	 * Récupère la liste contenant tout les objets pouvant être ramassé sur la map.
	 * @return LinkedList
	 */
	public LinkedList<Entity> getEntityLootList(){
		return entityLootList;
	}
	
	// 1ere Couche
	
	/**
	 * Ajoute une texture de 32x24 dans cette liste
	 * @param e : Entity
	 */
	public void addTextureInTerrainPremiereCouche(Entity e){
		this.terrainPremiereCoucheList.add(e);
	}
	
	/**
	 * Retire la texture demandé
	 * @param e : Entity
	 */
	public void removeTextureInTerrainPremiereCouche(Entity e){
		this.terrainPremiereCoucheList.remove(e);
	}
	
	/**
	 * Vide le terrain (Textures).
	 */
	public void clearTerrainPremiereCouche(){
		this.terrainPremiereCoucheList.clear();
	}
	
	/**
	 * Retourne la liste contenant des textures du terrain
	 * @return LinkedList
	 */
	public LinkedList<Entity> getTextureInPremiereCoucheList(){
		return terrainPremiereCoucheList;
	}
	
	// 2eme Couche
	
	/**
	 * Ajoute une texture de 32x24 dans cette liste
	 * @param e : Entity
	 */
	public void addTextureInTerrainSecondeCouche(Entity e){
		this.terrainSecondeCoucheList.add(e);
	}
	
	/**
	 * Retire la texture demandé
	 * @param e : Entity
	 */
	public void removeTextureInTerrainSecondeCouche(Entity e){
		this.terrainSecondeCoucheList.remove(e);
	}
	
	/**
	 * Vide le terrain (Textures).
	 */
	public void clearTerrainSecondeCouche(){
		this.terrainSecondeCoucheList.clear();
	}
	
	/**
	 * Retourne la liste contenant des textures du terrain
	 * @return LinkedList
	 */
	public LinkedList<Entity> getTextureInSecondeCoucheList(){
		return terrainSecondeCoucheList;
	}
	
	/**
	 * Ajoute un projectile
	 * @param e : Entity
	 */
	public void addProjectile(Entity e){
		this.projectileList.add(e);
	}
	
	/**
	 * Retire le projectile
	 * @param e : Entity
	 */
	public void removeProjectile(Entity e){
		this.projectileList.remove(e);
	}

	/**
	 * Vide la liste de projectile.
	 */
	public void clearProjectile(){
		this.projectileList.clear();
	}
	
	/**
	 * 
	 * @return La liste des projectiles
	 */
	public LinkedList<Entity> getProjectileList(){
		return projectileList;
	}
}

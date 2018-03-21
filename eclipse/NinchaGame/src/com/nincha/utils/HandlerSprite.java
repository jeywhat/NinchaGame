package com.nincha.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * HandlerSprite comme son nom l'indique sert de contenir toutes les sprites (Images) du jeu.
 * Sous formes de différentes listes.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class HandlerSprite {

	private String cheminRes = "ressources/sprites/";
	//SPRITES ENTITY - .get(i)
	// 0 : Nincha | 1 : Slime | .....
	private LinkedList<BufferedImage> entitySprites = new LinkedList<BufferedImage>();
	//ICON ITEM LIST
	// 0 : Pièce | ... | 507 : Artefact7 |
	private BufferedImage[] itemSprites = new BufferedImage[550];
	
	private LinkedList<BufferedImage> projectileSprites = new LinkedList<BufferedImage>();
	
	private LinkedList<BufferedImage> terrainSprites = new LinkedList<BufferedImage>();
	
	private Image[] cursorInGame = new Image[2];
	
	private LinkedList<BufferedImage> utilsImage = new LinkedList<BufferedImage>();
	
	public HandlerSprite(){}
	
	public LinkedList<BufferedImage> getSpriteList(){
		return entitySprites;
	}
	
	public void addSpriteEntity(BufferedImage bI){
		entitySprites.add(bI);
	}
	
	public BufferedImage getSpriteEntity(int i){
		return entitySprites.get(i);
	}
	
	public BufferedImage[] getItemSpriteList(){
		return itemSprites;
	}
	
	public void addItemSprite(int i, BufferedImage bI){
		itemSprites[i] = bI;
	}
	
	public BufferedImage getItemSprite(int i){
		return itemSprites[i];
	}
	
	public LinkedList<BufferedImage> getProjectileSprite(){
		return projectileSprites;
	}
	
	public LinkedList<BufferedImage> getTerrainSprite(){
		return terrainSprites;
	}
	
	public BufferedImage getTerrainSprite(int i){
		return terrainSprites.get(i);
	}
	
	public Image getCursor(int i){
		return cursorInGame[i];
	}
	
	public BufferedImage getUtilsImage(int i){
		return utilsImage.get(i);
	}
	
	/**
	 * Charge les textures liées aux terrains
	 */
	public void spriteTerrainLoading(){
		BufferedTextureLoader bft = new BufferedTextureLoader();
		try{
			loadImage(bft, "terrain/floor.png", terrainSprites);
			loadImage(bft, "terrain/wall.png", terrainSprites);
			loadImage(bft, "terrain/wall-top.png", terrainSprites);
			loadImage(bft, "terrain/wall-right.png", terrainSprites);
			loadImage(bft, "terrain/wall-bottom.png", terrainSprites);
			loadImage(bft, "terrain/wall-left.png", terrainSprites);
			loadImage(bft, "terrain/wall-corner-top-G.png", terrainSprites);
			loadImage(bft, "terrain/wall-corner-top-D.png", terrainSprites);
			loadImage(bft, "terrain/wall-corner-bottom-D.png", terrainSprites);
			loadImage(bft, "terrain/wall-corner-bottom-G.png", terrainSprites);
			loadImage(bft, "terrain/obstacle1.png", terrainSprites);
			loadImage(bft, "terrain/obstacle2.png", terrainSprites);
			loadImage(bft, "terrain/obstacle3.png", terrainSprites);
			loadImage(bft, "terrain/obstacle4.png", terrainSprites);
			loadImage(bft, "terrain/obstacle5.png", terrainSprites);
			loadImage(bft, "terrain/obstacle6.png", terrainSprites);
			loadImage(bft, "terrain/obstacle7.png", terrainSprites);
			loadImage(bft, "terrain/obstacle8.png", terrainSprites);
			loadImage(bft, "terrain/gates.png", terrainSprites);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Charge les textures liées aux EntityPlayer / EntityMob
	 */
	public void spriteEntityLoading(){
		BufferedTextureLoader bft = new BufferedTextureLoader();
		try{
			loadImage(bft, "entity/nincha.png", entitySprites);
			loadImage(bft, "entity/slime.png", entitySprites);
			loadImage(bft, "entity/zombie.png", entitySprites);
			loadImage(bft, "entity/goblin_mage.png", entitySprites);
			loadImage(bft, "entity/rat.png", entitySprites);
			loadImage(bft, "entity/goblin.png", entitySprites);
			loadImage(bft, "entity/goblin_war.png", entitySprites);
			loadImage(bft, "entity/pigman.png", entitySprites);
			loadImage(bft, "entity/sharkman.png", entitySprites);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Charge les textures liées aux items
	 */
	public void spriteItemLoading(){
		BufferedTextureLoader bft = new BufferedTextureLoader();
		try{
			loadImageTab(0, bft, "items/coin.png", itemSprites);
			loadImageTab(1, bft, "items/boost_armor.png", itemSprites);
			loadImageTab(2, bft, "items/potion_poison.png", itemSprites);
			loadImageTab(100, bft, "items/potion_vie_1.png", itemSprites);
			loadImageTab(200, bft, "items/potion_vie_2.png", itemSprites);
			loadImageTab(300, bft, "items/potion_vie_3.png", itemSprites);
			loadImageTab(500, bft, "items/artefact_inconnu.png", itemSprites);
			loadImageTab(501, bft, "items/artefact_peluche.png", itemSprites);
			loadImageTab(502, bft, "items/artefact_livre_ancestral.png", itemSprites);
			loadImageTab(503, bft, "items/artefact_clef_universel.png", itemSprites);
			loadImageTab(504, bft, "items/artefact_oeil_du_serpent.png", itemSprites);
			loadImageTab(505, bft, "items/artefact_calisse_d_harmonie.png", itemSprites);
			loadImageTab(506, bft, "items/artefact_collier_magique.png", itemSprites);
			loadImageTab(507, bft, "items/artefact_masque_vaudou.png", itemSprites);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Charge les textures liées à l'interface du jeu.
	 */
	public void imageUtilsLoading(){
		BufferedTextureLoader bft = new BufferedTextureLoader();
		try{
			loadCursor(0,bft, "ressources/cursor.gif");
			loadCursor(1,bft, "ressources/inventory/cursor.gif");
			loadImageUtils(bft, "ressources/mainMenu/background.png");
			loadImageUtils(bft, "ressources/mainMenu/bouton_jouer_released.png");
			loadImageUtils(bft, "ressources/mainMenu/bouton_jouer_push.png");
			loadImageUtils(bft, "ressources/mainMenu/bouton_aide_released.png");
			loadImageUtils(bft, "ressources/mainMenu/bouton_aide_push.png");
			loadImageUtils(bft, "ressources/mainMenu/bouton_quitter_released.png");
			loadImageUtils(bft, "ressources/mainMenu/bouton_quitter_push.png");
			loadImageUtils(bft, "ressources/mainMenu/aide_background.png");
			loadImageUtils(bft, "ressources/mainMenu/aide_bouton_retour_released.png");
			loadImageUtils(bft, "ressources/mainMenu/aide_bouton_retour_push.png");
			loadImageUtils(bft, "ressources/inventory/background.png");
			loadImageUtils(bft, "ressources/endGame/victory_background.png");
			loadImageUtils(bft, "ressources/endGame/defeat_background.png");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Calcule le sprite animé du projectile du nincha (dès le début du jeu)
	 */
	public void projectileSpriteCalculateRender(){
		BufferedTextureLoader bft = new BufferedTextureLoader();
		BufferedImage shuriken = null;
		File file;
		try{
			file = new File(cheminRes+"entity/shuriken.png");
			shuriken = bft.loadTexture(file);
			projectileSprites.add(shuriken);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		double i = 22.5;
		while(i<=45){
			double rotationRequired = Math.toRadians (i);
			double locationX = shuriken.getWidth() / 2;
			double locationY = shuriken.getHeight() / 2;
			AffineTransform tx = new AffineTransform();
			tx.rotate(rotationRequired, locationX, locationY);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
			projectileSprites.add(op.filter(shuriken, null));
			i += 22.5;
		}
	}
	
	/**
	 * Charge un curseur de souris.
	 * @param id : Place dans le tableau
	 * @param bft : BufferedTextureLoader
	 * @param chemin : Le chemin d'accès au fichier
	 */
	public void loadCursor(int id, BufferedTextureLoader bft, String chemin) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		cursorInGame[id] = tk.getImage(chemin);
	}
	
	/**
	 * Charge une image et le met dans la liste utils
	 * @param bft : BufferedTextureLoader
	 * @param chemin : Le chemin d'accès au fichier
	 * @throws IOException : Si le fichier n'existe pas.
	 */
	private void loadImageUtils(BufferedTextureLoader bft, String chemin) throws IOException{
		File file = new File(chemin);
		utilsImage.add(bft.loadTexture(file));
	}
	
	/**
	 * Charge une image et le met dans la liste correspondante
	 * @param bft : BufferedTextureLoader
	 * @param chemin : Le chemin d'accès au fichier
	 * @param list : LinkedList<BufferedImage>
	 * @throws IOException : Si le fichier n'existe pas.
	 */
	private void loadImage(BufferedTextureLoader bft, String chemin, LinkedList<BufferedImage> list) throws IOException{
		File file = new File(cheminRes+chemin);
		list.add(bft.loadTexture(file));
	}
	
	/**
	 * Charge une image et le met dans le tableau correspondant
	 * @param bft : BufferedTextureLoader
	 * @param chemin : Le chemin d'accès au fichier
	 * @param tab : BufferedImage[]
	 * @throws IOException : Si le fichier n'existe pas.
	 */
	private void loadImageTab(int i, BufferedTextureLoader bft, String chemin, BufferedImage[] tab) throws IOException{
		File file = new File(cheminRes+chemin);
		tab[i] = bft.loadTexture(file);
	}

}

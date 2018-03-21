package com.nincha.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.nincha.entity.EntityPlayer;
import com.nincha.entity.EntityType;
import com.nincha.generation.GenerationMap;
import com.nincha.gui.GuiInGame;
import com.nincha.utils.CompteurStatistiques;
import com.nincha.utils.FontLoading;
import com.nincha.utils.HandlerEntity;
import com.nincha.utils.HandlerSprite;
import com.nincha.utils.KeyInputController;
import com.nincha.utils.MouseController;
import com.nincha.utils.MusicPlayed;

/**
 * Game est la classe mère du jeu.
 * Elle permet de lancer un nouveau jeu.
 * (C'est le coeur du jeu.)
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 832552093031781288L;
	private static String version = "Version 1.0 - NC";

	// TICKS
	private int updates = 0;

	private static boolean running = false;
	private static boolean guiOpen = false;
	private static boolean transitionSalle = false;
	private static boolean debug = false;
	private static GameState gameState = GameState.MainMenu;

	private boolean firstSpawn = true;
	private static float opacity = 1.0F;
	public static Thread thread;

	private static MainMenu mainMenu;
	private static HandlerEntity handlerE;
	private static HandlerSprite handlerS;
	private static FontLoading font;
	private static GuiInGame guiIG;
	private static CompteurStatistiques compteurStats;
	private static Window window;
	
	private static EndGame endGame;

	private GenerationMap geneMap;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	public Game() {
	}

	/**
	 * Lance le jeu
	 */
	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stop le jeu
	 */
	private synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	/**
	 * La première méthode a executé comme elle contient la GameLoop.
	 */
	public void run() {
		// La methode loadUtils charge tout les élements du jeu y compris la
		// première map du jeu. Pour une meilleur fluidité.
		loadUtils();
		initMenu();
		this.requestFocus();
		//GAME LOOP - DEBUT
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS : " + frames + " | TICKS : " + updates);
				updates = 0;
				frames = 0;
			}

			/**
			 * Cette portion permet de bloquer les FPS à 60. 
			 * Il est inutile d'avoir plus étant donné que le tick est de maximum 60.
			 */
			
			if(frames == 60) frames--; if(updates == 61) updates--;
			

		}
		stop();
		//GAME LOOP - FIN
	}

	/**
	 * Charge l'ensemble des Utilitaires permettant le bon fonctionnement du jeu
	 * Par la même occasion la première carte est chargé, évitant ainsi un léger temps
	 * d'attente suite au lancement d'une partie.
	 */
	private void loadUtils() {
		/**
		 * Initialisation des gestionnaires : - HandlerEntity : Gère les entités (Terrain/Personnage principal/Monstres/Projectiles/...). 
		 * 									  - HandlerSprite : Gère les textures (Terrain/Personnage principal/Monstres/Projectiles/HUDInventory/..).
		 */
		handlerE = new HandlerEntity();
		handlerS = new HandlerSprite();

		/**
		 * Charge toutes les images du jeux (sprites) ex: le personnage
		 * principal, les monstres, les items, les textures de la map.
		 */
		handlerS.spriteTerrainLoading();
		handlerS.spriteEntityLoading();
		handlerS.spriteItemLoading();
		handlerS.imageUtilsLoading();
		handlerS.projectileSpriteCalculateRender();

		/**
		 * Charge les polices du jeu.
		 */
		font = new FontLoading();

		/**
		 * Initialise le personnage principal avec 100 pts de vie / 1 pt
		 * d'attaque / 0 pt d'armure
		 */
		handlerE.setNincha(new EntityPlayer(200, 140, 100, 11.0, 0.0, EntityType.EntityPlayer));

		/**
		 * Génère le terrain
		 */
		geneMap = new GenerationMap();

		/**
		 * Initialise l'interface du jeu (Barre de vie / Pièces Collectée / ...
		 */
		guiIG = new GuiInGame();

		/**
		 * Initialise le compteur de statisitiques
		 */
		compteurStats = new CompteurStatistiques();

		/**
		 * Les contrôles souris / clavier
		 */
		this.addMouseListener(new MouseController());
		this.addKeyListener(new KeyInputController());
		
		endGame = new EndGame();
	}

	/**
	 * Initialisation du menu principal du jeu
	 */
	private void initMenu() {
		mainMenu = new MainMenu();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = Game.getHandlerSprite().getCursor(1);
		Cursor monCurseur = tk.createCustomCursor(img, new Point(10, 7), "CursorMainMenu");
		this.setCursor(monCurseur);
	}

	/**
	 * Méthode compliqué à expliquer, mais le tick est la partie calcul (le
	 * déplacement des ennemies, le calcul de l'AStar, les evènement de
	 * ramassage d'item sur le sol, la génération de map, etc...) Elle est
	 * executée toutes les 50ms (environ, si mes calculs sont exactes)
	 */
	private void tick() {
		if (firstSpawn && gameState == GameState.InGameMenu) {
			initGameTick();
		}
		
		if (gameState == GameState.InGameMenu){
			handlerE.tick();
		}

		if (opacity >= 0 && transitionSalle) {
			opacity -= (float) ((0.018F * updates) / 61);
		} else if (opacity <= 0 && transitionSalle) {
			transitionSalle = false;
			opacity = 1.0F;
		}

		if (debug) {
			debugSalle();
			debug = false;
		}
	}

	/**
	 * Methode compliqué à expliquer, mais le render s'occupe de dessiner un
	 * nombre d'images par seconde. Elle est executée toutes les images Par
	 * seconde (Si vous avez un bon PC c'est 60FPS, sinon si c'est inférieur à
	 * 60, FPS = TICK;
	 */
	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		// Fond noir de base(pour eviter les flashs screen)
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

		if (gameState == GameState.MainMenu || gameState == GameState.AideMenu) {
			mainMenu.render(g);
			MusicPlayed.playSound("cat.wav");
		} else if (gameState == GameState.InGameMenu) {
			// Charge toutes les entites sur la jframe
			handlerE.render(g);
			guiIG.render(g);

			if (guiOpen) {
				g.setColor(new Color(0, 0, 0, 0.7F));
				g.fillRect(0, 0, getWidth(), getHeight());
			}

			// Transition (non agressive) entre différente map - Nature Smooth
			if ((transitionSalle && opacity > 0) || (firstSpawn && opacity > 0)) {
				transitionMap(g);
			}
		} else if (gameState == GameState.VictoryMenu){
			endGame.render(g);
		} else if (gameState == GameState.DefeatMenu){
			endGame.render(g);
		}
		g.dispose();
		bs.show();

	}

	/**
	 * Initialisation de la partie mineure d'un lancement de partie 
	 * (transition non aggressive / changement de  curseur)
	 */
	private void initGameTick() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = Game.getHandlerSprite().getCursor(0);
		Cursor monCurseur = tk.createCustomCursor(img, new Point(0, 1), "CursorInGame");
		this.setCursor(monCurseur);
		if (opacity > 0) {
			opacity -= (float) ((0.018F * updates) / 61);
		}

		if (opacity <= 0) {
			firstSpawn = false;
			opacity = 1.0F;
		}
	}

	/**
	 * Transition entre chaque carte.
	 * Permettant de changer de carte en douceur.
	 * Calque noir qui se voit son opacité diminué chaque tick.
	 * @param g : Zone de Canvas
	 */
	private void transitionMap(Graphics g) {
		g.setColor(new Color(0, 0, 0, opacity));
		g.fillRect(72, 82, 964 - 72, 702 - 82);
	}

	/**
	 * Utilitaire pour les développeurs en cas de problème
	 * Action : Génère une nouvelle carte de jeu.
	 * Commande : CTRL + MAJ + G
	 */
	private void debugSalle() {
		if (!handlerE.getEntityList().isEmpty())
			handlerE.clearEntity();
		if (!handlerE.getTextureInPremiereCoucheList().isEmpty())
			handlerE.clearTerrainPremiereCouche();
		if (!handlerE.getTextureInSecondeCoucheList().isEmpty())
			handlerE.clearTerrainSecondeCouche();
		if (!handlerE.getEntityLootList().isEmpty())
			handlerE.clearEntityLoot();
		if (!handlerE.getProjectileList().isEmpty())
			handlerE.clearProjectile();
		handlerE.getNincha().setX(200);
		handlerE.getNincha().setY(140);
		handlerE.getNincha().setPosition(2, 1);
		new GenerationMap();
		setTransitionSalle(true);
	}

	/**
	 * Le HandlerEntity contenant l'intégralité des entity du jeu
	 * @return HandlerEntity
	 */
	public static HandlerEntity getHandlerEntity() {
		return handlerE;
	}

	/**
	 * Le HandlerSprite contenant quasi l'intégralité des Images du jeu (Sprite essentiellement)
	 * @return HandlerSprite
	 */
	public static HandlerSprite getHandlerSprite() {
		return handlerS;
	}

	/**
	 * GuiInGame contient toute l'interface du jeu (HUD Points de vie / HUD Artefacts / Numéro Salle/Etage / ...)
	 * @return GuiInGame
	 */
	public static GuiInGame getGuiInGame() {
		return guiIG;
	}

	/**
	 * Retourne true, si un Gui est ouvert par dessus la fenêtre du jeu.
	 * @return boolean
	 */
	public static boolean isGuiOpen() {
		return guiOpen;
	}

	/**
	 * MainMenu contient tout le menu principal et ses attributs.
	 * @return MainMenu
	 */
	public static MainMenu getMainMenu() {
		return mainMenu;
	}
	
	/**
	 * EndGame contient l'affichage de fin de partie.
	 * @return EndGame
	 */
	public static EndGame getEndGame() {
		return endGame;
	}

	/**
	 * CompteurStatistiques contient l'ensemble des statistiques du jeu associé au joueur.
	 * @return CompteurStatistiques
	 */
	public static CompteurStatistiques getCompteurStats() {
		return compteurStats;
	}

	/**
	 * FontLoading contient les polices d'écritures utilisées par le jeu.
	 * @return FontLoading
	 */
	public static FontLoading getFontLoading() {
		return font;
	}

	/**
	 * Définit s'il y a un Gui d'ouvert ou non.
	 * @param isOpen : boolean
	 */
	public static void setGuiOpen(boolean isOpen) {
		Game.guiOpen = isOpen;
	}

	/**
	 * Définit si il doit y avoir une transition de salle ou non.
	 * @param isTransistion : boolean
	 */
	public static void setTransitionSalle(boolean isTransistion) {
		Game.transitionSalle = isTransistion;
	}

	/**
	 * Récupère la fenêtre mère du jeu.
	 * @return Window
	 */
	public static Window getWindow() {
		return window;
	}
	
	/**
	 * GameState contient le lieu où l'on se trouve dans le jeu.
	 * @return GameState
	 */
	public static GameState getGameState() {
		return gameState;
	}

	/**
	 * Définit notre lieu dans le jeu (à quel moment on est dans le jeu)
	 * @param gs GameState
	 */
	public static void setGameState(GameState gs) {
		gameState = gs;
	}

	/**
	 * Methode Développeur :
	 * Définit si une méthode débuggage a été exécuté.
	 * @param debug : boolean
	 */
	public static void setDebug(boolean debug) {
		Game.debug = debug;
	}

	public static void main(String args[]) {
		Game.window = new Window(1024, 768, "Nincha Game | " + version, new Game());
	}
}

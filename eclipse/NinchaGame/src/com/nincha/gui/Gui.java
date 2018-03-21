package com.nincha.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JDialog;

import com.nincha.main.Game;

/**
 * Modèle de fenêtre, permettant la création la création de fenêtre par dessus le jeu. 
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public abstract class Gui extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param title : Titre de la fenêtre
	 * @param width : Largeur de la fenêtre
	 * @param height : Hauteur de la fenêtre
	 * @param type : Type de Gui
	 */
	public Gui(String title, int width, int height, GuiType type) {
		Game.setGuiOpen(true);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = Game.getHandlerSprite().getCursor(1);
		Cursor monCurseur = tk.createCustomCursor(img, new Point(21/2, 28/4), "CursorInventory");
		this.setCursor(monCurseur);
		addContentInGui();
		this.setTitle("INVENTAIRE");
		this.setSize(width, height);
		this.setBackground(Color.black);
		this.setModal(true);
		this.setResizable(false);
		this.setLocationRelativeTo(Game.getWindow().window);
		this.setUndecorated(true);
		this.addKeyListener(new ControllerKeyGui(this, type));
		this.addMouseListener(new ControllerMouseGui(this, type));
		this.requestFocusInWindow();
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Ajoute tous le contenu de cette méthode dans cette fenêtre.
	 */
	public abstract void addContentInGui();
	
}

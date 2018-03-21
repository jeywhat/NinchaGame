package com.nincha.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.nincha.entity.EntityPlayer;
import com.nincha.main.Game;

/**
 * Modèle de JPanel, permettant la l'affichage d'une image en background. 
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image img;
	private Font f;
	private GuiType type;

	public Panel(Image img, GuiType type) {
		this.img = img;
		this.type = type;
		Dimension size = new Dimension(img.getWidth(this), img.getHeight(this));
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setSize(size);
		this.setLayout(null);
		f = new Font("IMPACT", Font.BOLD, 14);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
		//System.out.println(type);
		if (this.type == GuiType.Inventaire) {
			g.setFont(Game.getFontLoading().getFont(2).deriveFont(8F));
			String pourcentageTime = Game.getCompteurStats().realTimeGame();
			int widthPourcentageTime = g.getFontMetrics().stringWidth(pourcentageTime);
			g.drawString(Game.getCompteurStats().realTimeGame(), 557 - (widthPourcentageTime / 2), 427);
			g.setColor(Color.green);
			g.fillRect(489, 138, progressBarCalculHp(), 12);
			g.setColor(Color.cyan);
			g.fillRect(489, 174, progressBarCalculExp(), 12);
		}
		repaint();
	}

	/**
	 * Calcule la progression de la barre d'expérience du joueur principal
	 * @return int : le pourcentage d'expérience actuel en fonction du niveau à atteindre
	 */
	private int progressBarCalculExp() {
		EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();
		int level = nincha.getLevel();
		int exp = nincha.getExp();
		int expToLevelUp = nincha.getExpToLevelUp(level);
		int pourcentage = (int) ((exp * 100) / expToLevelUp);
		int pourcentageBar = (136 * pourcentage) / 100;
		return pourcentageBar;
	}

	/**
	 * Calcule le pourcentage de point(s) de vie restant du joueur principal
	 * @return int : le pourcentage de point de vie restant
	 */
	private int progressBarCalculHp() {
		EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();
		int pourcentageVie = nincha.pourcentageHP();
		return (136 * pourcentageVie) / 100;
	}
}
package com.nincha.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;

import com.nincha.main.Game;

/**
 * ControllerMouseGui permet de récupérer les déplacements / cliques souris 
 * quand l'on est dans une fenêtre qui extends Gui
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class ControllerMouseGui extends MouseAdapter {

	private JDialog jd;
	private GuiType type;

	public ControllerMouseGui(JDialog jd, GuiType type) {
		this.jd = jd;
		this.type = type;
	}

	public void mousePressed(MouseEvent e) {
		//System.out.println(e.getX() + " | " + e.getY());
		if (e.getButton() == e.BUTTON1) {
			if ((e.getX() >= 611 && e.getY() >= 13) && (e.getX() <= 635 && e.getY() <= 34)) {
				Game.setGuiOpen(false);
				jd.setVisible(false);
				jd.dispose();
			}
		}
	}
}

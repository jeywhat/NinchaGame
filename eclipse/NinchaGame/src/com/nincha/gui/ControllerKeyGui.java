package com.nincha.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;

import com.nincha.main.Game;

/**
 * ControllerKeyGui permet de récupérer les touches appuyés 
 * quand l'on est dans une fenêtre qui extends Gui
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class ControllerKeyGui extends KeyAdapter{
	
	private JDialog jd;
	private GuiType type;
	
	public ControllerKeyGui(JDialog jd, GuiType type){
		this.jd = jd;
		this.type = type;
	}
	
	public void keyPressed(KeyEvent event) {
		//I ou ECHAP
		if(event.getKeyCode() == 73 || event.getKeyCode() == 27){
			Game.setGuiOpen(false);
			jd.setVisible(false);
			jd.dispose();
		}
	}
}

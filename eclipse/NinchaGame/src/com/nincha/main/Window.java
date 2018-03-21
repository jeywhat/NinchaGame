package com.nincha.main;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Modèle de fenêtre JFrame utilisé par Game
 *
 * @author Nincha Corporation
 * @version 1.0
 */
public class Window {

	public static JFrame window;
	
	public Window(int l,int h, String titre, Game game){
		game.setPreferredSize(new Dimension(l,h));
		game.setMaximumSize(new Dimension(l,h));
		game.setMinimumSize(new Dimension(l,h));
		
		window = new JFrame(titre);
		window.add(game);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		game.start();
	}
}

package com.nincha.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Charge les polices d'écriture utilisées dans le jeu.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class FontLoading {

	private ArrayList<Font> fontHandler = new ArrayList<Font>();
	
	public FontLoading(){
		loading();
	}
	
	/**
	 * Crée/Charge les polices d'écriture utilisées dans le jeu.
	 */
	private void loading(){
		try {
			fontHandler.add(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("ressources/font/upheavtt.ttf")));
			fontHandler.add(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("ressources/font/pixel.ttf")));
			fontHandler.add(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("ressources/font/pixel_better.ttf")));
			fontHandler.add(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("ressources/font/AldotheApache.ttf")));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FontFormatException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Récupère la police demandée.
	 * @param i : Indice du placement de la police dans la liste.
	 * @return : la police (Font)
	 */
	public Font getFont(int i){
		return this.fontHandler.get(i);
	}
	
	
	
	
}

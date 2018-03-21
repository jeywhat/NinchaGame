package com.nincha.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nincha.entity.EntityPlayer;
import com.nincha.utils.HandlerSprite;

/**
 * EndGame comme son nom l'indique est le dernier affichage du jeu.
 * Tableau des scores
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class EndGame {

	private BufferedImage backgroundVictoire;
	private BufferedImage backgroundDefaite;
	private static String timeStop = "";
	private static long timeLongStop = 0;
	
	private HandlerSprite hs = Game.getHandlerSprite();
	private Font font = Game.getFontLoading().getFont(3);
	private EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();
	
	public EndGame(){
		this.backgroundVictoire = hs.getUtilsImage(11);
		this.backgroundDefaite = hs.getUtilsImage(12);
	}
	
	/**
	 * Affiche les différents composant du scoreboard
	 * @param g : Graphics
	 */
	public void render(Graphics g){
		if(Game.getGameState() == GameState.VictoryMenu){
			g.drawImage(backgroundVictoire, 0, 0, null);
		}else if(Game.getGameState() == GameState.DefeatMenu){
			g.drawImage(backgroundDefaite, 0, 0, null);
		}
		g.setColor(Color.white);
		g.setFont(font.deriveFont(35F));
		g.drawString(nincha.getLevel()+"", 605, 339);
		g.drawString(nincha.getPiece()+"", 605, 339 + (56));
		g.drawString(Game.getGuiInGame().getArtefactCollected()+" / 7", 605, 339 + (112));
		g.drawString(timeStop, 605, 339 + (162));
		g.setFont(font.deriveFont(45F));
		g.drawString(calculScore()+"", 605, 578);
	}
	
	/**
	 * Calcule le score final du joueur.
	 * @return int : Score final du joueur.
	 */
	public int calculScore(){
		return (int) (((nincha.getLevel()*30)*(1+Game.getGuiInGame().getArtefactCollected()*50)+(nincha.getPiece()*3)*15)/(timeLongStop/10000));
	}
	
	public static void setTimeStop(String s){
		timeStop = s;
	}
	
	public static void setTimeLongStop(long lg){
		timeLongStop = lg;
	}
}

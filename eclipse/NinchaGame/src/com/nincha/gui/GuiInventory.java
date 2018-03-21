package com.nincha.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.nincha.entity.EntityPlayer;
import com.nincha.entity.ItemEffects;
import com.nincha.entity.ItemType;
import com.nincha.main.Game;
import com.nincha.utils.CompteurStatistiques;

/**
 * GuiInventory est l'inventaire du joueur, lorsqu'on presse la touche 'I'.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class GuiInventory extends Gui {

	private static final long serialVersionUID = 1L;
	/**
	 * Liste des boutons du JDialog
	 */
	private JButton[] buttonCache;

	private GuiType type;

	public GuiInventory(String title, int width, int height, GuiType type) {
		super(title, width, height, type);
		this.type = type;
	}

	public void addContentInGui() {
		EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();
		Panel panel = new Panel(new ImageIcon(Game.getHandlerSprite().getUtilsImage(10)).getImage(), GuiType.Inventaire);
		
		CompteurStatistiques compteur = Game.getCompteurStats();
		this.buttonCache = new JButton[nincha.getInventory().size()];
		
		
		int niveau = nincha.getLevel();
		int coin = nincha.getPiece();
		int nbrMobBasic = compteur.getNbrEnnemisBasicTues();
		int nbrMobIntermediaire = compteur.getNbrEnnemisIntermediaireTues();
		int nbrMobExpert = compteur.getNbrEnnemisMiniBossTues();
		double atk = nincha.getAtk();
		double armor = nincha.getArmor();
		DecimalFormat armor_arrondi = new DecimalFormat("0.000");
		double pv = nincha.getPv();
		double pvInit = nincha.getPvInitial();
		double exp = nincha.getExp();
		double expToLevelUp = nincha.getExpToLevelUp(niveau);

		JPanel panelInventory = new JPanel();
		panelInventory.setLayout(new BoxLayout(panelInventory, BoxLayout.PAGE_AXIS));
		
		final JScrollPane scroll = new JScrollPane(panelInventory);
		scroll.setBounds(17, 56, 467 - 17, 436 - 58);
		scroll.setPreferredSize(new Dimension(467 - 17, 700 - 58));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.addMouseWheelListener(new MouseWheelListener(){
			public void mouseWheelMoved(MouseWheelEvent event) {
				final JScrollBar scrollBar = scroll.getVerticalScrollBar();
			    final int rotation = event.getWheelRotation();
			    if (scrollBar != null) {
			        scrollBar.setValue(scrollBar.getValue()
			                + (scrollBar.getBlockIncrement(rotation) * rotation / 10));
			    }
				
			}
		});
		
		for (int i = 0; i < nincha.getInventory().size(); i++) {
			if (nincha.getInventory().get(i).getType() != ItemType.ARTEFACT) {
				JPanel item = new JPanel();
				item.setMinimumSize(new Dimension(467 - 17, 50));
				JLabel text = new JLabel(nincha.getInventory().get(i).getName());
				item.add(text);
				JButton bouton = new JButton("Utiliser");
				buttonCache[i] = bouton;
				bouton.putClientProperty("id", i);
				bouton.addActionListener(new ListenerBoutons(this, bouton, buttonCache));
				bouton.setFocusable(false);
				item.add(bouton);
				panelInventory.add(item);
			}
		}
		panel.add(scroll);
		
		Font font = Game.getFontLoading().getFont(3).deriveFont(20f);
		
		// Les Points de Vie restants
		addLabel(Integer.toString((int) pv) + "/" + Integer.toString((int) pvInit), 518, 111, 140, 30, panel);
		// L'expérience restantes
		addLabel(Integer.toString((int) exp) + "/" + Integer.toString((int) expToLevelUp), 528, 147, 140, 30, panel);
		// Affiche le niveau du joueur
		addLabel(Integer.toString(niveau), 557, 187, 140, 32, font, panel);
		// Affiche l'ataque du joueur
		addLabel(Double.toString(atk), 518, 213, 140, 30, panel);
		// Affiche la défense du joueur
		addLabel(armor_arrondi.format(armor), 518, 241, 140, 30, panel);
		// Affiche le nombre de pièce récoltées par le joueur
		addLabel("x" + Integer.toString(coin), 518, 267, 140, 30, panel);
		// Affiche le nombre de monstres basiques tué
		addLabel(Integer.toString(nbrMobBasic), 602, 336, 140, 30, panel);
		// Affiche le nombre de monstres intermédiaires tué
		addLabel(Integer.toString(nbrMobIntermediaire), 602, 348, 140, 30, panel);
		// Affiche le nombre de monstres expert(mini-boss) tué
		addLabel(Integer.toString(nbrMobExpert), 602, 360, 140, 30, panel);
		this.getContentPane().add(panel);
	}

	/**
	 * Ajoute un texte dans un JLabel
	 * @param text : Texte de celui-ci
	 * @param x : Sa position x
	 * @param y : Sa postion y
	 * @param width : Sa largeur
	 * @param height : Sa hauteur
	 * @param jp : Me JPanel associé
	 */
	private void addLabel(String text, int x, int y, int width, int height, JPanel jp) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		jp.add(label);
	}

	/**
	 * Ajoute un texte dans un JLabel avec une Font bien précise
	 * @param text : Texte de celui-ci
	 * @param x : Sa position x
	 * @param y : Sa postion y
	 * @param width : Sa largeur
	 * @param height : Sa hauteur
	 * @param f : La Font utilisé
	 * @param jp : Me JPanel associé
	 */
	private void addLabel(String text, int x, int y, int width, int height, Font f, JPanel jp) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		label.setFont(f);
		jp.add(label);
	}
}

/**
 * Traite les événement lors de l'action sur un bouton.
 *  
 * @author Nincha Corporation
 * @version 1.0
 */
class ListenerBoutons implements ActionListener{

	private JDialog jd;
	private JButton j;
	private JButton[] list;
	private EntityPlayer nincha = (EntityPlayer) Game.getHandlerEntity().getNincha();

	/**
	 * 
	 * @param jd : Le JDialog visé.
	 * @param j : Le JButton appuyé.
	 * @param boutonList : La liste des JButton de l'inventaire
	 */
	public ListenerBoutons(JDialog jd, JButton j, JButton[] boutonList) {
		this.jd = jd;
		this.j=j;
		this.list = boutonList;
	}
	
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < list.length; i++) {
			if (e.getSource() == list[i]) {
				if(list[i] != null)
					Game.setGuiOpen(false);
					jd.setVisible(false);
					jd.dispose();
					new ItemEffects(nincha.getInventory().get(i));
			}
		}

	}

}
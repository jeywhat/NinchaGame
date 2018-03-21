package com.nincha.utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.nincha.entity.Entity;
import com.nincha.gui.GuiInventory;
import com.nincha.gui.GuiType;
import com.nincha.main.Game;
import com.nincha.main.GameState;

/**
 * ControllerKeyGui permet de récupérer les touches appuyés 
 * quand l'on est en jeu.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class KeyInputController extends KeyAdapter {

	private Entity nincha = Game.getHandlerEntity().getNincha();

	private int zPressed = 0, qPressed = 0, sPressed = 0, dPressed = 0;
	private int ctrlPressed = 0, majPressed = 0, gPressed = 0;
	private long timerSave = System.currentTimeMillis();

	public KeyInputController() {}

	public void keyPressed(KeyEvent event) {
		int keyInput = event.getKeyCode();
		if (Game.getGameState() == GameState.InGameMenu) {
			switch (keyInput) {
			// Z
			case 90:
				zPressed = keyInput;
				break;
			// Q
			case 81:
				qPressed = keyInput;
				break;
			// S
			case 83:
				sPressed = keyInput;
				break;
			// D
			case 68:
				dPressed = keyInput;
				break;
			// UP
			case 38:
				zPressed = keyInput + 52;
				break;
			// LEFT
			case 37:
				qPressed = keyInput + 44;
				break;
			// DOWN
			case 40:
				sPressed = keyInput + 43;
				break;
			// RIGHT
			case 39:
				dPressed = keyInput + 29;
				break;
			// I
			case 73:
				if (nincha.getVeloX() > 0)
					nincha.setVeloX(0);
				if (nincha.getVeloY() > 0)
					nincha.setVeloY(0);
				new GuiInventory("INVENTAIRE", 650, 450, GuiType.Inventaire);
				break;
			// MAJ
			case 16:
				majPressed = keyInput;
				break;
			// CTRL
			case 17:
				ctrlPressed = keyInput;
				break;
			// MAJ
			case 71:
				gPressed = keyInput;
				break;
			// ESC
			case 27:
				System.exit(1);
			default:
				break;
			}
			huitDirection();
			controleMultiple();
		}
		else if (Game.getGameState() == GameState.VictoryMenu || Game.getGameState() == GameState.DefeatMenu){
			switch (keyInput) {
			// ESC
			case 27:
				System.exit(1);
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Methode de Multi-INPUT - 1ème Partie
	 * LES DIRECTIONS
	 */
	private void huitDirection() {
		int direction = zPressed + qPressed + sPressed + dPressed;
		switch (direction) {
		// Z
		case 90:
			nincha.setVeloY(-3F);
			nincha.setPosition(2, 4);
			break;
		// Q
		case 81:
			nincha.setVeloX(-3F);
			nincha.setPosition(2, 2);
			break;
		// S
		case 83:
			nincha.setVeloY(3F);
			nincha.setPosition(2, 1);
			break;
		// D
		case 68:
			nincha.setVeloX(3F);
			nincha.setPosition(2, 3);
			break;
		// Z+Q
		case 171:
			nincha.setVeloY((float) -(3 / Math.sqrt(2)));
			nincha.setVeloX((float) -(3 / Math.sqrt(2)));
			break;
		// Z+D
		case 158:
			nincha.setVeloY((float) -(3 / Math.sqrt(2)));
			nincha.setVeloX((float) (3 / Math.sqrt(2)));
			break;
		// S+Q
		case 164:
			nincha.setVeloY((float) (3 / Math.sqrt(2)));
			nincha.setVeloX((float) -(3 / Math.sqrt(2)));
			break;
		// S+D
		case 151:
			nincha.setVeloY((float) (3 / Math.sqrt(2)));
			nincha.setVeloX((float) (3 / Math.sqrt(2)));
			break;
		default:
			break;
		}
	}

	/**
	 * Methode de Multi-INPUT - 2ème Partie
	 * LES MULTIPLES CONTRÔLES
	 */
	private void controleMultiple() {
		int controleMultiple = ctrlPressed + majPressed + gPressed;
		switch (controleMultiple) {
		// CTRL + MAJ + G -> RESET MAP
		case 104:
			if (timerSave - System.currentTimeMillis() <= 0) {
				timerSave = System.currentTimeMillis() + 10000;
				Game.setDebug(true);
			}
			break;
		default:
			break;
		}
	}

	public void keyReleased(KeyEvent event) {
		int keyInput = event.getKeyCode();
		if (Game.getGameState() == GameState.InGameMenu) {
			switch (keyInput) {
			// Z
			case 90:
				zPressed = 0;
				nincha.setVeloY(0);
				break;
			// Q
			case 81:
				qPressed = 0;
				nincha.setVeloX(0);
				break;
			// S
			case 83:
				sPressed = 0;
				nincha.setVeloY(0);
				break;
			// D
			case 68:
				dPressed = 0;
				nincha.setVeloX(0);
				break;
			// UP
			case 38:
				zPressed = 0;
				nincha.setVeloY(0);
				break;
			// LEFT
			case 37:
				qPressed = 0;
				nincha.setVeloX(0);
				break;
			// DOWN
			case 40:
				sPressed = 0;
				nincha.setVeloY(0);
				break;
			// RIGHT
			case 39:
				dPressed = 0;
				nincha.setVeloX(0);
				break;
			// MAJ
			case 16:
				majPressed = 0;
				break;
			// CTRL
			case 17:
				ctrlPressed = 0;
				break;
			// MAJ
			case 71:
				gPressed = 0;
				break;
			default:
				break;
			}
		}
	}
}

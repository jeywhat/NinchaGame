package com.nincha.utils;

/**
 * CompteurStatistiques est comme sont nom l'indique
 * une classe répertoriant toute les statistiques du joueur
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class CompteurStatistiques {

	/**
	 * L'heure exacte où le jeu a été lancé
	 */
	private long tempsJeuStart;
	private String time = "";
	private int nbrEnnemisBasicTues = 0;
	private int nbrEnnemisIntermediaireTues = 0;
	private int nbrEnnemisMiniBossTues = 0;

	public CompteurStatistiques() {
		tempsJeuStart = System.currentTimeMillis();
	}

	/**
	 * Cette methode calcule le temps passé dans le jeu sous forme d'un type long.
	 * @return le temps passé dans le jeu, sous forme d'un type long.
	 */
	public long timeGame() {
		return System.currentTimeMillis() - tempsJeuStart;
	}

	/**
	 * Cette methode calcule le temps passé dans le jeu sous forme de heures/min/secondes.
	 * @return Une chaîne de caractère, retournant le temps passé dans le jeu.
	 */
	public String realTimeGame() {
		time = "";
		int hours = (int) ((timeGame() / (1000 * 60 * 60)));
		int minutes = (int) ((timeGame() / (1000 * 60)) % 60);
		int seconds = (int) (timeGame() / 1000) % 60;
		// Heure
		if (hours != 0) {
			calculHeureMinuteSeconde(hours);
			time += " H ";
		}
		// Minutes
		if (minutes != 0) {
			calculHeureMinuteSeconde(minutes);
			time += " min ";
		}
		// Secondes
		calculHeureMinuteSeconde(seconds);
		time += " sec";
		return time;
	}

	/**
	 * Affichage Heures / Minutes / Secondes dans une String
	 * @param temps : int
	 */
	private void calculHeureMinuteSeconde(int temps) {
		if (Integer.toString(temps).length() <= 1)
			time += "0" + temps + "";
		else
			time += temps + "";
	}

	public int getNbrEnnemisBasicTues() {
		return nbrEnnemisBasicTues;
	}

	public void addNbrEnnemisBasicTues() {
		this.nbrEnnemisBasicTues += 1;
	}

	public int getNbrEnnemisIntermediaireTues() {
		return nbrEnnemisIntermediaireTues;
	}

	public void addNbrEnnemisIntermediaireTues() {
		this.nbrEnnemisIntermediaireTues += 1;
	}

	public int getNbrEnnemisMiniBossTues() {
		return nbrEnnemisMiniBossTues;
	}

	public void addNbrEnnemisMiniBossTues() {
		this.nbrEnnemisMiniBossTues += 1;
	}

}

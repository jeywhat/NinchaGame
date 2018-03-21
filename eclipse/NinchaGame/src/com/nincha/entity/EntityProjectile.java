package com.nincha.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import com.nincha.main.Game;

/**
 * EntityProjectile permet de lancer un projectile vers un point bien précis de la fenêtre.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class EntityProjectile extends Entity {

	/**
	 * Taille du projectile 24x24
	 */
	private int width = 24, height = 24;
	/**
	 * Evenement de la souris (déifnit dans le constructeur)
	 */
	private MouseEvent e;

	/**
	 * Crée deux points imaginaire dans l'espace, permettant une vélocité parfaite (cercle parfait)
	 * (Expliqué plus bas)
	 */
	private float fictiveX;
	private float fictiveY;
	private float initX;
	private float initY;

	/**
	 * Animation de l'entity
	 */
	private EntityAnimation animationProjectile;

	/**
	 * Créer un projectile en fonction du curseur de la souris
	 * @param x : Position x du projectile. 
	 * @param y : Position y du projectile.
	 * @param atk : Son attaque, ici l'attaque du nincha (personnage principal)
	 * @param entityType : Type d'entity
	 * @param e : Récupère les événements de la souris
	 */
	public EntityProjectile(float x, float y, double atk, EntityType entityType, MouseEvent e) {
		super(x, y, atk, entityType);
		this.e = e;
		float[] velo = renderVelocity();
		setVeloX(velo[0]);
		setVeloY(velo[1]);
		this.animationProjectile = new EntityAnimation(1, Game.getHandlerSprite().getProjectileSprite());
	}

	public void tick(LinkedList<Entity> entity) {
		if (coord.getX() <= 75 || coord.getX() >= 938 || coord.getY() <= 95 || coord.getY() >= 675) {
			this.die();
		}
		coord.setX((float) (coord.getX() + velociteX));
		coord.setY((float) (coord.getY() + velociteY));
		collisionBounds(entity);
		this.animationProjectile.runAnimation();
	}

	public void render(Graphics g) {
		this.animationProjectile.drawAnimation(g, (int) coord.getX(), (int) coord.getY());
	}

	/**
	 * Cette methode permet de tester comme son nom l'indique, les colisions avec les autres entity
	 * @param object : une liste d'entity à tester
	 */
	private void collisionBounds(LinkedList<Entity> object) {
		for (int i = 0; i < Game.getHandlerEntity().getEntityList().size(); i++) {
			EntityMob tempObject = (EntityMob) Game.getHandlerEntity().getEntityList().get(i);
			if (getBounds().intersects(tempObject.getBounds())) {
				this.die();
				tempObject.setPv(tempObject.getPv() - (this.atk * (1-tempObject.getArmor())));
			}
		}
		
		for (int i = 0; i < Game.getHandlerEntity().getTextureInPremiereCoucheList().size(); i++) {
			Entity tempObject = Game.getHandlerEntity().getTextureInPremiereCoucheList().get(i);
			if (tempObject.getType() == EntityType.EntityObstacle) {
				if (getBounds().intersects(tempObject.getBounds())) {
					this.die();
				}
			}
		}
	}

	public int getPostion() {
		return 0;
	}

	public void setPosition(int col, int line) {
	}

	public Rectangle getBounds() {
		return new Rectangle((int) ((int) coord.getX()+width/2-width/8), (int) ((int) coord.getY() + height/2-height/8), (int) width/8, (int) height/8);
	}

	public Rectangle getBoundsBottom() {
		return new Rectangle((int) ((int) coord.getX()), (int) coord.getY() + (int) height / 2, (int) width,
				(int) height / 2);
	}

	public Rectangle getBoundsFloor() {
		return new Rectangle((int) ((int) coord.getX()), (int) coord.getY() + height - (int) height / 10, (int) width,
				(int) height / 10);
	}

	/**
	 * Calcule la velocité que le projectile doit avoir en x et en y
	 * Pour pouvoir ariver au point demandé avec une vélocité "universelle".
	 * @return float[] : VelociteX et VelociteY
	 */
	private float[] renderVelocity() {
		// Changer la vitesse du shuriken (projectile)
		float fictiveHypo = 5.5F;

		float mouseX = e.getX();
		float mouseY = e.getY();

		initX = this.coord.getX();
		initY = this.coord.getY();

		float segmentX = this.getSegmentValues(initX, mouseX);
		float segmentY = this.getSegmentValues(initY, mouseY);

		float hypoMouse = this.pythagoreCalcul(segmentX, segmentY);
		fictiveX = this.getVector(hypoMouse, fictiveHypo, segmentX);
		fictiveY = this.getVector(hypoMouse, fictiveHypo, segmentY);

		return new float[] { this.getSigneVelocity(mouseX, initX, fictiveX),
				this.getSigneVelocity(mouseY, initY, fictiveY) };
	}

	/**
	 * Les segments entre la position du Nincha et le clique souris
	 * @param init : Coordonnée position Nincha (x ou y)
	 * @param mouse : Coordonnée position curseur souris (x ou y)
	 * @return float : le segment en question
	 */
	private float getSegmentValues(float init, float mouse) {
		if (init >= mouse)
			return init - mouse;
		else
			return mouse - init;
	}

	/**
	 * Calcul d l'hypoténuse d'un triangle avec le théorème de Pythagore
	 * @param x : côté
	 * @param y : côté
	 * @return float : hypoténuse
	 */
	private float pythagoreCalcul(float x, float y) {
		return (float) Math.sqrt((x * x) + (y * y));
	}

	/**
	 * le Segment fictif
	 * @param mouseHypo
	 * @param segHypo
	 * @param segmentValue
	 * @return
	 */
	private float getVector(float mouseHypo, float segHypo, float segmentValue) {
		return (segHypo * segmentValue) / mouseHypo;
	}

	/**
	 * Savoir le signe à appliquer pour bien diriger le projectile vers le point demandé.
	 * @param mouse
	 * @param init
	 * @param fictive
	 * @return
	 */
	private float getSigneVelocity(float mouse, float init, float fictive) {
		if (mouse - init >= 0)
			return fictive;
		else
			return fictive * (-1);
	}

	public int pourcentageHP() {return 0;}

	public void die() {
		Game.getHandlerEntity().removeProjectile(this);
	}

}

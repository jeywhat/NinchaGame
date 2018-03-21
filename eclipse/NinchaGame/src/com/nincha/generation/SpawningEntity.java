package com.nincha.generation;

import java.util.LinkedList;

import com.nincha.entity.Entity;
import com.nincha.entity.EntityType;
import com.nincha.main.Game;
/**
 * SpawningEntity permet de tester l'apparition des objets sur la map
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class SpawningEntity {

	private Entity entity;

	public SpawningEntity(Entity e) {
		this.entity = e;
	}

	/**
	/**
	 * Savoir si l'entity posé est en conflit (HitBox qui rentre en intersection) avec une autre entity 
	 * @param listE : La liste d'entity testée
	 * @return boolean
	 */
	private boolean ifCollisionBounds(LinkedList<Entity> listE) {
		for (int i = 0; i < listE.size(); i++) {
			Entity tempObject = listE.get(i);
			if (tempObject.getType() != EntityType.EntityFloor) {
				if (entity.getBounds().intersects(tempObject.getBounds())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Savoir si l'entity posé est en conflit (HitBox qui rentre en intersection) avec une autre entity 
	 * @return boolean
	 */
	private boolean ifCollisionBoundsNincha() {
		if (entity.getBounds().intersects(Game.getHandlerEntity().getNincha().getBounds())) {
			return true;
		}

		return false;
	}

	/**
	 * Savoir si l'entity posé est sur le sol
	 * @return boolean
	 */
	private boolean isFloor() {
		for (int i = 0; i < Game.getHandlerEntity().getTextureInPremiereCoucheList().size(); i++) {
			Entity tempObject = Game.getHandlerEntity().getTextureInPremiereCoucheList().get(i);
			if (entity.getBounds().intersects(tempObject.getBounds()) && tempObject.getType() == EntityType.EntityFloor) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return Retourne l'entity à afficher sur la map
	 */
	public Entity spawning() {
		Entity e = null;
		if (isFloor()) {
			if (ifCollisionBounds(Game.getHandlerEntity().getEntityList()) || ifCollisionBoundsNincha()
					|| ifCollisionBounds(Game.getHandlerEntity().getTextureInPremiereCoucheList())
					|| ifCollisionBounds(Game.getHandlerEntity().getTextureInSecondeCoucheList())) {
			} else {
				e = this.entity;
			}
		}
		return e;
	}

}

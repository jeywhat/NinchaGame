package com.nincha.entity;

import com.nincha.main.Game;

/**
 * ItemEffects est actuellement utilisé lorsque l'on utilise un objet dans l'inventaire
 * 
 * Applique les effets de l'objet utilisé.
 * @author Nincha Corporation
 * @version 1.0
 */
public class ItemEffects {

	private Item item;
	private EntityPlayer nincha = (EntityPlayer)(Game.getHandlerEntity().getNincha());
	
	/**
	 * Récupère un objet pour ensuite analyser ses effets
	 * @param item : Item
	 */
	public ItemEffects(Item item){
		this.item = item;
		applyEffects();
	}
	
	/**
	 * Methode appliquant les effets des objets
	 */
	public void applyEffects(){
		if(item.getType() == ItemType.POTION_HEAL){
			nincha.setPv(nincha.getPv() + ((nincha.getPvInitial() * item.getEffects())/100));
			if(nincha.getPv() > nincha.getPvInitial())
				nincha.setPv(nincha.getPvInitial());
			nincha.getInventory().remove(item);
		}
	}
}

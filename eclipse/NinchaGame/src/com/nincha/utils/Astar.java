package com.nincha.utils;

import java.awt.Rectangle;
import java.util.LinkedList;

import com.nincha.entity.Entity;
import com.nincha.entity.EntityMob;
import com.nincha.entity.EntityPlayer;
import com.nincha.entity.EntityType;
import com.nincha.main.Game;

public class Astar {

	LinkedList<Noeud> openlist = new LinkedList<Noeud>();
	LinkedList<Noeud> closedlist = new LinkedList<Noeud>();
	LinkedList<Noeud> chemins = new LinkedList<Noeud>();

	float velox;
	float veloy;
	Rectangle arrive;

	public Astar(EntityMob depart, EntityPlayer arrive) {
		Noeud dep = new Noeud(
				new Coord((float) depart.getBoundsBottom().getX(), (float) depart.getBoundsBottom().getY()));
		dep.setCoutf(0);
		dep.setCouth(0);
		dep.setCoutg(0);
		dep.setParent(new Noeud(new Coord(0, 0)));
		closedlist.add(dep);
		this.velox = (float) depart.getBoundsBottom().getWidth();
		this.veloy = (float) depart.getBoundsBottom().getHeight();
		this.arrive = arrive.getBounds();
	}

	public LinkedList<Noeud> runpath() {
		Noeud courant;
		courant = closedlist.get(0);
		Rectangle r = new Rectangle((int) (courant.getCoord().getX()), (int) (courant.getCoord().getY()), (int) velox,
				(int) veloy);
		addNoeudadj(courant);
		
		int i = 0;
		while (!r.intersects(this.arrive) && !openlist.isEmpty() && i < 250) {
			courant = bestNoeud(openlist);
			r = new Rectangle((int) (courant.getCoord().getX()), (int) (courant.getCoord().getY()), (int) velox,
					(int) veloy);
			addclosedlist(courant);
			addNoeudadj(courant);
			i++;
		}
		findpath();
		return this.chemins;
	}

	public LinkedList<Noeud> newpath(EntityMob depart, EntityPlayer arrive) {

		this.openlist.clear();
		this.chemins.clear();
		this.closedlist.clear();

		Noeud dep = new Noeud(
				new Coord((float) depart.getBoundsBottom().getX(), (float) depart.getBoundsBottom().getY()));
		dep.setCoutf(0);
		dep.setCouth(0);
		dep.setCoutg(0);
		dep.setParent(new Noeud(new Coord(0, 0)));
		closedlist.add(dep);
		this.velox = (float) depart.getBoundsBottom().getWidth();
		this.veloy = (float) depart.getBoundsBottom().getHeight();
		this.arrive = arrive.getBounds();
		return runpath();
	}

	public void findpath() {
		Noeud n = closedlist.getLast();

		while (n.getParent().getCoord().getX() != 0) {
			chemins.addFirst(n);
			n = n.getParent();
		}
	}

	public void addNoeudadj(Noeud noeud) {
		LinkedList<Noeud> tmp = new LinkedList<Noeud>();
		int it;
		Noeud up = new Noeud(new Coord(noeud.getCoord().getX(), noeud.getCoord().getY() - veloy));
		Noeud down = new Noeud(new Coord(noeud.getCoord().getX(), noeud.getCoord().getY() + veloy));
		Noeud right = new Noeud(new Coord(noeud.getCoord().getX() + velox, noeud.getCoord().getY()));
		Noeud left = new Noeud(new Coord(noeud.getCoord().getX() - velox, noeud.getCoord().getY()));

		tmp.add(up);
		tmp.add(down);
		tmp.add(right);
		tmp.add(left);
		Noeud noeud1;

		int i = 0;
		while (i < tmp.size()) {
			if ((inmap(tmp.get(i)) == false) || (intersectrec(tmp.get(i)) == false)) {
				tmp.remove(i);
			} else
				i++;
		}
		for (int j = 0; j < tmp.size(); j++) {
			noeud1 = tmp.get(j);
			if (inlist(noeud1, closedlist) == -1) {
				noeud1.setParent(noeud);
				noeud1.setCoutg(noeud1.getParent().getCoutg() + (float) distance(noeud1, noeud));
				noeud1.setCouth((float) distance(noeud1,
						new Noeud(new Coord((float) arrive.getCenterX(), (float) arrive.getCenterY()))));
				noeud1.setCoutf(noeud1.getCoutg() + noeud1.getCouth());
				if ((it = inlist(noeud1, openlist)) != -1) {
					if (noeud1.getCoutf() < openlist.get(it).getCoutf()) {
						openlist.get(it).setCoord(noeud1.getCoord());
						openlist.get(it).setCoutf(noeud1.getCoutf());
						openlist.get(it).setCoutg(noeud1.getCoutg());
						openlist.get(it).setCouth(noeud1.getCouth());
					}
				} else {
					openlist.addFirst(noeud1);
				}
			}
		}
	}

	public void addclosedlist(Noeud n) {
		int i;
		closedlist.add(n);
		if ((i = inlist(n, openlist)) != -1) {
			openlist.remove(i);
		}
		return;
	}

	public Noeud bestNoeud(LinkedList<Noeud> l) {
		Noeud best = l.getFirst();
		float tmpf = l.getFirst().getCoutf();

		for (Noeud noeud : l) {
			if (noeud.getCoutf() < tmpf) {
				tmpf = noeud.getCoutf();
				best = noeud;
			}
		}
		return best;
	}

	public double distance(Noeud n, Noeud n1) {
		int x = (int) n.getCoord().getX();
		int y = (int) n.getCoord().getY();
		int x1 = (int) n1.getCoord().getX();
		int y1 = (int) n1.getCoord().getY();
		return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
	}

	public boolean inmap(Noeud n) {
		return (((n.getCoord().getX() > 0) && (n.getCoord().getX() + velox < 1024))
				&& ((n.getCoord().getY() > 0) && (n.getCoord().getY() + veloy < 768)));
	}

	public boolean intersectrec(Noeud n) {
		Rectangle r = new Rectangle((int) (n.getCoord().getX()), (int) (n.getCoord().getY()), (int) velox, (int) veloy);
		for (int i = 728; i < Game.getHandlerEntity().getTextureInPremiereCoucheList().size(); i++) {
			Entity tempObject = Game.getHandlerEntity().getTextureInPremiereCoucheList().get(i);
			if (tempObject.getType() == EntityType.EntityObstacle) {
				if (r.intersects(tempObject.getBounds())) {
					return false;
				}
			}
		}
		return true;
	}

	public int inlist(Noeud noeud, LinkedList<Noeud> l) {
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).equals(noeud))
				return i;
		}
		return -1;
	}
}

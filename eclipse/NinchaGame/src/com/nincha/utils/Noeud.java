package com.nincha.utils;

public class Noeud {

	Noeud parent;
	float coutg, couth, coutf;
	Coord coord;
	
	
	
	public Noeud(Coord coord){
		this.coord = coord;
	}
	
	public Boolean equals(Noeud noeud){
		return (this.coord.getX() == noeud.coord.getX()) && (this.coord.getY() == noeud.coord.getY());
	}

	public Noeud getParent() {
		return parent;
	}


	public void setParent(Noeud parent) {
		this.parent = parent;
	}


	public float getCoutg() {
		return coutg;
	}


	public void setCoutg(float coutg) {
		this.coutg = coutg;
	}


	public float getCouth() {
		return couth;
	}


	public void setCouth(float couth) {
		this.couth = couth;
	}


	public float getCoutf() {
		return coutf;
	}


	public void setCoutf(float coutf) {
		this.coutf = coutf;
	}


	public Coord getCoord() {
		return coord;
	}


	public void setCoord(Coord coord) {
		this.coord = coord;
	}
	
	
}

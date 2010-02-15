/*
 * @(#)Node.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.model;

import java.util.ArrayList;

/**
 * This class stores the Node attributes.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class Node {
	private static int identifier;
	private int id;
	private int x;
	private int y;
	private ArrayList<Node> neighbours;
	private boolean visited;
	private boolean generated;
	
	public Node(int x, int y){
		id = identifier++;
		this.x = x;
		this.y = y;
		this.visited = false;
		this.generated = false;
		neighbours = new ArrayList<Node>();
	}

	public int getId() {
		return id;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void addNeighbour(Node neighbour){
		/* 
		 *  create some more randomness
		 */
		int index = (int)(Math.random()*(neighbours.size()+1));
		neighbours.add(index, neighbour);
	}
	
	public boolean deleteNeighbour(int id){
		for (Node neighbour : neighbours) {
			if(neighbour.getId() == id){
				neighbours.remove(id);
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<Node> getNeighbours(){
		return neighbours;
	} 

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public boolean isGenerated(){
		return generated;
	}
	
	public void setGenerated(boolean generated){
		this.generated = generated;
	}
	
	public static void resetIdentifier(){
		identifier = 0;
	}
}

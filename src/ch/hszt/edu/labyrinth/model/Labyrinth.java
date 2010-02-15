/*
 * @(#)Labyrinth.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.model;

import java.util.ArrayList;

import ch.hszt.edu.labyrinth.core.LabyrinthFactory;

/**
 * This class stores the Labyrinth attributes.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class Labyrinth {
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private int height;
	private int width;
	private int start;
	private int goal;

	public Labyrinth(int height, int width) {
		this.height = height;
		this.width = width;
		LabyrinthFactory labyrinthFactory = new LabyrinthFactory();
		nodes = labyrinthFactory.createLabyrinth(height, width);
	}
	
	public void resetNodes(){
		for (Node node : nodes) {
			node.setVisited(false);
		}
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}
}

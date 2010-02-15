/*
 * @(#)CoordinateSystem.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas M�ller, Linda Hartmann and Andreas Br�nnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas M�ller, Linda Hartmann and Andreas Br�nnimann.
 */

package ch.hszt.edu.labyrinth.model;

/**
 * This class handels the positions that are used to paint the nodes on the panel.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas M�ller, Linda Hartmann and Andreas Br�nnimann
 */


public class CoordinateSystem {
	private int size;
	private int xCoordinate;
	private int yCoordinate;
	private int fieldWith;
	private Labyrinth labyrinth;
	
	public CoordinateSystem(Labyrinth labyrinth, int height, int width, int fieldWith){
		this.labyrinth = labyrinth;
		this.fieldWith = fieldWith;		
		size = height*width;
		xCoordinate = 0;
		yCoordinate = height - fieldWith;
	}

	public void calculateNextPosition() {
		xCoordinate = xCoordinate+ fieldWith;
		
		if(xCoordinate/fieldWith == labyrinth.getWidth()){
			xCoordinate = 0;
			yCoordinate = yCoordinate-fieldWith;
		}			
	}
	
	public int getSize() {
		return size;
	}

	public int getXCoordinate() {
		return xCoordinate;
	}

	public int getYCoordinate() {
		return yCoordinate;
	}

	public int getFieldWith() {
		return fieldWith;
	}
}

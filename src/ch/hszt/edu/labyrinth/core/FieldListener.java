/*
 * @(#)FieldListener.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.hszt.edu.labyrinth.gui.PaintingFactory;
import ch.hszt.edu.labyrinth.model.Labyrinth;

/**
 * This class listens to the Labyrinth Panel and handels the start and goal definitions. 
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class FieldListener implements ActionListener {
	private boolean setStart = true;
	private Labyrinth labyrinth;
	private PaintingFactory paintingFactory;
	private static boolean blocked;
	
	public FieldListener(Labyrinth labyrinth, PaintingFactory paintingFactory){
		this.labyrinth = labyrinth;
		this.paintingFactory = paintingFactory;
	}
	
	public void actionPerformed(ActionEvent e) {		
		if (!blocked) {
			if(setStart){
				/* 
				 * check if start node = goal node
				 */
				if(labyrinth.getStart() != labyrinth.getGoal()){
					/* 
					 * colour the old start node
					 */
					paintingFactory.colourNode(labyrinth.getStart(), Color.WHITE);	
				}
				
				/* 
				 * colour all nodes white
				 */
				paintingFactory.resetNodeColour(labyrinth.getNodes());
				
				/* 
				 * colour the new start node
				 */
				paintingFactory.colourNode(Integer.parseInt(e.getActionCommand()), Color.BLUE);
				labyrinth.setStart(Integer.parseInt(e.getActionCommand()));
			}
			else if(!setStart){
				/* 
				 * check if start node = goal node
				 */
				if(labyrinth.getStart() != labyrinth.getGoal()){
					/* 
					 * colour the old goal node
					 */
					paintingFactory.colourNode(labyrinth.getGoal(), Color.WHITE);
				}
				
				/* 
				 * colour all nodes white
				 */
				paintingFactory.resetNodeColour(labyrinth.getNodes());
				
				/* 
				 * colour the new goal node
				 */
				paintingFactory.colourNode(Integer.parseInt(e.getActionCommand()), Color.GREEN);
				labyrinth.setGoal(Integer.parseInt(e.getActionCommand()));
			}	
			else{
				System.out.println("Der Weg wurde bereits gefunden, bitte generieren Sie ein neues Labyrinth.");
				return;
			}
			setStart = !setStart;
		}
		else{
			System.out.println("Bitte warten Sie, bis der Weg gfunden wurde, bevor  Sie erneut suchen.");
		}
	}
	
	public static void setBlocked(boolean blocked) {
		FieldListener.blocked = blocked;
	}
}

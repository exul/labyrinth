/*
 * @(#)ControlListener.java 1.0 10/02/03
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

import ch.hszt.edu.labyrinth.gui.ControlManager;
import ch.hszt.edu.labyrinth.gui.PaintingFactory;
import ch.hszt.edu.labyrinth.helpers.StopWatch;
import ch.hszt.edu.labyrinth.model.Labyrinth;
import ch.hszt.edu.labyrinth.model.Node;

/**
 * This class listens to the Control Panel and handels all events. It is used
 * to start the generation process and the search process. 
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class ControlListener implements ActionListener {
	private Labyrinth labyrinth;
	private PaintingFactory paintingFactory;
	private ControlManager controlManager;
	private int algorithm;
	private static boolean blocked;

	public ControlListener(ControlManager controlManager) {
		this.controlManager = controlManager;
	}

	public void actionPerformed(ActionEvent e) {
		int start;
		int goal;
		
		/* 
		 * Set variable for choosing the search algorithm
		 */
		if (e.getActionCommand().equals("breathfirst")) {
			algorithm = 0;
		}
		if (e.getActionCommand().equals("deptfirst")) {
			algorithm = 1;
		}

		/* 
		 * Actions for generate button
		 */
		if (e.getActionCommand().equals("generate")) {
			if (!blocked) {			
				/* 
				 * reset the node count, because it's a new labyrinth
				 */
				Node.resetIdentifier();
	
				/* 
				 * create the StopWatch to measure the time
				 */
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				String message;
	
				/* 
				 * create the new labyrinth
				 */
				labyrinth = new Labyrinth(controlManager.getLabyrintheight()
						.getValue(), controlManager.getLabyrinthwidth().getValue());
	
				stopWatch.stop();
				message = "Die Generierung des Labyrinths dauerte "
						+ stopWatch.elapsedTimeMillis() + " Millisekunden.";
	
				stopWatch.start();
				/* 
				 * create the painting factory and paint the labyrinth
				 */
				paintingFactory = new PaintingFactory(labyrinth);
				paintingFactory.paintNodes(controlManager.getFrame(),
						controlManager.getPanel(), 600, 600);
				paintingFactory.paintWalls();
	
				stopWatch.stop();
				message = message + " Das Zeichnen dauerte "
						+ stopWatch.elapsedTimeMillis() + " Millisekunden";
				System.out.println(message);
	
				/* 
				 * set default start and goal
				 */
				start = 0;
				goal =  labyrinth.getNodes().size()-1;
				labyrinth.setStart(start);
				labyrinth.setGoal(goal);
				paintingFactory.colourNode(start, Color.BLUE);
				paintingFactory.colourNode(goal, Color.GREEN);
				
				System.out.println("Durch anklicken der Felder können Sie Start und Ziel ändern.");
				
				/* 
				 * enable the search and delay button
				 */
				controlManager.getDelayValue().setEnabled(true);
				controlManager.getSearchButton().setEnabled(true);
			}
			else{
				System.out.println("Bitte warten Sie, bis der Weg gfunden wurde, bevor  Sie erneut generieren.");
			}
		}

		/* 
		 * Actions for search button
		 */
		if (e.getActionCommand().equals("search")) {
			if (!blocked) {
				/* 
				 * create the walker and a thread (thread is needed because of
				 * the AWT thread)
				 */
				Walker walker = new Walker(labyrinth, paintingFactory);
				Thread t = new Thread(walker);

				/* 
				 * set algorithm and delay
				 */
				walker.setAlgorithm(algorithm);
				walker.setDelay(controlManager.getDelayValue().getValue());

				/* 
				 * start the run
				 */
				t.start();
			}
			else{
				System.out.println("Bitte warten Sie, bis der Weg gfunden wurde, bevor  Sie erneut suchen.");
			}
		}
	}

	public static void setBlocked(boolean blocked) {
		ControlListener.blocked = blocked;
	}
}

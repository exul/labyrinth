/*
 * @(#)StartUp.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.helpers;

import javax.swing.JFrame;

import ch.hszt.edu.labyrinth.gui.ControlManager;
import ch.hszt.edu.labyrinth.gui.WindowFactory;

/**
 * This class starts the application. It creates a Window that contains all he needed components.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class StartUp {
	public static void main(String[] args) {
		/* 
		 * the height and width of the window
		 */
		int frameHeight = 600;
		int frameWidth = 900;

		/* 
		 * the height and width to paint the labyrinth
		 */
		int panelHeight = 600;
		int panelWidth = 600;

		/* 
		 * create the window
		 */
		WindowFactory windowFactory = new WindowFactory();
		JFrame frame = windowFactory.createWindow("Labyrinth", frameHeight,
				frameWidth);
		frame.setResizable(false);

		/* 
		 * create the control panel
		 */
		ControlManager controlManager = new ControlManager(frame);
		controlManager.creatControles(frame, panelHeight, panelWidth);

		frame.setVisible(true);
	}
}

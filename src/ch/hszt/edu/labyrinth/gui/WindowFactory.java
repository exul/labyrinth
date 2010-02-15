/*
 * @(#)WindowFactory.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.gui;

import javax.swing.JFrame;

/**
 * This class creates a window depending on the parameters height and width and returns
 * it as a JFrame object.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class WindowFactory {
	public JFrame createWindow(String title, int height, int width){
		/* 
		 * create and set up the window.
		 */
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width + 15, height + 35);
		frame.setLayout(null);
		return frame;
	}
}

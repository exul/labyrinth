/*
 * @(#)StreamManager.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.helpers;

import java.io.PrintStream;

import javax.swing.JTextArea;

/**
 * This helper class redirects the standard output-stream a textarea in the control panel.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class StreamManager {

	private static PrintStream ps = null;

	public StreamManager(final JTextArea label) {
		ps = new PrintStream(System.out) {
			public void println(String x) {
				label.setText((x + "\n"));
			}
		};
	}

	public PrintStream getPs() {
		return ps;
	}
}


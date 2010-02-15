/*
 * @(#)StopWatch.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas M�ller, Linda Hartmann and Andreas Br�nnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas M�ller, Linda Hartmann and Andreas Br�nnimann.
 */

package ch.hszt.edu.labyrinth.helpers;

/**
 * This helper class is used to measure the time between two certain points of time.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas M�ller, Linda Hartmann and Andreas Br�nnimann
 */

	public class StopWatch {
	    private long start;
	    private long stop;
	    
	    public void start() {
	        start = System.currentTimeMillis();
	    }
	    
	    public void stop() {
	        stop = System.currentTimeMillis();
	    }
	    
	    public long elapsedTimeMillis() {
	        return stop - start;
	    }
	    
	    public String toString() {
	        return "elapsedTimeMillis: " + Long.toString(elapsedTimeMillis()); // print execution time
	    }
	}

/*
 * @(#)StopWatch.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.helpers;

/**
 * This helper class is used to measure the time between two certain points of time.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
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

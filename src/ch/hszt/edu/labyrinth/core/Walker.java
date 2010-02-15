/*
 * @(#)Walker.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import ch.hszt.edu.labyrinth.gui.PaintingFactory;
import ch.hszt.edu.labyrinth.helpers.StopWatch;
import ch.hszt.edu.labyrinth.model.Labyrinth;
import ch.hszt.edu.labyrinth.model.Node;

/**
 * This class contains the two algorithms breadthfirst and depthfirst. The way from start
 * to goal is calculated depending on the selected algorithm.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class Walker extends Thread {
	private ArrayList<Node> nodes;
	private ArrayList<Integer> way = new ArrayList<Integer>();
	private boolean goalFound;
	private static int[] backtrackingTree;
	private int start;
	private int goal;
	private PaintingFactory paintingFactory;
	private int delay;
	private int algorithm;
	private Labyrinth labyrinth;

	public Walker(Labyrinth labyrinth, PaintingFactory paintingFactory){
		nodes = labyrinth.getNodes();
		backtrackingTree = new int[nodes.size()];
		start = labyrinth.getStart();
		goal = labyrinth.getGoal();
		this.labyrinth = labyrinth;
		this.paintingFactory = paintingFactory;
		delay = 1;
		algorithm = 0;
	}
	
	@Override
	public void run() {		
		ControlListener.setBlocked(true);
		FieldListener.setBlocked(true);
		paintingFactory.resetNodeColour(nodes);

		/* 
		 * create the StopWatch to measure the time
		 */
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		switch (algorithm) {
		case 0:
			/* 
			 * start walking
			 */
			try {
					if (breadthfirst(start, goal, paintingFactory, delay)) {
						calculateWay(start, goal, paintingFactory, delay);
					} else {
						System.out.println("No way has been found");
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			break;

		default:
			/* 
			 * start walking
			 */
			try {
					if (depthfirst(start, goal, paintingFactory, delay)) {
						calculateWay(start, goal, paintingFactory, delay);
					} else {
						System.out.println("No way has been found");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			break;
		}
		
		stopWatch.stop();
		System.out.println("Der Weg wurde in " + stopWatch.elapsedTimeMillis() + " Millisekunden gefunden.");
		
		labyrinth.resetNodes();
		
		ControlListener.setBlocked(false);
		FieldListener.setBlocked(false);
	}

	private boolean breadthfirst(int start, int goal,
			PaintingFactory paintingFactory, int delay)
			throws InterruptedException {
		Queue<Node> queue = new LinkedList<Node>();
		ArrayList<Node> neighbours;
		Node parentNode;

		/* 
		 * add start note to queue
		 */
		for (Node node : nodes) {
			if (node.getId() == start) {
				queue.add(node);
				break;
			}
		}

		while (!queue.isEmpty()) {
			/* 
			 * mark actual node as visited
			 */
			queue.peek().setVisited(true);

			/* 
			 * get neigbours
			 */
			neighbours = queue.peek().getNeighbours();

			/* 
			 * goal is reached
			 */
			if (queue.peek().getId() == goal) {
				/* 
				 * sleep a bit, before painting the way
				 */
				Thread.sleep(delay);
				return true;
			}

			/* 
			 * do not colour the start and the goal node
			 */
			if(queue.peek().getId() != labyrinth.getStart() && queue.peek().getId() != labyrinth.getGoal()){
				/* 
				 * paint node to show the way
				 */
				paintingFactory.colourNode(queue.peek().getId(), Color.GRAY);
			}

			/* 
			 * sleep to show how the walking works
			 */
			Thread.sleep(delay);

			/* 
			 * save last node to know the parent node
			 */
			parentNode = queue.poll();

			for (Node neighbour : neighbours) {
				if (!neighbour.isVisited()) {
					queue.add(neighbour);

					backtrackingTree[neighbour.getId()] = parentNode.getId();
				}
			}
		}

		/* 
		 * no way has been found
		 */
		return false;
	}

	private boolean depthfirst(int start, int goal,
			PaintingFactory paintingFactory, int delay)
			throws InterruptedException {
		goalFound = false;

		/* 
		 * do depthfirstStep with start-node
		 */
		for (Node node : nodes) {
			if (node.getId() == start) {
				node.setVisited(true);
				/* 
				 * do not colour the start and the goal node
				 */
				if(node.getId() != labyrinth.getStart() && node.getId() != labyrinth.getGoal()){
					/* 
					 * paint node to show the way
					 */
					paintingFactory.colourNode(node.getId(), Color.GRAY);
				}
				
				Thread.sleep(delay);
				depthfirstStep(node, goal, paintingFactory, delay);
			}
		}

		return goalFound;
	}

	private void depthfirstStep(Node node, int goal,
			PaintingFactory paintingFactory, int delay)
			throws InterruptedException {
		Node parentNode = node;

		for (Node neighbour : node.getNeighbours()) {
			if (!neighbour.isVisited() && !goalFound) {
				neighbour.setVisited(true);
				backtrackingTree[neighbour.getId()] = parentNode.getId();

				/* 
				 * goal is reached
				 */
				if (neighbour.getId() == goal) {
					goalFound = true;
				}
				
				/* 
				 * do not colour the start and the goal node
				 */
				if(neighbour.getId() != labyrinth.getStart() && neighbour.getId() != labyrinth.getGoal()){
					/* 
					 * paint node to show the way
					 */
					paintingFactory.colourNode(neighbour.getId(), Color.GRAY);
				}
				
				Thread.sleep(delay);
				depthfirstStep(neighbour, goal, paintingFactory, delay);

			}
		}
	}

	private void calculateWay(int start, int goal,
			PaintingFactory paintingFactory, int delay)
			throws InterruptedException {
		int position;

		position = goal;
		while (true) {
			/* 
			 * add position to the way
			 */
			way.add(position);

			/* 
			 * check if the starting point is reached
			 */
			if (position != start) {
				position = backtrackingTree[position];
			} else {
				break;
			}
		}

		/* 
		 * print the way
		 */
		for (int i = way.size() - 1; i >= 0; i--) {
			if(way.get(i) != labyrinth.getStart() && way.get(i) != labyrinth.getGoal()){
				paintingFactory.colourNode(way.get(i), Color.RED);
			}
			Thread.sleep(delay);
		}
	}

	
	public int getDelay() {
		return delay;
	}

	
	public void setDelay(int delay) {
		this.delay = delay;
	}

	
	public int getAlgorithm() {
		return algorithm;
	}

	
	public void setAlgorithm(int algorithm) {
		this.algorithm = algorithm;
	}
}

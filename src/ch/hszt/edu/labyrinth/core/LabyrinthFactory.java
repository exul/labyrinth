/*
 * @(#)LabyrinthFactory.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.core;

import java.util.ArrayList;

import ch.hszt.edu.labyrinth.model.Node;

/**
 * This class generates the labyrinth depending on the parameters height and width.
 * It returns an ArrayList that represents the labyrinth. 
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class LabyrinthFactory {
	
	public ArrayList<Node> createLabyrinth(int height, int width) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		/* 
		 * initalize nodes
		 */
		Node tempNode;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				tempNode = new Node(j%width, i%height);
				nodes.add(tempNode);	
			}
		}
				
		/* 
		 * create the nodeBucket and variables for the random numbers
		 */
		ArrayList<Node> nodeBucket = new ArrayList<Node>();
		int randomStartNr;
		int randomNodeNr; 
		
		/* 
		 * current node
		 */
		Node currentNode;
		
		/* 
		 * neighbours
		 */
		ArrayList<Node> neighbours;
		Node currentNeighbour;
		
		/* 
		 * next neighbours (this are the neighbours neighbours ;-)
		 */
		ArrayList<Node> nNeighbours;
		
		/* 
		 * add a random node as start node
		 */
		randomStartNr = (int) (Math.random()*nodes.size());
		nodeBucket.add(nodes.get(randomStartNr));
		
		while (!nodeBucket.isEmpty()) {
			/* 
			 * take a node out of the bucket
			 */
			randomNodeNr = (int) (Math.random()*nodeBucket.size());
			
			/* 
			 * get current node and remove it from the list
			 */
			currentNode = nodeBucket.get(randomNodeNr);
			nodeBucket.remove(randomNodeNr);			
			
			/* 
			 * get all neighbours of the current node (doesn't matter if they are connected or not)
			 */
			neighbours = getNeighbours(currentNode, nodes, height, width);
			
			/* 
			 * loop through all neighbours
			 */
			for(int i = 0; i < neighbours.size(); i++){								
				/* 
				 * get the current neighbour
				 */
				currentNeighbour = neighbours.get(i);
				
				/* 
				 * get these neighbours neighbours
				 */
				nNeighbours = currentNeighbour.getNeighbours();
				
				
				/* 
				 * if there are no neighbours in the list, the node is blocked
				 */
				if(nNeighbours.size() < 1){
					/* 
					 * set connection to the neighbour
					 */
					currentNode.addNeighbour(currentNeighbour);
									
					/* 
					 * set connection backwards
					 */
					currentNeighbour.addNeighbour(currentNode);					
				}
				
				/* 
				 * add the neighbour to the bucket
				 */
				if(!currentNeighbour.isGenerated()){
					nodeBucket.add(currentNeighbour);
					currentNeighbour.setGenerated(true);
				}
			}	
		}
		return nodes;
	}

	private ArrayList<Node> getNeighbours(Node node, ArrayList<Node> nodes, int height, int width) {
		int nodeID = node.getId();
		ArrayList<Node> neighbours = new ArrayList<Node>();
		
		/* 
		 * check if the node is on the left border
		 */
		if(node.getX() != 0){
			neighbours.add(nodes.get(nodeID-1));
		}
		
		/* 
		 * check if the node is on the right border
		 */
		if(node.getX() != width-1){
			neighbours.add(nodes.get(nodeID+1));
		}
		
		/* 
		 * check if the node is on the bottom border
		 */
		if(node.getY() != 0){
			neighbours.add(nodes.get(nodeID-width));
		}
		
		/* 
		 * check if the node is on the top border
		 */
		if(node.getY() != height-1){
			neighbours.add(nodes.get(nodeID+width));
		}
		
		return neighbours;
	}
}

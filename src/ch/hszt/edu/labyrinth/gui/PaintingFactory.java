/*
 * @(#)PaintingFactory.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.hszt.edu.labyrinth.core.FieldListener;
import ch.hszt.edu.labyrinth.model.CoordinateSystem;
import ch.hszt.edu.labyrinth.model.Labyrinth;
import ch.hszt.edu.labyrinth.model.Node;

/**
 * This class is responsible for all painting tasks. It colours the walls as well as the nodes.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class PaintingFactory {
	private ArrayList<Node> nodes;
	private JButton[] fields;
	private Labyrinth labyrinth;

	public PaintingFactory(Labyrinth labyrinth) {
		nodes = labyrinth.getNodes();
		fields = new JButton[nodes.size()];
		this.labyrinth = labyrinth;
	}

	public void paintNodes(JFrame frame, JPanel panel, int height, int width) {
		FieldListener fieldListener = new FieldListener(labyrinth, this);
		int fieldWidth;		
	
		/* 
		 * calculate size
		 */
		if(height/labyrinth.getHeight() < width/labyrinth.getWidth()){
			fieldWidth = height/labyrinth.getHeight();			
		}
		else{
			fieldWidth = width/labyrinth.getWidth();			
		}
		
		CoordinateSystem coordinateSystem = new CoordinateSystem(labyrinth, height, width,
				fieldWidth);
		int csFieldWith = coordinateSystem.getFieldWith();
		int xCoordinate;
		int yCoordinate;

		/* 
		 * create labyrinth panel
		 */
		panel.removeAll();
		panel.repaint();
		panel.setBounds(0, 0, width, height);
		panel.setLayout(null);
		frame.add(panel);

		for (Integer i = 0; i < nodes.size(); i++) {
			/* 
			 * get x and y coordinate
			 */
			xCoordinate = coordinateSystem.getXCoordinate();
			yCoordinate = coordinateSystem.getYCoordinate();

			/* 
			 * create a label for each node
			 */
			fields[i] = new JButton();

			/* 
			 * setFontSize
			 */
			Font curFont = fields[i].getFont();
			fields[i].setFont(new Font(curFont.getFontName(), curFont
					.getStyle(), 10));

			/* 
			 * set alignement
			 */
			fields[i].setHorizontalAlignment(JButton.CENTER);
			fields[i].setVerticalAlignment(JButton.CENTER);

			/* 
			 * background color
			 */
			fields[i].setBackground(Color.WHITE);

			/* 
			 * background should not be transparent
			 */
			fields[i].setOpaque(true);

			/* 
			 * set position and size
			 */
			fields[i].setBounds(xCoordinate, yCoordinate, csFieldWith,
					csFieldWith);
			
			/* 
			 * set listener
			 */
			fields[i].addActionListener(fieldListener);
			fields[i].setActionCommand(i.toString());

			panel.add(fields[i]);

			/* 
			 * next position
			 */
			coordinateSystem.calculateNextPosition();
		}

		/* 
		 * Display the window.
		 */
		frame.setVisible(true);
	}

	public void paintWalls() {
		ArrayList<Node> neighbours;
		int wLeft, wRight, wBottom, wTop;

		for (Node node : nodes) {
			neighbours = node.getNeighbours();
			wLeft = 1;
			wRight = 1;
			wBottom = 1;
			wTop = 1;

			for (Node neighbour : neighbours) {
				if (neighbour.getX() == node.getX() - 1) {
					wLeft = 0;
				} else if (neighbour.getX() == node.getX() + 1) {
					wRight = 0;
				} else if (neighbour.getY() == node.getY() + 1) {
					wTop = 0;
				} else if (neighbour.getY() == node.getY() - 1) {
					wBottom = 0;
				}
			}

			fields[node.getId()].setBorder(BorderFactory.createMatteBorder(
					wTop, wLeft, wBottom, wRight, Color.black));
		}
	}

	public void colourNode(int nodeId, Color color) {
			fields[nodeId].setBackground(color);
	}
	
	public void resetNodeColour(ArrayList<Node> nodes){
		for (Node node : nodes) {
			if(node.getId() != labyrinth.getStart() && node.getId() != labyrinth.getGoal()){
				fields[node.getId()].setBackground(Color.WHITE);
			}
		}
	}
}
/*
 * @(#)ControlManager.java 1.0 10/02/03
 *
 * Copyright (c) 2008-2009 Andreas Müller, Linda Hartmann and Andreas Brönnimann
 * All rights reserved.
 * 
 * The original version of this source code and documentation is copyrighted
 * and owned by Andreas Müller, Linda Hartmann and Andreas Brönnimann.
 */

package ch.hszt.edu.labyrinth.gui;

import java.awt.Color;
import java.util.Hashtable;
import javax.swing.*;

import ch.hszt.edu.labyrinth.core.ControlListener;
import ch.hszt.edu.labyrinth.helpers.StreamManager;
import ch.hszt.edu.labyrinth.model.Labyrinth;

/**
 * This class contains all the visible parts that are used at the control panel.
 * It passes all events to the <cod>ControlListener<code>.
 * 
 * @version 1.0 2 February 2010
 * @author Andreas Müller, Linda Hartmann and Andreas Brönnimann
 */

public class ControlManager{
	JFrame frame;
	Labyrinth labyrinth;
	JSlider labyrinthwidth = new JSlider(JSlider.HORIZONTAL, 10, 100, 20);
	JSlider labyrintheight = new JSlider(JSlider.HORIZONTAL, 10, 100, 20);
	JSlider delayValue = new JSlider(JSlider.HORIZONTAL, 0, 1000, 10);
	JButton generateButton = new JButton("Generiere Labyrinth");
	JRadioButton breathfirstButton = new JRadioButton("Breitensuche");
	JRadioButton deptfirstButton = new JRadioButton("Tiefensuche");
	JButton searchButton = new JButton("Suche Weg");
	JPanel panel;
	PaintingFactory paintingFactory;
	int algorithm = 0;

	public ControlManager(JFrame frame) {
		this.frame = frame;
		panel = new JPanel();
	}

	public void creatControles(JFrame frame, int height, int width) {
		ControlListener controlListener = new ControlListener(this);
		
		/* 
		 * create panels
		 */
		JPanel generatePanel = new JPanel();
		generatePanel.setBounds(width + 5, 5, 294, 250);
		generatePanel.setLayout(null);
		generatePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(width + 5, 270, 294, 250);
		searchPanel.setLayout(null);
		searchPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		/* 
		 * create output-box
		 */
		JTextArea taOutput = new JTextArea();
		taOutput.setBounds(width+5, 535, 294, 50);
		taOutput.setBackground(Color.WHITE);
		taOutput.setDisabledTextColor(Color.BLACK);	
		taOutput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		taOutput.setLineWrap(true);
		taOutput.setEnabled(false);

		/* 
		 * Create labels
		 */
		JLabel labelsize = new JLabel("Labyrinthgrösse");
		labelsize.setBounds(10, 10, 150, 20);
		labelsize.setForeground(Color.BLUE);
		generatePanel.add(labelsize);

		JLabel labelwidth = new JLabel("Breite:");
		labelwidth.setBounds(10, 40, 150, 20);
		generatePanel.add(labelwidth);

		JLabel labelheight = new JLabel("Höhe:");
		labelheight.setBounds(10, 110, 150, 20);
		generatePanel.add(labelheight);

		/* 
		 * Create the size sliders
		 */
		labyrinthwidth.setBounds(10, 60, 270, 45);
		generatePanel.add(labyrinthwidth);
		labyrintheight.setBounds(10, 130, 270, 45);
		generatePanel.add(labyrintheight);

		/* 
		 * Create the label table for size sliders
		 */
		Hashtable<Integer, JLabel> labelTableSize = new Hashtable<Integer, JLabel>();
		labelTableSize.put(new Integer(10), new JLabel("10"));
		labelTableSize.put(new Integer(55), new JLabel("55"));
		labelTableSize.put(new Integer(100), new JLabel("100"));

		/* 
		 * Turn on labels at major tick marks.
		 */
		labyrinthwidth.setLabelTable(labelTableSize);
		labyrinthwidth.setMinorTickSpacing(10);
		labyrinthwidth.setPaintTicks(true);
		labyrinthwidth.setPaintLabels(true);
		labyrintheight.setLabelTable(labelTableSize);
		labyrintheight.setMinorTickSpacing(10);
		labyrintheight.setPaintTicks(true);
		labyrintheight.setPaintLabels(true);

		/* 
		 * Create the generate Button
		 */
		generateButton.setBounds(45, 200, 200, 40);
		generateButton.addActionListener(controlListener);
		generateButton.setActionCommand("generate");
		generatePanel.add(generateButton);

		/* 
		 * Create labels
		 */
		JLabel labelsearch = new JLabel("Suche Weg!");
		labelsearch.setBounds(10, 10, 150, 20);
		labelsearch.setForeground(Color.BLUE);
		searchPanel.add(labelsearch);

		JLabel labelalgorithm = new JLabel("Wähle Algorithmus:");
		labelalgorithm.setBounds(10, 40, 150, 20);
		searchPanel.add(labelalgorithm);

		JLabel labeldelay = new JLabel("Anzeigeverzögerung [ms]:");
		labeldelay.setBounds(10, 120, 270, 20);
		searchPanel.add(labeldelay);

		/* 
		 * Create the radio buttons
		 */
		breathfirstButton.setBounds(10, 60, 150, 25);
		breathfirstButton.setActionCommand("breathfirst");
		breathfirstButton.setSelected(true);
		searchPanel.add(breathfirstButton);

		deptfirstButton.setBounds(10, 85, 150, 25);
		deptfirstButton.setActionCommand("deptfirst");
		searchPanel.add(deptfirstButton);

		/* 
		 * Group the radio buttons.
		 */
		ButtonGroup group = new ButtonGroup();
		group.add(breathfirstButton);
		group.add(deptfirstButton);

		/* 
		 * Register a listener for the radio buttons.
		 */
		breathfirstButton.addActionListener(controlListener);
		deptfirstButton.addActionListener(controlListener);

		/* 
		 * Create the label table for delay sliders
		 */
		Hashtable<Integer, JLabel> labelTableDelay = new Hashtable<Integer, JLabel>();
		labelTableDelay.put(new Integer(1), new JLabel("0"));
		labelTableDelay.put(new Integer(500), new JLabel("500"));
		labelTableDelay.put(new Integer(1000), new JLabel("1000"));

		/* 
		 * Create the delay sliders
		 */
		delayValue.setBounds(10, 140, 270, 45);
		delayValue.setLabelTable(labelTableDelay);
		delayValue.setMinorTickSpacing(50);
		delayValue.setPaintTicks(true);
		delayValue.setPaintLabels(true);
		searchPanel.add(delayValue);

		/* 
		 * JButton searchButton = new JButton("Suche Weg");
		 */
		searchButton.setBounds(45, 200, 200, 40);
		searchButton.addActionListener(controlListener);
		searchButton.setActionCommand("search");
		searchButton.setEnabled(false);
		searchPanel.add(searchButton);

		frame.add(generatePanel);
		frame.add(searchPanel);
		frame.add(taOutput);
		
		/* 
		 *  redirect the system.out.println to the label
		 */
		StreamManager streamManager = new StreamManager(taOutput);
		System.setOut(streamManager.getPs());

	}

	public JFrame getFrame() {
		return frame;
	}
	
	public JPanel getPanel() {
		return panel;
	}

	public JSlider getLabyrinthwidth() {
		return labyrinthwidth;
	}

	public JSlider getLabyrintheight() {
		return labyrintheight;
	}

	public JSlider getDelayValue() {
		return delayValue;
	}

	public JButton getGenerateButton() {
		return generateButton;
	}

	public JButton getSearchButton() {
		return searchButton;
	}	
}

package com.grobster.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ZipperGui implements FileZipperObserver {
	private JLabel inLabel;
	private JLabel outLabel;
	private JTextField inField;
	private JTextField outField;
	private JButton runButton;
	private JProgressBar progress;
	private JLabel fileNameLabel;
	private JLabel statusSizeLabel;
	private JLabel toLabel;
	private JLabel sizeLabel;
	
	private JPanel mainPanel;
	private JPanel inPanel;
	private JPanel outPanel;
	private JPanel sizePanel;
	private JPanel fileNamePanel;
	private JPanel runButtonPanel;
	private JPanel progressPanel;
	
	private JFrame frame;
	private FileZipper zipper;
	private long fileSize;
	private int progressLevel;
	
	public ZipperGui(FileZipper zipper) {
		this.zipper = zipper;
		zipper.addObserver((FileZipperObserver)this);
	}
	
	public void createView() {
		frame = new JFrame("Zipper");
		frame.setSize(300, 300);
		
		// components
		inLabel = new JLabel("In:");
		outLabel = new JLabel("Out:");
		inField = new JTextField(25);
		outField = new JTextField(25);
		runButton = new JButton("Run");
		runButton.addActionListener(new RunButtonListener());
		progress = new JProgressBar(0, 100);
		progress.setValue(0);
		progress.setStringPainted(true);
		fileNameLabel = new JLabel("File ...");
		statusSizeLabel = new JLabel("0");
		toLabel = new JLabel("  of ");
		sizeLabel = new JLabel("0");
		
		
		//panels
		mainPanel = new JPanel();
		inPanel = new JPanel();
		outPanel = new JPanel();
		sizePanel = new JPanel();
		fileNamePanel = new JPanel();
		runButtonPanel = new JPanel();
		progressPanel = new JPanel();
		
		inPanel.add(inLabel);
		inPanel.add(inField);
		
		outPanel.add(outLabel);
		outPanel.add(outField);
		
		sizePanel.add(statusSizeLabel);
		sizePanel.add(toLabel);
		sizePanel.add(sizeLabel);
		runButtonPanel.add(runButton);
		
		
		fileNamePanel.add(fileNameLabel);
		progressPanel.add(progress);
		
		//panel layouts
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//inPanel.setLayout(new BoxLayout(inPanel, BoxLayout.X_AXIS));
		//outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.X_AXIS));
		//sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.X_AXIS));
		
		mainPanel.add(inPanel);
		mainPanel.add(outPanel);
		mainPanel.add(runButtonPanel);
		mainPanel.add(progressPanel);
		mainPanel.add(fileNamePanel);
		mainPanel.add(sizePanel);

		
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public void update() {
		if(fileSize != zipper.fileSize()) {
			fileSize += zipper.getDataCount();
			statusSizeLabel.setText(String.valueOf(fileSize));
			progressLevel = (int) (fileSize * 100L / zipper.fileSize());
			progress.setValue(progressLevel);
			progress.setStringPainted(true);
			System.out.println("Current Data Size: " + fileSize);
			if(fileSize == zipper.fileSize()) {
				runButton.setEnabled(true);
			}
		}
	}
	
	class RunButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(inField.getText() != "" && outField.getText() != "") {
				zipper.setIn(inField.getText().trim());
				zipper.setOut(outField.getText().trim());
				fileSize = 0;
				fileNameLabel.setText(zipper.getFileBeingZipped());
				sizeLabel.setText(String.valueOf(zipper.fileSize()));
				runButton.setEnabled(false);

				
				SwingWorker sw = new SwingWorker() {
					public Object doInBackground() {
						zipper.compress();
						return null;
					}
				};
				sw.execute();
			}
		}
	}
}
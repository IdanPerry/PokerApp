package com.idan.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.idan.client.ClientConnection;

/**
 * This class represents the lobby of the poker room in which the player can
 * choose a table to play at.
 * 
 * @author Idan Perry
 * @version 03.05.2013
 *
 */

public class Lobby extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 500;
	private static final int HEIGHT = 250;	
	
	private final ClientConnection clientConnection;
	private final JPanel mainPanel;
	private final TableWindow tableGUI;
	private JButton openTable;
	
	/**
	 * Constructs the lobby of the poker room.
	 * 
	 * @param clientConnection the connection of the player to establish
	 * 						   with this lobby.
	 */
	public Lobby(ClientConnection clientConnection) {
		super("Logged in as " + clientConnection.getPlayer().getName());
		this.clientConnection = clientConnection;		
		tableGUI = new TableWindow(clientConnection);
		mainPanel = new JPanel();
		
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
			
		initComponnents();
		add(mainPanel, BorderLayout.CENTER);
	}
	
	/*
	 * Initializes the components of this panel.
	 */
	private void initComponnents() {	
		openTable = new JButton("Open Table");
		openTable.addActionListener(this);
		
		mainPanel.setBackground(Color.WHITE);	
		mainPanel.setBorder(BorderFactory.createTitledBorder("Choose table"));
		mainPanel.add(openTable);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				tableGUI.setVisible(true);
				clientConnection.setTableGUI(tableGUI);
			}
		});
	}
}

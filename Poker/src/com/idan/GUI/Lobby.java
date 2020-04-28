package com.idan.GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

public class Lobby extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private final ClientConnection clientConnection;
	private final JFrame lobbyFrame;
	private final TableWindow tableGUI;
	private JButton openTable;
	
	/**
	 * Constructs the lobby of the poker room.
	 * 
	 * @param clientConnection the connection of the player to establish
	 * 						   with this lobby.
	 */
	public Lobby(ClientConnection clientConnection) {
		this.clientConnection = clientConnection;		
		lobbyFrame = new JFrame("Logged in as " + clientConnection.getPlayer().getName());
		tableGUI = new TableWindow(clientConnection);
		setBorder(BorderFactory.createTitledBorder("Choose table"));
		
		customizeFrame();
		initComponnents();
	}

	public JFrame getLobbyFrame() {
		return lobbyFrame;
	}
	
	/*
	 * Customizes the frame properties.
	 */
	private void customizeFrame() {
		lobbyFrame.setSize(500, 250);
		lobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lobbyFrame.setResizable(false);
		lobbyFrame.setLayout(new FlowLayout());
		lobbyFrame.setContentPane(this);
	}

	/*
	 * Initializes the components of this panel.
	 */
	private void initComponnents() {	
		openTable = new JButton("Open Table");
		openTable.addActionListener(this);
		lobbyFrame.add(openTable);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				tableGUI.getTableFrame().setVisible(true);
				clientConnection.setTableGUI(tableGUI);
			}
		});
	}
}

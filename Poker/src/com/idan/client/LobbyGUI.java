package com.idan.client;

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

public class LobbyGUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JFrame lobbyFrame;
	private JButton openTable;
	
	private ClientConnection clientConnection;
	private TableGUI tableGUI;
	
	public LobbyGUI(ClientConnection c) {
		this.clientConnection = c;
		initComponnents();
	}
	
	public JFrame getLobbyFrame() {
		return lobbyFrame;
	}
	
	private void initComponnents() {
		lobbyFrame = new JFrame("Logged in as " + clientConnection.getPlayer().getName());
		lobbyFrame.setBounds(200, 200, 500, 250);
		lobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lobbyFrame.setResizable(false);
		
		setBorder(BorderFactory.createTitledBorder("Choose table"));
		
		openTable = new JButton("Open Table");
		openTable.addActionListener(this);
		
		lobbyFrame.setLayout(new FlowLayout());
		lobbyFrame.setContentPane(this);
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
				try {
					tableGUI = new TableGUI(clientConnection);
					tableGUI.getTableFrame().setVisible(true);
					clientConnection.setTableGUI(tableGUI);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

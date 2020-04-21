package com.idan.client;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.idan.game.Player;

public class Client {
	private static final String IP = "localHost";
	private static final int PORT = 1342;
	
	private Socket socket;
	private ClientConnection clientConnection;
	private Player player;
	
	private int clientId;

	public Client() {
		// TODO: get the real name/screen name from database
		String name = JOptionPane.showInputDialog("Enter your name");
		player = new Player(name);
		player.setChips(10000);
		
		System.out.println("Logged in as " + player.getName());

		try {
			socket = new Socket(IP, PORT);			
			clientConnection = new ClientConnection(socket, player);
			clientConnection.start();

		} catch (IOException e) {
			System.err.println("Server is not connected, you were logged out. \nPlease connect the server first and try again.");
		}
	}
	
	/**
	 * Returns client id number.
	 * @return client id number
	 */
	public int getClientId() {
		return clientId;
	}
}

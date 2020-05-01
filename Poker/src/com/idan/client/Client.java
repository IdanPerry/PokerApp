package com.idan.client;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.idan.game.Player;

public class Client {
	private static final String IP = "localHost";
	private static final int PORT = 1342;

	/**
	 * Constructs a Client object.
	 * Initializes player's name and a client connection to the server.
	 */
	public Client() {
		// TODO: get the real name/screen name from database
		String name = JOptionPane.showInputDialog("Enter your name");
		Player player = new Player(name);
		player.setChips(10000);
		
		System.out.println("Logged in as " + player.getName());

		try {
			Socket socket = new Socket(IP, PORT);			
			ClientConnection clientConnection = new ClientConnection(socket, player);
			clientConnection.start();

		} catch (IOException e) {
			System.err.println("Server is not connected, you were logged out. \nPlease connect the server first and try again.");
		}
	}
	
	/**
	 * Constructs a Client object with the specified player name.
	 * Initializes player's name and a client connection to the server.
	 * 
	 * @param name the name of the player
	 */
	public Client(String name) {
		Player player = new Player(name);
		player.setChips(10000);
		
		System.out.println("Logged in as " + player.getName());

		try {
			Socket socket = new Socket(IP, PORT);			
			ClientConnection clientConnection = new ClientConnection(socket, player);
			clientConnection.start();

		} catch (IOException e) {
			System.err.println("Server is not connected, you were logged out. \nPlease connect the server first and try again.");
		}
	}
}

package com.idan.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.idan.game.*;

/**
 * This class represents the game server.
 * 
 * @author Idan Perry
 * @version 04.05.2020
 */

public class Server {
	private static final int PORT = 1342;
	
	private ServerSocket serverSocket;
	private final ArrayList<ServerConnection> connections;

	/**
	 * Constructs the game server.
	 */
	public Server() {
		connections = new ArrayList<ServerConnection>();	
		boolean running = true;

		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server is connected");
			HeadsUpTable table = new HeadsUpTable(1, this);
			table.start();

			while (running) {
				Socket clientSocket = serverSocket.accept();

				// New connection with the a new client
				ServerConnection serverConnection = new ServerConnection(clientSocket, this, table);
				connections.add(serverConnection);
				
				// TableInformation is the serialized object.
				serverConnection.setTableInfo(table.getTableInfo());
				serverConnection.start();
			}

		} catch (IOException e) {
			System.err.println("Server was disconnected");
		}
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Server wasn't closed properly.");
		}
	}
	
	/**
	 * Returns connections list of this server.
	 * 
	 * @return connections list of this server
	 */
	public ArrayList<ServerConnection> getConnections() {
		return connections;
	}
}

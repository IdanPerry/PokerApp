package com.idan.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.idan.game.Table;

public class Server {
	private ServerSocket serverSocket;
	private ArrayList<ServerConnection> connections;

	private static final int PORT = 1342;

	public Server() {
		boolean running = true;

		connections = new ArrayList<ServerConnection>();

		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server is connected");
			Table table = new Table(1);
			table.start();

			while (running) {
				Socket clientSocket = serverSocket.accept();

				// New connection with the a new client
				ServerConnection serverConnection = new ServerConnection(clientSocket, this, table);
				connections.add(serverConnection);
				
				// TableInformation is the object the is being serialized.
				serverConnection.setTableInfo(table.getTableInfo());
				serverConnection.start();
			}

		} catch (IOException e) {
			System.err.println("Server was disconnected");
		}
	}
	
	public ArrayList<ServerConnection> getConnections() {
		return connections;
	}
}

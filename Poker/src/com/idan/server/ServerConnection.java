package com.idan.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.idan.game.*;

/**
 * This class represents a server side connection.
 * 
 * @author Idan Perry
 * @version 04.05.2020
 */

public class ServerConnection extends Thread {
	private static final int MIN_PLAYERS = 2;
	private final Socket socket;
	private final Server server;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;

	private final HeadsUpTable table;
	private TableInformation tableInfo;
	private Player player;
	private String actionInput;
	private int betSize;
	private boolean connected;
	private boolean running;

	/**
	 * Constructs a ServerConnection object.
	 * 
	 * @param socket the socket to connect through
	 * @param server
	 * @param table
	 */
	public ServerConnection(Socket socket, Server server, HeadsUpTable table) {
		super("Server_Connection_Thread");
		this.socket = socket;
		this.server = server;
		this.table = table;
		actionInput = "";
		running = true;
	}

	/**
	 * Returns this connection ObjectInputStream.
	 * 
	 * @return this connection ObjectInputStream
	 */
	public ObjectInputStream getObjectInput() {
		return objectInput;
	}

	/**
	 * Returns this connection ObjectOutputStream.
	 * 
	 * @return this connection ObjectOutputStream
	 */
	public ObjectOutputStream getObjectOutput() {
		return objectOutput;
	}

	/**
	 * Sets the table information for this connection. this an object containing the
	 * information needed to transfer between the client and the server.
	 * 
	 * @param tableInfo the table information to be set for this connection.
	 */
	public void setTableInfo(TableInformation tableInfo) {
		this.tableInfo = tableInfo;
	}

	/*
	 * Sends the player all necessary information about the table he sits at.
	 * Parameter actionInput is the last action commited by a player at the table.
	 */
	private void sendInfoToClient(String actionOutput) {
		tableInfo.setNumOfPlayersInHand(table.getPlayersInHand().size());
		tableInfo.setPlayer(player);

		try {
			objectOutput.writeObject(actionOutput);
			objectOutput.writeObject(tableInfo);
			objectOutput.flush();
			objectOutput.reset();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Sends all players sitting at the table, all necessary information about this
	 * table. Parameter actionInput is the last action commited by a player at the
	 * table.
	 */
	private void sendInfoToAllClients(String actionOutput) {
		for (int i = 0; i < server.getConnections().size(); i++) {
			server.getConnections().get(i).sendInfoToClient(actionOutput);
		}
	}

	/*
	 * Initializes the object intput and output streams.
	 */
	private void openObjectStream() {
		try {
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectInput = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

		connected = true;
	}

	/*
	 * Recieves a player object from client as he sits in the table assosiated with
	 * this connection.
	 */
	private void readPlayer() {
		try {
			player = (Player) objectInput.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Recieves the last action commited by a player at the table, and the bet size
	 * of the action if there was any.
	 */
	private void readActionInput() {
		try {			
			actionInput = (String) objectInput.readObject();
			betSize = (int) objectInput.readObject();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Checks what street the current hand is in.
	 */
	private void checkStreet() {
		if (table.isHoleCardsWereDealt()) {
			tableInfo.setPlayers(table.getPlayersInHand());
			sendInfoToAllClients("New Hand");
			table.setHoleCardsWereDealt(false);

		} else if (table.isFlopWasDealt()) {
			tableInfo.setFlop(table.getDealer().getFlop());
			sendInfoToAllClients("FLOP");

		} else if (table.isTurnWasDealt()) {
			tableInfo.setTurn(table.getDealer().getTurn());
			sendInfoToAllClients("TURN");

		} else if (table.isRiverWasDealt()) {
			tableInfo.setRiver(table.getDealer().getRiver());
			sendInfoToAllClients("RIVER");
		}
	}

	/*
	 * Checks the last action recieved from the player.
	 */
	private void checkAction() {
		if (actionInput.equals("fold")) {
			player.fold();
			sendInfoToAllClients(player.getName() + " folds");

		} else if (actionInput.equals("call")) {
			player.call(tableInfo.getBet() - tableInfo.getSmallBlind());
			sendInfoToAllClients(player.getName() + " calls");

		} else if (actionInput.equals("check")) {
			player.check();
			sendInfoToAllClients(player.getName() + " checks");

		} else if (actionInput.equals("bet")) {
			player.bet(betSize);
			sendInfoToAllClients(player.getName() + " bets " + betSize);

		} else if (actionInput.equals("raise")) {
			player.raise(betSize);
			sendInfoToAllClients(player.getName() + " raises " + betSize);

		} else if (actionInput.equals("leave")) {
			running = false;
			table.getPlayersInHand().remove(player);
			System.out.println(player + " left the table");

//			try {
//				objectOutput.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	/*
	 * Wait for players to seat in the table to require minimum
	 * players to start the game.
	 */
	private synchronized void waitForOtherPlayers() {
		while (table.getTablePlayers().size() < MIN_PLAYERS) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void proceed() {
		notifyAll();
	}

	@Override
	public void run() {
		openObjectStream();

		while (connected) {
			// Server gets the player data and states as he connects to the server.
			readPlayer();

			// Player joins the table
			table.seatPlayer(player);
			waitForOtherPlayers();

			// Game is in process. each iteration checks what street the current
			// hand is in and waits for this player's (who assosiates with
			// this connection) action.
			while (running) {
				checkStreet();
				// Server is waiting for players actions, represented by strings here
				readActionInput();
				table.continueRun();
				checkAction();
			}
		}
	}
}

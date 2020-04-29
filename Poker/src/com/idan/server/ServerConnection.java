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
 * @version 03.05.2013
 */

public class ServerConnection extends Thread {
	private final Socket socket;
	private final Server server;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	
	private final HeadsUpTable table;
	private TableInformation tableInfo;	
	private Player player;	
	private String actionInput;
	private int betSize;
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
		actionInput  = "";
		running = true;
	}
	
	public void setTableInfo(TableInformation tableInfo) {
		this.tableInfo = tableInfo;
	}

	private void openObjectStream() {
		try {
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectInput = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendInfoToClient() {
		try {
			objectOutput.writeObject("");
			objectOutput.writeObject(tableInfo);
			objectOutput.flush();
			objectOutput.reset();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	private void sendInfoToAllClients(String actionOutput) {
		for (int i = 0; i < server.getConnections().size(); i++) {
			server.getConnections().get(i).sendInfoToClient(actionOutput);
		}
	}

	private void readPlayer() {
		try {
			player = (Player) objectInput.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	private void readActionInput() {
		try {
			actionInput = (String) objectInput.readObject();
			betSize = (int) objectInput.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		openObjectStream();

		// Server gets the player data and states as he connects to the server.
		readPlayer();

		// Player joins the table
		table.seatPlayer(player);

		// Game is in process, every player at the table is dealt hole cards
		// only when at least 2 players are seating at the table - new hand will
		// be dealt and run
		while (table.getTablePlayers().size() < 2) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		while (running) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

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

			// Server is waiting for players actions, represented by strings here
			readActionInput();

			if (actionInput.equals("fold")) {
				player.fold();
				
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				sendInfoToAllClients(player.getName() + " folds");

			} else if (actionInput.equals("call")) {
				player.call();

				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				sendInfoToAllClients(player.getName() + " calls");

			} else if (actionInput.equals("check")) {
				player.check();
				
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				sendInfoToAllClients(player.getName() + " checks");

			} else if (actionInput.equals("bet")) {
				player.bet(betSize);
				
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				sendInfoToAllClients(player.getName() + " bets " + betSize);

			} else if (actionInput.equals("raise")) {
				player.raise(betSize);
				
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				sendInfoToAllClients(player.getName() + " raises " + betSize);
				
			} else if (actionInput.equals("leave")) {
				running = false;
				try {
					objectOutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

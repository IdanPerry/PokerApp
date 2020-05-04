package com.idan.game;

import javax.swing.JOptionPane;

import com.idan.server.Server;

/**
 * This class represents a 2 players table.
 * 
 * @author Idan Perry
 * @version 04.05.2020
 *
 */

public class HeadsUpTable extends Table {
	private static final int MAX_PLAYERS = 2;

	/**
	 * Constructs a 2 players table.
	 * 
	 * @param tableId the id number of this table
	 */
	public HeadsUpTable(int tableId, Server server) {
		super(tableId, server);
	}

	@Override
	public synchronized void seatPlayer(Player player) {
		if (getTablePlayers().size() < MAX_PLAYERS) {
			getTablePlayers().add(player);

			notifyAll();

		} else
			System.out.println("Table is full");
	}

	@Override
	public void seatPlayers(int players) {
		if (players < MAX_PLAYERS) {
			setNumOfPlayers(players);
			String inputPlayersName;

			for (int i = 0; i < getNumOfPlayers(); i++) {
				inputPlayersName = JOptionPane.showInputDialog("Enter player's name");
				Player player = new Player(inputPlayersName);

				getTablePlayers().add(player);
			}
		}
	}
}

package com.idan.game;

import javax.swing.JOptionPane;

public class HeadsUpTable extends Table {
	private static final int MAX_PLAYERS = 2;
	private static final int[] SEATS = new int[2];
	
	public HeadsUpTable(int tableId) {
		super(tableId);
	}
	
	public HeadsUpTable() {
		super();
	}
	
	@Override
	public void seatPlayer(Player player) {
		if (tablePlayers.size() < MAX_PLAYERS) {
			tablePlayers.add(player);
			
		} else {			
			System.out.println("Table is full");
		}
		
	}
	
	@Override
	public void seatPlayers(int players) {
		if (players < MAX_PLAYERS) {
			setNumOfPlayers(players);
			String inputPlayersName;
			
			for (int i = 0; i < getNumOfPlayers(); i++) {
				inputPlayersName = JOptionPane.showInputDialog("Enter player's name");
				Player player = new Player(inputPlayersName);

				tablePlayers.add(player);
			}
		}
	}
	
}

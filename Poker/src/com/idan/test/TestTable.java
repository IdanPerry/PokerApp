package com.idan.test;

import java.util.ArrayList;

/**
 * This class represented a poker table.
 * 
 * @author Idan Perry
 * @version 13.05.2013
 *
 */

public class TestTable {
	private static final int MAX_PLAYERS = 2;
	
	private final ArrayList<TestPlayer> tablePlayers;
	private final TestTexasHoldemDealer dealer;
	private int numOfPlayers;

	/**
	 * Constructs a poker table.
	 */
	public TestTable() {
		tablePlayers = new ArrayList<TestPlayer>();
		dealer = new TestTexasHoldemDealer(this);
		
		TestPlayer player1 = new TestPlayer("Idan");
		seatPlayer(player1);
		
		TestPlayer player2 = new TestPlayer("Perry");
		seatPlayer(player2);		

		for(int i = 0; i < 3000; i++) {
			dealer.getDeck().shuffle();
			dealer.checkDealing();
			
			TestHandComparison compare = new TestHandComparison(this);
			TestHandEvaluation evaluate = new TestHandEvaluation(dealer, this);
			evaluate.evaluateAllHands();
			compare.showDown();
		}
	}

	/**
	 * Returns a list of the players at the table.
	 * 
	 * @return a list of the players at the table
	 */
	public ArrayList<TestPlayer> getTablePlayers() {
		return tablePlayers;
	}

	/**
	 * Initializes the number of players at this table.
	 * 
	 * @param numOfPlayers the number of players at this table
	 */
	protected void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	/**
	 * Returns the number of players at this table.
	 * 
	 * @return the number of players at this table
	 */
	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	/**
	 * Allocates a seat for the player at this table.
	 * 
	 * @param player the player to be seated at this table
	 */
	public void seatPlayer(TestPlayer player) {
		if (tablePlayers.size() < MAX_PLAYERS)
			tablePlayers.add(player);
		 else 
			System.out.println("Table is full");
	}
}

package com.idan.test;

import java.util.ArrayList;

public class TestTable {
	private static final int MAX_PLAYERS = 9;
	protected static final int MIN_PLAYRES = 2;
	protected ArrayList<TestPlayer> tablePlayers;
	protected ArrayList<TestPlayer> playersInHand;
	protected TestTexasHoldemDealer dealer;
	protected TestPlayer player;
	protected int tableId;
	protected int numOfPlayers;

	protected int butPosition;
	protected int sbPosition;
	protected int bbPosition;

	protected String action;
	protected int bet;
	protected int pot;
	protected int foldCounter = 0;

	public boolean holeCardsWereDealt;
	protected boolean running;
	protected boolean preFlop;
	protected boolean flop;
	protected boolean turn;
	protected boolean river;

	protected static final int SMALL_BLIND = 50;
	protected static final int BIG_BLIND = 100;

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
			
			TestEvaluator e = new TestEvaluator(dealer, this);
			e.resetHandRanks();
			e.evaluateAllHands();
			e.showDown();
		}
	}

	public TestTable(int tableId) {
		this.tableId = tableId;

		tablePlayers = new ArrayList<TestPlayer>();
		dealer = new TestTexasHoldemDealer(this);
	}

	public ArrayList<TestPlayer> getTablePlayers() {
		return tablePlayers;
	}

	public int getTableId() {
		return tableId;
	}

	protected void setTableId(int tableId) {
		this.tableId = tableId;
	}

	protected void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	public void seatPlayer(TestPlayer player) {
		if (tablePlayers.size() < MAX_PLAYERS) {
			tablePlayers.add(player);

		} else {
			System.out.println("Table is full");
		}

	}
}

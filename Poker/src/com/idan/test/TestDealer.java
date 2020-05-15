package com.idan.test;

public abstract class TestDealer {
	private final TestCardsDeck deck;
	private final TestTable table;
	private TestCard[] flop = new TestCard[3];
	private TestCard turn;
	private TestCard river;

	/**
	 * Constructs a dealer, which manages the game at a single table.
	 * 
	 * @param table the table this dealer manages.
	 */
	public TestDealer(TestTable table) {
		this.table = table;
		deck = new TestCardsDeck();
	}
	
	/**
	 * Constructs a dealer. used for JUnit test.
	 */
	public TestDealer() {
		deck = null;
		table = null;
	}

	/**
	 * Returns the table this dealer manages.
	 * 
	 * @return the table this dealer manages
	 */
	public TestTable getTable() {
		return table;
	}

	/**
	 * Returns the flop of the current hand.
	 * 
	 * @return the flop of the current hand
	 */
	public TestCard[] getFlop() {
		return flop;
	}

	/**
	 * Returns the turn of the current hand.
	 * 
	 * @return the turn of the current hand
	 */
	public TestCard getTurn() {
		return turn;
	}

	/**
	 * Returns the river of the current hand.
	 * 
	 * @return the river of the current hand
	 */
	public TestCard getRiver() {
		return river;
	}
	
	/**
	 * Returns the deck of cards of the table this dealer manages.
	 * 
	 * @return the deck of cards of the table this dealer manages
	 */
	public TestCardsDeck getDeck() {
		return deck;
	}

	/**
	 * Draw first 3 community cards from the deck.
	 * 
	 * @param cards the number of cards each player holds (2 in holdem, 4 in omaha)
	 */
	public void dealFlop(int cards) {
		int size = table.getTablePlayers().size();
		
		flop[0] = deck.getCardsDeck().get(1 + size * cards);
		flop[1] = deck.getCardsDeck().get(2 + size * cards);
		flop[2] = deck.getCardsDeck().get(3 + size * cards);
	}

	/**
	 * Draw 4th community card from the deck.
	 * 
	 * @param cards the number of cards each player holds (2 in holdem, 4 in omaha)
	 */
	public void dealTurn(int cards) {
		turn = deck.getCardsDeck().get(5 + table.getTablePlayers().size() * cards);
	}

	/**
	 * Draw 5th community card from the deck.
	 * 
	 * @param cards the number of cards each player holds (2 in holdem, 4 in omaha)
	 */
	public void dealRiver(int cards) {
		river = deck.getCardsDeck().get(7 + table.getTablePlayers().size() * cards);
	}
	
	/**
	 * Print flop cards to the console.
	 */
	public void printFlop() {
		System.out.println("\nFlop: " + flop[0].getRank() + flop[0].getSuit() + flop[1].getRank() + flop[1].getSuit()
				+ flop[2].getRank() + flop[2].getSuit() + "\n");
	}
	
	/**
	 * Print turn card to the console.
	 */
	public void printTurn() {
		System.out.println("\nTurn: " + turn.getRank() + turn.getSuit() + "\n");
	}
	
	/**
	 * Print river card to the console.
	 */
	public void printRiver() {
		System.out.println("\nRiver: " + river.getRank() + river.getSuit() + "\n");
	}
	
	/**
	 * Dealer deals all players at the table 2 holecards.
	 */
	public abstract void dealHoleCards();
	
	/**
	 * Prints the holecards of all players at the table.
	 */
	public abstract void printHoleCards();
	
	/**
	 * Prints the hands of all players at the table.
	 */
	public abstract void printHands();
	

	/** TEST ** checking hands statistics */
	// public void drawHoleCards() {
	//
	// table.getPlayers().get(0).setHoleCards(deck.getCardsDeck().get(51),
	// deck.getCardsDeck().get(46));
	// table.getPlayers().get(1).setHoleCards(deck.getCardsDeck().get(45),
	// deck.getCardsDeck().get(44));
	//
	// deck.getCardsDeck().remove(51);
	// deck.getCardsDeck().remove(46);
	// deck.getCardsDeck().remove(45);
	// deck.getCardsDeck().remove(44);
	// }
}

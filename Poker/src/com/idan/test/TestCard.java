package com.idan.test;

public class TestCard  {
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 15;
	
	private final Rank rank;
	private final Suit suit;
	
	public enum Rank {
		TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8), 
		NINE("9", 9), TEN("10", 11), JACK("J", 12), QUEEN("Q", 13), KING("K", 14), ACE("A", 15);
		
		private final String rank;
		private final int value;
		
		Rank(String rank, int value) {
			this.rank = rank;
			this.value = value;
		}
		
		/**
		 * Returns the card's value in accordance to the cards rank.
		 * 
		 * @return the value in accordance to the card's rank.
		 */
		public int getValue() {
			return value;
		}
		
		/**
		 * Returns the card's rank as a string representation.
		 * @return the card's rank as a string representation.
		 */
		@Override
		public String toString() {
			return rank;
		}
	}
	
	public enum Suit {
		// the strings are unicode symbols in accordance to the suits
		SPADES("\u2660", 1), CLUBS("\u2663", 2), HEARTS("\u2665", 3), DIAMONDS("\u2666", 4);	
		
		private final String symbol;
		private final int value;
		
		/**
		 * Construcs a Suit object of enum type.
		 * @param symbol a string representing the suit symbol.
		 */
		Suit(String symbol, int value) {
			this.symbol = symbol;
			this.value = value;
		}
		
		/**
		 * Returns the card's value in accordance to the cards suit.
		 * 
		 * @return the value in accordance to the card's suit.
		 */
		public int getValue() {
			return value;
		}
		
		/**
		 * Returns the card's suit as a unicode symbol.
		 * @return the card's suit as a unicode symbol.
		 */
		@Override
		public String toString() {
			return symbol;
		}
	}
	
	/**
	 * Constructs a game card with the specified rank and suit.
	 * 
	 * @param rank the card rank
	 * @param suit the card suit
	 * @param rankValue the card value of the rank
	 * @param suitValue the card value of the suit
	 */
	public TestCard(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	/**
	 * Returns the rank of this card.
	 * 
	 * @return the rank of this card
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Returns the suit of this card.
	 * 
	 * @return the suit of this card
	 */
	public Suit getSuit() {
		return suit;
	}

//	public void checkDeck(){
//		for (int i = 0; i < cardsDeck.size(); i++){
//			System.out.println(cardsDeck.get(i).getRank() + cardsDeck.get(i).getSuit());
//		}	//used for debug
//	}
}

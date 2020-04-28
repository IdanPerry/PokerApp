package com.idan.game;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * This class represents a game card.
 * 
 * @author Idan Perry
 * @version 13.05.2013
 *
 */

public class Card implements Serializable {
	private static final long serialVersionUID = 8723973531140999334L;
	
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 15;
	
	private final Rank rank;
	private final Suit suit;
	private ImageIcon cardImage;
	
	public enum Rank {
		TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8), 
		NINE("9", 9), TEN("10", 11), JACK("J", 12), QUEEN("Q", 13), KING("K", 14), ACE("A", 15);
		
		private String rank;
		private int value;
		
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
		 * Changes ACE value to eaither 1 or 15 (1 for lowest straight).
		 * 
		 * @param value the value of ACE
		 */
		public void setAceValue(int value) {
			ACE.value = value;
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
		private String symbol;
		private int value;
		
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
	public Card(Rank rank, Suit suit) {
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
	
	/**
	 * Sets the graphic image of this card.
	 * 
	 * @param cardImage the image of this card
	 */
	public void setCardImage(ImageIcon cardImage) {
		this.cardImage = cardImage;
	}
	
	/**
	 * Returns the graphic image of this card.
	 * 
	 * @return the graphic image of this card
	 */
	public ImageIcon getCardImage() {
		return cardImage;
	}
}

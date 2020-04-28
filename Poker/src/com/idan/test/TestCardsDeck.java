package com.idan.test;

import java.util.ArrayList;
import java.util.Collections;

public class TestCardsDeck {
	public static final int MAX_CARDS = 52;
	
	private final ArrayList<TestCard> cardsDeck;

	/**
	 * Constructs a CardsDeck object. Initializes a deck of 52 cards.
	 */
	public TestCardsDeck() {
		cardsDeck = new ArrayList<TestCard>(MAX_CARDS);
		
		for (TestCard.Rank rank : TestCard.Rank.values()) {
			for (TestCard.Suit suit : TestCard.Suit.values()) {
				TestCard card = new TestCard(rank, suit);
				cardsDeck.add(card);
			}
		}
	}
	
	/**
	 * Returns a deck of cards.
	 * 
	 * @return a deck of cards
	 */
	public ArrayList<TestCard> getCardsDeck() {
		return cardsDeck;
	}
	
	/**
	 * Shuffles the cards of this deck.
	 */
	public void shuffle() {
		Collections.shuffle(cardsDeck);
	}
}

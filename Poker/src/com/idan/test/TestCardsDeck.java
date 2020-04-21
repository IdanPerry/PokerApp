package com.idan.test;

import java.util.ArrayList;

public class TestCardsDeck extends TestCard {
	
	private static final long serialVersionUID = -759739111081498528L;
	
	private ArrayList<TestCard> cardsDeck;

	public TestCardsDeck() {
		cardsDeck = new ArrayList<TestCard>();
		for (int i = 0; i < RANKS.length; i++){
			for (int j = 0; j < SUITS.length; j++){
				TestCard card = new TestCard(RANKS[i], SUITS[j], i+2, j+1);
				cardsDeck.add(card);
			}
		}
	}
	
	public ArrayList<TestCard> getCardsDeck() {
		return cardsDeck;
	}
}

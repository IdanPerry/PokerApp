package com.idan.test;

import java.io.Serializable;

public class TestCard implements Serializable {

	private static final long serialVersionUID = 8723973531140999334L;
	
	protected String rank;
	protected String suit;
	protected int rankValue;
	protected int suitValue;
	protected static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
	protected static final String[] SUITS = {"\u2660", "\u2663", "\u2665", "\u2666"}; //spade, club, heart, diamond
	
	public TestCard(String rank, String suit, int rankValue, int suitValue) {
		this.rank = rank;
		this.suit = suit;
		this.rankValue = rankValue;
		this.suitValue = suitValue;
	}
	
	public TestCard() {	
	}
	
	public String getRank() {
		return rank;
	}

	public String getSuit() {
		return suit;
	}
	
	public int getRankValue() {
		return rankValue;
	}

	public int getSuitValue() {
		return suitValue;
	}

//	public void checkDeck(){
//		for (int i = 0; i < cardsDeck.size(); i++){
//			System.out.println(cardsDeck.get(i).getRank() + cardsDeck.get(i).getSuit());
//		}	//used for debug
//	}
}

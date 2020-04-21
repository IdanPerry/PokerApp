package com.idan.game;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Card implements Serializable {

	private static final long serialVersionUID = 8723973531140999334L;
	
	private String rank;
	private String suit;
	private int rankValue;
	private int suitValue;
	private ImageIcon cardImage;
	protected static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
	protected static final String[] SUITS = {"\u2660", "\u2663", "\u2665", "\u2666"}; //spade, club, heart, diamond
	
	public Card(String rank, String suit, int rankValue, int suitValue) {
		this.rank = rank;
		this.suit = suit;
		this.rankValue = rankValue;
		this.suitValue = suitValue;
	}
	
	public Card() {	
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
	
	public void setCardImage(ImageIcon cardImage) {
		this.cardImage = cardImage;
	}
	
	public ImageIcon getCardImage() {
		return cardImage;
	}
}

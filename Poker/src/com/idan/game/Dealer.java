package com.idan.game;

import java.util.Collections;

public class Dealer {

	private Card[] flop = new Card[3];
	private Card turn;
	private Card river;

	protected CardsDeck deck;
	protected Table table;
	
	public Dealer() {
	}

	public Dealer(Table table) {
		this.table = table;
		deck = new CardsDeck();
	}

	public Card[] getFlop() {
		return flop;
	}

	public Card getTurn() {
		return turn;
	}

	public Card getRiver() {
		return river;
	}

	public void shuffle() {
		Collections.shuffle(deck.getCardsDeck());
	}

	public void dealFlop(int cards) {
		flop[0] = deck.getCardsDeck().get(1 + table.getPlayers().size() * cards);
		flop[1] = deck.getCardsDeck().get(2 + table.getPlayers().size() * cards);
		flop[2] = deck.getCardsDeck().get(3 + table.getPlayers().size() * cards);
	}

	public void dealTurn(int cards) {
		turn = deck.getCardsDeck().get(5 + table.getPlayers().size() * cards);
	}

	public void dealRiver(int cards) {
		river = deck.getCardsDeck().get(7 + table.getPlayers().size() * cards);
	}
	
	public void printFlop() {
		System.out.println("\nFlop: " + flop[0].getRank() + flop[0].getSuit() + flop[1].getRank() + flop[1].getSuit()
				+ flop[2].getRank() + flop[2].getSuit() + "\n");
	}
	
	public void printTurn() {
		System.out.println("\nTurn: " + turn.getRank() + turn.getSuit() + "\n");
	}
	
	public void printRiver() {
		System.out.println("\nRiver: " + river.getRank() + river.getSuit() + "\n");
	}

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

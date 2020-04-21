package com.idan.test;

import java.util.Collections;

public class TestDealer {

	protected TestCard[] flop = new TestCard[3];
	protected TestCard turn;
	protected TestCard river;

	protected TestCardsDeck deck;
	protected TestTable table;

	public TestDealer() {
	}

	public TestDealer(TestTable table) {
		this.table = table;
		deck = new TestCardsDeck();
	}

	public TestCard[] getFlop() {
		return flop;
	}

	public TestCard getTurn() {
		return turn;
	}

	public TestCard getRiver() {
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
}

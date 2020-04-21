package com.idan.omaha;

import com.idan.game.Dealer;
import com.idan.game.Table;

public class OmahaDealer extends Dealer {
	
	public OmahaDealer() {
	}

	public OmahaDealer(Table table) {
		super(table);
	}
	
	public void dealHoleCards() {
		for (int i = 0; i < table.getPlayers().size(); i++) {
			table.getPlayers().get(i).setOmahaHoleCards(deck.getCardsDeck().get(i),
					deck.getCardsDeck().get(i + table.getPlayers().size()),
					deck.getCardsDeck().get(table.getPlayers().size() * 2 + i),
					deck.getCardsDeck().get(table.getPlayers().size() * 3 + i));
		}

	}
	
	public void printHoleCards() {
		for (int i = 0; i < table.getPlayers().size(); i++) {
			System.out.print(
					table.getPlayers().get(i).getName() + " has: " + table.getPlayers().get(i).getHoleCard1().getRank()
							+ table.getPlayers().get(i).getHoleCard1().getSuit());
			System.out.print(table.getPlayers().get(i).getHoleCard2().getRank()
					+ table.getPlayers().get(i).getHoleCard2().getSuit());
			System.out.print(table.getPlayers().get(i).getHoleCard3().getRank()
							+ table.getPlayers().get(i).getHoleCard3().getSuit());
			System.out.print(table.getPlayers().get(i).getHoleCard4().getRank()
					+ table.getPlayers().get(i).getHoleCard4().getSuit() + "\n");
		}
	}
	
	public void printHands() {
		for (int i = 0; i < table.getPlayers().size(); i++) {
			System.out.print(
					table.getPlayers().get(i).getName() + " has: " + table.getPlayers().get(i).getHoleCard1().getRank()
							+ table.getPlayers().get(i).getHoleCard1().getSuit());
			System.out.print(table.getPlayers().get(i).getHoleCard2().getRank()
					+ table.getPlayers().get(i).getHoleCard2().getSuit());
			System.out.print(table.getPlayers().get(i).getHoleCard3().getRank()
					+ table.getPlayers().get(i).getHoleCard3().getSuit());
			System.out.print(table.getPlayers().get(i).getHoleCard4().getRank()
					+ table.getPlayers().get(i).getHoleCard4().getSuit() + "\n");
		}

		System.out.println("\nBoard: " + getFlop()[0].getRank() + getFlop()[0].getSuit() + getFlop()[1].getRank() + getFlop()[1].getSuit()
				+ getFlop()[2].getRank() + getFlop()[2].getSuit() + getTurn().getRank() + getTurn().getSuit() + getRiver().getRank()
				+ getRiver().getSuit() + "\n");
	}
	
	// Used for debug
	public void checkDealing() {
		dealHoleCards();
		dealFlop(4);
		dealTurn(4);
		dealRiver(4);

		printHands();
	}
}

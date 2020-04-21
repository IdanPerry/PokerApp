package com.idan.texasholdem;

import com.idan.game.Dealer;
import com.idan.game.Table;

public class TexasHoldemDealer extends Dealer {

	public TexasHoldemDealer() {
	}

	public TexasHoldemDealer(Table table) {
		super(table);
	}

	public void dealHoleCards() {
		for (int i = 0; i < table.getPlayers().size(); i++) {
			table.getPlayers().get(i).setTexasHoleCards(deck.getCardsDeck().get(i),
					deck.getCardsDeck().get(i + table.getPlayers().size()));
		}
	}

	public void printHoleCards() {
		for (int i = 0; i < table.getPlayers().size(); i++) {
			System.out.print(
					table.getPlayers().get(i).getName() + " has: " + table.getPlayers().get(i).getHoleCard1().getRank()
							+ table.getPlayers().get(i).getHoleCard1().getSuit());
			System.out.print(table.getPlayers().get(i).getHoleCard2().getRank()
					+ table.getPlayers().get(i).getHoleCard2().getSuit() + "\n");
		}
	}

	public void printHands() {
		for (int i = 0; i < table.getPlayers().size(); i++) {
			System.out.print(
					table.getPlayers().get(i).getName() + " has: " + table.getPlayers().get(i).getHoleCard1().getRank()
							+ table.getPlayers().get(i).getHoleCard1().getSuit());
			System.out.print(table.getPlayers().get(i).getHoleCard2().getRank()
					+ table.getPlayers().get(i).getHoleCard2().getSuit() + "\n");
		}

		System.out.println("\nBoard: " + getFlop()[0].getRank() + getFlop()[0].getSuit() + getFlop()[1].getRank()
				+ getFlop()[1].getSuit() + getFlop()[2].getRank() + getFlop()[2].getSuit() + getTurn().getRank()
				+ getTurn().getSuit() + getRiver().getRank() + getRiver().getSuit() + "\n");
	}

	// Used for test
	public void checkDealing() {
		dealHoleCards();
		dealFlop(2);
		dealTurn(2);
		dealRiver(2);

		printHands();
	}

}

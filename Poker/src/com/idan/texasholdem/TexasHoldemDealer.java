package com.idan.texasholdem;

import com.idan.game.Dealer;
import com.idan.game.Table;

/**
 * This class represents a Texas Holdem poker game dealer.
 * 
 * @author Idan Perry
 * @version 03.05.2013
 */

public class TexasHoldemDealer extends Dealer {

	/**
	 * Constructs a Texas Holdem dealer which manages a Texas Holdem poker table
	 * game.
	 * 
	 * @param table the table this dealer manages
	 */
	public TexasHoldemDealer(Table table) {
		super(table);
	}

	@Override
	public void dealHoleCards() {
		int size = getTable().getTablePlayers().size();
		
		for (int i = 0; i < size; i++) {
			getTable().getTablePlayers().get(i).setTexasHoleCards(getDeck().getCardsDeck().get(i),
					getDeck().getCardsDeck().get(i + size));
		}
	}

	@Override
	public void printHoleCards() {
		for (int i = 0; i < getTable().getTablePlayers().size(); i++) {
			System.out.print(getTable().getTablePlayers().get(i).getName() + " has: "
					+ getTable().getTablePlayers().get(i).getHoleCard1().getRank()
					+ getTable().getTablePlayers().get(i).getHoleCard1().getSuit());
			System.out.print("" + getTable().getTablePlayers().get(i).getHoleCard2().getRank()
					+ getTable().getTablePlayers().get(i).getHoleCard2().getSuit() + "\n");
		}
	}

	@Override
	public void printHands() {
		for (int i = 0; i < getTable().getTablePlayers().size(); i++) {
			System.out.print(getTable().getTablePlayers().get(i).getName() + " has: "
					+ getTable().getTablePlayers().get(i).getHoleCard1().getRank()
					+ getTable().getTablePlayers().get(i).getHoleCard1().getSuit());

			System.out.print(getTable().getTablePlayers().get(i).getHoleCard2().getRank().toString()
					+ getTable().getTablePlayers().get(i).getHoleCard2().getSuit().toString() + "\n");
		}

		System.out.println("\nBoard: " + getFlop()[0].getRank() + getFlop()[0].getSuit() + getFlop()[1].getRank()
				+ getFlop()[1].getSuit() + getFlop()[2].getRank() + getFlop()[2].getSuit().toString()
				+ getTurn().getRank() + getTurn().getSuit() + getRiver().getRank() + getRiver().getSuit() + "\n");
	}
}

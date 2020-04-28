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
	 * Constructs a Texas Holdem dealer which manages a Texas Holdem
	 * poker table game.
	 * 
	 * @param table the table this dealer manages
	 */
	public TexasHoldemDealer(Table table) {
		super(table);
	}

	/**
	 * Dealer deals all players at the table 2 holecards.
	 */
	public void dealHoleCards() {
		for (int i = 0; i < getTable().getTablePlayers().size(); i++) {
			getTable().getTablePlayers().get(i).setTexasHoleCards(getDeck().getCardsDeck().get(i),
					getDeck().getCardsDeck().get(i + getTable().getTablePlayers().size()));
		}
	}

	/**
	 * Prints the holecards of all players at the table.
	 */
	public void printHoleCards() {
		for (int i = 0; i < getTable().getTablePlayers().size(); i++) {
			System.out.print(getTable().getTablePlayers().get(i).getName() + " has: "
					+ getTable().getTablePlayers().get(i).getHoleCard1().getRank().toString()
					+ getTable().getTablePlayers().get(i).getHoleCard1().getSuit().toString());
			System.out.print(getTable().getTablePlayers().get(i).getHoleCard2().getRank().toString()
					+ getTable().getTablePlayers().get(i).getHoleCard2().getSuit().toString() + "\n");
		}
	}

	/**
	 * Prints the hands of all players at the table.
	 */
	public void printHands() {
		for (int i = 0; i < getTable().getTablePlayers().size(); i++) {
			System.out.print(getTable().getTablePlayers().get(i).getName() + " has: "
					+ getTable().getTablePlayers().get(i).getHoleCard1().getRank().toString()
					+ getTable().getTablePlayers().get(i).getHoleCard1().getSuit().toString());
			
			System.out.print(getTable().getTablePlayers().get(i).getHoleCard2().getRank().toString()
					+ getTable().getTablePlayers().get(i).getHoleCard2().getSuit().toString() + "\n");
		}

		System.out.println("\nBoard: " + getFlop()[0].getRank().toString() + getFlop()[0].getSuit().toString()
				+ getFlop()[1].getRank().toString() + getFlop()[1].getSuit().toString()
				+ getFlop()[2].getRank().toString() + getFlop()[2].getSuit().toString() + getTurn().getRank().toString()
				+ getTurn().getSuit().toString() + getRiver().getRank().toString() + getRiver().getSuit().toString()
				+ "\n");
	}

	/* for debug use */
	public void checkDealing() {
		dealHoleCards();
		dealFlop(2);
		dealTurn(2);
		dealRiver(2);

		printHands();
	}

}

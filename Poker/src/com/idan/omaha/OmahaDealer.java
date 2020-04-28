package com.idan.omaha;

import com.idan.game.Dealer;
import com.idan.game.Table;

/**
 * This class represents Omaha poker game dealer.
 * 
 * @author Idan Perry
 * @version 03.05.2013
 */

public class OmahaDealer extends Dealer {

	/**
	 * Constructs Omaha dealer which manages Omaha
	 * poker table game.
	 * 
	 * @param table the table this dealer manages
	 */
	public OmahaDealer(Table table) {
		super(table);
	}
	
	/**
	 * Dealer deals all players at the table 2 holecards.
	 */
	public void dealHoleCards() {
		for (int i = 0; i < getTable().getTablePlayers().size(); i++) {
			getTable().getTablePlayers().get(i).setOmahaHoleCards(getDeck().getCardsDeck().get(i),
					getDeck().getCardsDeck().get(i + getTable().getTablePlayers().size()),
					getDeck().getCardsDeck().get(getTable().getTablePlayers().size() * 2 + i),
					getDeck().getCardsDeck().get(getTable().getTablePlayers().size() * 3 + i));
		}
	}
	
	/**
	 * Prints the holecards of all players at the table.
	 */
	public void printHoleCards() {
		for (int i = 0; i < getTable().getTablePlayers().size(); i++) {
			System.out.print(
					getTable().getTablePlayers().get(i).getName() + " has: " + getTable().getTablePlayers().get(i).getHoleCard1().getRank()
							+ getTable().getTablePlayers().get(i).getHoleCard1().getSuit().toString());
			System.out.print(getTable().getTablePlayers().get(i).getHoleCard2().getRank().toString()
					+ getTable().getTablePlayers().get(i).getHoleCard2().getSuit().toString());
			System.out.print(getTable().getTablePlayers().get(i).getHoleCard3().getRank().toString()
							+ getTable().getTablePlayers().get(i).getHoleCard3().getSuit().toString());
			System.out.print(getTable().getTablePlayers().get(i).getHoleCard4().getRank().toString()
					+ getTable().getTablePlayers().get(i).getHoleCard4().getSuit().toString() + "\n");
		}
	}
	
	/**
	 * Prints the hands of all players at the table.
	 */
	public void printHands() {
		for (int i = 0; i < getTable().getTablePlayers().size(); i++) {
			System.out.print(
					getTable().getTablePlayers().get(i).getName() + " has: " + getTable().getTablePlayers().get(i).getHoleCard1().getRank().toString()
							+ getTable().getTablePlayers().get(i).getHoleCard1().getSuit().toString());
			System.out.print(getTable().getTablePlayers().get(i).getHoleCard2().getRank().toString()
					+ getTable().getTablePlayers().get(i).getHoleCard2().getSuit().toString());
			System.out.print(getTable().getTablePlayers().get(i).getHoleCard3().getRank().toString()
					+ getTable().getTablePlayers().get(i).getHoleCard3().getSuit().toString());
			System.out.print(getTable().getTablePlayers().get(i).getHoleCard4().getRank().toString()
					+ getTable().getTablePlayers().get(i).getHoleCard4().getSuit().toString() + "\n");
		}

		System.out.println("\nBoard: " + getFlop()[0].getRank() + getFlop()[0].getSuit() + getFlop()[1].getRank() + getFlop()[1].getSuit()
				+ getFlop()[2].getRank() + getFlop()[2].getSuit() + getTurn().getRank() + getTurn().getSuit() + getRiver().getRank()
				+ getRiver().getSuit() + "\n");
	}
	
	/* for debug use */
	public void checkDealing() {
		dealHoleCards();
		dealFlop(4);
		dealTurn(4);
		dealRiver(4);

		printHands();
	}
}

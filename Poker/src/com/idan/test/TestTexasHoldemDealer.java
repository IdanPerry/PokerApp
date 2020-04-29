package com.idan.test;

public class TestTexasHoldemDealer extends TestDealer {

	/**
	 * Constructs a Texas Holdem dealer which manages a Texas Holdem poker table
	 * game.
	 * 
	 * @param table the table this dealer manages
	 */
	public TestTexasHoldemDealer(TestTable table) {
		super(table);
	}

	/**
	 * Dealer deals all players at the table 2 holecards.
	 */
	public void dealHoleCards() {
		int size = getTable().getTablePlayers().size();

		for (int i = 0; i < size; i++) {
			getTable().getTablePlayers().get(i).setTexasHoleCards(getDeck().getCardsDeck().get(i),
					getDeck().getCardsDeck().get(i + size));
		}
	}

	/**
	 * Prints the holecards of all players at the table.
	 */
	public void printHoleCards() {
		for (int i = 0; i < getTable().getTablePlayers().size(); i++) {
			System.out.print(getTable().getTablePlayers().get(i).getName() + " has: "
					+ getTable().getTablePlayers().get(i).getHoleCard1().getRank()
					+ getTable().getTablePlayers().get(i).getHoleCard1().getSuit());
			System.out.print("" + getTable().getTablePlayers().get(i).getHoleCard2().getRank()
					+ getTable().getTablePlayers().get(i).getHoleCard2().getSuit() + "\n");
		}
	}

	/**
	 * Prints the hands of all players at the table.
	 */
	public void printHands() {
		for (int i = 0; i < getTable().getTablePlayers().size(); i++) {
			System.out.print(getTable().getTablePlayers().get(i).getName() + " has: "
					+ getTable().getTablePlayers().get(i).getHoleCard1().getRank()
					+ getTable().getTablePlayers().get(i).getHoleCard1().getSuit());

			System.out.print("" + getTable().getTablePlayers().get(i).getHoleCard2().getRank()
					+ getTable().getTablePlayers().get(i).getHoleCard2().getSuit() + "\n");
		}

		System.out.println("\nBoard: " + getFlop()[0].getRank() + getFlop()[0].getSuit() + getFlop()[1].getRank()
				+ getFlop()[1].getSuit() + getFlop()[2].getRank() + getFlop()[2].getSuit() + getTurn().getRank()
				+ getTurn().getSuit() + getRiver().getRank() + getRiver().getSuit() + "\n");
	}

	public void checkDealing() {
		dealHoleCards();
		dealFlop(2);
		dealTurn(2);
		dealRiver(2);

		printHands();
	}
}

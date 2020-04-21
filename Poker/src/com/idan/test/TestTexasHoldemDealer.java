package com.idan.test;

public class TestTexasHoldemDealer extends TestDealer {

	public TestTexasHoldemDealer() {
	}

	public TestTexasHoldemDealer(TestTable table) {
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
	
	/** TEST */
	public void drawHoleCards() {

		table.getPlayers().get(0).setTexasHoleCards(deck.getCardsDeck().get(12), deck.getCardsDeck().get(49));
		table.getPlayers().get(1).setTexasHoleCards(deck.getCardsDeck().get(47), deck.getCardsDeck().get(32));

		deck.getCardsDeck().remove(51);
		deck.getCardsDeck().remove(46);
		deck.getCardsDeck().remove(45);
		deck.getCardsDeck().remove(44);
	}
	
	/** TEST */
	public void drawHand() {
		table.getPlayers().get(0).setTexasHoleCards(deck.getCardsDeck().get(12), deck.getCardsDeck().get(49));
		table.getPlayers().get(1).setTexasHoleCards(deck.getCardsDeck().get(47), deck.getCardsDeck().get(32));
		flop[0] = deck.getCardsDeck().get(43);
		flop[1] = deck.getCardsDeck().get(39);
		flop[2] = deck.getCardsDeck().get(46);
		turn = deck.getCardsDeck().get(27);
		river = deck.getCardsDeck().get(50);
		
		printHands();
	}

	/** TEST */
	public void checkDealing() {
		dealHoleCards();
		dealFlop(2);
		dealTurn(2);
		dealRiver(2);

		printHands();
	}

}

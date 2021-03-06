package com.idan.test;

import java.util.ArrayList;

import com.idan.test.TestCard;
import com.idan.test.TestDealer;
import com.idan.test.TestPlayer;
import com.idan.test.TestTable;

/**
 * This class represents a hand evaluation tool for each poker table.
 * 
 * @author Idan Perry
 * @version 03.05.2013
 */

public class TestHandEvaluation {
	private static final int FINAL_HAND = 5;
	
	private final TestDealer dealer;
	private final TestTable table;
	private TestCard[] hand;

	private boolean strFlush; // for debug
	
	public enum HandRank {
		HIGH_CARD(0, "High Catd"),
		PAIR(1, "One Pair"),
		TWO_PAIRS(2, "Two Pairs"),
		TRIPS(3, "Trips"),
		STRAIGHT(4, "Straight"),
		FLUSH(5, "Flush"),
		FULL_HOUSE(6, "Full House"),
		QUADS(7, "Quads"),
		STRAIGHT_FLUSH(8, "Straight Flush");
		
		private final int value;
		private final String string;
		
		/**
		 * Constructs HandRank enum object.
		 * 
		 * @param value the value of the hand
		 * @param string the hand as a string
		 */
		HandRank(int value, String string) {
			this.value = value;
			this.string = string;
		}
		
		/**
		 * Returns the value of the hand.
		 * 
		 * @return the value of the hand
		 */
		public int getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			return string;
		}
	}

	/**
	 * Constructs a hand eavaluation object.
	 * 
	 * @param dealer the dealer of the table which uses this evaluation tool
	 * @param table the table which uses this evaluation tool
	 */
	public TestHandEvaluation(TestDealer dealer, TestTable table) {
		this.dealer = dealer;
		this.table = table;
	}
	
	/**
	 * Initalizes the 5 cards hand for the player.
	 * Used for JUnit test.
	 */
	public void setHand() {
		hand = new TestCard[FINAL_HAND];
	}
	
	/**
	 * Start sequence of hand evaluation for all players at the table,
	 * checking the highest ranking first down to the lowest.
	 */
	public void evaluateAllHands() {
		TestPlayer player;
		resetHandRanks();

		for (int i = 0; i < table.getTablePlayers().size(); i++) {
			player = table.getTablePlayers().get(i);
			hand = new TestCard[FINAL_HAND];

			initAndSortAllHands();
			if (checkStrFlush(player))
				continue;

			initAndSortAllHands();
			if (checkQuads(player))
				continue;

			initAndSortAllHands();
			if (checkFullHouse(player))
				continue;

			initAndSortAllHands();
			if (checkFlush(player))
				continue;

			initAndSortAllHands();
			if (checkStraight(player))
				continue;

			initAndSortAllHands();
			if (player.isTrips())
				continue;

			initAndSortAllHands();
			if (checkTwoPairs(player))
				continue;

			initAndSortAllHands();
			if (player.isPair())
				continue;

			initAndSortAllHands();
			checkHighCard(player);
		}
	}

	// for debug
	public void handAlarm() {
		if (strFlush)
			System.out.println(" * * * * * * * S T R A I G H T - F L U S H * * * * * * *");
	}

	// Combines each player's hole cards with the board (flop, turn and river)
	// in array to make a 7 cards hand in ascending rank order
	private void initAndSortAllHands() {
		ArrayList<TestCard> sevenCards;
		TestPlayer player;

		// initalize the seven cards hand
		for (int i = 0; i < table.getTablePlayers().size(); i++) {
			sevenCards = new ArrayList<TestCard>(7);
			player = table.getTablePlayers().get(i);

			sevenCards.add(player.getHoleCard1());
			sevenCards.add(player.getHoleCard2());
			sevenCards.add(dealer.getFlop()[0]);
			sevenCards.add(dealer.getFlop()[1]);
			sevenCards.add(dealer.getFlop()[2]);
			sevenCards.add(dealer.getTurn());
			sevenCards.add(dealer.getRiver());

			player.setSevenCardsTempHand(sevenCards);
		}

		// sort the hand
		for (int i = 0; i < table.getTablePlayers().size(); i++)
			table.getTablePlayers().get(i).sortHandByRank(table.getTablePlayers().get(i).getSevenCardsTempHand());
	}

	/*
	 * Check if the hand is a straight-flush
	 */
	public boolean checkStrFlush(TestPlayer player) {
		player.sortHandBySuit(player.getSevenCardsTempHand());

		for (int j = 6; j > 3; j--) {
			if (player.getSevenCardsTempHand().get(j).getSuit().getValue() == player.getSevenCardsTempHand().get(j - 4)
					.getSuit().getValue()) {

				for (int i = 0; i < hand.length; i++)
					hand[i] = player.getSevenCardsTempHand().get(j - i);

				// Sort by rank
				sortHand(hand);

				// check ascending values for straight
				for (int i = 0; i < hand.length - 1; i++) {
					if (hand[i].getRank().getValue() != hand[i + 1].getRank().getValue() + 1)
						return false;
				}

				player.setStrFlush(true);
				player.setHandRank(HandRank.STRAIGHT_FLUSH);
				player.setFiveCardsHand(hand);

				strFlush = true;
				return true;
			}
		}

		if (!player.isStrFlush())
			return false;

		// speciel case - lowest straight-flush (Ace to 5)
		if (hand[4].getRank().equals(TestCard.Rank.ACE) && hand[3].getRank().equals(TestCard.Rank.FIVE)
				&& hand[2].getRank().equals(TestCard.Rank.FOUR) && hand[1].getRank().equals(TestCard.Rank.THREE)
				&& hand[0].getRank().equals(TestCard.Rank.TWO)) {

			player.setStrFlush(true);
			player.setHandRank(HandRank.STRAIGHT_FLUSH);
			player.setFiveCardsHand(hand);

			strFlush = true;
			return true;
		}

		return false;
	}

	/*
	 * Check if the hand is four-of-a-kind.
	 */
	public boolean checkQuads(TestPlayer player) {
		int j;
		for (j = 0; j <= 3; j++) {
			if (player.getSevenCardsTempHand().get(j).getRank()
					.equals(player.getSevenCardsTempHand().get(j + 3).getRank())) {
				player.setQuads(true);
				player.setHandRank(HandRank.QUADS);
				break;
			}
		}

		if (!player.isQuads())
			return false;

		// init 5 cards hand
		for (int i = 0; i < hand.length - 1; i++) {
			hand[i] = player.getSevenCardsTempHand().get(j);
			player.getSevenCardsTempHand().remove(j);
		}

		// add kicker
		hand[4] = player.getSevenCardsTempHand().get(2);

		player.setFiveCardsHand(hand);
		return true;
	}

	/*
	 * Check if the hand is a full-house
	 */
	public boolean checkFullHouse(TestPlayer player) {
		// search for trips first
		checkTrips(player);

		// not qualified for full-house
		if (!player.isTrips()) {
			player.setTrips(false);
			return false;
		}

		// search for a pair
		for (int j = 3; j >= 1; j--) {
			if (player.getSevenCardsTempHand().get(j).getRank()
					.equals(player.getSevenCardsTempHand().get(j - 1).getRank())) {
				player.setFullHouse(true);
				player.setHandRank(HandRank.FULL_HOUSE);

				// add the pair to the 5 cards hand
				hand[3] = player.getSevenCardsTempHand().get(j);
				hand[4] = player.getSevenCardsTempHand().get(j - 1);

				player.setFiveCardsHand(hand);
				return true;
			}
		}

		// add kickers for the trips
		hand[3] = player.getSevenCardsTempHand().get(3);
		hand[4] = player.getSevenCardsTempHand().get(2);
		player.setFiveCardsHand(hand);

		return false;
	}

	/*
	 * Check if the hand is a flush
	 */
	public boolean checkFlush(TestPlayer player) {
		player.sortHandBySuit(player.getSevenCardsTempHand());
		int j;

		// search a sequence of the same color
		for (j = 6; j > 3; j--) {
			if (player.getSevenCardsTempHand().get(j).getSuit().getValue() == player.getSevenCardsTempHand().get(j - 4)
					.getSuit().getValue()) {
				player.setFlush(true);
				player.setHandRank(HandRank.FLUSH);
				break;
			}
		}

		if (!player.isFlush())
			return false;

		// init 5 cards hand
		for (int i = 0; i < hand.length; i++)
			hand[i] = player.getSevenCardsTempHand().get(j - i);

		// Sorting the suited cards array by ascending values
		sortHand(hand);

		player.setFiveCardsHand(hand);
		return true;
	}

	/*
	 * Check if the hand is a straight.
	 */
	public boolean checkStraight(TestPlayer player) {
		int size = player.getSevenCardsTempHand().size()-1;
		int fixSteps = 4;
		int counter = 0; // counts the increments between 2 folowing cards

		// scan blocks of 5 cards across the 7 cards array
		for (; size >= fixSteps; size--) {
			counter = 0;
			for (int i = size; i > size-fixSteps; i--) {
				if (player.getSevenCardsTempHand().get(i).getRank()
						.getValue() == player.getSevenCardsTempHand().get(i - 1).getRank().getValue() + 1)
					counter++;
			}
			
			if (counter == fixSteps)
				break;
		}

		if (counter == fixSteps) {
			player.setStraight(true);
			player.setHandRank(HandRank.STRAIGHT);

			// init 5 cards hand
			for (int i = 0; i < hand.length; i++)
				hand[i] = player.getSevenCardsTempHand().get(size - fixSteps + i);

			player.setFiveCardsHand(hand);
			return true;
		}

		// Special "wheel straight" case (Ace to 5)
		if (player.getSevenCardsTempHand().get(0).getRank().equals(TestCard.Rank.TWO)
				&& player.getSevenCardsTempHand().get(1).getRank().equals(TestCard.Rank.THREE)
				&& player.getSevenCardsTempHand().get(2).getRank().equals(TestCard.Rank.FOUR)
				&& player.getSevenCardsTempHand().get(3).getRank().equals(TestCard.Rank.FIVE)
				&& player.getSevenCardsTempHand().get(6).getRank().equals(TestCard.Rank.ACE)) {

			player.setStraight(true);
			player.setHandRank(HandRank.STRAIGHT);

			// init 5 cards hand
			hand[0] = player.getSevenCardsTempHand().get(6);
			for (int i = 1; i < hand.length; i++)
				hand[i] = player.getSevenCardsTempHand().get(i - 1);

			player.setFiveCardsHand(hand);
			return true;
		}

		return false;
	}

	/*
	 * Check if the hand is three-of-a-kind.
	 */
	public void checkTrips(TestPlayer player) {
		for (int j = 6; j > 1; j--) {
			if (player.getSevenCardsTempHand().get(j).getRank()
					.equals(player.getSevenCardsTempHand().get(j - 2).getRank())) {

				player.setTrips(true);
				player.setHandRank(HandRank.TRIPS);
				hand[0] = player.getSevenCardsTempHand().get(j);
				hand[1] = player.getSevenCardsTempHand().get(j - 1);
				hand[2] = player.getSevenCardsTempHand().get(j - 2);
				player.getSevenCardsTempHand().remove(j);
				player.getSevenCardsTempHand().remove(j - 1);
				player.getSevenCardsTempHand().remove(j - 2);
				break;
			}
		}
	}

	/*
	 * Check if the hand is two-pairs.
	 */
	public boolean checkTwoPairs(TestPlayer player) {
		// first pair
		checkPair(player);

		// doesn't qualify for 2 pairs
		if (!player.isPair()) {
			player.setPair(false);
			return false;
		}

		// second pair
		for (int j = 4; j > 0; j--) {
			if (player.getSevenCardsTempHand().get(j).getRank()
					.equals(player.getSevenCardsTempHand().get(j - 1).getRank())) {
				player.setTwoPairs(true);
				player.setHandRank(HandRank.TWO_PAIRS);

				// add second pair to the 5 cards hand
				hand[2] = player.getSevenCardsTempHand().get(j);
				hand[3] = player.getSevenCardsTempHand().get(j - 1);
				player.getSevenCardsTempHand().remove(j);
				player.getSevenCardsTempHand().remove(j - 1);

				// add kicker
				hand[4] = player.getSevenCardsTempHand().get(2);
				player.setFiveCardsHand(hand);
				return true;
			}
		}

		// add 3 kickers for the single pair found
		hand[2] = player.getSevenCardsTempHand().get(4);
		hand[3] = player.getSevenCardsTempHand().get(3);
		hand[4] = player.getSevenCardsTempHand().get(2);
		player.setFiveCardsHand(hand);
		
		return false;
	}

	/*
	 * Check if the hand is one-pair.
	 */
	public void checkPair(TestPlayer player) {
		for (int j = 6; j > 0; j--) {
			if (player.getSevenCardsTempHand().get(j).getRank()
					.equals(player.getSevenCardsTempHand().get(j - 1).getRank())) {
				player.setPair(true);
				player.setHandRank(HandRank.PAIR);

				// add the pair to the 5 cards hand
				hand[0] = player.getSevenCardsTempHand().get(j);
				hand[1] = player.getSevenCardsTempHand().get(j - 1);
				player.getSevenCardsTempHand().remove(j);
				player.getSevenCardsTempHand().remove(j - 1);
				break;
			}
		}
	}

	/*
	 * Check if the hand is a high-card hand.
	 */
	public void checkHighCard(TestPlayer player) {
		player.setHandRank(HandRank.HIGH_CARD);

		for (int i = 0, j = 6; i < hand.length; i++, j--)
			hand[i] = player.getSevenCardsTempHand().get(j);

		player.setFiveCardsHand(hand);
	}

	/*
	 * Resets hand rank of all players at the table.
	 */
	private void resetHandRanks() {
		TestPlayer player;

		for (int i = 0; i < table.getTablePlayers().size(); i++) {
			player = table.getTablePlayers().get(i);
			player.setPair(false);
			player.setTwoPairs(false);
			player.setTrips(false);
			player.setStraight(false);
			player.setFlush(false);
			player.setFullHouse(false);
			player.setQuads(false);
			player.setStrFlush(false);
		}
	}
	
	/*
	 * Bubble sort by rank-value in ascending order.
	 */
	private void sortHand(TestCard[] hand) {
		for (int i = 0; i < hand.length; i++) {
			for (int j = i + 1; j < hand.length; j++) {
				if (hand[i].getRank().getValue() > hand[j].getRank().getValue()) {
					TestCard temp = hand[i];
					hand[i] = hand[j];
					hand[j] = temp;
				}
			}
		}
	}
}

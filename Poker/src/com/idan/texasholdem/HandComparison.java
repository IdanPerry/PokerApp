package com.idan.texasholdem;

import com.idan.game.Player;
import com.idan.game.Table;
import com.idan.texasholdem.HandEvaluation.HandRank;

/**
 * This class represents a comparison tool between the player's hands.
 * 
 * @author Idan Perry
 * @version 03.15.2013
 */

public class HandComparison {	
	private static final int HAND_SIZE = 5;
	private final Table table;
	private HandRank handRank;

	/**
	 * Constructs a hand comparison object to use in the specified table.
	 * 
	 * @param table the table this hand comparison is to be used
	 */
	public HandComparison(Table table) {
		this.table = table;
	}

	/**
	 * Flips the cards and compare the hands.
	 */
	public void showDown() {
		int size = table.getTablePlayers().size();
		table.sortPlayersByHandValue(table.getTablePlayers());
		handRank = table.getTablePlayers().get(size - 1).getHandRank();

		// one player holds a hand stronger than others.
		if (size >= 2 && table.getTablePlayers().get(size - 1).getHandValue() > table.getTablePlayers().get(size - 2)
				.getHandValue()) {
			table.getTablePlayers().get(size - 1).setScore(1);
			printWinner(table.getTablePlayers().get(size - 1));
			return;
		}

		compareSameValueHands(table.getTablePlayers().get(size - 1), table.getTablePlayers().get(size - 2));
	}

	/*
	 * Compares hands which has the same strength (one pair vs. one pair etc.)
	 */
	private void compareSameValueHands(Player player1, Player player2) {
		Player player = null;
		
		switch (handRank) {
		case STRAIGHT_FLUSH:
			player = compareStraightAndFlush(player1, player2);
			break;

		case QUADS:
			player = compareQuads(player1, player2);
			break;

		case FULL_HOUSE:
			player = compareFullHouse(player1, player2);
			break;

		case FLUSH:
			player = compareStraightAndFlush(player1, player2);
			break;

		case STRAIGHT:
			player = compareStraightAndFlush(player1, player2);
			break;

		case TRIPS:
			player = compareTrips(player1, player2);
			break;

		case TWO_PAIRS:
			player = compareTwoPairs(player1, player2);
			break;

		case PAIR:
			player = comparePair(player1, player2);
			break;

		case HIGH_CARD:
			player = compareHighCard(player1, player2);
			break;
		}
		
		if(player != null) {
			player.setScore(1);
			printWinner(player);
		}
	}
	
	/*
	 * Returns the player with the highest kicker card.
	 * if all kickers are the same, the hands are equal and a null
	 * will be returned.
	 */
	private Player compareKickers(Player player1, Player player2, int i) {
		while(i < HAND_SIZE && player1.getFiveCardsHand()[i].getRank().equals(player2.getFiveCardsHand()[i].getRank()))
			i++;
			
		if(i == HAND_SIZE)
			return null;
		
		if(player1.getFiveCardsHand()[i].getRank().getValue() > player2.getFiveCardsHand()[i].getRank().getValue())
			return player1;
		 
		return player2;
	}

	/*
	 * Prints the plyare with the winning hand and the winning hand.
	 */
	private void printWinner(Player player) {
		System.out.print(player.getName() + " wins with " + player.getHandRank() + ":	");

		for (int i = 0; i < player.getFiveCardsHand().length; i++) {
			System.out.print(player.getFiveCardsHand()[i].getRank());
			System.out.print(player.getFiveCardsHand()[i].getSuit());
		}

		System.out.println("\n");
	}

	/*
	 * Compares hands of 2 players, which ranked as High Card.
	 * returns the player with the highest high card or null
	 * if both hands are equal.
	 */
	private Player compareHighCard(Player player1, Player player2) {
		int i = 0;

		while (i < HAND_SIZE && player1.getFiveCardsHand()[i].getRank().equals(player2.getFiveCardsHand()[i].getRank()))
			i++;

		// both players have the same hand
		if(i == HAND_SIZE )
			return null;
		
		if (player1.getFiveCardsHand()[i].getRank().getValue() > player2.getFiveCardsHand()[i].getRank().getValue())
			return player1;
		
		return player2;
	}

	/*
	 * Compares hands of 2 players, which ranked as Pair.
	 * returns the player with the highest pair or null
	 * if both hands are equal.
	 */
	private Player comparePair(Player player1, Player player2) {
		// compare the pair
		if(player1.getFiveCardsHand()[0].getRank().getValue() > player2.getFiveCardsHand()[0].getRank().getValue())
			return player1;
		else if(player1.getFiveCardsHand()[0].getRank().getValue() < player2.getFiveCardsHand()[0].getRank().getValue())
			return player2;
		
		return compareKickers(player1, player2, 2);	
	}

	/*
	 * Compares hands of 2 players, which ranked as Two Pairs.
	 * returns the player with the highest two pairs or null
	 * if both hands are equal.
	 */
	private Player compareTwoPairs(Player player1, Player player2) {
		// compair pairs, first the bigger value than the second
		for(int i = 0; i <= 2; i+=2) {
			if(player1.getFiveCardsHand()[i].getRank().getValue() > player2.getFiveCardsHand()[i].getRank().getValue())
				return player1;
			else if(player1.getFiveCardsHand()[i].getRank().getValue() < player2.getFiveCardsHand()[i].getRank().getValue())
				return player2;
		}
		
		// compair kicker
		return compareCardFromEach(player1, player2, 4);
	}

	/*
	 * Compares hands of 2 players, which ranked as Trips.
	 * returns the player with the highest trips or null
	 * if both hands are equal.
	 */
	private Player compareTrips(Player player1, Player player2) {
		// compair trips
		if(player1.getFiveCardsHand()[0].getRank().getValue() > player2.getFiveCardsHand()[0].getRank().getValue())
			return player1;
		else if(player1.getFiveCardsHand()[0].getRank().getValue() < player2.getFiveCardsHand()[0].getRank().getValue())
			return player2;
		
		//compair kickers
		return compareKickers(player1, player2, 3);
	}

	/*
	 * Compares hands of 2 players, which ranked as Straight or Flush or Straight-Flush.
	 * returns the player with the highest of the above respectively, or null if both
	 * hands are equal.
	 */
	private Player compareStraightAndFlush(Player player1, Player player2) {
		// compare by highest value in the hand
		return compareCardFromEach(player1, player2, 0);
	}

	/*
	 * Compares hands of 2 players, which ranked as Full-House.
	 * returns the player with the highest full-house or null
	 * if both hands are equal.
	 */
	private Player compareFullHouse(Player player1, Player player2) {
		// compair trips
		if(player1.getFiveCardsHand()[0].getRank().getValue() > player2.getFiveCardsHand()[0].getRank().getValue())
			return player1;
		else if(player1.getFiveCardsHand()[0].getRank().getValue() < player2.getFiveCardsHand()[0].getRank().getValue())
			return player2;
		
		// compare the pair
		return compareCardFromEach(player1, player2, 3);	
	}

	/*
	 * Compares hands of 2 players, which ranked as Quads.
	 * returns the player with the highest quads or null
	 * if both hands are equal.
	 */
	private Player compareQuads(Player player1, Player player2) {
		return compareCardFromEach(player1, player2, 0);
	}
	
	private Player compareCardFromEach(Player player1, Player player2, int i) {
		if(player1.getFiveCardsHand()[i].getRank().getValue() > player2.getFiveCardsHand()[i].getRank().getValue())
			return player1;
		else if(player1.getFiveCardsHand()[i].getRank().getValue() < player2.getFiveCardsHand()[i].getRank().getValue())
			return player2;
		
		return null;
	}
}

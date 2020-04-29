package com.idan.test;

import java.util.ArrayList;
import java.util.Comparator;

import com.idan.test.TestHandEvaluation.HandRank;

/**
 * This class represents a poker player.
 * 
 * @author Idan Perry
 * @version 03.05.2013
 */

public class TestPlayer  {
	public static final String[] HAND_RANK_STR = {"High Card", "Pair", "Two Pairs", 
			"Trips", "Straight", "Flush", "Full House", "Quads", "Straight Flush"};
	
	private String name;
	private HandRank handRank;
	private int score;
	
	private TestCard holeCard1;
	private TestCard holeCard2;
	private TestCard holeCard3;
	private TestCard holeCard4;
	private ArrayList<TestCard> sevenCardsTempHand;  // holecards and all community cards combined
	private TestCard[] fiveCardsHand;  // best posible hand made out of 5 cards

	private boolean strFlush;
	private boolean quads;
	private boolean fullHouse;
	private boolean flush;
	private boolean straight;
	private boolean trips;
	private boolean twoPairs;
	private boolean pair;
	
	/**
	 * Constructs a poker player object.
	 * 
	 * @param name the name of the player
	 */
	public TestPlayer(String name) {
		this.name = name;
	}

	/**
	 * Sets the player's name.
	 * 
	 * @param name the player's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the player.
	 * 
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Initializes the player's 2 holecards.
	 * 
	 * @param holeCard1 first holecard
	 * @param holeCard2 second holecard
	 */
	public void setTexasHoleCards(TestCard holeCard1, TestCard holeCard2) {
		this.holeCard1 = holeCard1;
		this.holeCard2 = holeCard2;
	}
	
	/**
	 * Initializes the player's 4 holecards.
	 * 
	 * @param holeCard1 first holecard
	 * @param holeCard2 second holecard
	 * @param holeCard3 third holecard
	 * @param holeCard4 forth holecard
	 */
	public void setOmahaHoleCards(TestCard holeCard1, TestCard holeCard2, TestCard holeCard3, TestCard holeCard4) {
		this.holeCard1 = holeCard1;
		this.holeCard2 = holeCard2;
		this.holeCard3 = holeCard3;
		this.holeCard4 = holeCard4;
	}
	
	/**
	 * Returns the first holecard.
	 * 
	 * @return the first holecard
	 */
	public TestCard getHoleCard1() {
		return holeCard1;
	}
	
	/**
	 * Returns the second holecard.
	 * 
	 * @return the second holecard
	 */
	public TestCard getHoleCard2() {
		return holeCard2;
	}
	
	/**
	 * Returns the third holecard.
	 * 
	 * @return the third holecard
	 */
	public TestCard getHoleCard3() {
		return holeCard3;
	}
	
	/**
	 * Returns the forth holecard.
	 * 
	 * @return the forth holecard
	 */
	public TestCard getHoleCard4() {
		return holeCard4;
	}
	
	/**
	 * Sets a hand to the player.
	 * 
	 * @param hand an array of cards representing the hand
	 */
	public void setFiveCardsHand(TestCard[] hand) {
		this.fiveCardsHand = hand;
	}
	
	/**
	 * Returns the player's hand.
	 * 
	 * @return the player's hand
	 */
	public TestCard[] getFiveCardsHand() {
		return fiveCardsHand;
	}
	
	/**
	 * Returns a temporary seven cards hand.
	 * 
	 * @return a temporary seven cards hand
	 */
	public ArrayList<TestCard> getSevenCardsTempHand() {
		return sevenCardsTempHand;
	}

	/**
	 * Sets a temporary seven cards hand to the player.
	 * 
	 * @param hand an array of cards representing the temporary hand
	 */
	public void setSevenCardsTempHand(ArrayList<TestCard> sevenCardsTempHand) {
		this.sevenCardsTempHand = sevenCardsTempHand;
	}

	public void setStrFlush(boolean strFlush) {
		this.strFlush = strFlush;
	}

	public boolean isStrFlush() {
		return strFlush;
	}

	public void setQuads(boolean quads) {
		this.quads = quads;
	}

	public boolean isQuads() {
		return quads;
	}

	public void setFullHouse(boolean fullHouse) {
		this.fullHouse = fullHouse;
	}

	public boolean isFullHouse() {
		return fullHouse;
	}

	public void setFlush(boolean flush) {
		this.flush = flush;
	}

	public boolean isFlush() {
		return flush;
	}

	public void setStraight(boolean straight) {
		this.straight = straight;
	}

	public boolean isStraight() {
		return straight;
	}

	public void setTrips(boolean trips) {
		this.trips = trips;
	}

	public boolean isTrips() {
		return trips;
	}

	public void setTwoPairs(boolean twoPairs) {
		this.twoPairs = twoPairs;
	}

	public boolean isTwoPairs() {
		return twoPairs;
	}

	public void setPair(boolean pair) {
		this.pair = pair;
	}

	public boolean isPair() {
		return pair;
	}
	
	public HandRank getHandRank() {
		return handRank;
	}

	public void setHandRank(HandRank handRank) {
		this.handRank = handRank;
	}

	public int getHandValue() {
		return handRank.getValue();
	}

	public void setScore(int score) {
		this.score = this.score + score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void sortHandByRank(ArrayList<TestCard> hand) {
		hand.sort(new Comparator<TestCard>() {

			@Override
			public int compare(TestCard card1, TestCard card2) {
				return Integer.compare(card1.getRank().getValue(), card2.getRank().getValue());
			}
		});
	}
	
	public void sortHandBySuit(ArrayList<TestCard> hand) {
		hand.sort(new Comparator<TestCard>() {

			@Override
			public int compare(TestCard card1, TestCard card2) {
				return Integer.compare(card1.getSuit().getValue(), card2.getSuit().getValue());
			}
		});
	}
}

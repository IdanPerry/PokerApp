package com.idan.test;

import java.util.ArrayList;
import java.util.Comparator;

public class TestPlayer  {
	private String name;
	private int handValue;
	private int chips;
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
	
	private boolean fold;
	private boolean check;
	private boolean call;
	private boolean bet;
	private boolean raise;
	
	private boolean dealer;
	private boolean bigBlind;
	private boolean smallBlind;	
	private boolean yourTurn;
	
	public static final String[] HAND_RANK = {"High Card", "Pair", "Two Pairs", 
			"Trips", "Straight", "Flush", "Full House", "Quads", "Straight Flush"};
	
	public TestPlayer(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	// Each player gets his hole cards
	public void setTexasHoleCards(TestCard holeCard1, TestCard holeCard2) {
		this.holeCard1 = holeCard1;
		this.holeCard2 = holeCard2;
	}
	
	// Each player gets his hole cards
	public void setOmahaHoleCards(TestCard holeCard1, TestCard holeCard2, TestCard holeCard3, TestCard holeCard4) {
		this.holeCard1 = holeCard1;
		this.holeCard2 = holeCard2;
		this.holeCard3 = holeCard3;
		this.holeCard4 = holeCard4;
	}
	
	public TestCard getHoleCard1() {
		return holeCard1;
	}
	
	public TestCard getHoleCard2() {
		return holeCard2;
	}
	
	public TestCard getHoleCard3() {
		return holeCard3;
	}
	
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
	
	public void setChips(int chips) {
		this.chips = chips;
	}
	
	public int getChips() {
		return chips;
	}

	public boolean isDealer() {
		return dealer;
	}

	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}

	public boolean isBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(boolean bigBlind) {
		this.bigBlind = bigBlind;
	}

	public boolean isSmallBlind() {
		return smallBlind;
	}

	public void setSmallBlind(boolean smallBlind) {
		this.smallBlind = smallBlind;
	}
	
	public boolean isYourTurn() {
		return yourTurn;
	}
	
	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
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

	public void setHandValue(int handValue) {
		this.handValue = handValue;
	}

	public int getHandValue() {
		return handValue;
	}

	public void setScore(int score) {
		this.score = this.score + score;
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean isFold() {
		return fold;
	}
	
	public boolean isCall() {
		return call;
	}
	
	public boolean isCheck() {
		return check;
	}
	
	public boolean isBet() {
		return bet;
	}
	
	public boolean isRaise() {
		return raise;
	}

	public void fold() {
		fold = true;
	}

	public boolean check() {
		check = true;
		return check;
	}
	
	public boolean call() {
		call = true;
		return call;
	}
	
	public boolean bet() {
		bet = true;
		return bet;
	}

	public boolean raise() {
		raise = true;
		return raise;
	}
	
	public void resetActions() {
		fold = false;
		check = false;
		call = false;
		bet = false;
		raise = false;
	}
	
	public void sortHandByRank(ArrayList<TestCard> hand) {
		hand.sort(new Comparator<TestCard>() {

			@Override
			public int compare(TestCard card1, TestCard card2) {
				return Integer.compare(card1.getRank().getValue(), card2.getRank().getValue());
			}
		});
//		Card temp;
//		
//		for (int i = 0; i < hand.length; i++) {
//			for(int j = i+1; j < hand.length; j++) {
//				if(hand[i].getRank().getValue() > hand[j].getRank().getValue()) {
//					temp = hand[i];
//					hand[i] = hand[j];
//					hand[j] = temp;
//				}
//			}
//		}
	}
	
	public void sortHandBySuit(ArrayList<TestCard> hand) {
		hand.sort(new Comparator<TestCard>() {

			@Override
			public int compare(TestCard card1, TestCard card2) {
				return Integer.compare(card1.getSuit().getValue(), card2.getSuit().getValue());
			}
		});
		
//		Card temp;
//		
//		for (int i = 0; i < hand.length; i++) {
//			for(int j = i+1; j < hand.length; j++) {
//				if(hand[i].getSuit().getValue() > hand[j].getSuit().getValue()) {
//					temp = hand[i];
//					hand[i] = hand[j];
//					hand[j] = temp;
//				}
//			}
//		}
	}
}

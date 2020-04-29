package com.idan.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import com.idan.texasholdem.HandEvaluation.HandRank;

/**
 * This class represents a poker player.
 * 
 * @author Idan Perry
 * @version 03.05.2013
 */

public class Player implements Serializable {
	private static final long serialVersionUID = 271175255872953148L;
	
	public static final String[] HAND_RANK_STR = {"High Card", "Pair", "Two Pairs", 
			"Trips", "Straight", "Flush", "Full House", "Quads", "Straight Flush"};
	
	private final String name;
	private HandRank handRank;
	private int chips;
	private int score;
	private int currentBet;
	private int lastBet;
	private int seat;
	
	private Card holeCard1;
	private Card holeCard2;
	private Card holeCard3;
	private Card holeCard4;
	private ArrayList<Card> sevenCardsTempHand;  // holecards and all community cards combined
	private Card[] fiveCardsHand;  // best posible hand made out of 5 cards

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
	
	private boolean dealerPosition;
	private boolean bigBlindPosition;
	private boolean smallBlindPosition;	
	private boolean yourTurn;
	private boolean win;
	private boolean allIn;
	
	/**
	 * Constructs a poker player object.
	 * 
	 * @param name the name of the player
	 */
	public Player(String name) {
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
	public void setTexasHoleCards(Card holeCard1, Card holeCard2) {
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
	public void setOmahaHoleCards(Card holeCard1, Card holeCard2, Card holeCard3, Card holeCard4) {
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
	public Card getHoleCard1() {
		return holeCard1;
	}
	
	/**
	 * Returns the second holecard.
	 * 
	 * @return the second holecard
	 */
	public Card getHoleCard2() {
		return holeCard2;
	}
	
	/**
	 * Returns the third holecard.
	 * 
	 * @return the third holecard
	 */
	public Card getHoleCard3() {
		return holeCard3;
	}
	
	/**
	 * Returns the forth holecard.
	 * 
	 * @return the forth holecard
	 */
	public Card getHoleCard4() {
		return holeCard4;
	}
	
	/**
	 * Sets a hand to the player.
	 * 
	 * @param hand an array of cards representing the hand
	 */
	public void setFiveCardsHand(Card[] hand) {
		this.fiveCardsHand = hand;
	}
	
	/**
	 * Returns the player's hand.
	 * 
	 * @return the player's hand
	 */
	public Card[] getFiveCardsHand() {
		return fiveCardsHand;
	}
	
	/**
	 * Returns a temporary seven cards hand.
	 * 
	 * @return a temporary seven cards hand
	 */
	public ArrayList<Card> getSevenCardsTempHand() {
		return sevenCardsTempHand;
	}

	/**
	 * Sets a temporary seven cards hand to the player.
	 * 
	 * @param hand an array of cards representing the temporary hand
	 */
	public void setSevenCardsTempHand(ArrayList<Card> sevenCardsTempHand) {
		this.sevenCardsTempHand = sevenCardsTempHand;
	}

	/**
	 * Sets or changes the chip bank of the player.
	 * 
	 * @param chips the chips of the player
	 */
	public void setChips(int chips) {
		this.chips = chips;
	}
	
	/**
	 * Returns the chips of the player.
	 * 
	 * @return the chips of the player
	 */
	public int getChips() {
		return chips;
	}
	
	/**
	 * 
	 * @param seat
	 */
	public void setSeat(int seat) {
		this.seat = seat;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSeat() {
		return seat;
	}

	public boolean isDealerPosition() {
		return dealerPosition;
	}

	public void setDealerPosition(boolean dealer) {
		this.dealerPosition = dealer;
	}

	public boolean isBigBlindPosition() {
		return bigBlindPosition;
	}

	public void setBigBlindPosition(boolean bigBlind) {
		this.bigBlindPosition = bigBlind;
	}

	public boolean isSmallBlindPosition() {
		return smallBlindPosition;
	}

	public void setSmallBlindPosition(boolean smallBlind) {
		this.smallBlindPosition = smallBlind;
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
	
	public HandRank getHandRank() {
		return handRank;
	}

	public void setHandRank(HandRank handRank) {
		this.handRank = handRank;
	}

	public void setHandValue(HandRank handRank) {
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
	
	public boolean bet(int currentBet) {
		this.currentBet = currentBet;
		bet = true;
		chips -= currentBet;
		return bet;
	}
	
	public int getCurrentBet() {
		return currentBet;
	}

	public boolean raise(int currentBet) {
		this.currentBet = currentBet;
		raise = true;
		return raise;
	}
	
	public void setLastBet(int lastBet) {
		this.lastBet = lastBet;
	}
	
	public int getLastBet() {
		return lastBet;
	}
	
	public void setWin(boolean win) {
		this.win = win;
	}
	
	public boolean isWin() {
		return win;
	}
	
	public void setAllIn(boolean allIn) {
		this.allIn = allIn;
	}
	
	public boolean isAllIn() {
		return allIn;
	}
	
	public void resetActions() {
		fold = false;
		check = false;
		call = false;
		bet = false;
		raise = false;
		currentBet = 0;
		win = false;
		allIn = false;
	}
	
	public void sortHandByRank(ArrayList<Card> hand) {
		hand.sort(new Comparator<Card>() {

			@Override
			public int compare(Card card1, Card card2) {
				return Integer.compare(card1.getRank().getValue(), card2.getRank().getValue());
			}
		});
	}
	
	public void sortHandBySuit(ArrayList<Card> hand) {
		hand.sort(new Comparator<Card>() {

			@Override
			public int compare(Card card1, Card card2) {
				return Integer.compare(card1.getSuit().getValue(), card2.getSuit().getValue());
			}
		});
	}
}

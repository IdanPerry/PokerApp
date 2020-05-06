package com.idan.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import com.idan.texasholdem.HandEvaluation.HandRank;

/**
 * This class represents a poker player.
 * 
 * @author Idan Perry
 * @version 04.05.2020
 */

public class Player implements Serializable {
	private static final long serialVersionUID = 271175255872953148L;

	public static final String[] HAND_RANK_STR = { "High Card", "Pair", "Two Pairs", "Trips", "Straight", "Flush",
			"Full House", "Quads", "Straight Flush" };

	private final String name;
	private HandRank handRank;
	private int chips;
	private int score;
	private int currentBet;
	private int tempPot;
	private int seat;

	private Card holeCard1;
	private Card holeCard2;
	private Card holeCard3;
	private Card holeCard4;
	private ArrayList<Card> sevenCardsTempHand; // holecards and all community cards combined
	private Card[] fiveCardsHand; // best posible hand made out of 5 cards

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
	private boolean smallBlindAction;
	private boolean yourTurn;
	private boolean facingRaise;
	private boolean hasActed;
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
	
	public void setTempPot(int tempPot) {
		this.tempPot = tempPot;
	}
	
	public int getTempPot() {
		return tempPot;
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

	/**
	 * Returns true if this player is at the dealer position, false otherwise.
	 * 
	 * @return true if this player is at the dealer position, false otherwise
	 */
	public boolean isDealerPosition() {
		return dealerPosition;
	}

	/**
	 * Changes whether this player is at the dealer position.
	 * 
	 * @param smallBlind true if this player is at the dealer position
	 */
	public void setDealerPosition(boolean dealer) {
		this.dealerPosition = dealer;
	}

	/**
	 * Returns true if this player is at the big blind position, false otherwise.
	 * 
	 * @return true if this player is at the big blind position, false otherwise
	 */
	public boolean isBigBlindPosition() {
		return bigBlindPosition;
	}

	/**
	 * Changes whether this player is at the big blind position.
	 * 
	 * @param smallBlind true if this player is at the big blind position
	 */
	public void setBigBlindPosition(boolean bigBlind) {
		this.bigBlindPosition = bigBlind;
	}

	/**
	 * Returns true if this player is at the small blind position, false otherwise.
	 * 
	 * @return true if this player is at the small blind position, false otherwise
	 */
	public boolean isSmallBlindPosition() {
		return smallBlindPosition;
	}

	/**
	 * Changes whether this player is at the small blind position.
	 * 
	 * @param smallBlind true if this player is at the small blind position
	 */
	public void setSmallBlindPosition(boolean smallBlind) {
		this.smallBlindPosition = smallBlind;
	}

	/**
	 * Returns true if this player turn to act and false otherwise.
	 * 
	 * @return true if this player turn to act and false otherwise
	 */
	public boolean isYourTurn() {
		return yourTurn;
	}

	/**
	 * Changed the state of this player's turn to act.
	 * 
	 * @param yourTurn the boolean state of this player's turn
	 */
	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}

	public boolean isFacingRaise() {
		return facingRaise;
	}

	public void setFacingRaise(boolean facingRaise) {
		this.facingRaise = facingRaise;
	}

	public boolean isHasActed() {
		return hasActed;
	}

	public void setHasActed(boolean hasActed) {
		this.hasActed = hasActed;
	}

	/**
	 * Changes whether this player has Straight-flush.
	 * 
	 * @param strFlush true if this player has Straight-flush. false otherwise
	 */
	public void setStrFlush(boolean strFlush) {
		this.strFlush = strFlush;
	}

	/**
	 * Returns true if this player has Straight-flush, false otherwise.
	 * 
	 * @return true if this player has Straight-flush, false otherwise
	 */
	public boolean isStrFlush() {
		return strFlush;
	}

	/**
	 * Changes whether this player has Quads.
	 * 
	 * @param strFlush true if this player has Quads. false otherwise
	 */
	public void setQuads(boolean quads) {
		this.quads = quads;
	}

	/**
	 * Returns true if this player has Quads, false otherwise.
	 * 
	 * @return true if this player has Quads, false otherwise
	 */
	public boolean isQuads() {
		return quads;
	}

	/**
	 * Changes whether this player has Full-House.
	 * 
	 * @param strFlush true if this player has Full-House. false otherwise
	 */
	public void setFullHouse(boolean fullHouse) {
		this.fullHouse = fullHouse;
	}

	/**
	 * Returns true if this player has Full-House, false otherwise.
	 * 
	 * @return true if this player has Full-House, false otherwise
	 */
	public boolean isFullHouse() {
		return fullHouse;
	}

	/**
	 * Changes whether this player has flush.
	 * 
	 * @param strFlush true if this player has flush. false otherwise
	 */
	public void setFlush(boolean flush) {
		this.flush = flush;
	}

	/**
	 * Returns true if this player has flush, false otherwise.
	 * 
	 * @return true if this player has flush, false otherwise
	 */
	public boolean isFlush() {
		return flush;
	}

	/**
	 * Changes whether this player has Straight.
	 * 
	 * @param strFlush true if this player has Straight. false otherwise
	 */
	public void setStraight(boolean straight) {
		this.straight = straight;
	}

	/**
	 * Returns true if this player has Straight, false otherwise.
	 * 
	 * @return true if this player has Straight, false otherwise
	 */
	public boolean isStraight() {
		return straight;
	}

	/**
	 * Changes whether this player has Trips.
	 * 
	 * @param strFlush true if this player has Trips. false otherwise
	 */
	public void setTrips(boolean trips) {
		this.trips = trips;
	}

	/**
	 * Returns true if this player has Trips, false otherwise.
	 * 
	 * @return true if this player has Trips, false otherwise
	 */
	public boolean isTrips() {
		return trips;
	}

	/**
	 * Changes whether this player has Two Pairs.
	 * 
	 * @param strFlush true if this player has Two Pairs. false otherwise
	 */
	public void setTwoPairs(boolean twoPairs) {
		this.twoPairs = twoPairs;
	}

	/**
	 * Returns true if this player has Two Pairs, false otherwise.
	 * 
	 * @return true if this player has Two Pairs, false otherwise
	 */
	public boolean isTwoPairs() {
		return twoPairs;
	}

	/**
	 * Changes whether this player has Pair.
	 * 
	 * @param strFlush true if this player has Pair. false otherwise
	 */
	public void setPair(boolean pair) {
		this.pair = pair;
	}

	/**
	 * Returns true if this player has Pair, false otherwise.
	 * 
	 * @return true if this player has Pair, false otherwise
	 */
	public boolean isPair() {
		return pair;
	}

	/**
	 * Returns the hand rank of this player as enum object.
	 * 
	 * @return the hand rank of this player as enum object
	 */
	public HandRank getHandRank() {
		return handRank;
	}

	/**
	 * Changes the hand rank enum object of this player.
	 * 
	 * @param handRank the hand rank enum object of this player to be changed
	 */
	public void setHandRank(HandRank handRank) {
		this.handRank = handRank;
	}

	/**
	 * Returns the hand rank-value of this player.
	 * 
	 * @return the hand rank-value of this player
	 */
	public int getHandValue() {
		return handRank.getValue();
	}

	/**
	 * Adds winning score for this player. used for statistic analyze.
	 * 
	 * @param score the score to add to this player
	 */
	public void setScore(int score) {
		this.score = this.score + score;
	}

	/**
	 * Returns the winning score of this player.
	 * 
	 * @return the winning score of this player
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Returns true if this player submitted a fold.
	 * 
	 * @return true if this player submitted a fold
	 */
	public boolean isFold() {
		return fold;
	}

	/**
	 * Returns true if this player submitted a call.
	 * 
	 * @return true if this player submitted a call
	 */
	public boolean isCall() {
		return call;
	}

	/**
	 * Returns true if this player submitted a check.
	 * 
	 * @return true if this player submitted a check
	 */
	public boolean isCheck() {
		return check;
	}

	/**
	 * Returns true if this player submitted a bet.
	 * 
	 * @return true if this player submitted a bet
	 */
	public boolean isBet() {
		return bet;
	}

	/**
	 * Returns true if this player submitted a raise.
	 * 
	 * @return true if this player submitted a raise
	 */
	public boolean isRaise() {
		return raise;
	}

	/**
	 * Changes fold state of this player to true.
	 */
	public void fold() {
		fold = true;
	}

	/**
	 * Changes check state of this player to true.
	 */
	public void check() {
		check = true;
	}

	/**
	 * Reduces the amount to call from this player's chips.
	 */
	public void call(int currentBet) {
		chips -= currentBet;
		tempPot += currentBet;
		call = true;
	}

	/**
	 * Sets the current bet of this player, update the chips count and returns true.
	 * 
	 * @param currentBet the bet to be changed
	 * @return true
	 */
	public boolean bet(int currentBet) {
		this.currentBet = currentBet;
		chips -= currentBet;
		tempPot += currentBet;
		return bet = true;
	}

	/**
	 * Sets the current bet of this player and returns true.
	 * 
	 * @param currentBet the bet to be changed
	 * @return true
	 */
	public boolean raise(int currentBet) {
		this.currentBet = currentBet;
		tempPot += currentBet;
		return raise = true;
	}
	
	/**
	 * Returns the current bet of this player.
	 * 
	 * @return the current bet of this player
	 */
	public int getCurrentBet() {
		return currentBet;
	}
	
	/**
	 * Resets the current bet to 0. this method should be called
	 * at the start of each street in a hand.
	 */
	public void resetCurrentBet() {
		currentBet = 0;
	}

	/**
	 * Changes whether this player has to act at small blind position, preflop and
	 * it's his first action at the current hand.
	 * 
	 * @param smallBlindAction true if this player is at the small blind, preflop
	 *                         and it's his first action
	 */
	public void setSmallBlindAction(boolean smallBlindAction) {
		this.smallBlindAction = smallBlindAction;
	}

	/**
	 * Returns true if this player has to act at small blind position, preflop and
	 * it's his first action at the current hand.
	 * 
	 * @return this player has to act at small blind position, preflop and it's his
	 *         first action at the current hand
	 */
	public boolean isSmallBlindAction() {
		return smallBlindAction;
	}

	/**
	 * Changes the allin state of this player.
	 * 
	 * @param allIn the boolean state of allin
	 */
	public void setAllIn(boolean allIn) {
		this.allIn = allIn;
	}

	/**
	 * Returns true if this player is allin and false otherwise.
	 * 
	 * @return true if this player is allin and false otherwise
	 */
	public boolean isAllIn() {
		return allIn;
	}

	/**
	 * Reset all action booleans to false.
	 */
	public void resetActions() {
		fold = false;
		check = false;
		call = false;
		bet = false;
		raise = false;
		allIn = false;
		facingRaise = false;
		hasActed = false;
	}

	/**
	 * Sorts this player hand by its rank in ascending order.
	 * 
	 * @param hand the hand to be sorted
	 */
	public void sortHandByRank(ArrayList<Card> hand) {
		hand.sort(new Comparator<Card>() {

			@Override
			public int compare(Card card1, Card card2) {
				return Integer.compare(card1.getRank().getValue(), card2.getRank().getValue());
			}
		});
	}

	/**
	 * Sorts this player hand by its suit in ascending order.
	 * 
	 * @param hand the hand to be sorted
	 */
	public void sortHandBySuit(ArrayList<Card> hand) {
		hand.sort(new Comparator<Card>() {

			@Override
			public int compare(Card card1, Card card2) {
				return Integer.compare(card1.getSuit().getValue(), card2.getSuit().getValue());
			}
		});
	}
}

package com.idan.game;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 271175255872953148L;
	
	private String name;
	private int handValue;
	private int chips;
	private int score;
	private int currentBet;
	private int lastBet;
	private int seat;
	
	private Card holeCard1;
	private Card holeCard2;
	private Card holeCard3;
	private Card holeCard4;
	private Card[] hand;

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
	private boolean wins;
	private boolean allIn;
	
	public static final String[] HAND_RANK = {"High Card", "Pair", "Two Pairs", 
			"Trips", "Straight", "Flush", "Full House", "Quads", "Straight Flush"};
	
	public Player() {
	}
	
	public Player(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	// Each player gets his hole cards
	public void setTexasHoleCards(Card holeCard1, Card holeCard2) {
		this.holeCard1 = holeCard1;
		this.holeCard2 = holeCard2;
	}
	
	// Each player gets his hole cards
	public void setOmahaHoleCards(Card holeCard1, Card holeCard2, Card holeCard3, Card holeCard4) {
		this.holeCard1 = holeCard1;
		this.holeCard2 = holeCard2;
		this.holeCard3 = holeCard3;
		this.holeCard4 = holeCard4;
	}
	
	public Card getHoleCard1() {
		return holeCard1;
	}
	
	public Card getHoleCard2() {
		return holeCard2;
	}
	
	public Card getHoleCard3() {
		return holeCard3;
	}
	
	public Card getHoleCard4() {
		return holeCard4;
	}
	
	public void setHand(Card[] hand) {
		this.hand = hand;
	}
	
	public Card[] getHand() {
		return hand;
	}
	
	public void setChips(int chips) {
		this.chips = chips;
	}
	
	public int getChips() {
		return chips;
	}
	
	public void setSeat(int seat) {
		this.seat = seat;
	}
	
	public int getSeat() {
		return seat;
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
	
	public void setWin(boolean wins) {
		this.wins = wins;
	}
	
	public boolean isWins() {
		return wins;
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
		wins = false;
		allIn = false;
	}
}

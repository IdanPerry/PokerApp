package com.idan.server;

import java.io.Serializable;
import java.util.ArrayList;

import com.idan.game.Card;
import com.idan.game.Player;

public class TableInformation implements Serializable {
	private static final long serialVersionUID = 3308340352929276498L;
	
	private Player player;
	private Player winningPlayer;
	private ArrayList<String> tablePlayersNames;
	private ArrayList<Player> players;
	private int numOfPlayersInHand;
	private Card flop[];
	private Card turn;
	private Card river;
	private int pot;
	private int betSize;
	private int raise;
	private int smallBlind;
	private boolean raiseFlag;

	public TableInformation() {		
	}

	public synchronized Player getPlayer() {
		return player;
	}

	public synchronized void setPlayer(Player player) {
		this.player = player;
	}
	
	public synchronized Player getWinningPlayer() {
		return winningPlayer;
	}
	
	public synchronized void setWinningPlayer(Player winningPlayer) {
		this.winningPlayer = winningPlayer;
	}
	
	public synchronized void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public synchronized ArrayList<Player> getPlayers() {
		return players;
	}

	public synchronized ArrayList<String> getPlayersNames() {
		return tablePlayersNames;
	}

	public void setPlayersNames(ArrayList<String> tablePlayersNames) {
		this.tablePlayersNames = tablePlayersNames;
	}

	public synchronized int getNumOfPlayersInHand() {
		return numOfPlayersInHand;
	}

	public synchronized void setNumOfPlayersInHand(int numOfPlayersInHand) {
		this.numOfPlayersInHand = numOfPlayersInHand;
	}

	public synchronized Card[] getFlop() {
		return flop;
	}

	public synchronized void setFlop(Card[] flop) {
		this.flop = flop;
	}

	public synchronized Card getTurn() {
		return turn;
	}

	public synchronized void setTurn(Card turn) {
		this.turn = turn;
	}

	public synchronized Card getRiver() {
		return river;
	}

	public synchronized void setRiver(Card river) {
		this.river = river;
	}

	public synchronized int getPot() {
		return pot;
	}

	public synchronized void setPot(int pot) {
		this.pot = pot;
	}

	public synchronized int getBet() {
		return betSize;
	}

	public synchronized void setBet(int bet) {
		this.betSize = bet;
	}

	public synchronized int getRaise() {
		return raise;
	}

	public synchronized void setRaise(int raise) {
		this.raise = raise;
	}
	
	public synchronized void setRaiseFlag(boolean raiseFlag) {
		this.raiseFlag = raiseFlag;
	}
	
	public synchronized boolean isRaise() {
		return raiseFlag;
	}
	
	public synchronized int getSmallBlind() {
		return smallBlind;
	}
	
	public synchronized void setSmallBlind(int smallBlind) {
	this.smallBlind = smallBlind;
	}
}

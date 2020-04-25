package com.idan.server;

import java.io.Serializable;
import java.util.ArrayList;

import com.idan.game.Card;
import com.idan.game.Player;

public class TableInformation implements Serializable {
	private static final long serialVersionUID = 3308340352929276498L;
	
	private Player player;
	private ArrayList<String> tablePlayersNames;
	private ArrayList<Player> players;
	private int numOfPlayersInHand;
	private Card flop[];
	private Card turn;
	private Card river;
	private int pot;
	private int bet;
	private int raise;
	private boolean raiseFlag;

	public TableInformation() {		
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<String> getPlayersNames() {
		return tablePlayersNames;
	}

	public void setPlayersNames(ArrayList<String> tablePlayersNames) {
		this.tablePlayersNames = tablePlayersNames;
	}

	public int getNumOfPlayersInHand() {
		return numOfPlayersInHand;
	}

	public void setNumOfPlayersInHand(int numOfPlayersInHand) {
		this.numOfPlayersInHand = numOfPlayersInHand;
	}

	public Card[] getFlop() {
		return flop;
	}

	public void setFlop(Card[] flop) {
		this.flop = flop;
	}

	public Card getTurn() {
		return turn;
	}

	public void setTurn(Card turn) {
		this.turn = turn;
	}

	public Card getRiver() {
		return river;
	}

	public void setRiver(Card river) {
		this.river = river;
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public int getRaise() {
		return raise;
	}

	public void setRaise(int raise) {
		this.raise = raise;
	}
	
	public void setRaiseFlag(boolean raiseFlag) {
		this.raiseFlag = raiseFlag;
	}
	
	public boolean isRaise() {
		return raiseFlag;
	}
}

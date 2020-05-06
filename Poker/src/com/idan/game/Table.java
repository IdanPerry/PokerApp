package com.idan.game;

import java.util.ArrayList;
import java.util.Comparator;

import com.idan.server.Server;
import com.idan.server.TableInformation;
import com.idan.texasholdem.HandComparison;
import com.idan.texasholdem.HandEvaluation;
import com.idan.texasholdem.TexasHoldemDealer;

/**
 * This abstract class represents a poker table.
 * 
 * @author Idan Perry
 * @version 04.05.2020
 *
 */

public abstract class Table extends Thread {
	public static final int SMALL_BLIND = 50;
	public static final int BIG_BLIND = 100;
	public static final int MAX_SEATS = 9;
	protected static final int MIN_PLAYRES = 2;

	private final ArrayList<Player> tablePlayers;
	private final ArrayList<Player> playersInHand;
	private final ArrayList<String> playersNames;
	private final TexasHoldemDealer dealer;
	private final TableInformation tableInfo;
	private Server server;
	private Player player;

	private final int tableId;
	private int numOfPlayers;
	private int numOfPlayersInHand;
	private int butPosition;
	private int sbPosition;
	private int bbPosition;
	private int actionCounter;
	private int currentbet;
	private int pot;
	private boolean holeCardsWereDealt;
	private boolean running;
	private boolean preFlop;
	private boolean flopWasDealt;
	private boolean turnWasDealt;
	private boolean riverWasDealt;
	private boolean raiseMade;

	/**
	 * Constructs a poker table with a specified unique id.
	 * 
	 * @param tableId the table id
	 */
	public Table(int tableId, Server server) {
		this.tableId = tableId;
		this.server = server;

		tablePlayers = new ArrayList<Player>();
		dealer = new TexasHoldemDealer(this);
		tableInfo = new TableInformation();
		playersInHand = new ArrayList<Player>();
		playersNames = new ArrayList<String>();
	}

	/**
	 * Returns a list of the players at the table.
	 * 
	 * @return a list of the players at the table
	 */
	public synchronized ArrayList<Player> getTablePlayers() {
		return tablePlayers;
	}

	/**
	 * Returns a list of the players participating in the current hand.
	 * 
	 * @return a list of the players participating in the current hand
	 */
	public synchronized ArrayList<Player> getPlayersInHand() {
		return playersInHand;
	}

	/**
	 * Returns the table id.
	 * 
	 * @return the table id
	 */
	public synchronized int getTableId() {
		return tableId;
	}

	/**
	 * Returns the dealer at this table.
	 * 
	 * @return the dealer at this table
	 */
	public synchronized Dealer getDealer() {
		return dealer;
	}

	/**
	 * Returns this table game information.
	 * 
	 * @return this table game information
	 */
	public synchronized TableInformation getTableInfo() {
		return tableInfo;
	}

	/**
	 * Initializes the number of players at this table.
	 * 
	 * @param numOfPlayers the number of players at this table
	 */
	protected synchronized void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	/**
	 * Returns the number of players at this table.
	 * 
	 * @return the number of players at this table
	 */
	public synchronized int getNumOfPlayers() {
		return numOfPlayers;
	}

	/**
	 * Returns the number of players participating in the current hand.
	 * 
	 * @return the number of players participating in the current hand
	 */
	public synchronized int getNumOfPlayersInHand() {
		return numOfPlayersInHand;
	}

	/**
	 * Changes the state of holecards whether were dealt at this table or not.
	 * 
	 * @param holeCardsWereDealt the boolean flag to change.
	 */
	public synchronized void setHoleCardsWereDealt(boolean holeCardsWereDealt) {
		this.holeCardsWereDealt = holeCardsWereDealt;
	}

	/**
	 * Returns true if holecards were dealt at this table, otherwise returns flase.
	 * 
	 * @return true if holecards were dealt at this table, otherwise returns flase
	 */
	public synchronized boolean isHoleCardsWereDealt() {
		return holeCardsWereDealt;
	}

	/**
	 * Returns true if flop were dealt at this table, otherwise returns flase.
	 * 
	 * @return true if flop were dealt at this table, otherwise returns flase
	 */
	public synchronized boolean isFlopWasDealt() {
		return flopWasDealt;
	}

	/**
	 * Returns true if turn were dealt at this table, otherwise returns flase.
	 * 
	 * @return true if turn were dealt at this table, otherwise returns flase
	 */
	public synchronized boolean isTurnWasDealt() {
		return turnWasDealt;
	}

	/**
	 * Returns true if river were dealt at this table, otherwise returns flase.
	 * 
	 * @return true if river were dealt at this table, otherwise returns flase
	 */
	public synchronized boolean isRiverWasDealt() {
		return riverWasDealt;
	}

	/**
	 * Sort the players at this table by their hand strength in ascending order.
	 * 
	 * @param playerList the players to be sort
	 */
	public void sortPlayersByHandValue(ArrayList<Player> playerList) {
		playerList.sort(new Comparator<Player>() {

			@Override
			public int compare(Player player1, Player player2) {
				return Integer.compare(player1.getHandValue(), player2.getHandValue());
			}
		});
	}
	
	public synchronized void continueRun() {
		notifyAll();
	}
	
	/**
	 * Allocates a seat for the player at this table.
	 * 
	 * @param player the player to be seated at this table
	 */
	public abstract void seatPlayer(Player player);

	/**
	 * Allocates seats for a specified number of players at this table.
	 * 
	 * @param int the number of players to be seated at this table
	 */
	public abstract void seatPlayers(int players);
	
	/*
	 * Initializes the positions of the players at the dealer,
	 * big blind and small blind positions.
	 * this method should be used only once before the first hand is dealt.
	 */
	private void initPositions() {
		butPosition = tablePlayers.size() - 1;
		sbPosition = 0;
		bbPosition = 1;
	}
	
	/*
	 * Starts a new hand - resets previous hand booleans and dealer
	 * is making all necessary preparations.
	 */
	private void startHand() {		
		dealer.getDeck().shuffle();
		resetBlinds();
		dealer.dealHoleCards();
		initPlayersInHand();
		
		tableInfo.setRaiseFlag(false);
		tableInfo.setNumOfPlayersInHand(playersInHand.size());
		tableInfo.setPlayersNames(playersNames);
		
		actionCounter = 0;
		holeCardsWereDealt = true;
		flopWasDealt = false;
		turnWasDealt = false;
		riverWasDealt = false;
		raiseMade = false;
		preFlop = true;
		running = true;
		
		currentbet = BIG_BLIND;
		pot = SMALL_BLIND + BIG_BLIND;
		tableInfo.setSmallBlind(SMALL_BLIND);
		tableInfo.setBet(currentbet);
		tableInfo.setPot(pot);
		
		postBlinds();
		setPlayersTurns();	
	}
	
	/*
	 * Run the hand through all streets: pre-flop, flop, turn, river
	 * and showdown. action counter is used to determine when to advance
	 * to the next street: each raise action resets the counter to zero.
	 * if the counter reach the number of players at this table - the players
	 * remaining at the hand will play the next street. 
	 */
	private synchronized void runHand() {
		while(running) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			actionCounter++;
			
			if(player.isFold())
				playerFolded();
				
			else if(player.isCall())
				playerCalled();
				
			else if(player.isCheck())
				playerChecked();
				
			else if(player.isBet())
				playerBet();
				
			else if(player.isRaise())
				playerRaised(SMALL_BLIND, 0);		
		}
	}
	
	/*
	 * Resets dealer, small blind and big blind positions before starting new hand.
	 */
	private void resetBlinds() {
		for (int i = 0; i < tablePlayers.size(); i++) {
			tablePlayers.get(i).setBigBlindPosition(false);
			tablePlayers.get(i).setSmallBlindPosition(false);
			tablePlayers.get(i).setDealerPosition(false);
		}
		
		if (tablePlayers.size() == MIN_PLAYRES)
			butPosition = sbPosition;

		tablePlayers.get(butPosition).setDealerPosition(true);
		tablePlayers.get(bbPosition).setBigBlindPosition(true);
		tablePlayers.get(sbPosition).setSmallBlindPosition(true);
		tablePlayers.get(sbPosition).setSmallBlindAction(true);
	}

	/*
	 * Asks the players at the big blind and small blind positions to make the
	 * big/small blind bets in accordance to this table bet amount.
	 */
	private void postBlinds() {
		for (int i = 0; i < playersInHand.size(); i++) {
			// big blind
			if (playersInHand.get(i).isBigBlindPosition()) {
				playersInHand.get(i).bet(BIG_BLIND);
				System.out.println(playersInHand.get(i).getName() + " posts big blind " + BIG_BLIND);
				System.out.println(playersInHand.get(i).getName() + " has " + playersInHand.get(i).getChips());
			}

			// small blind
			if (playersInHand.get(i).isSmallBlindPosition()) {
				playersInHand.get(i).bet(SMALL_BLIND);
				System.out.println(playersInHand.get(i).getName() + " posts small blind " + SMALL_BLIND);
				System.out.println(playersInHand.get(i).getName() + " has " + playersInHand.get(i).getChips());
			}
		}
	}

	/*
	 * Sets turns for beginning of each street in the current hand.
	 */
	private void setPlayersTurns() {
		for (int i = 0; i < playersInHand.size(); i++) {
			playersInHand.get(i).setYourTurn(false);
			playersInHand.get(i).resetActions();
		}

		if (preFlop) {
			// 2 or 3 players in the current hand
			if (playersInHand.size() == MIN_PLAYRES || playersInHand.size() == 3) {
				playersInHand.get(butPosition).setYourTurn(true);
				player = playersInHand.get(butPosition);

				// more than 3 players in the current hand
			} else if (playersInHand.size() > 3) {
				playersInHand.get(bbPosition + 1).setYourTurn(true);
				player = playersInHand.get(bbPosition + 1);
			}

			// post flop
		} else if (flopWasDealt || turnWasDealt || riverWasDealt) {
			// 2 players in the current hand
			if (playersInHand.size() == MIN_PLAYRES) {
				playersInHand.get(bbPosition).setYourTurn(true);
				player = playersInHand.get(bbPosition);

				// more than 2 players in the current hand
			} else if (playersInHand.size() > MIN_PLAYRES) {
				playersInHand.get(sbPosition).setYourTurn(true);
				player = playersInHand.get(sbPosition);

			}
		}

		System.out.println(player.getName() + " Its your turn");
	}

	/*
	 * Sets turns for inside street playing.
	 */
	private void nextTurn() {
		for (int i = 0; i < playersInHand.size(); i++) {
			if (playersInHand.get(i).isYourTurn() && i < playersInHand.size() - 1) {
				playersInHand.get(i).setYourTurn(false);
				playersInHand.get(i).resetActions();
				playersInHand.get(i + 1).setYourTurn(true);
				player = playersInHand.get(i + 1);
				break;

			} else if (playersInHand.get(i).isYourTurn() && i == playersInHand.size() - 1) {
				playersInHand.get(i).setYourTurn(false);
				playersInHand.get(i).resetActions();
				playersInHand.get(0).setYourTurn(true);
				player = playersInHand.get(0);
				break;
			}
		}

		System.out.println(player.getName() + " Its your turn");
	}
	
	/*
	 * Dealer, small blind and big blind positions move clockwise every hand.
	 */
	private void movePositions() {
		butPosition++;
		sbPosition++;
		bbPosition++;

		// "button"
		if (butPosition == tablePlayers.size())
			butPosition = 0;

		// small blind
		if (sbPosition == tablePlayers.size())
			sbPosition = 0;

		// big blind
		if (bbPosition == tablePlayers.size())
			bbPosition = 0;
	}
	
	/*
	 * Deals flop cards.
	 */
	private void dealFlop() {
		dealer.dealFlop(2);
		dealer.printFlop();
		tableInfo.setFlop(dealer.getFlop());

		raiseMade = false;
		flopWasDealt = true;
		preFlop = false;
	}

	/*
	 * Deals turn card.
	 */
	private void dealTurn() {
		dealer.dealTurn(2);
		dealer.printTurn();
		tableInfo.setTurn(dealer.getTurn());

		raiseMade = false;
		turnWasDealt = true;
		flopWasDealt = false;
	}

	/*
	 * Deals river card.
	 */
	private void dealRiver() {
		dealer.dealRiver(2);
		dealer.printRiver();
		tableInfo.setRiver(dealer.getRiver());

		raiseMade = false;
		riverWasDealt = true;
		turnWasDealt = false;
	}

	/*
	 * Flips cards of the remianing players at the end of the hand,
	 * evaluates and compares the hands to determine the winner in the hand.
	 */
	private void showDown() {
		HandEvaluation evaluate = new HandEvaluation(dealer, this);
		HandComparison compare = new HandComparison(this);
		evaluate.resetHandRanks();
		evaluate.evaluateAllHands();
		player = compare.showDown();

		riverWasDealt = false;
		running = false;

		// move chips to the winner of this hand
		player.setChips(player.getChips() + pot);		
		tableInfo.setWinningPlayer(player);
		
		// reset
		playersInHand.clear();
		movePositions();
	}
	
	/*
	 * Initializes array of players who were dealt hole cards, for tracking
	 * which player is still playing through every street.
	 */
	private void initPlayersInHand() {
		for (int i = 0; i < tablePlayers.size(); i++) {
			playersInHand.add(tablePlayers.get(i));
			playersNames.add(tablePlayers.get(i).getName());
		}
	}
	
	/*
	 * The last action submmited in the current hand was fold.
	 * progresses to the next hand if the player last to act has folded
	 * and only one player remains in the hand.
	 */
	private void playerFolded() {
		playersInHand.remove(player);
		
		if(playersInHand.size() == 1) {
			playersInHand.clear();
			riverWasDealt = false;
			turnWasDealt = false;
			flopWasDealt = false;
			running = false;
			movePositions();
			
		} else
			nextTurn();
	}
	
	/*
	 * The last action submmited in the current hand was call.
	 * progresses to the next street if the player last to act has called.
	 */
	private void playerCalled() {
		if(preFlop && player.isSmallBlindAction()) {
			//player.setChips(player.getChips() - SMALL_BLIND);
			pot += currentbet - player.getCurrentBet();
			tableInfo.setPot(pot);
			
		} else if(preFlop && !raiseMade) {
			player.setChips(player.getChips() - currentbet + BIG_BLIND);
			pot += currentbet - BIG_BLIND;
			tableInfo.setPot(pot);
			
			// after raise made
		} else {
			player.setChips(player.getChips() - currentbet);
			pot += currentbet;
			tableInfo.setPot(pot);
		}
		
		if(actionCounter == playersInHand.size()) {
			nextSteet();
			setPlayersTurns();		
			
		} else 
			nextTurn();
	}
	
	/*
	 * The last action submmited in the current hand was check.
	 * progresses to the next street if the player last to act has checked.
	 */
	private void playerChecked() {
		if(actionCounter == playersInHand.size()) {
			nextSteet();
			setPlayersTurns();			
			
		} else 
			nextTurn();
	}
	
	/*
	 * The last action submmited in the current hand was bet.
	 * Updates the pot of this hand.
	 */
	private void playerBet() {
		currentbet = player.getCurrentBet();
		pot += currentbet;
		tableInfo.setBet(currentbet);
		tableInfo.setPot(pot);
		
		nextTurn();
	}
	
	/*
	 * The last action submmited in the current hand was raise.
	 * Updates the pot of this hand.
	 */
	private void playerRaised(int potChange, int betChange) {
		currentbet = player.getCurrentBet() - betChange;
		pot += currentbet - potChange;
		tableInfo.setBet(currentbet);
		tableInfo.setPot(pot);
		tableInfo.setRaiseFlag(true);
		
		if(preFlop)
			player.setChips(player.getChips() - player.getCurrentBet() + SMALL_BLIND);
		
		actionCounter = 0;
		nextTurn();
	}
	
	/*
	 * Progress to the next street in the current hand. 
	 */
	private void nextSteet() {
		actionCounter = 0;
		tableInfo.setSmallBlind(0);
		
		if(preFlop)
			dealFlop();
		
		else if(flopWasDealt)
			dealTurn();
		
		else if(turnWasDealt)
			dealRiver();
		
		else if(riverWasDealt)
			showDown();
	}
	
	private void notifyConnections() {
		for (int i = 0; i < playersInHand.size(); i++) {
			server.getConnections().get(i).proceed();
		}
	}

	@Override
	public synchronized void run() {
		while (tablePlayers.size() < MIN_PLAYRES) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//notifyConnections();
		initPositions();
		
		while(tablePlayers.size() >= MIN_PLAYRES) {
			startHand();
			runHand();
		}
	}
}

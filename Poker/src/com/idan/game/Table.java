package com.idan.game;

import java.util.ArrayList;

import com.idan.server.TableInformation;
import com.idan.texasholdem.HandComparison;
import com.idan.texasholdem.HandEvaluation;
import com.idan.texasholdem.TexasHoldemDealer;

/**
 * This abstract class represented a poker table.
 * 
 * @author Idan Perry
 * @version 13.05.2013
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
	private Player player;

	private final int tableId;
	private int numOfPlayers;
	private int numOfPlayersInHand;
	private int butPosition;
	private int sbPosition;
	private int bbPosition;
	private int bet;
	private int pot;
	private boolean holeCardsWereDealt;
	private boolean running;
	private boolean preFlop;
	private boolean flopWasDealt;
	private boolean turnWasDealt;
	private boolean riverWasDealt;

	/**
	 * Constructs a poker table with a specified unique id.
	 * 
	 * @param tableId the table id
	 */
	public Table(int tableId) {
		this.tableId = tableId;

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
	public ArrayList<Player> getTablePlayers() {
		return tablePlayers;
	}

	/**
	 * Returns a list of the players participating in the current hand.
	 * 
	 * @return a list of the players participating in the current hand
	 */
	public ArrayList<Player> getPlayersInHand() {
		return playersInHand;
	}

	/**
	 * Returns the table id.
	 * 
	 * @return the table id
	 */
	public int getTableId() {
		return tableId;
	}

	/**
	 * Returns the dealer at this table.
	 * 
	 * @return the dealer at this table
	 */
	public Dealer getDealer() {
		return dealer;
	}

	/**
	 * Returns this table game information.
	 * 
	 * @return this table game information
	 */
	public TableInformation getTableInfo() {
		return tableInfo;
	}

	/**
	 * Initializes the number of players at this table.
	 * 
	 * @param numOfPlayers the number of players at this table
	 */
	protected void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	/**
	 * Returns the number of players at this table.
	 * 
	 * @return the number of players at this table
	 */
	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	/**
	 * Returns the number of players participating in the current hand.
	 * 
	 * @return the number of players participating in the current hand
	 */
	public int getNumOfPlayersInHand() {
		return numOfPlayersInHand;
	}

	/**
	 * Changes the state of holecards whether were dealt at this table or not.
	 * 
	 * @param holeCardsWereDealt the boolean flag to change.
	 */
	public void setHoleCardsWereDealt(boolean holeCardsWereDealt) {
		this.holeCardsWereDealt = holeCardsWereDealt;
	}

	/**
	 * Returns the bet amount.
	 * 
	 * @return the bet amount
	 */
	public int getBet() {
		return bet;
	}

	/**
	 * Returns true if holecards were dealt at this table, otherwise returns flase.
	 * 
	 * @return true if holecards were dealt at this table, otherwise returns flase
	 */
	public boolean isHoleCardsWereDealt() {
		return holeCardsWereDealt;
	}

	/**
	 * Returns true if flop were dealt at this table, otherwise returns flase.
	 * 
	 * @return true if flop were dealt at this table, otherwise returns flase
	 */
	public boolean isFlopWasDealt() {
		return flopWasDealt;
	}

	/**
	 * Returns true if turn were dealt at this table, otherwise returns flase.
	 * 
	 * @return true if turn were dealt at this table, otherwise returns flase
	 */
	public boolean isTurnWasDealt() {
		return turnWasDealt;
	}

	/**
	 * Returns true if river were dealt at this table, otherwise returns flase.
	 * 
	 * @return true if river were dealt at this table, otherwise returns flase
	 */
	public boolean isRiverWasDealt() {
		return riverWasDealt;
	}

	/*
	 * Asks the players at the big blind and small blind positions
	 * to make the big/small blind bets in accordance to this table bet amount.
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
	 * Initializes the positions of the players at the "button", big blind
	 * and small blind positions.
	 */
	private void initPositions() {
		butPosition = tablePlayers.size() - 1;
		sbPosition = 0;
		bbPosition = 1;
	}

	/*
	 * Sets booleans to false after each hand.
	 */
	private void resetBlinds() {
		for (int i = 0; i < tablePlayers.size(); i++) {
			tablePlayers.get(i).setBigBlindPosition(false);
			tablePlayers.get(i).setSmallBlindPosition(false);
			tablePlayers.get(i).setDealerPosition(false);
		}
	}

	/*
	 *  "Button", small blind and big blind positions move clockwise every hand.
	 */
	private void nextPositions() {
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
	 * Sets turns for beginning of each street in the current hand.
	 */
	private void setPlayersTurns() {
		for (int i = 0; i < playersInHand.size(); i++) {
			playersInHand.get(i).setYourTurn(false);
			playersInHand.get(i).resetActions();
		}

		if (preFlop) {
			// 2 to 3 players in the current hand
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
	 * 
	 */
	private void flopAction() {
		player.setChips(player.getChips() - bet);
		pot += bet;
		tableInfo.setPot(pot);

		dealer.dealTurn(2);
		dealer.printTurn();

		turnWasDealt = true;
		flopWasDealt = false;
	}
	
	private void turnAction() {
		player.setChips(player.getChips() - bet);
		pot += bet;
		tableInfo.setPot(pot);

		dealer.dealRiver(2);
		dealer.printRiver();
		tableInfo.setRiver(dealer.getRiver());

		riverWasDealt = true;
		turnWasDealt = false;
	}
	
	private void riverAction() {
		player.setChips(player.getChips() - bet);
		pot += bet;
		tableInfo.setPot(pot);

		HandEvaluation evaluate = new HandEvaluation(dealer, this);
		HandComparison compare = new HandComparison(this);
		evaluate.resetHandRanks();
		evaluate.evaluateAllHands();
		compare.showDown();

		riverWasDealt = false;

		// move chips to the winner of this hand
		for (int i = 0; i < playersInHand.size(); i++) {
			if (playersInHand.get(i).isWin())
				playersInHand.get(i).setChips(playersInHand.get(i).getChips() + pot);
		}

		playersInHand.clear();
		nextPositions();
		startHand();
	}

	/*
	 * Running as long as one of the players made a raise.
	 * each raise forces the other players in the hand to react.
	 */
	private void raiseLoop() {
		while (running) {
			if (player.isFold()) {
				playersInHand.clear();
				nextPositions();
				riverWasDealt = false;
				turnWasDealt = false;
				flopWasDealt = false;
				startHand();
				break;

			} else if (player.isCall()) {
				if (preFlop) {
					player.setChips(player.getChips() - bet);
					pot += bet;
					tableInfo.setPot(pot);

					dealer.dealFlop(2);
					dealer.printFlop();

					flopWasDealt = true;
					preFlop = false;

					setPlayersTurns();

				} else if (flopWasDealt) {
					flopAction();
					setPlayersTurns();

				} else if (turnWasDealt) {
					turnAction();
					setPlayersTurns();

				} else if (riverWasDealt)
					riverAction();

				running = false;
				break;

			} else if (player.isRaise()) {
				bet = player.getCurrentBet() - player.getLastBet();
				pot += bet;
				player.setLastBet(bet);
				tableInfo.setBet(bet);
				tableInfo.setPot(pot);
				tableInfo.setRaiseFlag(true);

				nextTurn();
				raiseLoop();

				running = false;
				break;
			}

			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Running from the start of the hand untill flop is dealt
	 * or one of the players wins the hand preflop.
	 */
	private void preFlopGameLoop() {
		while (running) {
			if (player.isCheck()) {
				dealer.dealFlop(2);
				dealer.printFlop();
				tableInfo.setFlop(dealer.getFlop());

				flopWasDealt = true;
				preFlop = false;

				setPlayersTurns();
				break;

			} else if (player.isCall()) {				
				player.setChips(player.getChips() - bet + BIG_BLIND);
				pot += bet - BIG_BLIND;
				tableInfo.setPot(pot);

				dealer.dealFlop(2);
				dealer.printFlop();
				tableInfo.setFlop(dealer.getFlop());

				flopWasDealt = true;
				preFlop = false;

				setPlayersTurns();
				break;

			} else if (player.isRaise()) {
				bet = player.getCurrentBet() - BIG_BLIND;
				pot += bet;
				player.setLastBet(bet);
				tableInfo.setBet(bet);
				tableInfo.setPot(pot);
				tableInfo.setRaiseFlag(true);

				nextTurn();
				raiseLoop();
				break;

			} else if (player.isFold()) {
				playersInHand.clear();
				nextPositions();
				riverWasDealt = false;
				turnWasDealt = false;
				flopWasDealt = false;
				startHand();
				break;
			}

			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Running until one of the players wins the hand.
	 * winning could be during flop, turn, river or at showdown.
	 */
	private void postFlopGameLoop() {
		while (running) {
			if (player.isCheck()) {
				if (flopWasDealt) {
					dealer.dealTurn(2);
					dealer.printTurn();
					tableInfo.setTurn(dealer.getTurn());

					turnWasDealt = true;
					flopWasDealt = false;

					setPlayersTurns();
					break;

				} else if (turnWasDealt) {
					dealer.dealRiver(2);
					dealer.printRiver();
					tableInfo.setRiver(dealer.getRiver());

					riverWasDealt = true;
					turnWasDealt = false;

					setPlayersTurns();
					break;

				} else if (riverWasDealt) {
					riverAction();
					break;
				}

			} else if (player.isCall()) {
				if (flopWasDealt) {
					flopAction();
					setPlayersTurns();
					break;

				} else if (turnWasDealt) {
					turnAction();
					setPlayersTurns();
					break;

				} else if (riverWasDealt) {
					riverAction();
					break;
				}

			} else if (player.isBet()) {
				bet = player.getCurrentBet();
				pot += bet;
				tableInfo.setBet(bet);
				tableInfo.setPot(pot);
				
				nextTurn();
				raiseLoop();
				break;

			} else if (player.isRaise()) {				
				bet = player.getCurrentBet();
				pot += bet;
				tableInfo.setBet(bet);
				tableInfo.setPot(pot);
				tableInfo.setRaiseFlag(true);
				
				nextTurn();
				raiseLoop();
				break;
				
			} else if (player.isFold()) {
				playersInHand.clear();
				nextPositions();
				riverWasDealt = false;
				turnWasDealt = false;
				flopWasDealt = false;
				startHand();
				break;
			}

			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void postFlopAction() {
		running = true;

		while (running) {
			if (player.isCheck()) {
				nextTurn();
				postFlopGameLoop();
				break;

			} else if (player.isBet()) {
				bet = player.getCurrentBet();
				pot += bet;
				tableInfo.setBet(bet);
				tableInfo.setPot(pot);
				
				nextTurn();
				postFlopGameLoop();
				break;
			}

			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Starts a new hand.
	 */
	private void startHand() {
		dealer.getDeck().shuffle();
		resetBlinds();

		if (tablePlayers.size() == MIN_PLAYRES)
			butPosition = sbPosition;
		
		if (tablePlayers.size() >= MIN_PLAYRES) {
			tablePlayers.get(butPosition).setDealerPosition(true);
			tablePlayers.get(sbPosition).setSmallBlindPosition(true);
			tablePlayers.get(bbPosition).setBigBlindPosition(true);
		}

		dealer.dealHoleCards();
		holeCardsWereDealt = true;

		// array of players who were dealt hole cards,
		// for tracking which player is still playing through every street
		for (int i = 0; i < tablePlayers.size(); i++) {
			playersInHand.add(tablePlayers.get(i));
			playersNames.add(tablePlayers.get(i).getName());
		}

		bet = BIG_BLIND;
		pot = SMALL_BLIND + BIG_BLIND;
		tableInfo.setBet(bet);
		tableInfo.setPot(pot);
		tableInfo.setRaiseFlag(false);

		tableInfo.setNumOfPlayersInHand(playersInHand.size());
		tableInfo.setPlayersNames(playersNames);
		preFlop = true;
		setPlayersTurns();
		postBlinds();

		// pre-flop action
		running = true;

		while (running) {
			if (player.isFold()) {
				playersInHand.clear();
				nextPositions();
				startHand();
				break;

			} else if (player.isCall()) {
				player.setChips(player.getChips() - SMALL_BLIND);
				pot += bet - player.getCurrentBet();
				tableInfo.setPot(pot);
				
				nextTurn();
				preFlopGameLoop();
				break;

			} else if (player.isRaise()) {
				player.setChips(player.getChips() - player.getCurrentBet() + SMALL_BLIND);
				bet = player.getCurrentBet();
				pot += bet - SMALL_BLIND;
				player.setLastBet(bet);
				tableInfo.setBet(bet);
				tableInfo.setPot(pot);
				tableInfo.setRaiseFlag(true);

				nextTurn();
				preFlopGameLoop();
				break;
			}

			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// flop action
		postFlopAction();
		// turn action
		postFlopAction();
		// river action
		postFlopAction();
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

	@Override
	public void run() {
		while (tablePlayers.size() != MIN_PLAYRES) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		initPositions();
		startHand();
	}

}

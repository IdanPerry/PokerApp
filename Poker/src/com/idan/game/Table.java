package com.idan.game;

import java.util.ArrayList;

import com.idan.server.TableInformation;
import com.idan.texasholdem.Evaluator;
import com.idan.texasholdem.TexasHoldemDealer;

public abstract class Table extends Thread {
	public static final int SMALL_BLIND = 50;
	public static final int BIG_BLIND = 100;

	protected static final int MIN_PLAYRES = 2;
	private static final int[] SEATS = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	protected ArrayList<Player> tablePlayers;
	protected ArrayList<Player> playersInHand;
	protected ArrayList<String> playersNames;

	protected TexasHoldemDealer dealer;
	protected TableInformation tableInfo;
	protected Player player;

	protected int tableId;
	protected int numOfPlayers;
	protected int numOfPlayersInHand;

	protected int butPosition;
	protected int sbPosition;
	protected int bbPosition;

	protected String action;
	protected int bet;
	protected int pot;
	protected int foldCounter;
	protected int checkCounter = 0;
	protected int callCounter = 0;

	protected boolean holeCardsWereDealt;
	protected boolean running;
	protected boolean preFlop;
	protected boolean flop;
	protected boolean turn;
	protected boolean river;

	public Table() {
		tablePlayers = new ArrayList<Player>();
		dealer = new TexasHoldemDealer(this);
		tableInfo = new TableInformation();
	}

	public Table(int tableId) {
		this.tableId = tableId;

		tablePlayers = new ArrayList<Player>();
		dealer = new TexasHoldemDealer(this);
		tableInfo = new TableInformation();
	}

	public int[] getSeat() {
		return SEATS;
	}

	public ArrayList<Player> getPlayers() {
		return tablePlayers;
	}

	public ArrayList<Player> getPlayersInHand() {
		return playersInHand;
	}

	public int getTableId() {
		return tableId;
	}

	protected void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public TableInformation getTableInfo() {
		return tableInfo;
	}

	protected void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	public int getNumOfPlayersInHand() {
		return numOfPlayersInHand;
	}

	public Player getPlayer() {
		return player;
	}

	public void setHoleCardsWereDealt(boolean holeCardsWereDealt) {
		this.holeCardsWereDealt = holeCardsWereDealt;
	}

	public int getBet() {
		return bet;
	}

	public boolean isHoleCardsWereDealt() {
		return holeCardsWereDealt;
	}

	public boolean isFlop() {
		return flop;
	}

	public boolean isTurn() {
		return turn;
	}

	public boolean isRiver() {
		return river;
	}

	private void postBlinds() {
		for (int i = 0; i < playersInHand.size(); i++) {
			if (playersInHand.get(i).isBigBlind()) {

				playersInHand.get(i).bet(BIG_BLIND);
				System.out.println(playersInHand.get(i).getName() + " posts big blind " + BIG_BLIND);
				System.out.println(playersInHand.get(i).getName() + " has " + playersInHand.get(i).getChips());
			}

			if (playersInHand.get(i).isSmallBlind()) {

				playersInHand.get(i).bet(SMALL_BLIND);
				System.out.println(playersInHand.get(i).getName() + " posts small blind " + SMALL_BLIND);
				System.out.println(playersInHand.get(i).getName() + " has " + playersInHand.get(i).getChips());
			}
		}
	}

	private void initPositions() {
		butPosition = tablePlayers.size() - 1;
		sbPosition = 0;
		bbPosition = 1;
	}

	// sets booleans to false after each hand
	private void resetBlinds() {
		for (int i = 0; i < tablePlayers.size(); i++) {
			tablePlayers.get(i).setBigBlind(false);
			tablePlayers.get(i).setSmallBlind(false);
			tablePlayers.get(i).setDealer(false);
		}
	}

	// Dealer button, small and big blinds move clockwise every hand
	private void nextPositions() {
		butPosition++;
		sbPosition++;
		bbPosition++;

		if (butPosition == tablePlayers.size()) {
			butPosition = 0;
		}

		if (sbPosition == tablePlayers.size()) {
			sbPosition = 0;
		}

		if (bbPosition == tablePlayers.size()) {
			bbPosition = 0;
		}
	}

	// set turns for beginning of each street
	private void setPlayersTurns() {
		for (int i = 0; i < playersInHand.size(); i++) {
			playersInHand.get(i).setYourTurn(false);
			playersInHand.get(i).resetActions();
		}

		if (preFlop) {

			if (playersInHand.size() == MIN_PLAYRES || playersInHand.size() == 3) {
				playersInHand.get(butPosition).setYourTurn(true);
				player = playersInHand.get(butPosition);

			} else if (playersInHand.size() > 3) {
				playersInHand.get(bbPosition + 1).setYourTurn(true);
				player = playersInHand.get(bbPosition + 1);
			}

		} else if (flop || turn || river) {

			if (playersInHand.size() == MIN_PLAYRES) {
				playersInHand.get(bbPosition).setYourTurn(true);
				player = playersInHand.get(bbPosition);

			} else if (playersInHand.size() > MIN_PLAYRES) {
				playersInHand.get(sbPosition).setYourTurn(true);
				player = playersInHand.get(sbPosition);

			}
		}

		System.out.println(player.getName() + " Its your turn");
	}

	// set turns for inside street playing
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

	private void raiseLoop() {
		while (running) {
			if (player.isFold()) {

				playersInHand.clear();
				nextPositions();
				river = false;
				turn = false;
				flop = false;
				startHand();
				break;

			} else if (player.isCall()) {

				if (preFlop) {

					player.setChips(player.getChips() - bet);
					pot += bet;
					tableInfo.setPot(pot);

					dealer.dealFlop(2);
					dealer.printFlop();

					flop = true;
					preFlop = false;

					setPlayersTurns();

				} else if (flop) {

					player.setChips(player.getChips() - bet);
					pot += bet;
					tableInfo.setPot(pot);

					dealer.dealTurn(2);
					dealer.printTurn();

					turn = true;
					flop = false;

					setPlayersTurns();

				} else if (turn) {

					player.setChips(player.getChips() - bet);
					pot += bet;
					tableInfo.setPot(pot);

					dealer.dealRiver(2);
					dealer.printRiver();

					river = true;
					turn = false;

					setPlayersTurns();

				} else if (river) {

					player.setChips(player.getChips() - bet);
					pot += bet;
					tableInfo.setPot(pot);

					Evaluator e = new Evaluator(dealer, this);
					e.resetHandRanks();
					e.sortHand();
					e.checkStrFlush();
					e.showDown();

					river = false;

					for (int i = 0; i < playersInHand.size(); i++) {
						if (playersInHand.get(i).isWins()) {
							playersInHand.get(i).setChips(playersInHand.get(i).getChips() + pot);
						}
					}

					playersInHand.clear();
					nextPositions();
					startHand();

				}

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

	private void preFlopGameLoop() {
		while (running) {
			if (player.isCheck()) {

				dealer.dealFlop(2);
				dealer.printFlop();
				tableInfo.setFlop(dealer.getFlop());

				flop = true;
				preFlop = false;

				setPlayersTurns();
				break;

			} else if (player.isCall()) {

				// Working after raise
				
				player.setChips(player.getChips() - bet + BIG_BLIND);
				pot += bet - BIG_BLIND;
				tableInfo.setPot(pot);

				dealer.dealFlop(2);
				dealer.printFlop();
				tableInfo.setFlop(dealer.getFlop());

				flop = true;
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
				river = false;
				turn = false;
				flop = false;
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

	private void postFlopGameLoop() {
		while (running) {
			if (player.isCheck()) {

				if (flop) {
					dealer.dealTurn(2);
					dealer.printTurn();
					tableInfo.setTurn(dealer.getTurn());

					turn = true;
					flop = false;

					setPlayersTurns();
					break;

				} else if (turn) {

					dealer.dealRiver(2);
					dealer.printRiver();
					tableInfo.setRiver(dealer.getRiver());

					river = true;
					turn = false;

					setPlayersTurns();
					break;

				} else if (river) {

					Evaluator e = new Evaluator(dealer, this);
					e.resetHandRanks();
					e.sortHand();
					e.checkStrFlush();
					e.showDown();

					river = false;

					for (int i = 0; i < playersInHand.size(); i++) {
						if (playersInHand.get(i).isWins()) {
							playersInHand.get(i).setChips(playersInHand.get(i).getChips() + pot);
						}
					}

					playersInHand.clear();
					nextPositions();
					startHand();
					break;
				}

			} else if (player.isCall()) {

				if (flop) {

					player.setChips(player.getChips() - bet);
					pot += bet;
					tableInfo.setPot(pot);

					dealer.dealTurn(2);
					dealer.printTurn();
					tableInfo.setTurn(dealer.getTurn());

					turn = true;
					flop = false;

					setPlayersTurns();
					break;

				} else if (turn) {

					player.setChips(player.getChips() - bet);
					pot += bet;
					tableInfo.setPot(pot);

					dealer.dealRiver(2);
					dealer.printRiver();
					tableInfo.setRiver(dealer.getRiver());

					river = true;
					turn = false;

					setPlayersTurns();
					break;

				} else if (river) {

					player.setChips(player.getChips() - bet);
					pot += bet;
					tableInfo.setPot(pot);

					Evaluator e = new Evaluator(dealer, this);
					e.resetHandRanks();
					e.sortHand();
					e.checkStrFlush();
					e.showDown();

					river = false;

					for (int i = 0; i < playersInHand.size(); i++) {
						if (playersInHand.get(i).isWins()) {
							playersInHand.get(i).setChips(playersInHand.get(i).getChips() + pot);
						}
					}

					playersInHand.clear();
					nextPositions();
					startHand();
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
				river = false;
				turn = false;
				flop = false;
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

	private void startHand() {
		dealer.getDeck().shuffle();
		resetBlinds();

		if (tablePlayers.size() == MIN_PLAYRES) {
			butPosition = sbPosition;
		}

		if (tablePlayers.size() >= MIN_PLAYRES) {
			tablePlayers.get(butPosition).setDealer(true);
			tablePlayers.get(sbPosition).setSmallBlind(true);
			tablePlayers.get(bbPosition).setBigBlind(true);
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

		/* PRE-FLOP ACTION */
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
	
	public abstract void seatPlayer(Player player);

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

		playersInHand = new ArrayList<Player>();
		playersNames = new ArrayList<String>();
		initPositions();
		startHand();
	}

}

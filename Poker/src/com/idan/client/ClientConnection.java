package com.idan.client;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.idan.GUI.Lobby;
import com.idan.GUI.TableWindow;
import com.idan.game.Player;
import com.idan.server.TableInformation;

/**
 * This class represents a client (player) side connection.
 * 
 * @author Idan Perry
 * @version 04.05.2020
 */

public class ClientConnection extends Thread {
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;

	private final ImageIcon[] opponentCards;
	private Lobby lobbyGUI;
	private TableWindow tableGUI;
	private Player player;
	private TableInformation tableInfo;

	private String handState;
	private String actionInput;
	private int checkCounter;
	private boolean running;

	/**
	 * Constructs a ClientConnection object.
	 * 
	 * @param socket the socket to connect through
	 * @param player the player whom this connection to be established.
	 */
	public ClientConnection(Socket socket, Player player) {
		this.player = player;
		opponentCards = new ImageIcon[2];
		running = true;
		actionInput = "";

		try {
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectInput = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Sets the game table window.
	 * 
	 * @param tableGUI the table window
	 */
	public void setTableGUI(TableWindow tableGUI) {
		this.tableGUI = tableGUI;
	}

	/**
	 * Returns the table states and game information.
	 * 
	 * @return the table states and game information
	 */
	public TableInformation getTableInfo() {
		return tableInfo;
	}

	/**
	 * Returns the player this client connection belongs to.
	 * 
	 * @return the player this client connection belongs to
	 */
	public Player getPlayer() {
		return player;
	}
	
	public String getHandState() {
		return handState;
	}

	/**
	 * Returns the object output opened in this connection.
	 * 
	 * @return the object output opened in this connection
	 */
	public ObjectOutputStream getObjectOutput() {
		return objectOutput;
	}

	/**
	 * Changes this connection state.
	 * 
	 * @param running this connection state
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Sends player's object to the server.
	 * 
	 * @param player the player object to send to the server
	 */
	public void sendToServer(Player player) {
		try {
			objectOutput.writeObject(player);
			objectOutput.flush();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Sends game actions and bet amount to the server.
	 * 
	 * @param actionOutput the game actions represented as strings
	 * @param betSize      the bet amount
	 */
	public void sendToServer(String actionOutput, int betSize) {
		try {
			objectOutput.writeObject(actionOutput);
			objectOutput.writeObject(betSize);
			objectOutput.flush();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 * Initializes table information from this connection.
	 */
	private void readTableInfo() {
		try {
			actionInput = (String) objectInput.readObject();
			tableInfo = (TableInformation) objectInput.readObject();

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		player = tableInfo.getPlayer();
	}

	/*
	 * Prints the player's holecards to the table message box.
	 */
	private void printHolecards() {
		tableGUI.getMessagesBox().append("You were dealt " + player.getHoleCard1().getRank() + player.getHoleCard1().getSuit()
				+ player.getHoleCard2().getRank() + player.getHoleCard2().getSuit() + "\n");
	}

	/*
	 * Initialize all opponent players card images.
	 */
	private void setOponentsCardImgs() {
		for (int i = 0; i < tableInfo.getPlayers().size(); i++) {
			if (!tableInfo.getPlayers().get(i).getName().equals(player.getName())) {
				opponentCards[0] = tableInfo.getPlayers().get(i).getHoleCard1().getCardImage();
				opponentCards[1] = tableInfo.getPlayers().get(i).getHoleCard2().getCardImage();
			}
		}
	}
	
	/*
	 * Changes the position of the dealer button image according
	 * to the players positions in the current hand.
	 */
	private void setDealerBtnPosition() {
		if(player.isDealerPosition()) {
			tableGUI.getDealerBtnLabel()[0].setVisible(true);
			tableGUI.getDealerBtnLabel()[1].setVisible(false);
		} else {
			tableGUI.getDealerBtnLabel()[0].setVisible(false);
			tableGUI.getDealerBtnLabel()[1].setVisible(true);
		}
		
		tableGUI.validate();
	}

	/*
	 * Switch turns by changing table graphics and buttons. Parameter action - what
	 * buttons should this player currently needs. Paramter message - a dealer
	 * message to the table.
	 */
	private void changeTurns(String action, String message) {
		if (player.isYourTurn()) {
			tableGUI.getMessagesBox().append("Dealer: " + player.getName() + ", it's your turn \n");
			tableGUI.highLightPlayerBox();

			if (action.equals("call_raise_fold"))
				tableGUI.callRaiseFoldButtons();
			else
				tableGUI.checkBetButtons();

		} else
			tableGUI.dimPlayerBox();

		if (message != null && message.equals(actionInput))
			tableGUI.getMessagesBox().append("Dealer: " + actionInput + "\n");
	}

	/*
	 * Players actions decided apon inside hand street. When a new hand is dealt,
	 * the action begins at the player sitting to the left of the small blind
	 * position, while every street post flop the action begins with the player
	 * sitting at the small blind position.
	 */
	private void streetState() {
		// new hand
		if (actionInput.equals("New Hand")) {
			handState = "Preflop";
			checkCounter = 0;

			tableGUI.resetBoard();
			printHolecards(); // debug
			tableGUI.setHoleCardsImages(player.getHoleCard1().getCardImage(), player.getHoleCard2().getCardImage());

			setOponentsCardImgs();
			changeTurns("call_raise_fold", null);

			// flop cards
		} else if (actionInput.equals("FLOP")) {
			handState = "Postflop";
			player.resetCurrentBet();
			tableGUI.setFlopImages(tableInfo.getFlop()[0].getCardImage(), tableInfo.getFlop()[1].getCardImage(),
					tableInfo.getFlop()[2].getCardImage());

			// turn card
		} else if (actionInput.equals("TURN")) {
			handState = "Postflop";
			tableGUI.setTurnImage(tableInfo.getTurn().getCardImage());

			// river card
		} else if (actionInput.equals("RIVER")) {
			handState = "River";
			tableGUI.setRiverImage(tableInfo.getRiver().getCardImage());
		}
	}

	/*
	 * Faces up the opponents cards.
	 */
	private void showDown() {
		tableGUI.dimPlayerBox();
		tableGUI.showDown(opponentCards[0], opponentCards[1]);
		printWinningHand();

		// gives the players some time to observe the opponents hands.
		try {
			sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		tableGUI.resetHoleCardsPosition();
	}
	
	/*
	 * Prints the winning player's name and cards to the table message box.
	 */
	private void printWinningHand() {
		Player player = tableInfo.getWinningPlayer();
		tableGUI.getMessagesBox().append(player.getName() + " wins with " + player.getHandRank() + ": ");

		for (int i = 0; i < player.getFiveCardsHand().length; i++) {
			tableGUI.getMessagesBox().append(player.getFiveCardsHand()[i].getRank().toString());
			tableGUI.getMessagesBox().append(player.getFiveCardsHand()[i].getSuit().toString());
		}
		
		tableGUI.getMessagesBox().append("\n");
	}

	@Override
	public void run() {
		ClientConnection clientConnection = this;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
						| IllegalAccessException e) {
					e.printStackTrace();
				}

				lobbyGUI = new Lobby(clientConnection);
				lobbyGUI.setVisible(true);
			}
		});

		while (running) {
			// Waiting for string input from server (echos back any string sent
			// to the server).
			readTableInfo();
			tableGUI.setPlayerChips(player.getChips());
			streetState();
			setDealerBtnPosition();

			// fold
			if (actionInput.contains("folds")) {
				System.out.println(actionInput);
				changeTurns("call_raise_fold", actionInput);

				// call
			} else if (actionInput.contains("calls")) {
				System.out.println(actionInput);
				tableGUI.getMessagesBox().append("Dealer: " + actionInput + "\n");

				// preflop action
				if (handState.equals("Preflop")) {
					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();

						// following raise action
						if (tableInfo.isRaise())
							tableGUI.checkBetButtons();
						else {
							tableGUI.getMessagesBox().append("Dealer: " + player.getName() + ", it's your turn \n");
							tableGUI.checkRaiseButtons();
						}
							

					} else
						tableGUI.dimPlayerBox();

					// post flop action
				} else if (handState.equals("Postflop"))
					changeTurns("check_bet", null);

				// river action
				else if (handState.equals("River"))
					showDown();

				// check
			} else if (actionInput.contains("checks")) {
				System.out.println(actionInput);
				tableGUI.getMessagesBox().append("Dealer: " + actionInput + "\n");

				// pre flop action
				if (handState.equals("Preflop"))
					changeTurns("check_bet", null);

				// post flop action
				else if (handState.equals("Postflop"))
					changeTurns("check_bet", null);

				// river action
				else if (handState.equals("River")) {
					checkCounter++;

					// showdown
					if (checkCounter == 2)
						showDown();

					// change turns
					else if (checkCounter == 1)
						changeTurns("check_bet", player.getName());
				}

				// bet or raise
			} else if (actionInput.contains("bets") || (actionInput.contains("raises")
					&& (handState.equals("Preflop") || handState.equals("Postflop")))) {
				System.out.println(actionInput);
				changeTurns("call_raise_fold", actionInput);
			}
		}
	}
}

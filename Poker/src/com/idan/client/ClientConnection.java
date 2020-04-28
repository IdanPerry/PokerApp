package com.idan.client;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

import com.idan.GUI.Lobby;
import com.idan.GUI.TableWindow;
import com.idan.game.Player;
import com.idan.server.TableInformation;

/**
 * This class represents a client (player) side connection.
 * 
 * @author Idan Perry
 * @version 03.05.2013
 */

public class ClientConnection extends Thread {
	private final ObjectInputStream objectInput;
	private final ObjectOutputStream objectOutput;

	private final ImageIcon[] opponentCards;
	private Lobby lobbyGUI;
	private TableWindow tableGUI;
	private Player player;
	private TableInformation tableInfo;

	private String handState;
	private int checkCounter;
	private boolean running;
	private String actionInput;

	/**
	 * Constructs a ClientConnection object
	 * @param socket the socket to connect through
	 * @param player the player whom this connection to be established.
	 */
	public ClientConnection(Socket socket, Player player) {
		this.player = player;
		ObjectOutputStream tempObjectOutput;
		ObjectInputStream tempObjectInput;	
		
		try {
			tempObjectOutput = new ObjectOutputStream(socket.getOutputStream());
			tempObjectInput = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			tempObjectInput = null;
			tempObjectOutput = null;
			e.printStackTrace();
		}
		
		objectOutput = tempObjectOutput;
		objectInput = tempObjectInput;
		
		opponentCards = new ImageIcon[2];
		running = true;
		actionInput = "";		
	}

	/**
	 * Sets the game table window.
	 * @param tableGUI the table window
	 */
	public void setTableGUI(TableWindow tableGUI) {
		this.tableGUI = tableGUI;
	}

	/**
	 * Returns the table states and game information.
	 * @return the table states and game information
	 */
	public TableInformation getTableInfo() {
		return tableInfo;
	}

	/**
	 * Returns the player this client connection belongs to.
	 * @return the player this client connection belongs to
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns the object output opened in this connection.
	 * @return the object output opened in this connection
	 */
	public ObjectOutputStream getObjectOutput() {
		return objectOutput;
	}
	
	/**
	 * Changes this connection state.
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
		}
	}

	/**
	 * Sends game actions and bet amount to the server.
	 * 
	 * @param actionOutput the game actions represented as strings
	 * @param betSize the bet amount
	 */
	public void sendToServer(String actionOutput, int betSize) {
		try {
			objectOutput.writeObject(actionOutput);
			objectOutput.writeObject(betSize);
			objectOutput.flush();

		} catch (IOException e) {
			e.printStackTrace();
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
		}

		player = tableInfo.getPlayer();
	}

	/*
	 * Prints the player's holecards to the console.
	 */
	private void printHolecards() {
		System.out.println("You were dealt " + player.getHoleCard1().getRank() + player.getHoleCard1().getSuit()
				+ player.getHoleCard2().getRank() + player.getHoleCard2().getSuit());
	}

	@Override
	public void run() {
		ClientConnection clientConnection = this;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				lobbyGUI = new Lobby(clientConnection);
				lobbyGUI.getLobbyFrame().setVisible(true);
			}
		});

		while (running) {
			// Waiting for string input from server (echos back any string sent
			// to the server).
			readTableInfo();
			tableGUI.getTableImage().setPlayerChips(player.getChips());

			// new hand
			if (actionInput.equals("New Hand")) {
				handState = "Preflop";
				checkCounter = 0;

				tableGUI.resetBoard();
				printHolecards();
				tableGUI.setHoleCardsImages(player.getHoleCard1().getCardImage(), player.getHoleCard2().getCardImage());

				// set opponent card images
				for (int i = 0; i < tableInfo.getPlayers().size(); i++) {
					if (!tableInfo.getPlayers().get(i).getName().equals(player.getName())) {
						opponentCards[0] = tableInfo.getPlayers().get(i).getHoleCard1().getCardImage();
						opponentCards[1] = tableInfo.getPlayers().get(i).getHoleCard2().getCardImage();
					}
				}

				// players turn changes
				if (player.isYourTurn()) {
					tableGUI.highLightPlayerBox();
					tableGUI.callRaiseFoldButtons();
				} else 
					tableGUI.dimPlayerBox();
				
			// flop cards
			} else if (actionInput.equals("FLOP")) {
				handState = "Postflop";
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

			// fold
			if (actionInput.contains("folds")) {
				System.out.println(actionInput);
				tableGUI.getMessagesBox().append("Dealer: " + actionInput + "\n");

				// players turn changes
				if (player.isYourTurn()) {
					tableGUI.highLightPlayerBox();
					tableGUI.callRaiseFoldButtons();
				} else 
					tableGUI.dimPlayerBox();			

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
						else
							tableGUI.checkRaiseButtons();

					} else
						tableGUI.dimPlayerBox();

				// post flop action
				} else if (handState.equals("Postflop")) {

					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();
						tableGUI.checkBetButtons();
					} else
						tableGUI.dimPlayerBox();

				// river action
				} else if (handState.equals("River")) {
					tableGUI.dimPlayerBox();
					tableGUI.showDown(opponentCards[0], opponentCards[1]);

					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					tableGUI.resetHoleCardsPosition();
				}

			// check
			} else if (actionInput.contains("checks")) {
				System.out.println(actionInput);
				tableGUI.getMessagesBox().append("Dealer: " + actionInput + "\n");

				// pre flop action
				if (handState.equals("Preflop")) {
					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();
						tableGUI.checkBetButtons();
					} else
						tableGUI.dimPlayerBox();

				// post flop action
				} else if (handState.equals("Postflop")) {
					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();
						tableGUI.checkBetButtons();
					} else
						tableGUI.dimPlayerBox();

				// river action
				} else if (handState.equals("River")) {
					checkCounter++;

					// showdown
					if (checkCounter == 2) {
						tableGUI.dimPlayerBox();
						tableGUI.showDown(opponentCards[0], opponentCards[1]);

						try {
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						tableGUI.resetHoleCardsPosition();

					// change turns
					} else if (checkCounter == 1) {
						if (player.isYourTurn()) {
							tableGUI.getMessagesBox().append("Dealer: " + player.getName() + ", it's your turn \n");
							tableGUI.highLightPlayerBox();
							tableGUI.checkBetButtons();
						} else
							tableGUI.dimPlayerBox();

					}
				}

			// bet
			} else if (actionInput.contains("bets")) {
				System.out.println(actionInput);
				tableGUI.getMessagesBox().append("Dealer: " + actionInput + "\n");

				if (player.isYourTurn()) {
					tableGUI.highLightPlayerBox();
					tableGUI.callRaiseFoldButtons();
				} else
					tableGUI.dimPlayerBox();

			// raise
			} else if (actionInput.contains("raises")) {
				System.out.println(actionInput);
				tableGUI.getMessagesBox().append("Dealer: " + actionInput + "\n");

				// pre flop action
				if (handState.equals("Preflop")) {
					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();
						tableGUI.callRaiseFoldButtons();
					} else
						tableGUI.dimPlayerBox();

				// post flop action
				} else if (handState.equals("Postflop")) {
					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();
						tableGUI.callRaiseFoldButtons();
					} else
						tableGUI.dimPlayerBox();
				}
			}
		}
	}
}

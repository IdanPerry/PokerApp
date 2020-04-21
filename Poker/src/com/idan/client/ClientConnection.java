package com.idan.client;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

import com.idan.game.Player;
import com.idan.server.TableInformation;

public class ClientConnection extends Thread {
	private Socket socket;
	private ObjectInputStream objectInput;
	public ObjectOutputStream objectOutput;

	private LobbyGUI lobbyGUI;
	private TableGUI tableGUI;
	private Player player;
	private TableInformation tableInfo;

	private String handState;
	private int checkCounter = 0;
	public boolean running = true;
	private String actionInput = "";

	private ClientConnection clientConnection = this;

	private ImageIcon[] opponentCards = new ImageIcon[2];

	/**
	 * Constructs a ClientConnection object
	 * @param socket the socket to connect through
	 * @param player the player whom this connection to be established.
	 */
	public ClientConnection(Socket socket, Player player) {
		this.socket = socket;
		this.player = player;
	}

	
	public void setTableGUI(TableGUI tableGUI) {
		this.tableGUI = tableGUI;
	}

	public TableInformation getTableInfo() {
		return tableInfo;
	}

	public Player getPlayer() {
		return player;
	}

	private void openObjectStream() {
		try {
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectInput = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Sends any player's states, actions, etc.
	public void sendToServer(Player player) {
		try {
			objectOutput.writeObject(player);
			objectOutput.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendToServer(String actionOutput, int betSize) {
		try {
			objectOutput.writeObject(actionOutput);
			objectOutput.writeObject(betSize);
			objectOutput.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
	 * Prints the player's holecards to the console
	 */
	private void printHolecards() {
		System.out.println("You were dealt " + player.getHoleCard1().getRank() + player.getHoleCard1().getSuit()
				+ player.getHoleCard2().getRank() + player.getHoleCard2().getSuit());
	}

	@Override
	public void run() {
		openObjectStream();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lobbyGUI = new LobbyGUI(clientConnection);
					lobbyGUI.getLobbyFrame().setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		while (running) {
			// Waiting for string input from server (echos back any string sent
			// to the server).
			readTableInfo();
			tableGUI.getTableImage().setPlayerChips(player.getChips());

			if (actionInput.equals("New Hand")) {
				handState = "Preflop";
				checkCounter = 0;

				tableGUI.resetBoard();
				printHolecards();
				tableGUI.setHoleCardsImages(player.getHoleCard1().getCardImage(), player.getHoleCard2().getCardImage());

				for (int i = 0; i < tableInfo.getPlayers().size(); i++) {
					if (!tableInfo.getPlayers().get(i).getName().equals(player.getName())) {
						opponentCards[0] = tableInfo.getPlayers().get(i).getHoleCard1().getCardImage();
						opponentCards[1] = tableInfo.getPlayers().get(i).getHoleCard2().getCardImage();
					}
				}

				if (player.isYourTurn()) {
					tableGUI.highLightPlayerBox();
					tableGUI.callRaiseFoldButtons();
				} else 
					tableGUI.dimPlayerBox();
				
			} else if (actionInput.equals("FLOP")) {
				handState = "Postflop";
				tableGUI.setFlopImages(tableInfo.getFlop()[0].getCardImage(), tableInfo.getFlop()[1].getCardImage(),
						tableInfo.getFlop()[2].getCardImage());

			} else if (actionInput.equals("TURN")) {
				handState = "Postflop";
				tableGUI.setTurnImage(tableInfo.getTurn().getCardImage());

			} else if (actionInput.equals("RIVER")) {
				handState = "River";
				tableGUI.setRiverImage(tableInfo.getRiver().getCardImage());
			}

			/*************************************************************************/

			if (actionInput.contains("folds")) {
				System.out.println(actionInput);
				tableGUI.messagesBox.append("Dealer: " + actionInput + "\n");

				if (player.isYourTurn()) {
					tableGUI.highLightPlayerBox();
					tableGUI.callRaiseFoldButtons();
				} else 
					tableGUI.dimPlayerBox();			

			} else if (actionInput.contains("calls")) {
				System.out.println(actionInput);
				tableGUI.messagesBox.append("Dealer: " + actionInput + "\n");

				if (handState.equals("Preflop")) {
					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();

						if (tableInfo.isRaise())
							tableGUI.checkBetButtons();
						else
							tableGUI.checkRaiseButtons();

					} else
						tableGUI.dimPlayerBox();

				} else if (handState.equals("Postflop")) {

					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();
						tableGUI.checkBetButtons();
					} else
						tableGUI.dimPlayerBox();

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

			} else if (actionInput.contains("checks")) {
				System.out.println(actionInput);
				tableGUI.messagesBox.append("Dealer: " + actionInput + "\n");

				if (handState.equals("Preflop")) {
					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();
						tableGUI.checkBetButtons();
					} else
						tableGUI.dimPlayerBox();

				} else if (handState.equals("Postflop")) {
					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();
						tableGUI.checkBetButtons();
					} else
						tableGUI.dimPlayerBox();

				} else if (handState.equals("River")) {
					checkCounter++;

					if (checkCounter == 2) {
						tableGUI.dimPlayerBox();
						tableGUI.showDown(opponentCards[0], opponentCards[1]);

						try {
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						tableGUI.resetHoleCardsPosition();

					} else if (checkCounter == 1) {
						if (player.isYourTurn()) {
							tableGUI.messagesBox.append("Dealer: " + player.getName() + ", it's your turn \n");
							tableGUI.highLightPlayerBox();
							tableGUI.checkBetButtons();
						} else
							tableGUI.dimPlayerBox();

					}
				}

			} else if (actionInput.contains("bets")) {
				System.out.println(actionInput);
				tableGUI.messagesBox.append("Dealer: " + actionInput + "\n");

				if (player.isYourTurn()) {
					tableGUI.highLightPlayerBox();
					tableGUI.callRaiseFoldButtons();
				} else
					tableGUI.dimPlayerBox();

			} else if (actionInput.contains("raises")) {
				System.out.println(actionInput);
				tableGUI.messagesBox.append("Dealer: " + actionInput + "\n");

				if (handState.equals("Preflop")) {
					if (player.isYourTurn()) {
						tableGUI.highLightPlayerBox();
						tableGUI.callRaiseFoldButtons();
					} else
						tableGUI.dimPlayerBox();

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

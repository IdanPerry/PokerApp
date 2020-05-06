package com.idan.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.idan.client.ClientConnection;

/**
 * This class represents the graphic table component where game is taking place.
 * 
 * @author Idan Perry
 * @version 04.05.2020
 */

@SuppressWarnings("serial")
public class TableWindow extends JFrame implements ActionListener, ChangeListener {
	// frame
	private static final int WIDTH = 805;
	private static final int HEIGHT = 575;
	
	// cards
	private static final int[][] HOLE_CARDS = { { 345, 330 }, { 395, 330 }, { 345, 25 }, { 395, 25 } };
	private static final int[] FLOP_X = {270, 320, 370};
	private static final int TURN_X = 420;
	private static final int RIVER_X = 470;
	private static final int COMMUNITY_CARDS_Y = 190;
	private static final int CARD_WIDTH = 50;
	private static final int CARD_HEIGHT = 70;
	
	// message box
	private static final int MESSAGE_ROWS = 6;
	private static final int MESSAGE_COLS = 26;
	private static final int MESSAGE_WIDTH = 330;
	private static final int MESSAGE_HEIGHT = 90;
	private static final int MESSAGE_X = 0;
	private static final int MESSAGE_Y = 456;
	private static final Font MESSAGE_FONT = new Font("", Font.PLAIN, 15);
	
	// bet field and slider
	private static final int BET_FIELD_X = 710;
	private static final int BET_FIELD_Y = 440;
	private static final int BET_FIELD_WIDTH = 70;
	private static final int BET_FIELD_HEIGHT = 30;
	private static final Font BET_FIELD_FONT = new Font("Tahoma", Font.BOLD, 14);
	private static final int BET_SLIDER_X = 580;
	private static final int BET_SLIDER_Y = 475;
	private static final int BET_SLIDER_WIDTH = 200;
	private static final int BET_SLIDER_HEIGHT = 20;
	private static final int SLIDER_TICK_SPACE = 100;
	private static final int BIG_BLIND = 100;
	private static final int SMALL_BLIND = 50;
	
	// buttons
	private static final int BET_RAISE_BTN_X = 680;
	private static final int CALL_CHECK_BTN_X = 580;
	private static final int FOLD_BTN_X = 480;
	private static final int ACTION_BTN_Y = 500;
	private static final int ACTION_BTN_WIDTH = 100;
	private static final int ACTION_BTN_HEIGHT = 40;
	private static final int LEAVE_BTN_X = 0;
	private static final int LEAVE_BTN_Y = 0;
	private static final int LEAVE_BTN_WIDTH = 70;
	private static final int LEAVE_BTN_HEIGHT = 30;
	private static final Font BTN_FONT = new Font("Tahoma", Font.BOLD, 14);
	private static final Color BLUE = new Color(0, 51, 102);
	
	// player box
	private static final int[][] PLAYER_BOX = { { 340, 365 }, { 340, 60 } };
	private static final int BOX_WIDTH = 130;
	private static final int BOX_HEIGHT = 40;
	private static final int BOX_IMG_WIDTH = 40;
	private static final Color RED = new Color(87, 0, 0);
	private static final Color ORANGE = new Color(245, 100, 0);
	private static final Font PLAYER_BOX_FONT = new Font("Tahoma", Font.PLAIN, 14);
	private static final Font BOX_IMG_FONT = new Font("", Font.PLAIN, 36);
	
	// pot label
	private static final int POT_WIDTH = 100;
	private static final int POT_HEIGHT = 50;
	private static final int POT_X = 355;
	private static final int POT_Y = 150;
	private static final Font POT_FONT = new Font("Tahoma", Font.BOLD, 16);
	
	// dealer button icon
	private static final int[][] DEALER_BTN = {{450, 325}, {330, 110}};
	private static final int DEALER_BTN_SIZE = 30;
	
	private final ClientConnection clientConnection;
	private final Timer thisPlayerTimer;
	private final Timer otherPlayerTimer;
	private TableImage tableImage;
	
	private final JLabel[] playerBoxLabel;
	private final JLabel[] playerBoxImg; 
	private final JLabel[] holeCardsLabel;
	private final JLabel[] opponentHoleCardsLabel;
	private final JLabel[] flopLabel;
	private final JLabel[] dealerBtnLabel;
	private JLabel turnLabel;
	private JLabel riverLabel;
	private JLabel potLabel;
	private TextArea messagesBox;
	private JTextField betField;
	private JSlider betSlider;
	private JButton seatBtn;
	private JButton foldBtn;
	private JButton checkBtn;
	private JButton callBtn;
	private JButton betBtn;
	private JButton raiseBtn;
	private JButton leaveTableBtn;
	private ImageIcon cardBackImg;
	private ImageIcon dealerButton;
	private int chips;
	private boolean flashing;

	/**
	 * Constructs a window representing a poker table.
	 * 
	 * @param clientConnection
	 */
	public TableWindow(ClientConnection clientConnection) {
		super("No-limit Hold'em - Logged in as " + clientConnection.getPlayer().getName());
		this.clientConnection = clientConnection;
		
		playerBoxLabel = new JLabel[2];
		playerBoxImg = new JLabel[2];
		holeCardsLabel = new JLabel[2];
		opponentHoleCardsLabel = new JLabel[2];
		flopLabel = new JLabel[3];
		dealerBtnLabel = new JLabel[2];
		thisPlayerTimer = new Timer(1000, this);
		otherPlayerTimer = new Timer(1000, this);
		
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		initCardImage();
		initDealerButton();
		initPlayersBoxes();
		initCards();
		initButtons();	
		initComponents();
		initTableImage();
		validate();
	}
	
	public void setPlayerChips(int chips) {
		this.chips = chips;
	}

	/**
	 * Returns the connection object assosiated with this table component.
	 * 
	 * @return the connection object assosiated with this table component
	 */
	public ClientConnection getClientConnection() {
		return clientConnection;
	}

	/**
	 * Returns an array of the player boxes labels appear at the table.
	 * 
	 * @return an array of the player boxes labels appear at the table
	 */
	public JLabel[] getPlayerBoxLabel() {
		return playerBoxLabel;
	}
	
	/**
	 * Returns an array of the player image boxes labels appear at the table.
	 * 
	 * @return an array of the player image boxes labels appear at the table
	 */
	public JLabel[] getPlayerBoxImg() {
		return playerBoxImg;
	}

	/**
	 * Returns an array of the holecards labels of the player who assosiates
	 * with this table object.
	 * 
	 * @return an array of the holecards labels of the player who assosiates
	 * 		   with this table object.
	 */
	public JLabel[] getHoleCardsLabel() {
		return holeCardsLabel;
	}

	/**
	 * Returns an array of the opponent player holecards labels.
	 * 
	 * @return an array of the opponent player holecards labels.
	 */
	public JLabel[] getOpponentHoleCardsLabel() {
		return opponentHoleCardsLabel;
	}

	/**
	 * Returns an array of the flop cards labels of this table.
	 * 
	 * @return an array of the flop cards labels of this table
	 */ 
	public JLabel[] getFlopLabel() {
		return flopLabel;
	}
	
	/**
	 * Initializes the flop cards labels for this table.
	 * 
	 * @param flop1 the label of the 1st flop card
	 * @param flop2 the label of the 2nd flop card
	 * @param flop3 the label of the 3rd flop card
	 */
	public void setFlopLabels(ImageIcon flop1, ImageIcon flop2, ImageIcon flop3) {
		tableImage.getLayeredPane().add(flopLabel[0], 1);
		tableImage.getLayeredPane().add(flopLabel[1], 1);
		tableImage.getLayeredPane().add(flopLabel[2], 1);
		
		flopLabel[0].setIcon(flop1);
		flopLabel[1].setIcon(flop2);
		flopLabel[2].setIcon(flop3);
		
		flopLabel[0].setVisible(true);
		flopLabel[1].setVisible(true);
		flopLabel[2].setVisible(true);
	}

	/**
	 * Returns the turn card label of this table.
	 * 
	 * @return the turn card label of this table
	 */ 
	public JLabel getTurnLabel() {
		return turnLabel;
	}
	
	/**
	 * Initializes the turn card label for this table.
	 * 
	 * @param turn the label of the turn card
	 */
	public void setTurnLabel(ImageIcon turn) {
		tableImage.getLayeredPane().add(turnLabel, 1);
		turnLabel.setIcon(turn);
		turnLabel.setVisible(true);
	}

	/**
	 * Returns the river card label of this table.
	 * 
	 * @return the river card label of this table
	 */ 
	public JLabel getRiverLabel() {
		return riverLabel;
	}
	
	/**
	 * Initializes the river card label for this table.
	 * 
	 * @param river the label of the turn card
	 */
	public void setRiverLabel(ImageIcon river) {
		tableImage.getLayeredPane().add(riverLabel, 1);
		riverLabel.setIcon(river);
		riverLabel.setVisible(true);
	}
	
	/**
	 * Returns the dealer message message box of this table.
	 * 
	 * @return the dealer message message box of this table
	 */
	public TextArea getMessagesBox() {
		return messagesBox;
	}
	
	/**
	 * Initializes the flop cards images for this table.
	 * 
	 * @param flop1 the image of the 1st flop card
	 * @param flop2 the image of the 2nd flop card
	 * @param flop3 the image of the 3rd flop card
	 */
	public void setFlopImages(ImageIcon flop1, ImageIcon flop2, ImageIcon flop3) {	
		flopLabel[0].setIcon(flop1);
		flopLabel[1].setIcon(flop2);
		flopLabel[2].setIcon(flop3);
		
		flopLabel[0].setVisible(true);
		flopLabel[1].setVisible(true);
		flopLabel[2].setVisible(true);
	}
	
	/**
	 * Initializes the turn card image for this table.
	 * 
	 * @param turn the image of the turn card
	 */
	public void setTurnImage(ImageIcon turn) {
		turnLabel.setIcon(turn);
		turnLabel.setVisible(true);
	}
	
	/**
	 * Initializes the river card image for this table.
	 * 
	 * @param river the image of the river card
	 */
	public void setRiverImage(ImageIcon river) {
		riverLabel.setIcon(river);
		riverLabel.setVisible(true);
	}
	
	/**
	 * Returns the pot string label of this table.
	 * 
	 * @return the pot string label of this table
	 */
	public JLabel getPotLabel() {
		return potLabel;
	}
	
	/**
	 * Returns an array of the dealer button labels.
	 * 
	 * @returnan array of the dealer button labels
	 */
	public JLabel[] getDealerBtnLabel() {
		return dealerBtnLabel;
	}
	
	/**
	 * Removes all community cards from this table while
	 * preparing for a new hand to be dealt.
	 */
	public void resetBoard() {
		flopLabel[0].setIcon(null);
		flopLabel[1].setIcon(null);
		flopLabel[2].setIcon(null);
		turnLabel.setIcon(null);
		riverLabel.setIcon(null);
	}
	
	/**
	 * Changes the visibilty of all the community cards at this table.
	 * 
	 * @param visible true if the community cards are to be visible,
	 * 				  or false otherwise
	 */
	public void setBoardVisibility(boolean visible) {
		flopLabel[0].setVisible(visible);
		flopLabel[1].setVisible(visible);
		flopLabel[2].setVisible(visible);
		turnLabel.setVisible(visible);
		riverLabel.setVisible(visible);
	}
	
	/**
	 * Initializes the holecards images of the player assosiates with this table object.
	 * 
	 * @param holeCard1Img the image of the 1st holecard
	 * @param holeCard2img the image of the 2nd holecard
	 */
	public void setHoleCardsImages(ImageIcon holeCard1Img, ImageIcon holeCard2img) {
		holeCardsLabel[0].setIcon(holeCard1Img);
		holeCardsLabel[1].setIcon(holeCard2img);
		opponentHoleCardsLabel[0].setIcon(cardBackImg);
		opponentHoleCardsLabel[1].setIcon(cardBackImg);
	}
	
	/**
	 * Adds call, raise and fold buttons to the player's panel when it's
	 * his turn to act. this method should be called if the player is
	 * facing any bet or raise action of other players.
	 */
	public void callRaiseFoldButtons() {
		int call;
		if(clientConnection.getHandState().equals("Preflop"))
			call = clientConnection.getTableInfo().getBet() - clientConnection.getPlayer().getCurrentBet();
		else
			call = clientConnection.getTableInfo().getBet();
		
		callBtn.setText("Call " + call);
		betField.setText(BIG_BLIND + "");
		raiseBtn.setText("Raise " + (clientConnection.getTableInfo().getPlayer().getCurrentBet() + BIG_BLIND));
		betSlider.setValue(BIG_BLIND);
		
		tableImage.getLayeredPane().add(callBtn, 1);
		tableImage.getLayeredPane().add(raiseBtn, 1);
		tableImage.getLayeredPane().add(betField, 1);
		tableImage.getLayeredPane().add(betSlider, 1);
		tableImage.getLayeredPane().add(foldBtn, 1);

		validate();
		repaint();
	}

	/**
	 * Adds check and bet buttons to the player's panel  when it's
	 * his turn to act. this method should be called if the player
	 * isn't facing any bet or raise action of other players.
	 */
	public void checkBetButtons() {
		betField.setText(BIG_BLIND + "");
		betBtn.setText("Bet " + BIG_BLIND);
		betSlider.setValue(BIG_BLIND);
		
		tableImage.getLayeredPane().add(checkBtn, 1);
		tableImage.getLayeredPane().add(betBtn, 1);
		tableImage.getLayeredPane().add(betField, 1);
		tableImage.getLayeredPane().add(betSlider, 1);

		validate();
		repaint();
	}
	
	/**
	 * Adds check and raise buttons to the player's panel  when it's
	 * his turn to act. this method should be called only when the
	 * player sits in the big blind position in a new dealt hand.
	 */
	public void checkRaiseButtons() {
		betField.setText(BIG_BLIND + "");
		raiseBtn.setText("Raise " + (BIG_BLIND*2));
		betSlider.setValue(BIG_BLIND);
		
		tableImage.getLayeredPane().add(checkBtn, 1);
		tableImage.getLayeredPane().add(raiseBtn, 1);
		tableImage.getLayeredPane().add(betField, 1);
		tableImage.getLayeredPane().add(betSlider, 1);
		
		validate();
		repaint();
	}
	
	/**
	 * Stop flashing the box of this player (who assosiates with this table)
	 * when it's not his turn to act.
	 */
	public void dimPlayerBox() {
		flashing = false;
		playerBoxLabel[0].setText("<html>" + clientConnection.getPlayer().getName()
				+ "<br>" + clientConnection.getPlayer().getChips() + "</html>");

		otherPlayerTimer.start();
		thisPlayerTimer.stop();
		playerBoxLabel[0].setBackground(RED);
	}

	/**
	 * Start flashing the box of this player (who assosiates with this table)
	 * when it's his turn to act.
	 */
	public void highLightPlayerBox() {
		flashing = true;
		playerBoxLabel[0].setText("<html>" + clientConnection.getPlayer().getName()
				+ "<br>" + clientConnection.getPlayer().getChips() + "</html>");
			
		thisPlayerTimer.start();
		otherPlayerTimer.stop();
		playerBoxLabel[1].setBackground(RED);
	}
	
	/**
	 * Flips the oponents cards (those who went to showdown) and elevates the
	 * cards of all players involving in the showdown.
	 * 
	 * @param opponentCard1 first opponent holecard ImageIcon
	 * @param opponentCard2 second opponent holecard ImageIcon
	 */
	public void showDown(ImageIcon opponentCard1, ImageIcon opponentCard2) {
		opponentHoleCardsLabel[0].setIcon(opponentCard1);
		opponentHoleCardsLabel[1].setIcon(opponentCard2);
		
		for (int i = 0; i < 2; i++) {
			holeCardsLabel[i].setBounds(HOLE_CARDS[i][0],
					HOLE_CARDS[i][1] - 18, CARD_WIDTH, CARD_HEIGHT);
			
			opponentHoleCardsLabel[i].setBounds(HOLE_CARDS[i + 2][0],
					HOLE_CARDS[i + 2][1] - 18, CARD_WIDTH, CARD_HEIGHT);
		}
	}
	
	/**
	 * Reset the holecards to their original position to start a new hand.
	 */
	public void resetHoleCardsPosition() {
		for (int i = 0; i < 2; i++) {
			holeCardsLabel[i].setBounds(HOLE_CARDS[i][0],
					HOLE_CARDS[i][1], CARD_WIDTH, CARD_HEIGHT);
			
			opponentHoleCardsLabel[i].setBounds(HOLE_CARDS[i + 2][0],
					HOLE_CARDS[i + 2][1], CARD_WIDTH, CARD_HEIGHT);
		}
	}
	
	/*
	 * Initializes the table image panel.
	 */
	private void initTableImage() {
		tableImage = new TableImage(this);
		tableImage.setOpaque(true);

		// add components to tableImage
		tableImage.getLayeredPane().add(seatBtn, 0);
		tableImage.getLayeredPane().add(messagesBox, 1);
		tableImage.getLayeredPane().add(holeCardsLabel[0], 0);
		tableImage.getLayeredPane().add(holeCardsLabel[1], 0);
		tableImage.getLayeredPane().add(opponentHoleCardsLabel[0], 0);
		tableImage.getLayeredPane().add(opponentHoleCardsLabel[1], 0);
		tableImage.getLayeredPane().add(flopLabel[0], 1);
		tableImage.getLayeredPane().add(flopLabel[1], 1);
		tableImage.getLayeredPane().add(flopLabel[2], 1);
		tableImage.getLayeredPane().add(turnLabel, 1);
		tableImage.getLayeredPane().add(riverLabel, 1);
		tableImage.getLayeredPane().add(leaveTableBtn, 1);
		tableImage.getLayeredPane().add(potLabel, 2);
		tableImage.getLayeredPane().add(dealerBtnLabel[0], 2);
		tableImage.getLayeredPane().add(dealerBtnLabel[1], 2);
		tableImage.add(tableImage.getLayeredPane());
		
		setContentPane(tableImage);
	}

	/*
	 * Initializes meesage box, bet amount box and bet slider components.
	 */
	private void initComponents() {
		// message box
		messagesBox = new TextArea(MESSAGE_ROWS, MESSAGE_COLS);
		messagesBox.setBackground(Color.WHITE);
		messagesBox.setBounds(MESSAGE_X, MESSAGE_Y, MESSAGE_WIDTH, MESSAGE_HEIGHT);
		messagesBox.setFont(MESSAGE_FONT);

		// bet field box
		betField = new JTextField();
		betField.setBounds(BET_FIELD_X, BET_FIELD_Y, BET_FIELD_WIDTH, BET_FIELD_HEIGHT);
		betField.setHorizontalAlignment(JTextField.CENTER);
		betField.setFont(BET_FIELD_FONT);
		betField.setBorder(BorderFactory.createLineBorder(RED));
		
		// bet slider
		betSlider = new JSlider(JSlider.HORIZONTAL, BIG_BLIND, clientConnection.getPlayer().getChips(), BIG_BLIND);
		betSlider.setBounds(BET_SLIDER_X, BET_SLIDER_Y, BET_SLIDER_WIDTH, BET_SLIDER_HEIGHT);
		betSlider.setMajorTickSpacing(SLIDER_TICK_SPACE);
		betSlider.setMajorTickSpacing(BIG_BLIND);
		betSlider.setMinorTickSpacing(SMALL_BLIND);
		betSlider.setPaintTicks(true);
		betSlider.setSnapToTicks(true);
		betSlider.setPaintTrack(true);
		betSlider.addChangeListener(this);	
		
		// pot label
		potLabel = new JLabel();
		potLabel.setBounds(POT_X, POT_Y, POT_WIDTH, POT_HEIGHT);
		potLabel.setFont(POT_FONT);
		potLabel.setForeground(Color.WHITE);
		potLabel.setVisible(true);
	}
	
	/*
	 * Initializes the card back side image.
	 */
	private void initCardImage() {
		try {
			cardBackImg = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/card_back_blue.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initDealerButton() {
		try {
			dealerButton = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/dealer_button.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < 2; i++) {
			dealerBtnLabel[i] = new JLabel();
			dealerBtnLabel[i].setBounds(DEALER_BTN[i][0], DEALER_BTN[i][1], DEALER_BTN_SIZE, DEALER_BTN_SIZE);
			dealerBtnLabel[i].setIcon(dealerButton);
			dealerBtnLabel[i].setVisible(false);
		}
	}
	
	/*
	 * Initializes the players name boxes.
	 */
	private void initPlayersBoxes() {
		for (int i = 0; i < 2; i++) {
			playerBoxLabel[i] = new JLabel();
			playerBoxLabel[i].setVerticalAlignment(JLabel.CENTER);
			playerBoxLabel[i].setHorizontalAlignment(JLabel.CENTER);
			playerBoxLabel[i].setOpaque(true);
			playerBoxLabel[i].setBackground(RED);
			playerBoxLabel[i].setForeground(Color.WHITE);
			playerBoxLabel[i].setFont(PLAYER_BOX_FONT);
			playerBoxLabel[i].setBounds(PLAYER_BOX[i][0], PLAYER_BOX[i][1], BOX_WIDTH, BOX_HEIGHT);
			playerBoxLabel[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			
			playerBoxImg[i] = new JLabel();
			playerBoxImg[i].setVerticalAlignment(JLabel.CENTER);
			playerBoxImg[i].setHorizontalAlignment(JLabel.CENTER);
			playerBoxImg[i].setOpaque(true); 
			playerBoxImg[i].setBackground(Color.WHITE);
			playerBoxImg[i].setForeground(Color.BLACK);
			playerBoxImg[i].setFont(BOX_IMG_FONT);
			playerBoxImg[i].setText("\u2660");
			playerBoxImg[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			playerBoxImg[i].setBounds(PLAYER_BOX[i][0]- BOX_IMG_WIDTH,
					PLAYER_BOX[i][1], BOX_IMG_WIDTH, BOX_HEIGHT);
		}
	}
	
	/*
	 * Initializes the cards labels in this frame. 
	 */
	private void initCards() {
		// holecards
		for(int i = 0; i < 2; i++) {
			holeCardsLabel[i] = new JLabel();
			holeCardsLabel[i].setBounds(HOLE_CARDS[i][0], HOLE_CARDS[i][1], CARD_WIDTH, CARD_HEIGHT);
			opponentHoleCardsLabel[i] = new JLabel();
			opponentHoleCardsLabel[i].setBounds(HOLE_CARDS[i + 2][0], HOLE_CARDS[i + 2][1], CARD_WIDTH,
					CARD_HEIGHT);
		}

		// community cards
		for(int i = 0; i < 3; i++) {
			flopLabel[i] = new JLabel();
			flopLabel[i].setBounds(FLOP_X[i], COMMUNITY_CARDS_Y, CARD_WIDTH, CARD_HEIGHT);
		}

		turnLabel = new JLabel();
		turnLabel.setBounds(TURN_X, COMMUNITY_CARDS_Y, CARD_WIDTH, CARD_HEIGHT);
		riverLabel = new JLabel();
		riverLabel.setBounds(RIVER_X, COMMUNITY_CARDS_Y, CARD_WIDTH, CARD_HEIGHT);
	}
	
	/*
	 * Initializes all the buttons in this frame.
	 */
	private void initButtons() {
		// seat
		seatBtn = new JButton("Take Seat");
		seatBtn.setBounds(340, 370, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		seatBtn.setBackground(Color.WHITE);
		//raiseBtn.setBorder(BorderFactory.createLineBorder(Color.RED));
		seatBtn.addActionListener(this);
		
		// raise button
		raiseBtn = new JButton("Raise");
		raiseBtn.setContentAreaFilled(false);
		raiseBtn.setOpaque(true);
		raiseBtn.setBounds(BET_RAISE_BTN_X, ACTION_BTN_Y, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		raiseBtn.setBackground(BLUE);
		raiseBtn.setForeground(Color.WHITE);
		raiseBtn.setFont(BTN_FONT);
		raiseBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		raiseBtn.addActionListener(this);

		// bet button
		betBtn = new JButton("Bet");
		betBtn.setContentAreaFilled(false);
		betBtn.setOpaque(true);
		betBtn.setBounds(BET_RAISE_BTN_X, ACTION_BTN_Y, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		betBtn.setBackground(BLUE);
		betBtn.setForeground(Color.WHITE);
		betBtn.setFont(BTN_FONT);
		betBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		betBtn.addActionListener(this);

		// call button
		callBtn = new JButton("Call");
		callBtn.setContentAreaFilled(false);
		callBtn.setOpaque(true);
		callBtn.setBounds(CALL_CHECK_BTN_X, ACTION_BTN_Y, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		callBtn.setBackground(BLUE);
		callBtn.setForeground(Color.WHITE);
		callBtn.setFont(BTN_FONT);
		callBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		callBtn.addActionListener(this);

		// check button
		checkBtn = new JButton("Check");
		checkBtn.setContentAreaFilled(false);
		checkBtn.setOpaque(true);
		checkBtn.setBounds(CALL_CHECK_BTN_X, ACTION_BTN_Y, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		checkBtn.setBackground(BLUE);
		checkBtn.setForeground(Color.WHITE);
		checkBtn.setFont(BTN_FONT);
		checkBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		checkBtn.addActionListener(this);

		// fold button
		foldBtn = new JButton("fold");
		foldBtn.setContentAreaFilled(false);
		foldBtn.setOpaque(true);
		foldBtn.setBounds(FOLD_BTN_X, ACTION_BTN_Y, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		foldBtn.setBackground(BLUE);
		foldBtn.setForeground(Color.WHITE);
		foldBtn.setFont(BTN_FONT);
		foldBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		foldBtn.addActionListener(this);

		// leave table button
		leaveTableBtn = new JButton("Leave");
		leaveTableBtn.setContentAreaFilled(false);
		leaveTableBtn.setOpaque(true);
		leaveTableBtn.setBounds(LEAVE_BTN_X, LEAVE_BTN_Y, LEAVE_BTN_WIDTH, LEAVE_BTN_HEIGHT);
		leaveTableBtn.setBackground(BLUE);
		leaveTableBtn.setForeground(Color.WHITE);
		leaveTableBtn.setFont(BTN_FONT);
		leaveTableBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		leaveTableBtn.addActionListener(this);
	}
	
	/*
	 * Remove all actions buttons while this player is not in
	 * turn to act.
	 */
	private void removeButtons() {
		tableImage.getLayeredPane().remove(checkBtn);
		tableImage.getLayeredPane().remove(raiseBtn);
		tableImage.getLayeredPane().remove(foldBtn);
		tableImage.getLayeredPane().remove(betBtn);
		tableImage.getLayeredPane().remove(callBtn);
		tableImage.getLayeredPane().remove(betField);
		tableImage.getLayeredPane().remove(betSlider);
		
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// seat
		if(e.getSource() == seatBtn) {
			// Initially sends the player states to the server. this includes
			// the player's name, hole-cards, any action (call, fold...) etc.
			clientConnection.sendToServer(clientConnection.getPlayer());
			
			playerBoxLabel[0].setText("<html>" +
					clientConnection.getPlayer().getName() + "<br>" + chips + "</html>");		
			tableImage.getLayeredPane().add(playerBoxLabel[0], 2);
			tableImage.getLayeredPane().add(playerBoxImg[0], 2);
			tableImage.getLayeredPane().remove(seatBtn);
					
			tableImage.repaint();
		}
		
		// raise
		if(e.getSource() == raiseBtn) {
			int bet = Integer.parseInt(betField.getText());

			if (bet >= (BIG_BLIND * 2) && bet <= clientConnection.getPlayer().getChips()) {
				clientConnection.sendToServer("raise", bet);
				removeButtons();
			}
		}
		
		// bet
		if(e.getSource() == betBtn) {
			int bet = Integer.parseInt(betField.getText());

			if (bet >= BIG_BLIND && bet <= clientConnection.getPlayer().getChips()) {
				clientConnection.sendToServer("bet", bet);
				removeButtons();
			}
		}
		
		// call
		if(e.getSource() == callBtn) {
			clientConnection.sendToServer("call", 0);
			removeButtons();
		}
		
		// check
		if(e.getSource() == checkBtn) {
			clientConnection.sendToServer("check", 0);
			removeButtons();
		}
		
		// fold
		if(e.getSource() == foldBtn) {
			clientConnection.sendToServer("fold", 0);
			removeButtons();
		}
		
		// leave table
		if(e.getSource() == leaveTableBtn) {
			clientConnection.sendToServer("leave", 0);
			clientConnection.setRunning(false);

//			try {
//				clientConnection.getObjectOutput().close();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
			
			dispose();
		}
		
		// this player turn to act - box is flashing
		if(e.getSource() == thisPlayerTimer) {			
			if(flashing) {
				playerBoxLabel[0].setBackground(ORANGE);
				flashing = false;
			} else {
				playerBoxLabel[0].setBackground(RED);
				flashing = true;
			}
		}
		
		// other player turn - his box is flashing
		if(e.getSource() == otherPlayerTimer) {		
			if(flashing) {
				playerBoxLabel[1].setBackground(ORANGE);
				flashing = false;
			} else {
				playerBoxLabel[1].setBackground(RED);
				flashing = true;
			}
		}
	}

	// bet slider state
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		betField.setText(Integer.toString(slider.getValue()));
		betBtn.setText("Bet " + slider.getValue());
		raiseBtn.setText("Raise " + slider.getValue());	
	}
}

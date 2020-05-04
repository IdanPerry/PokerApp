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

@SuppressWarnings("serial")
public class TableWindow extends JFrame implements ActionListener, ChangeListener {
	// frame
	private static final int WIDTH = 705;
	private static final int HEIGHT = 525;
	
	// cards
	private static final int[][] PLAYER_BOX_POSITION = { { 290, 365 }, { 290, 52 } };
	private static final int[][] HOLE_CARDS_POSITION = { { 295, 330 }, { 345, 330 }, { 295, 18 }, { 345, 18 } };
	private static final int[] FLOP_X = {220, 270, 320};
	private static final int TURN_X = 370;
	private static final int RIVER_X = 420;
	private static final int COMMUNITY_CARDS_Y = 170;
	private static final int CARD_WIDTH = 50;
	private static final int CARD_HEIGHT = 70;
	
	// message box
	private static final int MESSAGE_ROWS = 6;
	private static final int MESSAGE_COLS = 26;
	private static final int MESSAGE_WIDTH = 330;
	private static final int MESSAGE_HEIGHT = 90;
	private static final int MESSAGE_X = 0;
	private static final int MESSAGE_Y = 410;
	private static final Font MESSAGE_FONT = new Font("Ariel", Font.PLAIN, 15);
	
	// bet field and slider
	private static final int BET_FIELD_X = 610;
	private static final int BET_FIELD_Y = 390;
	private static final int BET_FIELD_WIDTH = 70;
	private static final int BET_FIELD_HEIGHT = 30;
	private static final Font BET_FIELD_FONT = new Font("Tahoma", Font.BOLD, 14);
	private static final int BET_SLIDER_X = 480;
	private static final int BET_SLIDER_Y = 425;
	private static final int BET_SLIDER_WIDTH = 200;
	private static final int BET_SLIDER_HEIGHT = 20;
	private static final int SLIDER_TICK_SPACE = 100;
	private static final int MIN_BET = 100;
	
	// buttons
	private static final int BET_RAISE_BTN_X = 580;
	private static final int CALL_CHECK_BTN_X = 480;
	private static final int FOLD_BTN_X = 380;
	private static final int ACTION_BTN_Y = 450;
	private static final int ACTION_BTN_WIDTH = 100;
	private static final int ACTION_BTN_HEIGHT = 40;
	private static final int LEAVE_BTN_X = 0;
	private static final int LEAVE_BTN_Y = 0;
	private static final int LEAVE_BTN_WIDTH = 70;
	private static final int LEAVE_BTN_HEIGHT = 30;
	private static final Font BTN_FONT = new Font("Tahoma", Font.BOLD, 14);
	private static final Color LIGHT_BROWN = new Color(204, 102, 0);
	
	// player box
	private static final Color BROWN = new Color(153, 76, 0);
	private static final Color ORANGE = new Color(245, 100, 0);
	private static final Font PLAYER_BOX_FONT = new Font("Tahoma", Font.PLAIN, 14);
	private static final Font BOX_IMG_FONT = new Font("", Font.PLAIN, 36);
	private static final int BOX_WIDTH = 130;
	private static final int BOX_HEIGHT = 40;
	private static final int BOX_IMG_WIDTH = 40;
	
	private final ClientConnection clientConnection;
	private final Timer thisPlayerTimer;
	private final Timer otherPlayerTimer;
	private TableImage tableImage;
	
	private final JLabel[] playerBoxLabel;
	private final JLabel[] playerBoxImg; 
	private final JLabel[] holeCardsLabel;
	private final JLabel[] opponentHoleCardsLabel;
	private final JLabel[] flopLabel;
	private JLabel turnLabel;
	private JLabel riverLabel;
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
		thisPlayerTimer = new Timer(1000, this);
		otherPlayerTimer = new Timer(1000, this);
		
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		initCardImage();
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
	
	/*
	 * Initializes the players name boxes.
	 */
	private void initPlayersBoxes() {
		for (int i = 0; i < 2; i++) {
			playerBoxLabel[i] = new JLabel();
			playerBoxLabel[i].setVerticalAlignment(JLabel.CENTER);
			playerBoxLabel[i].setHorizontalAlignment(JLabel.CENTER);
			playerBoxLabel[i].setOpaque(true);
			playerBoxLabel[i].setBackground(BROWN);
			playerBoxLabel[i].setForeground(Color.WHITE);
			playerBoxLabel[i].setFont(PLAYER_BOX_FONT);
			playerBoxLabel[i].setBounds(PLAYER_BOX_POSITION[i][0], PLAYER_BOX_POSITION[i][1], BOX_WIDTH, BOX_HEIGHT);
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
			playerBoxImg[i].setBounds(PLAYER_BOX_POSITION[i][0]- BOX_IMG_WIDTH,
					PLAYER_BOX_POSITION[i][1], BOX_IMG_WIDTH, BOX_HEIGHT);
		}
	}
	
	/*
	 * Initializes the cards labels in this frame. 
	 */
	private void initCards() {
		// holecards
		for(int i = 0; i < 2; i++) {
			holeCardsLabel[i] = new JLabel();
			holeCardsLabel[i].setBounds(HOLE_CARDS_POSITION[i][0], HOLE_CARDS_POSITION[i][1], CARD_WIDTH, CARD_HEIGHT);
			opponentHoleCardsLabel[i] = new JLabel();
			opponentHoleCardsLabel[i].setBounds(HOLE_CARDS_POSITION[i + 2][0], HOLE_CARDS_POSITION[i + 2][1], CARD_WIDTH,
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
		seatBtn.setBounds(290, 350, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		seatBtn.setBackground(Color.WHITE);
		//raiseBtn.setBorder(BorderFactory.createLineBorder(Color.RED));
		seatBtn.addActionListener(this);
		
		// raise button
		raiseBtn = new JButton("Raise");
		raiseBtn.setContentAreaFilled(false);
		raiseBtn.setOpaque(true);
		raiseBtn.setBounds(BET_RAISE_BTN_X, ACTION_BTN_Y, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		raiseBtn.setBackground(LIGHT_BROWN);
		raiseBtn.setForeground(Color.WHITE);
		raiseBtn.setFont(BTN_FONT);
		raiseBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		raiseBtn.addActionListener(this);

		// bet button
		betBtn = new JButton("Bet");
		betBtn.setContentAreaFilled(false);
		betBtn.setOpaque(true);
		betBtn.setBounds(BET_RAISE_BTN_X, ACTION_BTN_Y, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		betBtn.setBackground(LIGHT_BROWN);
		betBtn.setForeground(Color.WHITE);
		betBtn.setFont(BTN_FONT);
		betBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		betBtn.addActionListener(this);

		// call button
		callBtn = new JButton("Call");
		callBtn.setContentAreaFilled(false);
		callBtn.setOpaque(true);
		callBtn.setBounds(CALL_CHECK_BTN_X, ACTION_BTN_Y, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		callBtn.setBackground(LIGHT_BROWN);
		callBtn.setForeground(Color.WHITE);
		callBtn.setFont(BTN_FONT);
		callBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		callBtn.addActionListener(this);

		// check button
		checkBtn = new JButton("Check");
		checkBtn.setContentAreaFilled(false);
		checkBtn.setOpaque(true);
		checkBtn.setBounds(CALL_CHECK_BTN_X, ACTION_BTN_Y, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		checkBtn.setBackground(LIGHT_BROWN);
		checkBtn.setForeground(Color.WHITE);
		checkBtn.setFont(BTN_FONT);
		checkBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		checkBtn.addActionListener(this);

		// fold button
		foldBtn = new JButton("fold");
		foldBtn.setContentAreaFilled(false);
		foldBtn.setOpaque(true);
		foldBtn.setBounds(FOLD_BTN_X, ACTION_BTN_Y, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		foldBtn.setBackground(LIGHT_BROWN);
		foldBtn.setForeground(Color.WHITE);
		foldBtn.setFont(BTN_FONT);
		foldBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		foldBtn.addActionListener(this);

		// leave table button
		leaveTableBtn = new JButton("Leave");
		leaveTableBtn.setContentAreaFilled(false);
		leaveTableBtn.setOpaque(true);
		leaveTableBtn.setBounds(LEAVE_BTN_X, LEAVE_BTN_Y, LEAVE_BTN_WIDTH, LEAVE_BTN_HEIGHT);
		leaveTableBtn.setBackground(LIGHT_BROWN);
		leaveTableBtn.setForeground(Color.WHITE);
		leaveTableBtn.setFont(BTN_FONT);
		leaveTableBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		leaveTableBtn.addActionListener(this);
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
		betField.setBorder(BorderFactory.createLineBorder(BROWN));
		
		// bet slider
		betSlider = new JSlider(JSlider.HORIZONTAL, MIN_BET, 5000, MIN_BET);
		betSlider.setBounds(BET_SLIDER_X, BET_SLIDER_Y, BET_SLIDER_WIDTH, BET_SLIDER_HEIGHT);
		betSlider.setMajorTickSpacing(SLIDER_TICK_SPACE);
		betSlider.setMinorTickSpacing(MIN_BET);
		betSlider.setPaintTicks(true);
		betSlider.setSnapToTicks(true);
		betSlider.setPaintTrack(true);
		betSlider.addChangeListener(this);			
	}

	public ClientConnection getClientConnection() {
		return clientConnection;
	}

	public TableImage getTableImage() {
		return tableImage;
	}

	public JLabel[] getPlayerBoxLabel() {
		return playerBoxLabel;
	}
	
	public JLabel[] getPlayerBoxImg() {
		return playerBoxImg;
	}

	public JLabel[] getHoleCardsLabel() {
		return holeCardsLabel;
	}

	public JLabel[] getOpponentHoleCardsLabel() {
		return opponentHoleCardsLabel;
	}

	public JLabel[] getFlopLabel() {
		return flopLabel;
	}

	public JLabel getTurnLabel() {
		return turnLabel;
	}

	public JLabel getRiverLabel() {
		return riverLabel;
	}
	
	public TextArea getMessagesBox() {
		return messagesBox;
	}
	
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
	
	public void setTurnLabel(ImageIcon turn) {
		tableImage.getLayeredPane().add(turnLabel, 1);
		turnLabel.setIcon(turn);
		turnLabel.setVisible(true);
	}
	
	public void setRiverLabel(ImageIcon river) {
		tableImage.getLayeredPane().add(riverLabel, 1);
		riverLabel.setIcon(river);
		riverLabel.setVisible(true);
	}
	
	public void setFlopImages(ImageIcon flop1, ImageIcon flop2, ImageIcon flop3) {	
		flopLabel[0].setIcon(flop1);
		flopLabel[1].setIcon(flop2);
		flopLabel[2].setIcon(flop3);
		
		flopLabel[0].setVisible(true);
		flopLabel[1].setVisible(true);
		flopLabel[2].setVisible(true);
	}
	
	public void setTurnImage(ImageIcon turn) {
		turnLabel.setIcon(turn);
		turnLabel.setVisible(true);
	}
	
	public void setRiverImage(ImageIcon river) {
		riverLabel.setIcon(river);
		riverLabel.setVisible(true);
	}

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
	
	public void removeBoardCards() {
		tableImage.getLayeredPane().remove(flopLabel[0]);
		tableImage.getLayeredPane().remove(flopLabel[1]);
		tableImage.getLayeredPane().remove(flopLabel[2]);
		tableImage.getLayeredPane().remove(turnLabel);
		tableImage.getLayeredPane().remove(riverLabel);
		
		repaint();
	}
	
	public void resetBoard() {
		flopLabel[0].setIcon(null);
		flopLabel[1].setIcon(null);
		flopLabel[2].setIcon(null);
		turnLabel.setIcon(null);
		riverLabel.setIcon(null);
	}
	
	public void setBoardVisibility(boolean visible) {
		flopLabel[0].setVisible(visible);
		flopLabel[1].setVisible(visible);
		flopLabel[2].setVisible(visible);
		turnLabel.setVisible(visible);
		riverLabel.setVisible(visible);
	}
	
	public void setHoleCardsImages(ImageIcon holeCard1Img, ImageIcon holeCard2img) {
		holeCardsLabel[0].setIcon(holeCard1Img);
		holeCardsLabel[1].setIcon(holeCard2img);
		opponentHoleCardsLabel[0].setIcon(cardBackImg);
		opponentHoleCardsLabel[1].setIcon(cardBackImg);
	}
	
	public void callRaiseFoldButtons() {
		messagesBox.append("Dealer: " + clientConnection.getPlayer().getName() + ", it's your turn \n");

		tableImage.getLayeredPane().add(callBtn, 1);
		callBtn.setText("Call " + (clientConnection.getTableInfo().getBet() - clientConnection.getPlayer().getCurrentBet()));
		tableImage.getLayeredPane().add(raiseBtn, 1);
		tableImage.getLayeredPane().add(betField, 1);
		betField.setText(MIN_BET + "");
		tableImage.getLayeredPane().add(betSlider, 1);
		tableImage.getLayeredPane().add(foldBtn, 1);

		validate();
		repaint();
	}

	public void checkBetButtons() {
		messagesBox.append("Dealer: " + clientConnection.getPlayer().getName() + ", it's your turn \n");

		tableImage.getLayeredPane().add(checkBtn, 1);
		tableImage.getLayeredPane().add(betBtn, 1);
		tableImage.getLayeredPane().add(betField, 1);
		betField.setText(MIN_BET + "");
		tableImage.getLayeredPane().add(betSlider, 1);

		validate();
		repaint();
	}
	
	public void checkRaiseButtons() {
		messagesBox.append("Dealer: " + clientConnection.getPlayer().getName() + ", it's your turn \n");
		
		tableImage.getLayeredPane().add(checkBtn, 1);
		tableImage.getLayeredPane().add(raiseBtn, 1);
		tableImage.getLayeredPane().add(betField, 1);
		betField.setText(MIN_BET + "");
		tableImage.getLayeredPane().add(betSlider, 1);
		
		validate();
		repaint();
	}
	
	public void dimPlayerBox() {
		flashing = false;
		playerBoxLabel[0].setText("<html>" + clientConnection.getPlayer().getName()
				+ "<br>" + clientConnection.getPlayer().getChips() + "</html>");

		otherPlayerTimer.start();
		thisPlayerTimer.stop();
		playerBoxLabel[0].setBackground(BROWN);
	}

	public void highLightPlayerBox() {
		flashing = true;
		playerBoxLabel[0].setText("<html>" + clientConnection.getPlayer().getName()
				+ "<br>" + clientConnection.getPlayer().getChips() + "</html>");
			
		thisPlayerTimer.start();
		otherPlayerTimer.stop();
		playerBoxLabel[1].setBackground(BROWN);
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
			holeCardsLabel[i].setBounds(HOLE_CARDS_POSITION[i][0],
					HOLE_CARDS_POSITION[i][1] - 35, CARD_WIDTH, CARD_HEIGHT);
			
			opponentHoleCardsLabel[i].setBounds(HOLE_CARDS_POSITION[i + 2][0],
					HOLE_CARDS_POSITION[i + 2][1] - 18, CARD_WIDTH, CARD_HEIGHT);
		}
	}
	
	/**
	 * Reset the holecards to their original position to start a new hand.
	 */
	public void resetHoleCardsPosition() {
		for (int i = 0; i < 2; i++) {
			holeCardsLabel[i].setBounds(HOLE_CARDS_POSITION[i][0],
					HOLE_CARDS_POSITION[i][1], CARD_WIDTH, CARD_HEIGHT);
			
			opponentHoleCardsLabel[i].setBounds(HOLE_CARDS_POSITION[i + 2][0],
					HOLE_CARDS_POSITION[i + 2][1], CARD_WIDTH, CARD_HEIGHT);
		}
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

			if (bet >= (MIN_BET * 2) && bet <= clientConnection.getPlayer().getChips()) {
				clientConnection.sendToServer("raise", bet);
				removeButtons();
			}
		}
		
		// bet
		if(e.getSource() == betBtn) {
			int bet = Integer.parseInt(betField.getText());

			if (bet >= MIN_BET && bet <= clientConnection.getPlayer().getChips()) {
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

			try {
				clientConnection.getObjectOutput().close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			dispose();
		}
		
		// this player turn to act - box is flashing
		if(e.getSource() == thisPlayerTimer) {			
			if(flashing) {
				playerBoxLabel[0].setBackground(ORANGE);
				flashing = false;
			} else {
				playerBoxLabel[0].setBackground(BROWN);
				flashing = true;
			}
		}
		
		// other player turn - his box is flashing
		if(e.getSource() == otherPlayerTimer) {		
			if(flashing) {
				playerBoxLabel[1].setBackground(ORANGE);
				flashing = false;
			} else {
				playerBoxLabel[1].setBackground(BROWN);
				flashing = true;
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider s = (JSlider) e.getSource();
		betField.setText(Integer.toString(s.getValue()));
		betBtn.setText("Bet " + s.getValue());
		raiseBtn.setText("Raise " + s.getValue());		
	}
}

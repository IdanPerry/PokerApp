package com.idan.test.GUI;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class TestTableWindow extends JFrame implements ActionListener, ChangeListener {
	// frame
	private static final int WIDTH = 813;
	private static final int HEIGHT = 586;
	
	// cards
	private static final int[][] HOLE_CARDS_POSITION = { { 345, 330 }, { 395, 330 }, { 345, 18 }, { 395, 18 } };
	private static final int[] FLOP_X = {270, 320, 370};
	private static final int TURN_X = 420;
	private static final int RIVER_X = 470;
	private static final int COMMUNITY_CARDS_Y = 170;
	private static final int CARD_WIDTH = 50;
	private static final int CARD_HEIGHT = 70;
	
	// message box
	private static final int MESSAGE_ROWS = 6;
	private static final int MESSAGE_COLS = 26;
	private static final int MESSAGE_WIDTH = 330;
	private static final int MESSAGE_HEIGHT = 90;
	private static final int MESSAGE_X = 0;
	private static final int MESSAGE_Y = 456;
	private static final Font MESSAGE_FONT = new Font("Ariel", Font.PLAIN, 15);
	
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
	private static final int MIN_BET = 100;
	
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
	private static final int[][] PLAYER_BOX_POSITION = { { 340, 365 }, { 340, 52 } };
	private static final int BOX_WIDTH = 130;
	private static final int BOX_HEIGHT = 40;
	private static final int BOX_IMG_WIDTH = 40;
	private static final Color RED = new Color(87, 0, 0);
	private static final Font PLAYER_BOX_FONT = new Font("Tahoma", Font.PLAIN, 14);
	private static final Font BOX_IMG_FONT = new Font("", Font.PLAIN, 36);
	
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
	private TestTableImage tableImage;
	private int chips;

	/**
	 * Constructs a window representing a poker table.
	 * 
	 * @param clientConnection
	 */
	public TestTableWindow() {
		playerBoxLabel = new JLabel[2];
		playerBoxImg = new JLabel[2];
		holeCardsLabel = new JLabel[2];
		opponentHoleCardsLabel = new JLabel[2];
		flopLabel = new JLabel[3];
		
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		initCardImage();
		initPlayersBoxes();
		initCards();
		initButtons();	
		initComponents();
		initTableImage();
		validate();
		
		callRaiseFoldButtons();
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
			playerBoxLabel[i].setBackground(RED);
			playerBoxLabel[i].setForeground(Color.WHITE);
			playerBoxLabel[i].setFont(PLAYER_BOX_FONT);
			playerBoxLabel[i].setText("<html>player_name" + "<br>" + 455 + "</html>");
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
		seatBtn.setBounds(340, 370, ACTION_BTN_WIDTH, ACTION_BTN_HEIGHT);
		seatBtn.setBackground(Color.WHITE);
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
	 * Initializes the table image panel.
	 */
	private void initTableImage() {
		tableImage = new TestTableImage();
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
		tableImage.getLayeredPane().add(playerBoxLabel[1], 2);
		tableImage.getLayeredPane().add(playerBoxImg[1], 2);
		tableImage.add(tableImage.getLayeredPane());
		
		setContentPane(tableImage);
	}

	/*
	 * Initializes meesage box, bet field box and bet slider components.
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
		betField.setBorder(BorderFactory.createLineBorder(BLUE));
		
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

	public JLabel[] getPlayerBoxLabel() {
		return playerBoxLabel;
	}
	
	public JLabel[] getPlayerBoxImg() {
		return playerBoxImg;
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
		tableImage.getLayeredPane().add(turnLabel,1);
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
		messagesBox.append("Dealer: it's your turn \n");

		tableImage.getLayeredPane().add(callBtn, 1);
		callBtn.setText("Call ");
		tableImage.getLayeredPane().add(raiseBtn, 1);
		tableImage.getLayeredPane().add(betField, 1);
		betField.setText(MIN_BET + "");
		tableImage.getLayeredPane().add(betSlider, 1);
		tableImage.getLayeredPane().add(foldBtn, 1);

		validate();
		repaint();
	}

	public void checkBetButtons() {
		messagesBox.append("Dealer: it's your turn \n");

		tableImage.getLayeredPane().add(checkBtn, 1);
		tableImage.getLayeredPane().add(betBtn, 1);
		tableImage.getLayeredPane().add(betField, 1);
		betField.setText(MIN_BET + "");
		tableImage.getLayeredPane().add(betSlider, 1);

		validate();
		repaint();
	}
	
	public void checkRaiseButtons() {
		messagesBox.append("Dealer: it's your turn \n");
		
		tableImage.getLayeredPane().add(checkBtn, 1);
		tableImage.getLayeredPane().add(raiseBtn, 1);
		tableImage.getLayeredPane().add(betField, 1);
		betField.setText(MIN_BET + "");
		tableImage.getLayeredPane().add(betSlider, 1);
		
		validate();
		repaint();
	}
	
//	public void dimPlayerBox() {
//		playerBoxLabel[0].setText("<html><span style='font-size:11px'>" + "player_name"
//				+ "<br>" + 5000 + "</span></html>");
//		playerBoxLabel[0].setBorder(BorderFactory.createLineBorder(Color.WHITE));
//
//		playerBoxLabel[1].setBorder(BorderFactory.createLineBorder(Color.CYAN));
//	}

//	public void highLightPlayerBox() {
//		playerBoxLabel[0].setText("<html><span style='font-size:11px'>" + "player_name"
//				+ "<br>" + 5000 + "</span></html>");
//		playerBoxLabel[0].setBorder(BorderFactory.createLineBorder(Color.CYAN));
//
//		playerBoxLabel[1].setBorder(BorderFactory.createLineBorder(Color.WHITE));
//	}
	
//	public void showDown(ImageIcon opponentCard1, ImageIcon opponentCard2) {
//		holeCardsLabel[0].setBounds(HOLE_CARDS_POSITION[0][0],
//				HOLE_CARDS_POSITION[0][1] - 35, CARD_WIDTH, CARD_HEIGHT);
//		
//		holeCardsLabel[1].setBounds(HOLE_CARDS_POSITION[1][0],
//				HOLE_CARDS_POSITION[1][1] - 35, CARD_WIDTH, CARD_HEIGHT);
//
//		opponentHoleCardsLabel[0].setIcon(opponentCard1);
//		opponentHoleCardsLabel[1].setIcon(opponentCard2);
//
//		opponentHoleCardsLabel[0].setBounds(HOLE_CARDS_POSITION[2][0],
//				HOLE_CARDS_POSITION[2][1] - 18, CARD_WIDTH, CARD_HEIGHT);
//		
//		opponentHoleCardsLabel[1].setBounds(HOLE_CARDS_POSITION[3][0],
//				HOLE_CARDS_POSITION[3][1] - 18, CARD_WIDTH, CARD_HEIGHT);
//	}
	
	public void resetHoleCardsPosition() {
		opponentHoleCardsLabel[0].setBounds(HOLE_CARDS_POSITION[2][0],
				HOLE_CARDS_POSITION[2][1], CARD_WIDTH, CARD_HEIGHT);
		
		opponentHoleCardsLabel[1].setBounds(HOLE_CARDS_POSITION[3][0],
				HOLE_CARDS_POSITION[3][1], CARD_WIDTH, CARD_HEIGHT);

		holeCardsLabel[0].setBounds(HOLE_CARDS_POSITION[0][0],
				HOLE_CARDS_POSITION[0][1], CARD_WIDTH, CARD_HEIGHT);
		
		holeCardsLabel[1].setBounds(HOLE_CARDS_POSITION[1][0],
				HOLE_CARDS_POSITION[1][1], CARD_WIDTH, CARD_HEIGHT);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// seat
		if(e.getSource() == seatBtn) {
			playerBoxLabel[0].setText("<html>player_name" + "<br>" + 5000 + "</html>");
			playerBoxLabel[0].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			tableImage.getLayeredPane().add(playerBoxLabel[0], 2);
			tableImage.getLayeredPane().add(playerBoxImg[0], 2);
			tableImage.getLayeredPane().remove(seatBtn);
			tableImage.repaint();
		}
		
		// raise
		if(e.getSource() == raiseBtn) {
			int bet = Integer.parseInt(betField.getText());

			if (bet >= 200 && bet <= 5000) {
				removeButtons();
			}
		}
		
		// bet
		if(e.getSource() == betBtn) {
			int bet = Integer.parseInt(betField.getText());

			if (bet >= 100 && bet <= 5000) {
				removeButtons();
			}
		}
		
		// call
		if(e.getSource() == callBtn) {
			removeButtons();
		}
		
		// check
		if(e.getSource() == checkBtn) {
			removeButtons();
		}
		
		// fold
		if(e.getSource() == foldBtn) {
			removeButtons();
		}
		
		// leave table
		if(e.getSource() == leaveTableBtn) {
			dispose();
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

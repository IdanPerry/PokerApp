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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.idan.client.ClientConnection;

public class TableWindow implements ActionListener, ChangeListener {
	private static final int WIDTH = 705;
	private static final int HEIGHT = 525;
	private static final int[][] BOX_POSITION = { { 275, 365 }, { 275, 52 } };
	private static final int[][] HOLE_CARDS_POSITION = { { 295, 330 }, { 345, 330 }, { 295, 18 }, { 345, 18 } };
	private static final int CARD_WIDTH = 50;
	private static final int CARD_HEIGHT = 70;	

	private TableImage tableImage;
	private ClientConnection clientConnection;

	private ImageIcon cardBackImg;
	
	private JFrame tableFrame;
	private JLabel[] playerBoxLabel = new JLabel[2];
	private JLabel[] holeCardsLabel = new JLabel[2];
	private JLabel[] opponentHoleCardsLabel = new JLabel[2];
	private JLabel[] flopLabel = new JLabel[3];
	private JLabel turnLabel;
	private JLabel riverLabel;
	private TextArea messagesBox;
	private JTextField betField;
	private JSlider betSlider;
	private JButton foldBtn;
	private JButton checkBtn;
	private JButton callBtn;
	private JButton betBtn;
	private JButton raiseBtn;
	private JButton leaveTableBtn;

	public TableWindow(ClientConnection c) {
		this.clientConnection = c;

		initComponents();
	}

	private void initComponents() {
		tableFrame = new JFrame("No-limit Hold'em - Logged in as " + clientConnection.getPlayer().getName());
		tableFrame.setSize(WIDTH, HEIGHT);
		tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tableFrame.setResizable(false);

		try {
			cardBackImg = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/card_back_blue.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 2; i++) {
			playerBoxLabel[i] = new JLabel();
			playerBoxLabel[i].setVerticalAlignment(JLabel.CENTER);
			playerBoxLabel[i].setHorizontalAlignment(JLabel.CENTER);
			playerBoxLabel[i].setOpaque(true);
			playerBoxLabel[i].setBackground(Color.GRAY);
			playerBoxLabel[i].setForeground(Color.WHITE);
		}

		/* init labels */
		// player box
		playerBoxLabel[0].setBounds(BOX_POSITION[0][0], BOX_POSITION[0][1], 140, 35);
		playerBoxLabel[1].setBounds(BOX_POSITION[1][0], BOX_POSITION[1][1], 140, 35);
		
		// holecards
		holeCardsLabel[0] = new JLabel();
		holeCardsLabel[0].setBounds(HOLE_CARDS_POSITION[0][0], HOLE_CARDS_POSITION[0][1], CARD_WIDTH, CARD_HEIGHT);
		holeCardsLabel[1] = new JLabel();
		holeCardsLabel[1].setBounds(HOLE_CARDS_POSITION[1][0], HOLE_CARDS_POSITION[1][1], CARD_WIDTH, CARD_HEIGHT);
		opponentHoleCardsLabel[0] = new JLabel();
		opponentHoleCardsLabel[0].setBounds(HOLE_CARDS_POSITION[2][0], HOLE_CARDS_POSITION[2][1], CARD_WIDTH,
				CARD_HEIGHT);
		opponentHoleCardsLabel[1] = new JLabel();
		opponentHoleCardsLabel[1].setBounds(HOLE_CARDS_POSITION[3][0], HOLE_CARDS_POSITION[3][1], CARD_WIDTH,
				CARD_HEIGHT);

		// community cards
		flopLabel[0] = new JLabel();
		flopLabel[0].setBounds(220, 170, CARD_WIDTH, CARD_HEIGHT);
		flopLabel[1] = new JLabel();
		flopLabel[1].setBounds(270, 170, CARD_WIDTH, CARD_HEIGHT);
		flopLabel[2] = new JLabel();
		flopLabel[2].setBounds(320, 170, CARD_WIDTH, CARD_HEIGHT);
		turnLabel = new JLabel();
		turnLabel.setBounds(370, 170, CARD_WIDTH, CARD_HEIGHT);
		riverLabel = new JLabel();
		riverLabel.setBounds(420, 170, CARD_WIDTH, CARD_HEIGHT);

		// message box
		messagesBox = new TextArea(6, 26);
		messagesBox.setBackground(Color.WHITE);
		messagesBox.setBounds(0, 410, 330, 90);
		messagesBox.setFont(new Font("Ariel", Font.PLAIN, 15));

		// bet amount box and slider
		betField = new JTextField();
		betField.setBounds(360, 417, 100, 30);
		betField.setAlignmentY(JTextField.CENTER_ALIGNMENT);
		betField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		betSlider = new JSlider(JSlider.HORIZONTAL, 100, clientConnection.getPlayer().getChips(), 100);
		betSlider.setBounds(462, 417, 218, 30);
		betSlider.setMajorTickSpacing(100);
		betSlider.addChangeListener(this);

		// raise button
		raiseBtn = new JButton("Raise");
		raiseBtn.setBounds(580, 450, 100, 40);
		raiseBtn.setBorder(BorderFactory.createLineBorder(Color.RED));
		raiseBtn.addActionListener(this);

		// bet button
		betBtn = new JButton("Bet");
		betBtn.setBounds(580, 450, 100, 40);
		betBtn.setBorder(BorderFactory.createLineBorder(Color.RED));
		betBtn.addActionListener(this);

		// call button
		callBtn = new JButton("Call");
		callBtn.setBounds(470, 450, 100, 40);
		callBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		callBtn.addActionListener(this);

		// check button
		checkBtn = new JButton("Check");
		checkBtn.setBounds(470, 450, 100, 40);
		checkBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		checkBtn.addActionListener(this);

		// fold button
		foldBtn = new JButton("fold");
		foldBtn.setBounds(360, 450, 100, 40);
		foldBtn.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		foldBtn.addActionListener(this);

		// leave table button
		leaveTableBtn = new JButton("Leave Table");
		leaveTableBtn.setBounds(0, 0, 150, 30);
		leaveTableBtn.addActionListener(this);

		tableImage = new TableImage(this);
		tableImage.setOpaque(true);

		tableFrame.setContentPane(tableImage);

		// add components to tableImage
		tableImage.getLayeredPane().add(messagesBox, new Integer(1));
		tableImage.getLayeredPane().add(holeCardsLabel[0], new Integer(0));
		tableImage.getLayeredPane().add(holeCardsLabel[1], new Integer(0));
		tableImage.getLayeredPane().add(opponentHoleCardsLabel[0], new Integer(0));
		tableImage.getLayeredPane().add(opponentHoleCardsLabel[1], new Integer(0));
		tableImage.getLayeredPane().add(flopLabel[0], new Integer(1));
		tableImage.getLayeredPane().add(flopLabel[1], new Integer(1));
		tableImage.getLayeredPane().add(flopLabel[2], new Integer(1));
		tableImage.getLayeredPane().add(turnLabel, new Integer(1));
		tableImage.getLayeredPane().add(riverLabel, new Integer(1));
		tableImage.getLayeredPane().add(leaveTableBtn, new Integer(1));
		tableImage.add(tableImage.getLayeredPane());
	}
	
	public JFrame getTableFrame() {
		return tableFrame;
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
		tableImage.getLayeredPane().add(flopLabel[0], new Integer(1));
		tableImage.getLayeredPane().add(flopLabel[1], new Integer(1));
		tableImage.getLayeredPane().add(flopLabel[2], new Integer(1));
		
		flopLabel[0].setIcon(flop1);
		flopLabel[1].setIcon(flop2);
		flopLabel[2].setIcon(flop3);
		
		flopLabel[0].setVisible(true);
		flopLabel[1].setVisible(true);
		flopLabel[2].setVisible(true);
	}
	
	public void setTurnLabel(ImageIcon turn) {
		tableImage.getLayeredPane().add(turnLabel, new Integer(1));
		turnLabel.setIcon(turn);
		turnLabel.setVisible(true);
	}
	
	public void setRiverLabel(ImageIcon river) {
		tableImage.getLayeredPane().add(riverLabel, new Integer(1));
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

		tableFrame.repaint();
	}
	
	public void removeBoardCards() {
		tableImage.getLayeredPane().remove(flopLabel[0]);
		tableImage.getLayeredPane().remove(flopLabel[1]);
		tableImage.getLayeredPane().remove(flopLabel[2]);
		tableImage.getLayeredPane().remove(turnLabel);
		tableImage.getLayeredPane().remove(riverLabel);
		
		tableFrame.repaint();
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

		tableImage.getLayeredPane().add(callBtn, new Integer(1));
		callBtn.setText("Call " + (clientConnection.getTableInfo().getBet() - clientConnection.getPlayer().getCurrentBet()));
		tableImage.getLayeredPane().add(raiseBtn, new Integer(1));
		tableImage.getLayeredPane().add(betField, new Integer(1));
		betField.setText("100");
		tableImage.getLayeredPane().add(betSlider, new Integer(1));
		tableImage.getLayeredPane().add(foldBtn, new Integer(1));

		tableFrame.validate();
		tableFrame.repaint();
	}

	public void checkBetButtons() {
		messagesBox.append("Dealer: " + clientConnection.getPlayer().getName() + ", it's your turn \n");

		tableImage.getLayeredPane().add(checkBtn, new Integer(1));
		tableImage.getLayeredPane().add(betBtn, new Integer(1));
		tableImage.getLayeredPane().add(betField, new Integer(1));
		betField.setText("100");
		tableImage.getLayeredPane().add(betSlider, new Integer(1));

		tableFrame.validate();
		tableFrame.repaint();
	}
	
	public void checkRaiseButtons() {
		messagesBox.append("Dealer: " + clientConnection.getPlayer().getName() + ", it's your turn \n");
		
		tableImage.getLayeredPane().add(checkBtn, new Integer(1));
		tableImage.getLayeredPane().add(raiseBtn, new Integer(1));
		tableImage.getLayeredPane().add(betField, new Integer(1));
		betField.setText("100");
		tableImage.getLayeredPane().add(betSlider, new Integer(1));
		
		tableFrame.validate();
		tableFrame.repaint();
	}
	
	public void dimPlayerBox() {
		playerBoxLabel[0].setText("<html><span style='font-size:11px'>" + clientConnection.getPlayer().getName()
				+ "<br>" + clientConnection.getPlayer().getChips() + "</span></html>");
		playerBoxLabel[0].setBorder(BorderFactory.createLineBorder(Color.WHITE));

		playerBoxLabel[1].setBorder(BorderFactory.createLineBorder(Color.CYAN));
	}

	public void highLightPlayerBox() {
		playerBoxLabel[0].setText("<html><span style='font-size:11px'>" + clientConnection.getPlayer().getName()
				+ "<br>" + clientConnection.getPlayer().getChips() + "</span></html>");
		playerBoxLabel[0].setBorder(BorderFactory.createLineBorder(Color.CYAN));

		playerBoxLabel[1].setBorder(BorderFactory.createLineBorder(Color.WHITE));
	}
	
	public void showDown(ImageIcon opponentCard1, ImageIcon opponentCard2) {
		holeCardsLabel[0].setBounds(HOLE_CARDS_POSITION[0][0],
				HOLE_CARDS_POSITION[0][1] - 35, CARD_WIDTH, CARD_HEIGHT);
		
		holeCardsLabel[1].setBounds(HOLE_CARDS_POSITION[1][0],
				HOLE_CARDS_POSITION[1][1] - 35, CARD_WIDTH, CARD_HEIGHT);

		opponentHoleCardsLabel[0].setIcon(opponentCard1);
		opponentHoleCardsLabel[1].setIcon(opponentCard2);

		opponentHoleCardsLabel[0].setBounds(HOLE_CARDS_POSITION[2][0],
				HOLE_CARDS_POSITION[2][1] - 18, CARD_WIDTH, CARD_HEIGHT);
		
		opponentHoleCardsLabel[1].setBounds(HOLE_CARDS_POSITION[3][0],
				HOLE_CARDS_POSITION[3][1] - 18, CARD_WIDTH, CARD_HEIGHT);
	}
	
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
		// raise
		if(e.getSource() == raiseBtn) {
			int bet = Integer.parseInt(betField.getText());

			if (bet >= 200 && bet <= clientConnection.getPlayer().getChips()) {
				clientConnection.sendToServer("raise", bet);
				removeButtons();
			}
		}
		
		// bet
		if(e.getSource() == betBtn) {
			int bet = Integer.parseInt(betField.getText());

			if (bet >= 100 && bet <= clientConnection.getPlayer().getChips()) {
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
			
			tableFrame.dispose();
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

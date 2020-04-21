package com.idan.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class TableImage extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;

	private BufferedImage table;
	private TableGUI tableGUI;
	private JLayeredPane layeredPane;

	private int clicked;
	private int chips;

	public TableImage(TableGUI tableGUI) {
		this.tableGUI = tableGUI;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		layeredPane = new JLayeredPane();

		try {
			table = ImageIO.read(getClass().getResourceAsStream("/table_scaled_700_500.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		layeredPane.setPreferredSize(new Dimension(table.getWidth(), table.getHeight()));
		addMouseListener(this);
	}

	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}

	public void setPlayerChips(int chips) {
		this.chips = chips;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(table, 0, 0, null);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Tahoma", Font.BOLD, 14));

		if (tableGUI.getClientConnection().getTableInfo() != null) {
			g2.drawString("Pot: " + tableGUI.getClientConnection().getTableInfo().getPot(), 300, 150);

			for (int i = 0; i < tableGUI.getClientConnection().getTableInfo().getPlayers().size(); i++) {
				if (!tableGUI.getClientConnection().getTableInfo().getPlayers().get(i).getName().equals(tableGUI.getClientConnection().getPlayer().getName())) {
					tableGUI.getPlayerBoxLabel()[1].setText("<html><span style='font-size:11px'>" +
							tableGUI.getClientConnection().getTableInfo().getPlayers().get(i).getName() + "<br>"
									+ tableGUI.getClientConnection().getTableInfo().getPlayers().get(i).getChips() + "</span></html>");
					
					layeredPane.add(tableGUI.getPlayerBoxLabel()[1], new Integer(2));
				}
			}
		}

		if (clicked == 1) {
			tableGUI.getPlayerBoxLabel()[0].setText("<html><span style='font-size:11px'>"
					+ tableGUI.getClientConnection().getPlayer().getName() + "<br>" + chips + "</span></html>");
			tableGUI.getPlayerBoxLabel()[0].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			layeredPane.add(tableGUI.getPlayerBoxLabel()[0], new Integer(2));
			clicked = 2;

		} else if (clicked == 0) {
			g2.fillOval(315, 340, 70, 70);
			g2.setColor(Color.BLACK);
			g2.drawString("Take seat", 322, 377);

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (e.getClickCount() == 1 && x < 380 && x > 320 && y < 400 && y > 340) {
			clicked = e.getClickCount();
			repaint();

			// Initially sends the player states to the server. this includes
			// the player's name, hole-cards, any action (call, fold...) etc.
			tableGUI.getClientConnection().sendToServer(tableGUI.getClientConnection().getPlayer());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}

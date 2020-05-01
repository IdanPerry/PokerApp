package com.idan.test.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TestTableImage extends JPanel {
	private final JLayeredPane layeredPane;
	private BufferedImage table;

	/**
	 * Constructs a panel with table image backgroung.
	 */
	public TestTableImage() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		layeredPane = new JLayeredPane();

		try {
			table = ImageIO.read(getClass().getResourceAsStream("/table_scaled_700_500.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		layeredPane.setPreferredSize(new Dimension(table.getWidth(), table.getHeight()));
	}

	/**
	 * Returns the JLayeredPane of this panel.
	 * 
	 * @return the JLayeredPane of this panel
	 */
	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(table, 0, 0, null);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Tahoma", Font.BOLD, 14));

		g2.drawString("Pot: " + 555, 300, 150);
	}
}

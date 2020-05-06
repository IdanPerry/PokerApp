package com.idan.GUI;

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

import com.idan.server.TableInformation;

public class TableImage extends JPanel {
	private static final long serialVersionUID = 1L;

	private final TableWindow tableGUI;
	private final JLayeredPane layeredPane;
	private TableInformation info;
	private BufferedImage table;

	/**
	 * Constructs a panel with table image backgroung.
	 */
	public TableImage(TableWindow tableGUI) {
		this.tableGUI = tableGUI;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		layeredPane = new JLayeredPane();

		try {
			table = ImageIO.read(getClass().getResourceAsStream("/my_poker_table_small_green.png"));
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
		
		info = tableGUI.getClientConnection().getTableInfo();

		if (tableGUI.getClientConnection().getTableInfo() != null) {
			tableGUI.getPotLabel().setText("Pot: " + info.getPot());

			for (int i = 0; i < info.getPlayers().size(); i++) {
				if (!info.getPlayers().get(i).getName().equals(info.getPlayer().getName())) {
					tableGUI.getPlayerBoxLabel()[1]
							.setText("<html>" + info.getPlayers().get(i).getName() + "<br>"
									+ info.getPlayers().get(i).getChips() + "</html>");

					layeredPane.add(tableGUI.getPlayerBoxLabel()[1], 2);
					layeredPane.add(tableGUI.getPlayerBoxImg()[1], 2);
				}
			}
		}
	}
}

package com.idan.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PlayerBox extends JPanel {
	private static final long serialVersionUID = 1L;

	public PlayerBox() {
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(50, 50, 50, 50);
	}
}

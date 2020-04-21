package com.idan.game;

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CardsDeck extends Card {
	
	private static final long serialVersionUID = -759739111081498528L;
	
	private ArrayList<Card> cardsDeck;

	public CardsDeck() {
		cardsDeck = new ArrayList<Card>();
		for (int i = 0; i < RANKS.length; i++){
			for (int j = 0; j < SUITS.length; j++){
				Card card = new Card(RANKS[i], SUITS[j], i+2, j+1);
				cardsDeck.add(card);
			}
		}
		
		drawCardsImages();
	}
	
	public ArrayList<Card> getCardsDeck() {
		return cardsDeck;
	}
	
	public void drawCardsImages() {
		try {
			cardsDeck.get(0).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/deuce_s.png"))));
			cardsDeck.get(1).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/deuce_c.png"))));
			cardsDeck.get(2).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/deuce_h.png"))));
			cardsDeck.get(3).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/deuce_d.png"))));
			cardsDeck.get(4).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/trey_s.png"))));
			cardsDeck.get(5).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/trey_c.png"))));
			cardsDeck.get(6).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/trey_h.png"))));
			cardsDeck.get(7).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/trey_d.png"))));
			cardsDeck.get(8).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/four_s.png"))));
			cardsDeck.get(9).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/four_c.png"))));
			cardsDeck.get(10).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/four_h.png"))));
			cardsDeck.get(11).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/four_d.png"))));
			cardsDeck.get(12).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/five_s.png"))));
			cardsDeck.get(13).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/five_c.png"))));
			cardsDeck.get(14).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/five_h.png"))));
			cardsDeck.get(15).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/five_d.png"))));
			cardsDeck.get(16).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/six_s.png"))));
			cardsDeck.get(17).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/six_c.png"))));
			cardsDeck.get(18).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/six_h.png"))));
			cardsDeck.get(19).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/six_d.png"))));
			cardsDeck.get(20).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/seven_s.png"))));
			cardsDeck.get(21).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/seven_c.png"))));
			cardsDeck.get(22).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/seven_h.png"))));
			cardsDeck.get(23).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/seven_d.png"))));
			cardsDeck.get(24).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/eight_s.png"))));
			cardsDeck.get(25).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/eight_c.png"))));
			cardsDeck.get(26).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/eight_h.png"))));
			cardsDeck.get(27).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/eight_d.png"))));
			cardsDeck.get(28).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/nine_s.png"))));
			cardsDeck.get(29).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/nine_c.png"))));
			cardsDeck.get(30).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/nine_h.png"))));
			cardsDeck.get(31).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/nine_d.png"))));
			cardsDeck.get(32).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/t_s.png"))));
			cardsDeck.get(33).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/t_c.png"))));
			cardsDeck.get(34).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/t_h.png"))));
			cardsDeck.get(35).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/t_d.png"))));
			cardsDeck.get(36).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/j_s.png"))));
			cardsDeck.get(37).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/j_c.png"))));
			cardsDeck.get(38).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/j_h.png"))));
			cardsDeck.get(39).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/j_d.png"))));
			cardsDeck.get(40).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/q_s.png"))));
			cardsDeck.get(41).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/q_c.png"))));
			cardsDeck.get(42).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/q_h.png"))));
			cardsDeck.get(43).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/q_d.png"))));
			cardsDeck.get(44).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/k_s.png"))));
			cardsDeck.get(45).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/k_c.png"))));
			cardsDeck.get(46).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/k_h.png"))));
			cardsDeck.get(47).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/k_d.png"))));
			cardsDeck.get(48).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/a_s.png"))));
			cardsDeck.get(49).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/a_c.png"))));
			cardsDeck.get(50).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/a_h.png"))));
			cardsDeck.get(51).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/a_d.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

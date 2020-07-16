package com.idan.game;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This class represents a deck of cards.
 * 
 * @author Idan Perry
 * @version 04.05.2020
 */

public class CardsDeck implements Serializable{
	private static final long serialVersionUID = -759739111081498528L;
	public static final int MAX_CARDS = 52;
	private static final String[] CARDS_URL = {"/deuce", "/trey", "/four", "/five", "/six", "/seven", "/eight", "/nine", "/t", "/j", "/q", "/k", "/a"};
	
	private final ArrayList<Card> cardsDeck;

	/**
	 * Constructs a CardsDeck object. Initializes a deck of 52 cards.
	 */
	public CardsDeck() {
		cardsDeck = new ArrayList<Card>(MAX_CARDS);
		
		for (Card.Rank rank : Card.Rank.values()) {
			for (Card.Suit suit : Card.Suit.values()) {
				Card card = new Card(rank, suit);
				cardsDeck.add(card);
			}
		}
		
		drawCardsImages();
	}
	
	/**
	 * Returns a deck of cards.
	 * 
	 * @return a deck of cards
	 */
	public ArrayList<Card> getCardsDeck() {
		return cardsDeck;
	}
	
	/**
	 * Shuffles the cards of this deck.
	 */
	public void shuffle() {
		Collections.shuffle(cardsDeck);
	}
	
	/**
	 * Sets the card images for all cards in this deck.
	 */
	public void drawCardsImages() {
		try {
			for(int i = 0, j = 0; i < CARDS_URL.length; i++, j += 4) {
				cardsDeck.get(j).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(CARDS_URL[i] + "_s.png"))));
				cardsDeck.get(j + 1).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(CARDS_URL[i] + "_c.png"))));
				cardsDeck.get(j + 2).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(CARDS_URL[i] + "_h.png"))));
				cardsDeck.get(j + 3).setCardImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(CARDS_URL[i] + "_d.png"))));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.idan.handevaluation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.idan.test.TestCard;
import com.idan.test.TestHandEvaluation;
import com.idan.test.TestPlayer;
import com.idan.test.TestTable;
import com.idan.test.TestTexasHoldemDealer;

public class TripsEvaluation {
	TestPlayer player;
	ArrayList<TestCard> sevenCardshand;	
	TestTable table;
	TestTexasHoldemDealer dealer;
	TestHandEvaluation test;
	
	/**
	 * Constructs trips evaluation tests.
	 */
	public TripsEvaluation() {
		player =  new TestPlayer("Player");
		sevenCardshand = new ArrayList<TestCard>(7);
		test = new TestHandEvaluation(dealer, table);
		test.setHand();
	}
	
	@Test
	public void lowestTrips() {
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.QUEEN, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.JACK, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.TEN, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.TWO, TestCard.Suit.DIAMONDS));	
		sevenCardshand.add(new TestCard(TestCard.Rank.TWO, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.TWO, TestCard.Suit.HEARTS));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkTrips(player);
		assertTrue(player.isTrips());
	}
	
	@Test
	public void midTrips() {
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.QUEEN, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.NINE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.NINE, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.NINE, TestCard.Suit.DIAMONDS));	
		sevenCardshand.add(new TestCard(TestCard.Rank.THREE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.TWO, TestCard.Suit.HEARTS));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkTrips(player);
		assertTrue(player.isTrips());
	}
	
	@Test
	public void highestTrips() {
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.TEN, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.EIGHT, TestCard.Suit.DIAMONDS));	
		sevenCardshand.add(new TestCard(TestCard.Rank.SIX, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.FOUR, TestCard.Suit.SPADES));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkTrips(player);
		assertTrue(player.isTrips());
	}
}

package com.idan.junittest.handevaluation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.idan.test.TestCard;
import com.idan.test.TestHandEvaluation;
import com.idan.test.TestPlayer;
import com.idan.test.TestTable;
import com.idan.test.TestTexasHoldemDealer;

public class StraightEvaluation {
	TestPlayer player;
	ArrayList<TestCard> sevenCardshand;	
	TestTable table;
	TestTexasHoldemDealer dealer;
	TestHandEvaluation test;
	
	/**
	 * Constructs straight evaluation tests.
	 */
	public StraightEvaluation() {
		player =  new TestPlayer("Player");
		sevenCardshand = new ArrayList<TestCard>(7);
		test = new TestHandEvaluation(dealer, table);
		test.setHand();
	}
	
	@Test
	public void wheelSraight() {		
		sevenCardshand.add(new TestCard(TestCard.Rank.TWO, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.THREE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.FOUR, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.FIVE, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.EIGHT, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.NINE, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.CLUBS));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkStraight(player);
		assertTrue(player.isStraight());
	}
	
	@Test
	public void midSraight() {		
		sevenCardshand.add(new TestCard(TestCard.Rank.THREE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.FIVE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.SIX, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.SEVEN, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.EIGHT, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.NINE, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.JACK, TestCard.Suit.CLUBS));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkStraight(player);
		assertTrue(player.isStraight());
	}
	
	@Test
	public void highestSraight() {		
		sevenCardshand.add(new TestCard(TestCard.Rank.THREE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.FIVE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.TEN, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.JACK, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.QUEEN, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.KING, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.CLUBS));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkStraight(player);
		assertTrue(player.isStraight());
	}
}

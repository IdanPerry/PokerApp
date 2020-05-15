package com.idan.junittest.handevaluation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.idan.test.TestCard;
import com.idan.test.TestHandEvaluation;
import com.idan.test.TestPlayer;
import com.idan.test.TestTable;
import com.idan.test.TestTexasHoldemDealer;

public class FlushEvaluation {
	TestPlayer player;
	ArrayList<TestCard> sevenCardshand;	
	TestTable table;
	TestTexasHoldemDealer dealer;
	TestHandEvaluation test;
	
	/**
	 * Constructs flush evaluation tests.
	 */
	public FlushEvaluation() {
		player =  new TestPlayer("Player");
		sevenCardshand = new ArrayList<TestCard>(7);
		test = new TestHandEvaluation(dealer, table);
		test.setHand();
	}
	
	@Test
	public void midFlush() {		
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.KING, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.TEN, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.NINE, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.SEVEN, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.SEVEN, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.FIVE, TestCard.Suit.DIAMONDS));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkFlush(player);
		assertTrue(player.isFlush());
	}
	
	@Test
	public void highestFlush() {		
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.KING, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.TEN, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.NINE, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.SEVEN, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.SEVEN, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.FIVE, TestCard.Suit.CLUBS));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkFlush(player);
		assertTrue(player.isFlush());
	}
}

package com.idan.junittest.handevaluation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.idan.test.TestCard;
import com.idan.test.TestHandEvaluation;
import com.idan.test.TestPlayer;
import com.idan.test.TestTable;
import com.idan.test.TestTexasHoldemDealer;

public class FullHouseEvaluation {
	TestPlayer player;
	ArrayList<TestCard> sevenCardshand;	
	TestTable table;
	TestTexasHoldemDealer dealer;
	TestHandEvaluation test;
	
	/**
	 * Constructs hand evaluation tests.
	 */
	public FullHouseEvaluation() {
		player =  new TestPlayer("Player");
		sevenCardshand = new ArrayList<TestCard>(7);
		test = new TestHandEvaluation(dealer, table);
		test.setHand();
	}
	
	@Test
	public void testFullHouse() {		
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.KING, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.EIGHT, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.EIGHT, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.SIX, TestCard.Suit.DIAMONDS));	
		sevenCardshand.add(new TestCard(TestCard.Rank.SIX, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.SIX, TestCard.Suit.HEARTS));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkFullHouse(player);
		assertTrue(player.isFullHouse());
	}
}

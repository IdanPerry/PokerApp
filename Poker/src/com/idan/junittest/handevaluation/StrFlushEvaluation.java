package com.idan.junittest.handevaluation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.idan.test.TestCard;
import com.idan.test.TestHandEvaluation;
import com.idan.test.TestPlayer;
import com.idan.test.TestTable;
import com.idan.test.TestTexasHoldemDealer;

public class StrFlushEvaluation {
	TestPlayer player;
	ArrayList<TestCard> sevenCardshand;	
	TestTable table;
	TestTexasHoldemDealer dealer;
	TestHandEvaluation test;
	
	/**
	 * Constructs hand evaluation tests.
	 */
	public StrFlushEvaluation() {
		player =  new TestPlayer("Player");
		sevenCardshand = new ArrayList<TestCard>(7);
		test = new TestHandEvaluation(dealer, table);
		test.setHand();
	}
	
	@Test
	public void royalFlush() {		
		sevenCardshand.add(new TestCard(TestCard.Rank.FOUR, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.FIVE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.TEN, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.JACK, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.QUEEN, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.KING, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.CLUBS));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkStrFlush(player);
		assertTrue(player.isStrFlush());
	}
}

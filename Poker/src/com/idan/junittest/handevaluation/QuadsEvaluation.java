package com.idan.junittest.handevaluation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.idan.test.TestCard;
import com.idan.test.TestHandEvaluation;
import com.idan.test.TestPlayer;
import com.idan.test.TestTable;
import com.idan.test.TestTexasHoldemDealer;

public class QuadsEvaluation {
	TestPlayer player;
	ArrayList<TestCard> sevenCardshand;	
	TestTable table;
	TestTexasHoldemDealer dealer;
	TestHandEvaluation test;
	
	/**
	 * Constructs quads evaluation tests.
	 */
	public QuadsEvaluation() {
		player =  new TestPlayer("Player");
		sevenCardshand = new ArrayList<TestCard>(7);
		test = new TestHandEvaluation(dealer, table);
		test.setHand();
	}
	
	@Test
	public void highestQuads() {
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.HEARTS));
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.EIGHT, TestCard.Suit.DIAMONDS));	
		sevenCardshand.add(new TestCard(TestCard.Rank.EIGHT, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.FOUR, TestCard.Suit.SPADES));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkQuads(player);
		assertTrue(player.isQuads());
	}
}

package com.idan.handevaluation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.idan.test.TestCard;
import com.idan.test.TestHandEvaluation;
import com.idan.test.TestPlayer;
import com.idan.test.TestTable;
import com.idan.test.TestTexasHoldemDealer;

public class HighCardEvaluation {
	TestPlayer player;
	ArrayList<TestCard> sevenCardshand;	
	TestTable table;
	TestTexasHoldemDealer dealer;
	TestHandEvaluation test;
	
	/**
	 * Constructs high card evaluation tests.
	 */
	public HighCardEvaluation() {
		player =  new TestPlayer("Player");
		sevenCardshand = new ArrayList<TestCard>(7);
		test = new TestHandEvaluation(dealer, table);
		test.setHand();
	}
	
	@Test
	public void highCard() {
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.ACE, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.KING, TestCard.Suit.DIAMONDS));
		sevenCardshand.add(new TestCard(TestCard.Rank.EIGHT, TestCard.Suit.CLUBS));
		sevenCardshand.add(new TestCard(TestCard.Rank.SEVEN, TestCard.Suit.DIAMONDS));	
		sevenCardshand.add(new TestCard(TestCard.Rank.SIX, TestCard.Suit.SPADES));
		sevenCardshand.add(new TestCard(TestCard.Rank.FOUR, TestCard.Suit.SPADES));
		
		player.setSevenCardsTempHand(sevenCardshand);
		test.checkHighCard(player);
		//assertTrue(player.isHighCard());
	}
}

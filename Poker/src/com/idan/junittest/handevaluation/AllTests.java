package com.idan.junittest.handevaluation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ FlushEvaluation.class, FullHouseEvaluation.class, HighCardEvaluation.class, PairEvaluation.class,
		QuadsEvaluation.class, StraightEvaluation.class, StrFlushEvaluation.class, TripsEvaluation.class,
		TwoPairsEvaluation.class })
public class AllTests {
	/*
	 * All tests here isolates the hand evaluation class methods,
	 * which evaluates the player's hand rank.
	 */
}

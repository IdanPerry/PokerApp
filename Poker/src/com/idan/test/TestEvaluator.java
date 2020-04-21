package com.idan.test;

import java.util.ArrayList;

public class TestEvaluator extends TestHandEvaluation {
	
	private ArrayList<TestPlayer> bestHands;
	private int max;
	
	public TestEvaluator(TestDealer dealer, TestTable table) {
		super(dealer, table);
	}

	private void loop(int card) {
		for (int i = 0; i < bestHands.size(); i++) {
			if (bestHands.get(i).getHand()[card].getRankValue() > max) {
				max = bestHands.get(i).getHand()[card].getRankValue();
			}
		}
		
		for (int j = 0; j < bestHands.size(); j++) {
			if (bestHands.get(j).getHand()[card].getRankValue() < max) {
				bestHands.remove(j);
			}
		}
	}
	
	private void print() {
		System.out.println(bestHands.get(0).getName() + " wins with " + TestPlayer.HAND_RANK[bestHands.get(0).getHandValue()]
				+ " " + bestHands.get(0).getHand()[0].getRank() + bestHands.get(0).getHand()[0].getSuit() + 
				bestHands.get(0).getHand()[1].getRank() + bestHands.get(0).getHand()[1].getSuit() + 
				bestHands.get(0).getHand()[2].getRank() + bestHands.get(0).getHand()[2].getSuit() + 
				bestHands.get(0).getHand()[3].getRank() + bestHands.get(0).getHand()[3].getSuit() + 
				bestHands.get(0).getHand()[4].getRank() + bestHands.get(0).getHand()[4].getSuit() + "\n");
		
		bestHands.get(0).setScore(1);
	}
	
	private void compHighCard() {
		max = bestHands.get(0).getHand()[0].getRankValue();

		// Checking the possibility of more than 2 players having
		// the same high card,
		// in which case - checking the next high card and so on
		loop(0);
		
		if (bestHands.size() >= 2) {
			// Checking the 2nd high card
			max = bestHands.get(0).getHand()[1].getRankValue();
			loop(1);
			
			if (bestHands.size() >= 2) {
				// Checking the 3rd high card
				max = bestHands.get(0).getHand()[2].getRankValue();
				loop(2);
				
				if (bestHands.size() >= 2) {
					// Checking the 4th high card
					max = bestHands.get(0).getHand()[3].getRankValue();
					loop(3);
					
					if (bestHands.size() >= 2) {
						// Checking the 5th high card
						max = bestHands.get(0).getHand()[4].getRankValue();
						loop(4);
						
						if (bestHands.size() >= 2) {
							// split pot
							System.out.println("Players win with " + TestPlayer.HAND_RANK[bestHands.get(0).getHandValue()]
									+ bestHands.get(0).getHand()[0].getRank() + bestHands.get(0).getHand()[0].getSuit() + 
									bestHands.get(0).getHand()[1].getRank() + bestHands.get(0).getHand()[1].getSuit() + 
									bestHands.get(0).getHand()[2].getRank() + bestHands.get(0).getHand()[2].getSuit() + 
									bestHands.get(0).getHand()[3].getRank() + bestHands.get(0).getHand()[3].getSuit() + 
									bestHands.get(0).getHand()[4].getRank() + bestHands.get(0).getHand()[4].getSuit());
							
						} else if (bestHands.size() == 1) {
							// There's a winner with 5th kicker
							print();
						}
						
					} else if (bestHands.size() == 1) {
						// There's a winner with 4th kicker
						print();
					}
					
				} else if (bestHands.size() == 1) {
					// There's a winner with 3rd kicker
					print();
				}
				
			} else if (bestHands.size() == 1) {
				// There's a winner with 2nd kicker
				print();
			}
			
		} else if (bestHands.size() == 1) {
			// there's a winner with 1st kicker
			print();
			
		}
	}
	
	private void compPair() {
		max = bestHands.get(0).getHand()[0].getRankValue();

		// Checking the possibility of more than 2 players having
		// the same pair,
		// in which case - checking high card
		loop(0);
		
		if (bestHands.size() >= 2) {
			// Checking 1st kicker
			max = bestHands.get(0).getHand()[2].getRankValue();	
			loop(2);
			
			if (bestHands.size() >= 2) {
				// Checking the 2nd kicker
				max = bestHands.get(0).getHand()[3].getRankValue();
				loop(3);
				
				if (bestHands.size() >= 2) {
					// Checking the 3rd kicker
					max = bestHands.get(0).getHand()[4].getRankValue();
					loop(4);
					
					if (bestHands.size() >= 2) {
						// split pot
						System.out.println("Players win with " + TestPlayer.HAND_RANK[bestHands.get(0).getHandValue()]
								+ bestHands.get(0).getHand()[0].getRank() + bestHands.get(0).getHand()[0].getSuit() + 
								bestHands.get(0).getHand()[1].getRank() + bestHands.get(0).getHand()[1].getSuit() + 
								bestHands.get(0).getHand()[2].getRank() + bestHands.get(0).getHand()[2].getSuit() + 
								bestHands.get(0).getHand()[3].getRank() + bestHands.get(0).getHand()[3].getSuit() + 
								bestHands.get(0).getHand()[4].getRank() + bestHands.get(0).getHand()[4].getSuit());
						
					} else if (bestHands.size() == 1) {
						// There's a winner with 3rd kicker
						print();
					}
					
				} else if (bestHands.size() == 1) {
					// There's a winner with 2nd kicker
					print();
				}
				
			} else if (bestHands.size() == 1) {
				// There's a winner with 1st kicker
				print();
			}
			
		} else if (bestHands.size() == 1) {
			// there's a winner with a pair
			print();
			
		}
	}

	private void compTwoPairs() {
		max = bestHands.get(0).getHand()[0].getRankValue();

		// Checking the possibility of more than 2 players having
		// the same 2 pairs,
		// in which case - checking the 1st pair
		loop(0);
		
		if (bestHands.size() >= 2) {
			// Checking 2nd pair
			max = bestHands.get(0).getHand()[2].getRankValue();	
			loop(2);
			
			if (bestHands.size() >= 2) {
				// Checking the kicker
				max = bestHands.get(0).getHand()[4].getRankValue();
				loop(4);
					
					if (bestHands.size() >= 2) {
						// split pot
						System.out.println("Players win with " + TestPlayer.HAND_RANK[bestHands.get(0).getHandValue()]
								+ bestHands.get(0).getHand()[0].getRank() + bestHands.get(0).getHand()[0].getSuit() + 
								bestHands.get(0).getHand()[1].getRank() + bestHands.get(0).getHand()[1].getSuit() + 
								bestHands.get(0).getHand()[2].getRank() + bestHands.get(0).getHand()[2].getSuit() + 
								bestHands.get(0).getHand()[3].getRank() + bestHands.get(0).getHand()[3].getSuit() + 
								bestHands.get(0).getHand()[4].getRank() + bestHands.get(0).getHand()[4].getSuit());
						
					} else if (bestHands.size() == 1) {
						// There's a winner with kicker
						print();
					}
				
			} else if (bestHands.size() == 1) {
				// There's a winner with 2nd pair
				print();
			}
			
		} else if (bestHands.size() == 1) {
			// there's a winner with 1st pair
			print();
			
		}
	}
	
	private void compTrips() {
		max = bestHands.get(0).getHand()[0].getRankValue();

		// Checking the possibility of more than 2 players having
		// the same trips,
		// in which case - checking the kickers
		loop(0);
		
		if (bestHands.size() >= 2) {
			// Checking 1st kicker
			max = bestHands.get(0).getHand()[3].getRankValue();	
			loop(3);
			
			if (bestHands.size() >= 2) {
				// Checking the 2nd kicker
				max = bestHands.get(0).getHand()[4].getRankValue();
				loop(4);
					
					if (bestHands.size() >= 2) {
						// split pot
						System.out.println("Players win with " + TestPlayer.HAND_RANK[bestHands.get(0).getHandValue()]
								+ bestHands.get(0).getHand()[0].getRank() + bestHands.get(0).getHand()[0].getSuit() + 
								bestHands.get(0).getHand()[1].getRank() + bestHands.get(0).getHand()[1].getSuit() + 
								bestHands.get(0).getHand()[2].getRank() + bestHands.get(0).getHand()[2].getSuit() + 
								bestHands.get(0).getHand()[3].getRank() + bestHands.get(0).getHand()[3].getSuit() + 
								bestHands.get(0).getHand()[4].getRank() + bestHands.get(0).getHand()[4].getSuit());
						
					} else if (bestHands.size() == 1) {
						// There's a winner with 2nd kicker
						print();
					}
				
			} else if (bestHands.size() == 1) {
				// There's a winner with 1st kicker
				print();
			}
			
		} else if (bestHands.size() == 1) {
			// there's a winner with trips
			print();
			
		}
	}
	
	private void compStraight() {
		max = bestHands.get(0).getHand()[0].getRankValue();

		// Checking the possibility of more than 2 players having
		// the same straight,
		// in which case - checking the highest card
		loop(0);

		if (bestHands.size() >= 2) {
			// Checking the highest card
			max = bestHands.get(0).getHand()[0].getRankValue();
			loop(0);

			if (bestHands.size() >= 2) {
				// split pot
				System.out.println("Players win with " + TestPlayer.HAND_RANK[bestHands.get(0).getHandValue()]
						+ bestHands.get(0).getHand()[0].getRank() + bestHands.get(0).getHand()[0].getSuit()
						+ bestHands.get(0).getHand()[1].getRank() + bestHands.get(0).getHand()[1].getSuit()
						+ bestHands.get(0).getHand()[2].getRank() + bestHands.get(0).getHand()[2].getSuit()
						+ bestHands.get(0).getHand()[3].getRank() + bestHands.get(0).getHand()[3].getSuit()
						+ bestHands.get(0).getHand()[4].getRank() + bestHands.get(0).getHand()[4].getSuit());
			}

		} else if (bestHands.size() == 1) {
			// There's a winner with highest card straight
			print();
		}
	}
	
	private void compFlush() {
		max = bestHands.get(0).getHand()[4].getRankValue();

		// Checking the possibility of more than 2 players having
		// the same flush,
		// in which case - checking the highest card
		loop(4);

		if (bestHands.size() >= 2) {
			// Checking the highest card
			max = bestHands.get(0).getHand()[4].getRankValue();
			loop(4);

			if (bestHands.size() >= 2) {
				// split pot
				System.out.println("Players win with " + TestPlayer.HAND_RANK[bestHands.get(0).getHandValue()]
						+ bestHands.get(0).getHand()[0].getRank() + bestHands.get(0).getHand()[0].getSuit()
						+ bestHands.get(0).getHand()[1].getRank() + bestHands.get(0).getHand()[1].getSuit()
						+ bestHands.get(0).getHand()[2].getRank() + bestHands.get(0).getHand()[2].getSuit()
						+ bestHands.get(0).getHand()[3].getRank() + bestHands.get(0).getHand()[3].getSuit()
						+ bestHands.get(0).getHand()[4].getRank() + bestHands.get(0).getHand()[4].getSuit());
			}

		} else if (bestHands.size() == 1) {
			// There's a winner with highest card flush
			print();
		}
	}
	
	private void compFull() {
		max = bestHands.get(0).getHand()[0].getRankValue();

		// Checking the possibility of more than 2 players having
		// the same full-house,
		// in which case - checking the trips
		loop(0);

		if (bestHands.size() >= 2) {
			// Checking the pair
			max = bestHands.get(0).getHand()[3].getRankValue();
			loop(3);

			if (bestHands.size() >= 2) {
				// split pot
				System.out.println("Players win with " + TestPlayer.HAND_RANK[bestHands.get(0).getHandValue()]
						+ bestHands.get(0).getHand()[0].getRank() + bestHands.get(0).getHand()[0].getSuit()
						+ bestHands.get(0).getHand()[1].getRank() + bestHands.get(0).getHand()[1].getSuit()
						+ bestHands.get(0).getHand()[2].getRank() + bestHands.get(0).getHand()[2].getSuit()
						+ bestHands.get(0).getHand()[3].getRank() + bestHands.get(0).getHand()[3].getSuit()
						+ bestHands.get(0).getHand()[4].getRank() + bestHands.get(0).getHand()[4].getSuit());

			} else if (bestHands.size() == 1) {
				// There's a winner with pair
			}

		} else if (bestHands.size() == 1) {
			// there's a winner with trips
			print();

		}
	}
	
	private void compQuads() {
		max = bestHands.get(0).getHand()[0].getRankValue();

		// Checking the possibility of more than 2 players having
		// quads,
		// in which case - checking the kicker
		loop(0);

		if (bestHands.size() >= 2) {
			// Checking the kicker
			max = bestHands.get(0).getHand()[4].getRankValue();
			loop(4);

			if (bestHands.size() >= 2) {
				// split pot
				System.out.println("Players win with " + TestPlayer.HAND_RANK[bestHands.get(0).getHandValue()]
						+ bestHands.get(0).getHand()[0].getRank() + bestHands.get(0).getHand()[0].getSuit()
						+ bestHands.get(0).getHand()[1].getRank() + bestHands.get(0).getHand()[1].getSuit()
						+ bestHands.get(0).getHand()[2].getRank() + bestHands.get(0).getHand()[2].getSuit()
						+ bestHands.get(0).getHand()[3].getRank() + bestHands.get(0).getHand()[3].getSuit()
						+ bestHands.get(0).getHand()[4].getRank() + bestHands.get(0).getHand()[4].getSuit());

			} else if (bestHands.size() == 1) {
				// There's a winner with kicker
				print();
			}

		} else if (bestHands.size() == 1) {
			// there's a winner with quads
			print();

		}
	}
	
	private void compStrFlush() {
		max = bestHands.get(0).getHand()[0].getRankValue();

		// Checking the possibility of more than 2 players having
		// straight-flush,
		// in which case - checking the highest card
		loop(0);

		if (bestHands.size() >= 2) {
			// Checking the highest card
			max = bestHands.get(0).getHand()[0].getRankValue();
			loop(0);

			if (bestHands.size() >= 2) {
				// split pot
				System.out.println("Players win with " + TestPlayer.HAND_RANK[bestHands.get(0).getHandValue()]
						+ bestHands.get(0).getHand()[0].getRank() + bestHands.get(0).getHand()[0].getSuit()
						+ bestHands.get(0).getHand()[1].getRank() + bestHands.get(0).getHand()[1].getSuit()
						+ bestHands.get(0).getHand()[2].getRank() + bestHands.get(0).getHand()[2].getSuit()
						+ bestHands.get(0).getHand()[3].getRank() + bestHands.get(0).getHand()[3].getSuit()
						+ bestHands.get(0).getHand()[4].getRank() + bestHands.get(0).getHand()[4].getSuit());
			}

		} else if (bestHands.size() == 1) {
			// There's a winner with highest card straight-flush
			print();
		}
	}
	
	public void showDown() {
		bestHands = new ArrayList<TestPlayer>();
		max = table.getPlayers().get(0).getHandValue();

		// Iterating through playerList array to find the best ranked hands
		for (int i = 0; i < table.getPlayers().size(); i++) {
			if (table.getPlayers().get(i).getHandValue() > max) {
				max = table.getPlayers().get(i).getHandValue();
			}
		}

		// Iterating through playerList again to find what hand is currently the
		// strongest
		// and how many players are holding this hand
		for (int i = 0; i < table.getPlayers().size(); i++) {

			// If the players in the list have the same hand rank (a pair for
			// example),
			// than check the kickers
			if (table.getPlayers().get(i).getHandValue() == max) {
				bestHands.add(table.getPlayers().get(i));
			}
		}

		if (bestHands.size() >= 2) {
			switch (bestHands.get(0).getHandValue()) {

			// If the best hand is high card
			case 0:
				compHighCard();
				break;
				
			// If the best hand is 1 pair	
			case 1:
				compPair();
				break;
				
			// If the best hand is 2 pairs	
			case 2:
				compTwoPairs();
				break;
				
			// If the best hand is trips
			case 3:
				compTrips();
				break;
				
			// If the best hand is straight	
			case 4:
				compStraight();
				break;
				
			// If the best hand is flush	
			case 5:
				compFlush();
				break;
				
			// If the best hand is full-house	
			case 6:
				compFull();
				break;
				
			// If the best hand is quads	
			case 7:
				compQuads();
				break;
				
			// If the best hand is straight-flush	
			case 8:
				compStrFlush();
				break;
			}
			
		} else if (bestHands.size() == 1) {
			// ther's a winner
			print();
		}
	}
}

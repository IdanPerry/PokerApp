package com.idan.texasholdem;

import java.util.ArrayList;

import com.idan.game.Player;
import com.idan.game.Table;

public class HandComparison {	
	private final Table table;
	private ArrayList<Player> bestHands;
	private int max;
	
	public HandComparison(Table table) {
		this.table = table;
	}

	private void loop(int card) {
		for (int i = 0; i < bestHands.size(); i++) {
			if (bestHands.get(i).getFiveCardsHand()[card].getRank().getValue() > max) {
				max = bestHands.get(i).getFiveCardsHand()[card].getRank().getValue();
			}
		}
		
		for (int j = 0; j < bestHands.size(); j++) {
			if (bestHands.get(j).getFiveCardsHand()[card].getRank().getValue() < max) {
				bestHands.remove(j);
			}
		}
	}
	
	private void printWinningHand() {
		for (int i = 0; i < table.getPlayersInHand().size(); i++) {
			if (table.getPlayersInHand().get(i).getName().equals(bestHands.get(0).getName())) {
				table.getPlayersInHand().get(i).setWin(true);
			}
		}
		
		System.out.println(bestHands.get(0).getName() + " wins with " + Player.HAND_RANK[bestHands.get(0).getHandValue()]
				+ " " + bestHands.get(0).getFiveCardsHand()[0].getRank() + bestHands.get(0).getFiveCardsHand()[0].getSuit() + 
				bestHands.get(0).getFiveCardsHand()[1].getRank() + bestHands.get(0).getFiveCardsHand()[1].getSuit() + 
				bestHands.get(0).getFiveCardsHand()[2].getRank() + bestHands.get(0).getFiveCardsHand()[2].getSuit() + 
				bestHands.get(0).getFiveCardsHand()[3].getRank() + bestHands.get(0).getFiveCardsHand()[3].getSuit() + 
				bestHands.get(0).getFiveCardsHand()[4].getRank() + bestHands.get(0).getFiveCardsHand()[4].getSuit() + "\n");
		
		bestHands.get(0).setScore(1);
	}
	
	private void compHighCard() {
		max = bestHands.get(0).getFiveCardsHand()[0].getRank().getValue();

		// Checking the possibility of more than 2 players having
		// the same high card,
		// in which case - checking the next high card and so on
		loop(0);
		
		if (bestHands.size() >= 2) {
			// Checking the 2nd high card
			max = bestHands.get(0).getFiveCardsHand()[1].getRank().getValue();
			loop(1);
			
			if (bestHands.size() >= 2) {
				// Checking the 3rd high card
				max = bestHands.get(0).getFiveCardsHand()[2].getRank().getValue();
				loop(2);
				
				if (bestHands.size() >= 2) {
					// Checking the 4th high card
					max = bestHands.get(0).getFiveCardsHand()[3].getRank().getValue();
					loop(3);
					
					if (bestHands.size() >= 2) {
						// Checking the 5th high card
						max = bestHands.get(0).getFiveCardsHand()[4].getRank().getValue();
						loop(4);
						
						if (bestHands.size() >= 2) {
							// split pot
							System.out.println("Players win with " + Player.HAND_RANK[bestHands.get(0).getHandValue()]
									+ bestHands.get(0).getFiveCardsHand()[0].getRank() + bestHands.get(0).getFiveCardsHand()[0].getSuit() + 
									bestHands.get(0).getFiveCardsHand()[1].getRank() + bestHands.get(0).getFiveCardsHand()[1].getSuit() + 
									bestHands.get(0).getFiveCardsHand()[2].getRank() + bestHands.get(0).getFiveCardsHand()[2].getSuit() + 
									bestHands.get(0).getFiveCardsHand()[3].getRank() + bestHands.get(0).getFiveCardsHand()[3].getSuit() + 
									bestHands.get(0).getFiveCardsHand()[4].getRank() + bestHands.get(0).getFiveCardsHand()[4].getSuit());
							
						} else if (bestHands.size() == 1) {
							// There's a winner with 5th kicker
							printWinningHand();
						}
						
					} else if (bestHands.size() == 1) {
						// There's a winner with 4th kicker
						printWinningHand();
					}
					
				} else if (bestHands.size() == 1) {
					// There's a winner with 3rd kicker
					printWinningHand();
				}
				
			} else if (bestHands.size() == 1) {
				// There's a winner with 2nd kicker
				printWinningHand();
			}
			
		} else if (bestHands.size() == 1) {
			// there's a winner with 1st kicker
			printWinningHand();
			
		}
	}
	
	private void compPair() {
		max = bestHands.get(0).getFiveCardsHand()[0].getRank().getValue();

		// Checking the possibility of more than 2 players having
		// the same pair,
		// in which case - checking high card
		loop(0);
		
		if (bestHands.size() >= 2) {
			// Checking 1st kicker
			max = bestHands.get(0).getFiveCardsHand()[2].getRank().getValue();	
			loop(2);
			
			if (bestHands.size() >= 2) {
				// Checking the 2nd kicker
				max = bestHands.get(0).getFiveCardsHand()[3].getRank().getValue();
				loop(3);
				
				if (bestHands.size() >= 2) {
					// Checking the 3rd kicker
					max = bestHands.get(0).getFiveCardsHand()[4].getRank().getValue();
					loop(4);
					
					if (bestHands.size() >= 2) {
						// split pot
						System.out.println("Players win with " + Player.HAND_RANK[bestHands.get(0).getHandValue()]
								+ bestHands.get(0).getFiveCardsHand()[0].getRank() + bestHands.get(0).getFiveCardsHand()[0].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[1].getRank() + bestHands.get(0).getFiveCardsHand()[1].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[2].getRank() + bestHands.get(0).getFiveCardsHand()[2].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[3].getRank() + bestHands.get(0).getFiveCardsHand()[3].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[4].getRank() + bestHands.get(0).getFiveCardsHand()[4].getSuit());
						
					} else if (bestHands.size() == 1) {
						// There's a winner with 3rd kicker
						printWinningHand();
					}
					
				} else if (bestHands.size() == 1) {
					// There's a winner with 2nd kicker
					printWinningHand();
				}
				
			} else if (bestHands.size() == 1) {
				// There's a winner with 1st kicker
				printWinningHand();
			}
			
		} else if (bestHands.size() == 1) {
			// there's a winner with a pair
			printWinningHand();
			
		}
	}

	private void compTwoPairs() {
		max = bestHands.get(0).getFiveCardsHand()[0].getRank().getValue();

		// Checking the possibility of more than 2 players having
		// the same 2 pairs,
		// in which case - checking the 1st pair
		loop(0);
		
		if (bestHands.size() >= 2) {
			// Checking 2nd pair
			max = bestHands.get(0).getFiveCardsHand()[2].getRank().getValue();	
			loop(2);
			
			if (bestHands.size() >= 2) {
				// Checking the kicker
				max = bestHands.get(0).getFiveCardsHand()[4].getRank().getValue();
				loop(4);
					
					if (bestHands.size() >= 2) {
						// split pot
						System.out.println("Players win with " + Player.HAND_RANK[bestHands.get(0).getHandValue()]
								+ bestHands.get(0).getFiveCardsHand()[0].getRank() + bestHands.get(0).getFiveCardsHand()[0].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[1].getRank() + bestHands.get(0).getFiveCardsHand()[1].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[2].getRank() + bestHands.get(0).getFiveCardsHand()[2].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[3].getRank() + bestHands.get(0).getFiveCardsHand()[3].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[4].getRank() + bestHands.get(0).getFiveCardsHand()[4].getSuit());
						
					} else if (bestHands.size() == 1) {
						// There's a winner with kicker
						printWinningHand();
					}
				
			} else if (bestHands.size() == 1) {
				// There's a winner with 2nd pair
				printWinningHand();
			}
			
		} else if (bestHands.size() == 1) {
			// there's a winner with 1st pair
			printWinningHand();
			
		}
	}
	
	private void compTrips() {
		max = bestHands.get(0).getFiveCardsHand()[0].getRank().getValue();

		// Checking the possibility of more than 2 players having
		// the same trips,
		// in which case - checking the kickers
		loop(0);
		
		if (bestHands.size() >= 2) {
			// Checking 1st kicker
			max = bestHands.get(0).getFiveCardsHand()[3].getRank().getValue();	
			loop(3);
			
			if (bestHands.size() >= 2) {
				// Checking the 2nd kicker
				max = bestHands.get(0).getFiveCardsHand()[4].getRank().getValue();
				loop(4);
					
					if (bestHands.size() >= 2) {
						// split pot
						System.out.println("Players win with " + Player.HAND_RANK[bestHands.get(0).getHandValue()]
								+ bestHands.get(0).getFiveCardsHand()[0].getRank() + bestHands.get(0).getFiveCardsHand()[0].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[1].getRank() + bestHands.get(0).getFiveCardsHand()[1].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[2].getRank() + bestHands.get(0).getFiveCardsHand()[2].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[3].getRank() + bestHands.get(0).getFiveCardsHand()[3].getSuit() + 
								bestHands.get(0).getFiveCardsHand()[4].getRank() + bestHands.get(0).getFiveCardsHand()[4].getSuit());
						
					} else if (bestHands.size() == 1) {
						// There's a winner with 2nd kicker
						printWinningHand();
					}
				
			} else if (bestHands.size() == 1) {
				// There's a winner with 1st kicker
				printWinningHand();
			}
			
		} else if (bestHands.size() == 1) {
			// there's a winner with trips
			printWinningHand();
			
		}
	}
	
	private void compStraight() {
		max = bestHands.get(0).getFiveCardsHand()[0].getRank().getValue();

		// Checking the possibility of more than 2 players having
		// the same straight,
		// in which case - checking the highest card
		loop(0);

		if (bestHands.size() >= 2) {
			// Checking the highest card
			max = bestHands.get(0).getFiveCardsHand()[0].getRank().getValue();
			loop(0);

			if (bestHands.size() >= 2) {
				// split pot
				System.out.println("Players win with " + Player.HAND_RANK[bestHands.get(0).getHandValue()]
						+ bestHands.get(0).getFiveCardsHand()[0].getRank() + bestHands.get(0).getFiveCardsHand()[0].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[1].getRank() + bestHands.get(0).getFiveCardsHand()[1].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[2].getRank() + bestHands.get(0).getFiveCardsHand()[2].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[3].getRank() + bestHands.get(0).getFiveCardsHand()[3].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[4].getRank() + bestHands.get(0).getFiveCardsHand()[4].getSuit());
			}

		} else if (bestHands.size() == 1) {
			// There's a winner with highest card straight
			printWinningHand();
		}
	}
	
	private void compFlush() {
		max = bestHands.get(0).getFiveCardsHand()[4].getRank().getValue();

		// Checking the possibility of more than 2 players having
		// the same flush,
		// in which case - checking the highest card
		loop(4);

		if (bestHands.size() >= 2) {
			// Checking the highest card
			max = bestHands.get(0).getFiveCardsHand()[4].getRank().getValue();
			loop(4);

			if (bestHands.size() >= 2) {
				// split pot
				System.out.println("Players win with " + Player.HAND_RANK[bestHands.get(0).getHandValue()]
						+ bestHands.get(0).getFiveCardsHand()[0].getRank() + bestHands.get(0).getFiveCardsHand()[0].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[1].getRank() + bestHands.get(0).getFiveCardsHand()[1].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[2].getRank() + bestHands.get(0).getFiveCardsHand()[2].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[3].getRank() + bestHands.get(0).getFiveCardsHand()[3].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[4].getRank() + bestHands.get(0).getFiveCardsHand()[4].getSuit());
			}

		} else if (bestHands.size() == 1) {
			// There's a winner with highest card flush
			printWinningHand();
		}
	}
	
	private void compFull() {
		max = bestHands.get(0).getFiveCardsHand()[0].getRank().getValue();

		// Checking the possibility of more than 2 players having
		// the same full-house,
		// in which case - checking the trips
		loop(0);

		if (bestHands.size() >= 2) {
			// Checking the pair
			max = bestHands.get(0).getFiveCardsHand()[3].getRank().getValue();
			loop(3);

			if (bestHands.size() >= 2) {
				// split pot
				System.out.println("Players win with " + Player.HAND_RANK[bestHands.get(0).getHandValue()]
						+ bestHands.get(0).getFiveCardsHand()[0].getRank() + bestHands.get(0).getFiveCardsHand()[0].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[1].getRank() + bestHands.get(0).getFiveCardsHand()[1].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[2].getRank() + bestHands.get(0).getFiveCardsHand()[2].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[3].getRank() + bestHands.get(0).getFiveCardsHand()[3].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[4].getRank() + bestHands.get(0).getFiveCardsHand()[4].getSuit());

			} else if (bestHands.size() == 1) {
				// There's a winner with pair
			}

		} else if (bestHands.size() == 1) {
			// there's a winner with trips
			printWinningHand();

		}
	}
	
	private void compQuads() {
		max = bestHands.get(0).getFiveCardsHand()[0].getRank().getValue();

		// Checking the possibility of more than 2 players having
		// quads,
		// in which case - checking the kicker
		loop(0);

		if (bestHands.size() >= 2) {
			// Checking the kicker
			max = bestHands.get(0).getFiveCardsHand()[4].getRank().getValue();
			loop(4);

			if (bestHands.size() >= 2) {
				// split pot
				System.out.println("Players win with " + Player.HAND_RANK[bestHands.get(0).getHandValue()]
						+ bestHands.get(0).getFiveCardsHand()[0].getRank() + bestHands.get(0).getFiveCardsHand()[0].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[1].getRank() + bestHands.get(0).getFiveCardsHand()[1].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[2].getRank() + bestHands.get(0).getFiveCardsHand()[2].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[3].getRank() + bestHands.get(0).getFiveCardsHand()[3].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[4].getRank() + bestHands.get(0).getFiveCardsHand()[4].getSuit());

			} else if (bestHands.size() == 1) {
				// There's a winner with kicker
				printWinningHand();
			}

		} else if (bestHands.size() == 1) {
			// there's a winner with quads
			printWinningHand();

		}
	}
	
	private void compStrFlush() {
		max = bestHands.get(0).getFiveCardsHand()[0].getRank().getValue();

		// Checking the possibility of more than 2 players having
		// straight-flush,
		// in which case - checking the highest card
		loop(0);

		if (bestHands.size() >= 2) {
			// Checking the highest card
			max = bestHands.get(0).getFiveCardsHand()[0].getRank().getValue();
			loop(0);

			if (bestHands.size() >= 2) {
				// split pot
				System.out.println("Players win with " + Player.HAND_RANK[bestHands.get(0).getHandValue()]
						+ bestHands.get(0).getFiveCardsHand()[0].getRank() + bestHands.get(0).getFiveCardsHand()[0].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[1].getRank() + bestHands.get(0).getFiveCardsHand()[1].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[2].getRank() + bestHands.get(0).getFiveCardsHand()[2].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[3].getRank() + bestHands.get(0).getFiveCardsHand()[3].getSuit()
						+ bestHands.get(0).getFiveCardsHand()[4].getRank() + bestHands.get(0).getFiveCardsHand()[4].getSuit());
			}

		} else if (bestHands.size() == 1) {
			// There's a winner with highest card straight-flush
			printWinningHand();
		}
	}
	
	public void showDown() {
		bestHands = new ArrayList<Player>();
		max = table.getPlayersInHand().get(0).getHandValue();

		// Iterating through playerList array to find the best ranked hands
		for (int i = 0; i < table.getPlayersInHand().size(); i++) {
			if (table.getPlayersInHand().get(i).getHandValue() > max) {
				max = table.getPlayersInHand().get(i).getHandValue();
			}
		}

		// Iterating through playerList again to find what hand is currently the
		// strongest
		// and how many players are holding this hand
		for (int i = 0; i < table.getPlayersInHand().size(); i++) {

			// If the players in the list have the same hand rank (a pair for
			// example),
			// than check the kickers
			if (table.getPlayersInHand().get(i).getHandValue() == max) {
				bestHands.add(table.getPlayersInHand().get(i));
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
				
			// If the best hand is quad's	
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
			printWinningHand();
		}
	}
}

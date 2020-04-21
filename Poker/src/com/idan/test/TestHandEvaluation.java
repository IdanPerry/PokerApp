package com.idan.test;

public class TestHandEvaluation {

	private TestCard[][] sevenCards;
	private boolean strFlush; // for debug
	protected TestCard[] hand;
	
	private TestDealer dealer;
	protected TestTable table;
	
	public TestHandEvaluation(TestDealer dealer, TestTable table) {
		this.dealer = dealer;
		this.table = table;
	}
	
	// for debug
	public void handAlarm() {
		if (strFlush) {
			System.out.println(" * * * * * * * S T R A I G H T - F L U S H * * * * * * *");
		}
	}

	// Combines each player's hole TestCards with the board (flop, turn and river)
	// in array to make a 7 TestCards hand in ascending rank order
	public void sortHand() {
		sevenCards = new TestCard[table.getPlayers().size()][7];
		TestCard temp;

		for (int i = 0; i < table.getPlayers().size(); i++) {
			sevenCards[i][0] = table.getPlayers().get(i).getHoleCard1();
			sevenCards[i][1] = table.getPlayers().get(i).getHoleCard2();
			sevenCards[i][2] = dealer.getFlop()[0];
			sevenCards[i][3] = dealer.getFlop()[1];
			sevenCards[i][4] = dealer.getFlop()[2];
			sevenCards[i][5] = dealer.getTurn();
			sevenCards[i][6] = dealer.getRiver();
		}

		for (int i = 0; i < sevenCards.length; i++) {
			for (int j = 0; j < sevenCards[i].length; j++) {
				for (int k = j+1; k < sevenCards[i].length; k++) {
					if (sevenCards[i][j].getRankValue() > sevenCards[i][k].getRankValue()) {
						temp = sevenCards[i][j];
						sevenCards[i][j] = sevenCards[i][k];
						sevenCards[i][k] = temp;
					}
				}
			}
		}
	}

	// TestCards suits sorting in array
	private void sortForFlush() {
		TestCard temp;
		
		for (int i = 0; i < sevenCards.length; i++) {
			for (int j = 0; j < sevenCards[i].length; j++) {
				for (int k = j+1; k < sevenCards[i].length; k++) {
					if (sevenCards[i][j].getSuitValue() > sevenCards[i][k].getSuitValue()) {
						temp = sevenCards[i][j];
						sevenCards[i][j] = sevenCards[i][k];
						sevenCards[i][k] = temp;
					}
				}
			}
		}
	}
	
	public void checkStrFlush() {
		sortForFlush();

		for (int i = 0; i < sevenCards.length; i++) {
			hand = new TestCard[5];
			
				for (int j = 6; j > 3; j--) {
					if (sevenCards[i][j].getSuitValue() == sevenCards[i][j-4].getSuitValue()) {

						hand[0] = sevenCards[i][j];
						hand[1] = sevenCards[i][j-1];
						hand[2] = sevenCards[i][j-2];
						hand[3] = sevenCards[i][j-3];
						hand[4] = sevenCards[i][j-4];

						// Sorting the suited TestCards array by ascending values
						for (int k = 0; k < hand.length; k++) {
							for (int m = k + 1; m < hand.length; m++) {
								if (hand[k].getRankValue() > hand[m].getRankValue()) {
									TestCard temp = hand[k];
									hand[k] = hand[m];
									hand[m] = temp;
								}
							}
						}
						
						if (hand[0].getRankValue() == hand[1].getRankValue() -1 && hand[1].getRankValue() == 
								hand[2].getRankValue() -1 && hand[2].getRankValue() == hand[3].getRankValue() -1 && 
								hand[3].getRankValue() == hand[4].getRankValue() -1){
							
							table.getPlayers().get(i).setStrFlush(true);
							table.getPlayers().get(i).setHandValue(8);
							table.getPlayers().get(i).setHand(hand);
							
							strFlush = true;
							
						} else if (hand[4].getRank().equals("A") && hand[3].getRank().equals("5")
								&& hand[2].getRank().equals("4") && hand[1].getRank().equals("3")
								&& hand[0].getRank().equals("2")) {
							
							table.getPlayers().get(i).setStrFlush(true);
							table.getPlayers().get(i).setHandValue(8);
							table.getPlayers().get(i).setHand(hand);
							
							strFlush = true;
						}

						break;
					}
				}
			}
		

		sortHand();
		checkQuads();

	}
	
	private void checkQuads() {
		for (int i = 0; i < sevenCards.length; i++) {
			hand = new TestCard[5];
			
			if (!table.getPlayers().get(i).isStrFlush()) {
				for (int j = 0; j < 4; j++) {
					if (sevenCards[i][j].getRank().equals(sevenCards[i][j + 3].getRank())) {
						table.getPlayers().get(i).setQuads(true);
						table.getPlayers().get(i).setHandValue(7);

						hand[0] = sevenCards[i][j];
						hand[1] = sevenCards[i][j+1];
						hand[2] = sevenCards[i][j+2];
						hand[3] = sevenCards[i][j+3];

						// Kicker is the 3rd TestCard in the array
						if (j == 3) {
							hand[4] = sevenCards[i][2];
							// Kicker is the highest TestCard
						} else {
							hand[4] = sevenCards[i][6];
						}

						table.getPlayers().get(i).setHand(hand);
					}
				}
			}
		}

		checkFullHouse();
	}
	
	private void checkFullHouse() {
		for (int i = 0; i < table.getPlayers().size(); i++) {
			hand = new TestCard[5];
			
			if (!table.getPlayers().get(i).isQuads() && !table.getPlayers().get(i).isStrFlush()) {
				try {

					for (int j = 6; j > 3; j--) {
						// Trips than pair, connected
						if (sevenCards[i][j].getRank().equals(sevenCards[i][j-2].getRank())
								&& sevenCards[i][j-3].getRank().equals(sevenCards[i][j-4].getRank())) {
							table.getPlayers().get(i).setFullHouse(true);
							table.getPlayers().get(i).setHandValue(6);

							hand[0] = sevenCards[i][j];
							hand[1] = sevenCards[i][j-1];
							hand[2] = sevenCards[i][j-2];
							hand[3] = sevenCards[i][j-3];
							hand[4] = sevenCards[i][j-4];

							table.getPlayers().get(i).setHand(hand);

							break;

							// Trips than pair, 1 gap
						} else if (sevenCards[i][j].getRank().equals(sevenCards[i][j-2].getRank())
								&& sevenCards[i][j-4].getRank().equals(sevenCards[i][j-5].getRank())) {
							table.getPlayers().get(i).setFullHouse(true);
							table.getPlayers().get(i).setHandValue(6);

							hand[0] = sevenCards[i][j];
							hand[1] = sevenCards[i][j-1];
							hand[2] = sevenCards[i][j-2];
							hand[3] = sevenCards[i][j-4];
							hand[4] = sevenCards[i][j-5];

							table.getPlayers().get(i).setHand(hand);

							break;

							// Trips than pair, 2 gaps
						} else if (sevenCards[i][j].getRank().equals(sevenCards[i][j-2].getRank())
								&& sevenCards[i][j-5].getRank().equals(sevenCards[i][j-6].getRank())) {
							table.getPlayers().get(i).setFullHouse(true);
							table.getPlayers().get(i).setHandValue(6);

							hand[0] = sevenCards[i][j];
							hand[1] = sevenCards[i][j-1];
							hand[2] = sevenCards[i][j-2];
							hand[3] = sevenCards[i][j-5];
							hand[4] = sevenCards[i][j-6];

							table.getPlayers().get(i).setHand(hand);

							break;

							// Pair than trips, connected
						} else if (sevenCards[i][j].getRank().equals(sevenCards[i][j-1].getRank())
								&& sevenCards[i][j-2].getRank().equals(sevenCards[i][j-4].getRank())) {
							table.getPlayers().get(i).setFullHouse(true);
							table.getPlayers().get(i).setHandValue(6);

							hand[0] = sevenCards[i][j];
							hand[1] = sevenCards[i][j-1];
							hand[2] = sevenCards[i][j-2];
							hand[3] = sevenCards[i][j-3];
							hand[4] = sevenCards[i][j-4];

							table.getPlayers().get(i).setHand(hand);

							break;

							// Pair than trips, 1 gap
						} else if (sevenCards[i][j].getRank().equals(sevenCards[i][j-1].getRank())
								&& sevenCards[i][j-3].getRank().equals(sevenCards[i][j-5].getRank())) {
							table.getPlayers().get(i).setFullHouse(true);
							table.getPlayers().get(i).setHandValue(6);

							hand[0] = sevenCards[i][j];
							hand[1] = sevenCards[i][j-1];
							hand[2] = sevenCards[i][j-3];
							hand[3] = sevenCards[i][j-4];
							hand[4] = sevenCards[i][j-5];

							table.getPlayers().get(i).setHand(hand);

							break;

							// Pair than trips, 2 gaps
						} else if (sevenCards[i][j].getRank().equals(sevenCards[i][j-1].getRank())
								&& sevenCards[i][j-4].getRank().equals(sevenCards[i][j-6].getRank())) {
							table.getPlayers().get(i).setFullHouse(true);
							table.getPlayers().get(i).setHandValue(6);

							hand[0] = sevenCards[i][j];
							hand[1] = sevenCards[i][j-1];
							hand[2] = sevenCards[i][j-4];
							hand[3] = sevenCards[i][j-5];
							hand[4] = sevenCards[i][j-6];

							table.getPlayers().get(i).setHand(hand);

							break;
						}
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					// e.printStackTrace();
				}
			}
		}

		checkFlush();
	}

	private void checkFlush() {
		sortForFlush();

		for (int i = 0; i < sevenCards.length; i++) {
			hand = new TestCard[5];
			
			if (!table.getPlayers().get(i).isFullHouse()
					&& !table.getPlayers().get(i).isQuads() && !table.getPlayers().get(i).isStrFlush()) {

				for (int j = 6; j > 3; j--) {
					if (sevenCards[i][j].getSuitValue() == sevenCards[i][j-4].getSuitValue()) {
						table.getPlayers().get(i).setFlush(true);
						table.getPlayers().get(i).setHandValue(5);

						hand[0] = sevenCards[i][j];
						hand[1] = sevenCards[i][j-1];
						hand[2] = sevenCards[i][j-2];
						hand[3] = sevenCards[i][j-3];
						hand[4] = sevenCards[i][j-4];

						// Sorting the suited TestCards array by ascending values
						for (int k = 0; k < hand.length; k++) {
							for (int m = k + 1; m < hand.length; m++) {
								if (hand[k].getRankValue() > hand[m].getRankValue()) {
									TestCard temp = hand[k];
									hand[k] = hand[m];
									hand[m] = temp;
								}
							}
						}

						table.getPlayers().get(i).setHand(hand);

						break;
					}
				}
			}
		}

		sortHand();
		checkStraight();
	}
	
	/** TODO: fix algorithm. paired ranks should be ignored  */
	private void checkStraight() {
		for (int i = 0; i < sevenCards.length; i++) {
			hand = new TestCard[5];
			
			if (!table.getPlayers().get(i).isFlush() && !table.getPlayers().get(i).isFullHouse() &&
					!table.getPlayers().get(i).isQuads() && !table.getPlayers().get(i).isStrFlush()) {

				for (int j = 6; j > 3; j--) {
					if (sevenCards[i][j].getRankValue() == sevenCards[i][j-1].getRankValue() + 1 &&
							sevenCards[i][j-1].getRankValue() == sevenCards[i][j-2].getRankValue() + 1 &&
							sevenCards[i][j-2].getRankValue() == sevenCards[i][j-3].getRankValue() + 1 &&
							sevenCards[i][j-3].getRankValue() == sevenCards[i][j-4].getRankValue() + 1) {
						
						table.getPlayers().get(i).setStraight(true);
						table.getPlayers().get(i).setHandValue(4);

						hand[0] = sevenCards[i][j];
						hand[1] = sevenCards[i][j-1];
						hand[2] = sevenCards[i][j-2];
						hand[3] = sevenCards[i][j-3];
						hand[4] = sevenCards[i][j-4];

						table.getPlayers().get(i).setHand(hand);

						break;

						// The "Wheel" straight, taking A as the lowest TestCard
					} else if (sevenCards[i][6].getRank().equals("A") && sevenCards[i][3].getRank().equals("5")
							&& sevenCards[i][2].getRank().equals("4") && sevenCards[i][1].getRank().equals("3")
							&& sevenCards[i][0].getRank().equals("2")) {

						table.getPlayers().get(i).setStraight(true);
						table.getPlayers().get(i).setHandValue(4);

						hand[0] = sevenCards[i][3];
						hand[1] = sevenCards[i][2];
						hand[2] = sevenCards[i][1];
						hand[3] = sevenCards[i][0];
						hand[4] = sevenCards[i][6];

						table.getPlayers().get(i).setHand(hand);

						break;
					}
				}
			}
		}

		checkTrips();
	}
	
	private void checkTrips() {
		for (int i = 0; i < sevenCards.length; i++) {
			hand = new TestCard[5];

			if (!table.getPlayers().get(i).isStraight() && !table.getPlayers().get(i).isFlush() &&
					!table.getPlayers().get(i).isFullHouse() && !table.getPlayers().get(i).isQuads()
					&& !table.getPlayers().get(i).isStrFlush()) {

				for (int j = 6; j > 1; j--) {
					if (sevenCards[i][j].getRank().equals(sevenCards[i][j-2].getRank())) {
						table.getPlayers().get(i).setTrips(true);
						table.getPlayers().get(i).setHandValue(3);
						hand[0] = sevenCards[i][j];
						hand[1] = sevenCards[i][j-1];
						hand[2] = sevenCards[i][j-2];

						if (j == 6) {
							hand[3] = sevenCards[i][3];
							hand[4] = sevenCards[i][2];
						} else if (j == 5) {
							hand[3] = sevenCards[i][6];
							hand[4] = sevenCards[i][2];
						} else {
							hand[3] = sevenCards[i][6];
							hand[4] = sevenCards[i][5];
						}

						table.getPlayers().get(i).setHand(hand);

						break;
					}
				}
			}
		}

		checkTwoPairs();
	}
	
	private void checkTwoPairs() {
		for (int i = 0; i < sevenCards.length; i++) {
			hand = new TestCard[5];
			
			if (!table.getPlayers().get(i).isTrips() &&
					!table.getPlayers().get(i).isStraight() && !table.getPlayers().get(i).isFlush()
					&& !table.getPlayers().get(i).isFullHouse() && !table.getPlayers().get(i).isQuads()
					&& !table.getPlayers().get(i).isStrFlush()) {

				try {

					for (int j = 6; j > 2; j--) {
						// 2 pairs connected
						if (sevenCards[i][j].getRank().equals(sevenCards[i][j-1].getRank())
								&& sevenCards[i][j-2].getRank().equals(sevenCards[i][j-3].getRank())) {
							table.getPlayers().get(i).setTwoPairs(true);
							table.getPlayers().get(i).setHandValue(2);
							hand[0] = sevenCards[i][j];
							hand[1] = sevenCards[i][j-1];
							hand[2] = sevenCards[i][j-2];
							hand[3] = sevenCards[i][j-3];

							// kicker is bigger than pairs
							if (j == 6) {
								hand[4] = sevenCards[i][2];

								// kicker is smaller than pairs
							} else {
								hand[4] = sevenCards[i][6];
							}

							table.getPlayers().get(i).setHand(hand);

							break;

							// 2 pairs with 1 gap
						} else if (sevenCards[i][j].getRank().equals(sevenCards[i][j-1].getRank())
								&& sevenCards[i][j-3].getRank().equals(sevenCards[i][j-4].getRank())) {
							table.getPlayers().get(i).setTwoPairs(true);
							table.getPlayers().get(i).setHandValue(2);
							hand[0] = sevenCards[i][j];
							hand[1] = sevenCards[i][j-1];
							hand[2] = sevenCards[i][j-3];
							hand[3] = sevenCards[i][j-4];

							// kicker is between the pairs
							if (j == 6) {
								hand[4] = sevenCards[i][j-2];

								// kicker is bigger than pairs
							} else {
								hand[4] = sevenCards[i][6];
							}

							table.getPlayers().get(i).setHand(hand);

							break;

							// 2 pairs with 2 gaps
						} else if (sevenCards[i][j].getRank().equals(sevenCards[i][j-1].getRank())
								&& sevenCards[i][j-4].getRank().equals(sevenCards[i][j-5].getRank())) {
							table.getPlayers().get(i).setTwoPairs(true);
							table.getPlayers().get(i).setHandValue(2);
							hand[0] = sevenCards[i][j];
							hand[1] = sevenCards[i][j-1];
							hand[2] = sevenCards[i][j-4];
							hand[3] = sevenCards[i][j-5];

							// kicker is 1 TestCard after first pair
							if (j == 6) {
								hand[4] = sevenCards[i][j-2];

								// kicker is bigger than pairs
							} else {
								hand[4] = sevenCards[i][6];
							}

							table.getPlayers().get(i).setHand(hand);

							break;

							// 2 pairs with 3 gaps
						} else if (sevenCards[i][6].getRank().equals(sevenCards[i][5].getRank())
								&& sevenCards[i][1].getRank().equals(sevenCards[i][0].getRank())) {
							table.getPlayers().get(i).setTwoPairs(true);
							table.getPlayers().get(i).setHandValue(2);
							hand[0] = sevenCards[i][6];
							hand[1] = sevenCards[i][5];
							hand[2] = sevenCards[i][1];
							hand[3] = sevenCards[i][0];
							hand[4] = sevenCards[i][4];

							table.getPlayers().get(i).setHand(hand);

							break;
						}
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					// e.printStackTrace();
				}
			}
		}

		checkPair();
	}
	
	private void checkPair() {
		for (int i = 0; i < sevenCards.length; i++) {
			hand = new TestCard[5];
			
			if (!table.getPlayers().get(i).isTwoPairs() && !table.getPlayers().get(i).isTrips() &&
			!table.getPlayers().get(i).isStraight() && !table.getPlayers().get(i).isFlush() &&
					!table.getPlayers().get(i).isFullHouse() && !table.getPlayers().get(i).isQuads()
					&& !table.getPlayers().get(i).isStrFlush()){

				for (int j = 6; j > 0; j--) {
					if (sevenCards[i][j].getRank().equals(sevenCards[i][j-1].getRank())) {
						table.getPlayers().get(i).setPair(true);
						table.getPlayers().get(i).setHandValue(1);
						hand[0] = sevenCards[i][j];
						hand[1] = sevenCards[i][j-1];

							if (j == 6) {
								hand[2] = sevenCards[i][4];
								hand[3] = sevenCards[i][3];
								hand[4] = sevenCards[i][2];

							} else if (j == 5) {
								hand[2] = sevenCards[i][6];
								hand[3] = sevenCards[i][3];
								hand[4] = sevenCards[i][2];
								
							} else if (j == 4) {
								hand[2] = sevenCards[i][6];
								hand[3] = sevenCards[i][5];
								hand[4] = sevenCards[i][2];
								
							} else {
								hand[2] = sevenCards[i][6];
								hand[3] = sevenCards[i][5];
								hand[4] = sevenCards[i][4];	
							}

						table.getPlayers().get(i).setHand(hand);

						break;
					}
				}
			}
		}
		
		checkHighTestCard();
	}
	
	private void checkHighTestCard() {
		for (int i = 0; i < sevenCards.length; i++) {
			hand = new TestCard[5];
			
			if (!table.getPlayers().get(i).isPair() && !table.getPlayers().get(i).isTwoPairs() && !table.getPlayers().get(i).isTrips() &&
			!table.getPlayers().get(i).isStraight() && !table.getPlayers().get(i).isFlush() &&
					!table.getPlayers().get(i).isFullHouse() && !table.getPlayers().get(i).isQuads()
					&& !table.getPlayers().get(i).isStrFlush()){
				
				table.getPlayers().get(i).setHandValue(0);
				
				hand[0] = sevenCards[i][6];
				hand[1] = sevenCards[i][5];
				hand[2] = sevenCards[i][4];
				hand[3] = sevenCards[i][3];
				hand[4] = sevenCards[i][2];
				
				table.getPlayers().get(i).setHand(hand);
			}
		}
	}

	public void resetHandRanks() {
		for (int i = 0; i < table.getPlayers().size(); i++) {
			table.getPlayers().get(i).setPair(false);
			table.getPlayers().get(i).setTwoPairs(false);
			table.getPlayers().get(i).setTrips(false);
			table.getPlayers().get(i).setStraight(false);
			table.getPlayers().get(i).setFlush(false);
			table.getPlayers().get(i).setFullHouse(false);
			table.getPlayers().get(i).setQuads(false);
			table.getPlayers().get(i).setStrFlush(false);
		}
	}
}

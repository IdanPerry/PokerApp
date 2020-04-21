package com.idan.test;

public class TestMain {

	public static void main(String[] args) {
		TestTable table = new TestTable();
		

		for (int i = 0; i < table.getPlayers().size(); i++) {
			System.out.println("\n" + table.getPlayers().get(i).getName() + " total wins is "
					+ table.getPlayers().get(i).getScore());
		}
	}
}

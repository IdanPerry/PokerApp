package com.idan.test;

public class TestMain {

	public static void main(String[] args) {
		TestTable table = new TestTable();
		
		for (int i = 0; i < table.getTablePlayers().size(); i++) {
			System.out.println("\n" + table.getTablePlayers().get(i).getName() + " total wins is "
					+ table.getTablePlayers().get(i).getScore());
		}
	}
}

package final_cellular_automata;

import java.util.ArrayList;
import java.util.HashSet;

public class Cell {
	public ArrayList<Short> comeToLifeStates = new ArrayList<>();
	public ArrayList<Short> stayAliveStates = new ArrayList<>();
	public static int totalStates = Runner.totalStates;
	public int state = -1;
	public ArrayList<HashSet<Short>> surroundingStates = new ArrayList<>();

	public Cell(String type) {
		if (type.equals("alive")) {
			state = (int) (System.nanoTime() % totalStates);
			setUpComingToLifeStates();
			setUpStayAlive();
		}
	}

	public Cell(ArrayList<Cell> removalSet) {

		state = removalSet.get(0).state;

		for (Cell parent : removalSet) {
			comeToLifeStates.addAll(parent.comeToLifeStates);
			stayAliveStates.addAll(parent.stayAliveStates);
		}
	}

	private void setUpStayAlive() {
		short temp = 0;
		for (short i = 1; i <= 8; i++) {
			for (short j = (short) (i + 1); j <= 8; j++) {
				
					temp = (short) (Math.pow(2, i) + Math.pow(2, j));
					stayAliveStates.add(temp);
				
			}
		}
		for (short i = 1; i <= 9; i++) {
			for (short j = (short) (i + 1); j <= 8; j++) {
				for (short k = (short) (j + 1); k <= 8; k++) {
					
						temp = (short) (Math.pow(2, i) + Math.pow(2, j) + Math.pow(2, k));
						stayAliveStates.add(temp);
					
				}
			}
		}
	}

	private void setUpComingToLifeStates() {
		short temp = 0;
		for (short i = 1; i <= 9; i++) {
			for (short j = (short) (i + 1); j <= 8; j++) {
				for (short k = (short) (j + 1); k <= 8; k++) {
					
						temp = (short) (Math.pow(2, i) + Math.pow(2, j) + Math.pow(2, k));
						comeToLifeStates.add(temp);
					
				}
			}
		}
	}
}

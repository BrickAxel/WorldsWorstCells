package final_cellular_automata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Cell {
	public ArrayList<Short> comeToLifeStates = new ArrayList<>();
	public ArrayList<Short> stayAliveStates = new ArrayList<>();
	public static int totalStates = Runner.totalStates;
	public int state = -1;
	public ArrayList<HashSet<Short>> surroundingStates = new ArrayList<>();
	public static double mutateOdds = 0.01;
	public static double specialOdds = 0.1;
	public static Random rnd = new Random(Runner.seed);
	public static boolean muttCurb = false;
	public static int maxLambda = 56;

	public Cell(String type) {
		if (type.equals("alive")) {
			state = rnd.nextInt(totalStates);
			setUpComingToLifeStates();
			setUpStayAlive();
		}
	}

	public Cell(ArrayList<Cell> removalSet) {
		if (removalSet.size() == 0) {
			System.out.println("killa man");
		}
		state = removalSet.get(0).state;

		for (Cell parent : removalSet) {
			if (parent.comeToLifeStates.size() != 0) {
				for (Short littleGuy : parent.comeToLifeStates) {
					if (!comeToLifeStates.contains(littleGuy)) {
						comeToLifeStates.add(littleGuy);
					}
				}
				for (Short littleGuy : parent.stayAliveStates) {
					if (!stayAliveStates.contains(littleGuy)) {
						stayAliveStates.add(littleGuy);
					}
				}
			}
		}
		if (rnd.nextDouble(100) < mutateOdds) {
			short newAddtion = (short) rnd.nextInt((int) Math.pow(2, 9));
			if (!comeToLifeStates.contains(newAddtion)) {
				comeToLifeStates.add(newAddtion);
			} else {
				comeToLifeStates.remove((Object) newAddtion);
			}
			if (!stayAliveStates.contains(newAddtion)) {
				stayAliveStates.add(newAddtion);
			} else {
				stayAliveStates.remove((Object) newAddtion);
				// stayAliveStates.remove(newAddtion);
			}
		}
		while (comeToLifeStates.size() > maxLambda && muttCurb) {
			comeToLifeStates.remove(rnd.nextInt(maxLambda));
			stayAliveStates.remove(rnd.nextInt(stayAliveStates.size()));
		}
		if (rnd.nextDouble(100) < specialOdds) {
			comeToLifeStates.clear();
			for (short i = 0; i < Math.pow(2, 9); i++) {
				stayAliveStates.add(i);
			}
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

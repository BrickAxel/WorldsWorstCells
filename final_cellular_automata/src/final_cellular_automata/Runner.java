package final_cellular_automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Runner {
	public static int width = 300;
	public static int height = width;
	public static Cell[][] cellGrid = new Cell[width][height];
	public static double odds = 15; // odds out of 100 that a cell starts as alive
	public static int totalStates = 3;
	public static int gens = 50000;
	public static Cell dead = new Cell("dead");
	public static int seed = 10;

	public static void main(String[] args) throws InterruptedException {
		Viewer seer = new Viewer();
		seer.myComponent.scale=3;
		seer.main(null);
		// itialize();
		// testInital();
		testBiasInitialization();
		// testRockPaperScissors();
		//gridPrint();
		for (int i = 0; i < gens; i++) {
				oneGen();
				
				//System.out.println(i);
				if(i%100==0) {
					gridPrint();
				}
				
				Viewer.myComponent.newSquares(cellGrid);
				seer.update();
				 try {
					TimeUnit.MILLISECONDS.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

		}
	}

	private static void testRockPaperScissors() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				cellGrid[i][j] = dead;
			}
		}
		cellGrid[1][1] = new Cell("alive");
		cellGrid[1][1].state = 0;
		cellGrid[1][2] = new Cell("alive");
		cellGrid[1][2].state = 0;
		cellGrid[1][3] = new Cell("alive");
		cellGrid[1][3].state = 0;
		cellGrid[2][1] = new Cell("alive");
		cellGrid[2][1].state = 1;
		cellGrid[2][2] = new Cell("alive");
		cellGrid[2][2].state = 1;
		cellGrid[2][3] = new Cell("alive");
		cellGrid[2][3].state = 1;

		cellGrid[5][1] = new Cell("alive");
		cellGrid[5][1].state = 1;
		cellGrid[5][2] = new Cell("alive");
		cellGrid[5][2].state = 1;
		cellGrid[5][3] = new Cell("alive");
		cellGrid[5][3].state = 1;
		cellGrid[6][1] = new Cell("alive");
		cellGrid[6][1].state = 2;
		cellGrid[6][2] = new Cell("alive");
		cellGrid[6][2].state = 2;
		cellGrid[6][3] = new Cell("alive");
		cellGrid[6][3].state = 2;

		cellGrid[9][1] = new Cell("alive");
		cellGrid[9][1].state = 2;
		cellGrid[9][2] = new Cell("alive");
		cellGrid[9][2].state = 2;
		cellGrid[9][3] = new Cell("alive");
		cellGrid[9][3].state = 2;
		cellGrid[10][1] = new Cell("alive");
		cellGrid[10][1].state = 0;
		cellGrid[10][2] = new Cell("alive");
		cellGrid[10][2].state = 0;
		cellGrid[10][3] = new Cell("alive");
		cellGrid[10][3].state = 0;
		// cellGrid[2][3] = new Cell("alive");

	}

	private static void testBiasInitialization() {
		Random rnd = new Random(seed);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (rnd.nextInt(100) < odds) {
					cellGrid[i][j] = new Cell("alive");
					cellGrid[i][j].state = ((height - i - 1) * totalStates) / height;
				} else {
					cellGrid[i][j] = dead;
				}
			}
		}

	}

	private static void testInital() {

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				cellGrid[i][j] = dead;
			}
		}
		cellGrid[1][1] = new Cell("alive");
		cellGrid[2][2] = new Cell("alive");
		cellGrid[2][3] = new Cell("alive");
		cellGrid[1][3] = new Cell("alive");
		cellGrid[0][3] = new Cell("alive");
		// cellGrid[2][3] = new Cell("alive");
	}

	private static void gridPrint() {
		int[] maxData = new int[totalStates];
		int[] popCount = new int[totalStates];
		for (int a = 0; a < height; a++) {
			for (int b = 0; b < width; b++) {
				if (cellGrid[b][a].state >= 0) {
					//System.out.print(cellGrid[b][a].state);
					if (maxData[cellGrid[b][a].state] < cellGrid[b][a].comeToLifeStates.size()) {
						maxData[cellGrid[b][a].state] = cellGrid[b][a].comeToLifeStates.size();
					}
					popCount[cellGrid[b][a].state]++;
				} else {
					//System.out.print(" ");
				}
			}
			//System.out.println();
		}
		for (int i = 0; i < maxData.length; i++) {
			System.out.println(i + ": max :" + maxData[i] + ": total :" + popCount[i]);
		}
		System.out.println(
				"====================================================================================================================");
	}

	private static void oneGen() {
		Cell[][] temp = new Cell[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				temp[i][j] = dead;
				if (cellGrid[i][j].state != -1) {
					int currentState = cellGrid[i][j].state;
					short tagNum = 0;
					int rotarty = 0;
					for (int upDown = -1; upDown <= 1; upDown++) {
						for (int leftRight = -1; leftRight <= 1; leftRight++) {
							if (!(leftRight == 0 && upDown == 0)) {
								rotarty++;
								int upAndDown = (i + height + upDown) % height;
								int leftAndRight = (j + width + leftRight) % width;
								int compareState = cellGrid[upAndDown][leftAndRight].state;
								if (currentState == compareState && tagNum >= 0) {
									tagNum = (short) (tagNum + (Math.pow(2, rotarty)));
								} else if ((((compareState - currentState) + totalStates - 1)
										% totalStates) < (totalStates / 2) && compareState != -1) {
									tagNum = -1;
								}
							}
						}
					}
					if (cellGrid[i][j].stayAliveStates.contains(tagNum)) {
						temp[i][j] = cellGrid[i][j];
					}
				} else {
					ArrayList<Cell> tournimentSet = new ArrayList<>();
					ArrayList<Integer> iCord = new ArrayList<>();
					ArrayList<Integer> jCord = new ArrayList<>();
					for (int upDown = -1; upDown <= 1; upDown++) {
						for (int leftRight = -1; leftRight <= 1; leftRight++) {
							if (!(leftRight == 0 && upDown == 0)) {
								int upAndDown = (i + height + upDown) % height;
								int leftAndRight = (j + width + leftRight) % width;
								if (cellGrid[upAndDown][leftAndRight].state != -1) {
									tournimentSet.add(cellGrid[upAndDown][leftAndRight]);
									iCord.add(i);
									jCord.add(j);
								}
							}
						}
					}
					if (tournimentSet.size() == 0) {
						temp[i][j] = dead;
					} else {
						temp[i][j] = runTourniment(tournimentSet, iCord, jCord);
					}
				}
			}
		}
		cellGrid = temp;
	}

	private static Cell runTourniment(ArrayList<Cell> tournimentSet, ArrayList<Integer> iCord,
			ArrayList<Integer> jCord) {
		ArrayList<Cell> finalSet = checkReproduction(tournimentSet, iCord, jCord);

		if (finalSet.size() == 0) {
			return dead;
		} else if (finalSet.size() == 1) {
			return finalSet.get(0);
		}

		ArrayList<Cell> removalSet = new ArrayList<>();
		removalSet.addAll(finalSet);

		for (Cell her : finalSet) {
			for (Cell him : finalSet) {
				if ((((her.state - him.state) + totalStates - 1) % totalStates) < (totalStates / 2)) {
					removalSet.remove(him);
				}
			}
		}

		if (removalSet.size() == 1) {
			return removalSet.get(0);
		} else if (removalSet.size() >= 1) {
			return new Cell(removalSet);
		}

		HashMap<Integer, Integer> biggest = new HashMap<>();
		for (Cell bigCheck : finalSet) {
			if (biggest.containsKey(bigCheck.state)) {
				biggest.put(bigCheck.state, biggest.get(bigCheck.state) + 1);
			}
		}
		int max = 0;
		int stateChoosen = 0;
		for (Integer bigCheck : biggest.keySet()) {
			if (biggest.get(bigCheck) > max) {
				max = biggest.get(bigCheck);
				stateChoosen = bigCheck;
			}
		}

		ArrayList<Cell> lastSet = new ArrayList<>();
		for (Cell majorityWinners : finalSet) {
			if (majorityWinners.state == stateChoosen) {
				lastSet.add(majorityWinners);
			}
		}
		return new Cell(lastSet);
		// TODO . If multiple of the same set can,
		// hybridize. If multiple of different sets could reproduce there, rock paper
		// scissor logic applies. If that would
		// result in a draw, majority rules. If that is still a draw, the lowest lambda
		// wins. If it’s still a draw, random
		// chance.
	}

	private static ArrayList<Cell> checkReproduction(ArrayList<Cell> tournimentSet, ArrayList<Integer> iCord,
			ArrayList<Integer> jCord) {
		ArrayList<Cell> potentialParents = new ArrayList<>();
		for (int k = 0; k < tournimentSet.size(); k++) {
			short tagNum = 0;
			int rotarty = 0;
			int currentState = tournimentSet.get(k).state;
			for (int upDown = -1; upDown <= 1; upDown++) {
				for (int leftRight = -1; leftRight <= 1; leftRight++) {
					if (!(leftRight == 0 && upDown == 0)) {
						rotarty++;
						int upAndDown = (iCord.get(k) + height + upDown) % height;
						int leftAndRight = (jCord.get(k) + width + leftRight) % width;
						int compareState = cellGrid[upAndDown][leftAndRight].state;
						if (currentState == compareState && tagNum >= 0) {
							tagNum = (short) (tagNum + Math.pow(2, rotarty));
						} else if ((((compareState - currentState) + totalStates - 1) % totalStates) < 3
								&& compareState != -1) {
							tagNum = -1;
						}
					}
				}
			}
			if (tournimentSet.get(k).comeToLifeStates.contains(tagNum)) {
				potentialParents.add(tournimentSet.get(k));
			}
		}
		return potentialParents;
	}

	private static void itialize() {
		Random rnd = new Random(seed);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (rnd.nextInt(100) < odds) {
					cellGrid[i][j] = new Cell("alive");
				} else {
					cellGrid[i][j] = dead;
				}
			}
		}
	}

}

// TODO:
// DONE create multi species cellular automata
// DONE create different rule sets
// rock paper scissors
// mutations
// specialization
// mutate specialization

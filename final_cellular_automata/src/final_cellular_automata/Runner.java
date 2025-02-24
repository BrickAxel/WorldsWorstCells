package final_cellular_automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Runner {
	public static int width = 10;
	public static int height = width;
	public static Cell[][] cellGrid = new Cell[width][height];
	public static double odds = 15; // odds out of 100 that a cell starts as alive
	public static int totalStates = 1;
	public static int gens = 100;

	public static void main(String[] args) {
		//itialize();
		testInital();
		gridPrint();
		for(int i = 0;i<gens;i++) {
			oneGen();
			gridPrint();
		}
	}

	private static void testInital() {
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
					cellGrid[i][j] = new Cell("dead");
			}
		}
		cellGrid[1][1] = new Cell("alive");
		cellGrid[2][2] = new Cell("alive");
		cellGrid[2][3] = new Cell("alive");
		cellGrid[1][3] = new Cell("alive");
		cellGrid[0][3] = new Cell("alive");
		//cellGrid[2][3] = new Cell("alive");
	}

	private static void gridPrint() {
		for(int a = 0; a<height;a++) {
			for(int b = 0;b<width;b++) {
				if(cellGrid[b][a].state>=0) {
					System.out.print(cellGrid[b][a].state);
				}else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println("====================================================================================================================");
	}

	private static void oneGen() {
		Cell[][] temp = new Cell[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				temp[i][j] = new Cell("Dead");
				if(i==0&&j==2) {
					System.out.println("here");
				}
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
								} else if ((((compareState - currentState) + totalStates - 1) % totalStates) < (totalStates/2)) {
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
						temp[i][j] = new Cell("Dead");
					} else {
						temp[i][j] = runTourniment(tournimentSet,iCord,jCord);
					}
				}
			}
		}
		cellGrid = temp;
	}

	private static Cell runTourniment(ArrayList<Cell> tournimentSet, ArrayList<Integer> iCord, ArrayList<Integer> jCord) {
 		ArrayList<Cell> finalSet = checkReproduction(tournimentSet, iCord, jCord);
		
		if(finalSet.size()==0) {
			return new Cell("dead");
		}else if(finalSet.size()==1) {
			return finalSet.get(0);
		}
		
		ArrayList<Cell> removalSet = new ArrayList<>();
		removalSet.addAll(finalSet);
		
		for(Cell her:finalSet) {
			for(Cell him:finalSet) {
				if((((her.state - him.state) + totalStates - 1) % totalStates) < (totalStates/2)) {
					removalSet.remove(him);
				}
			}
		}
		
		if(removalSet.size()==1) {
			return removalSet.get(0);
		}else if(removalSet.size()>=1) {
			return new Cell(removalSet);
		}
		
		HashMap<Integer, Integer> biggest = new HashMap<>();
		for(Cell bigCheck:finalSet) {
			if(biggest.containsKey(bigCheck.state)) {
				biggest.put(bigCheck.state, biggest.get(bigCheck.state)+1);
			}
		}
		int max = 0;
		int stateChoosen = 0;
		for(Integer bigCheck:biggest.keySet()) {
			if(biggest.get(bigCheck)>max) {
				max = biggest.get(bigCheck);
				stateChoosen=bigCheck;
			}
		}
		
		ArrayList<Cell> lastSet = new ArrayList<>();
		for(Cell majorityWinners:finalSet) {
			if(majorityWinners.state == stateChoosen) {
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

	private static ArrayList<Cell> checkReproduction(ArrayList<Cell> tournimentSet, ArrayList<Integer> iCord, ArrayList<Integer> jCord) {
		ArrayList<Cell> potentialParents = new ArrayList<>();
		for (int k = 0; k<tournimentSet.size();k++) {
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
						} else if ((((compareState - currentState) + totalStates - 1) % totalStates) < 3&& compareState!=-1) {
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
		Random rnd =new Random();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (rnd.nextInt(100) < odds) {
					cellGrid[i][j] = new Cell("alive");
				} else {
					cellGrid[i][j] = new Cell("dead");
				}
			}
		}
	}

}

// TODO:
// create multi species cellular automata
// create different rule sets
// rock paper scissors
// mutations
// specialization
// mutate specialization

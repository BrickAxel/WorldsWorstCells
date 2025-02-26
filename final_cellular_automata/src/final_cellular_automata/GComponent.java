package final_cellular_automata;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GComponent extends JPanel {

	private ArrayList<Squares> dotDotDot = new ArrayList<>();
	private ArrayList<Cubes> blockyBois = new ArrayList<>();
	double scale = 1;
	Graphics2D g2;
	Graphics g;
	private static final long serialVersionUID = 1L;

	public void newSquares(int[][] theBois) {
		dotDotDot.clear();
		for (int i = 0; i < theBois.length; i++) {
			for (int k = 0; k < theBois[0].length; k++) {
				if (theBois[i][k] == 1) {
					dotDotDot.add(new Squares(k, i, scale));
				}
			}
		}
	}
	public void newSquares(Cell[][] theBois) {
		dotDotDot.clear();
		for (int i = 0; i < theBois.length; i++) {
			for (int k = 0; k < theBois[0].length; k++) {
				if (theBois[i][k].state >-1) {
					dotDotDot.add(new Squares(k, i, scale, new Color((50*theBois[i][k].state+30)%255,(90*theBois[i][k].state+200)%255,(20*theBois[i][k].state+50)%255)));
				}
			}
		}
	}

	public void newSquares(double[][] theBois) {
		dotDotDot.clear();
		for (int i = 0; i < theBois.length; i++) {
			for (int k = 0; k < theBois[0].length; k++) {
				if (theBois[i][k] == 0) {
					// dotDotDot.add(new Squares(k,i,scale,Color.black));
				} else if (theBois[i][k] < 0) {
					dotDotDot.add(new Squares(k, i, scale, Color.PINK));
				} else if (theBois[i][k] < 0.25) {
					dotDotDot.add(new Squares(k, i, scale, Color.BLUE));
				} else if (theBois[i][k] < 0.3) {
					dotDotDot.add(new Squares(k, i, scale, Color.RED));
				} else if (theBois[i][k] < 0.35) {
					dotDotDot.add(new Squares(k, i, scale, Color.GREEN));
				} else if (theBois[i][k] < 0.4) {
					dotDotDot.add(new Squares(k, i, scale, Color.MAGENTA));
				} else if (theBois[i][k] < 0.5) {
					dotDotDot.add(new Squares(k, i, scale, Color.ORANGE));
				} else if (theBois[i][k] < 1.5) {
					dotDotDot.add(new Squares(k, i, scale, Color.GREEN));
				} else if (theBois[i][k] < 2.0) {
					dotDotDot.add(new Squares(k, i, scale, Color.ORANGE));
				} else if (theBois[i][k] < 2.5) {
					dotDotDot.add(new Squares(k, i, scale, Color.RED));
				} else if (theBois[i][k] < 3.0) {
					dotDotDot.add(new Squares(k, i, scale, Color.PINK));
				} else if (theBois[i][k] >= 3.0) {
					dotDotDot.add(new Squares(k, i, scale, Color.MAGENTA));
				}
			}
		}
	}

	public void piAreCubed(int[][][] theBois) {
		blockyBois.clear();
		for (int i = 0; i < theBois.length; i++) {
			for (int j = 0; j < theBois[0].length; j++) {
				for (int k = 0; k < theBois[0][0].length; k++) {
					if (theBois[i][j][k] == 1) {
						blockyBois.add(new Cubes(i, j, k, 0,255,100));
						//System.out.println("check");
					}
				}
			}
		}
	}

	// paints the visual
	@Override
	protected void paintComponent(Graphics g) {
		this.g = g;
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		g2.setColor(Color.blue);
		Rectangle2D backGround1 = new Rectangle2D.Double(0, 0, 1650, 1000);
		g2.fill(backGround1);
		g2.draw(backGround1);
		g2.setColor(Color.black);
		Rectangle2D backGround = new Rectangle2D.Double(5, 5, 1525, 775);
		g2.fill(backGround);
		g2.draw(backGround);
		for (Squares sqr : dotDotDot) {
			// g2.setColor(sqr.dotMaker);
			sqr.drawOn(g2);
		}

		for (Cubes bingus : blockyBois) {
			//System.out.println("here");
			bingus.drawOn(g2);
		}
	}
}

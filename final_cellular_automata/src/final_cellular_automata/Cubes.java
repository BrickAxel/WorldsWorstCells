package final_cellular_automata;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Cubes {
	public Color dotMaker = Color.GREEN;
	public int xCord;
	public int yCord;
	public int zCord;
	public int scale=3;
	public static double yorg = 775;
	public static double xorg = 5;
	Graphics g;
	Object a;

	public Cubes(int i, int j, int k, int r, int g, int b) {
		xCord = scale*i;
		yCord = scale*j;
		zCord = scale*k;
		dotMaker = new Color(Math.abs(120-5*k%254), Math.abs(120-7*k%254), Math.abs(120-13*k%254), b);
	}

	// draws a circle for the locations
	public void drawOn(Graphics g) {
		this.g = g;
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D dot = new Rectangle2D.Double(xorg + xCord, yorg - yCord, scale, scale);
		g2.setColor(dotMaker);
		g2.fill(dot);
		g2.draw(dot);
		
		Rectangle2D dot2 = new Rectangle2D.Double(xorg + 750- zCord, yorg - yCord, scale, scale);
		g2.setColor(dotMaker);
		g2.fill(dot2);
		g2.draw(dot2);
		
		Rectangle2D dot3 = new Rectangle2D.Double(xorg + xCord, 10+zCord, scale, scale);
		g2.setColor(dotMaker);
		g2.fill(dot3);
		
		//g2.draw(dot3);
		// System.out.println(dotMaker);
	}

	public void redrawOn() {
		drawOn(g);
	}

	public void clear() {
		// g.clear;
	}
}

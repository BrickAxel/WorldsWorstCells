package final_cellular_automata;

import javax.swing.JFrame;

public class Viewer {
	public static JFrame frame = new JFrame();
	final static int frameWidth = 1650;
	final static int frameHeight = 1000;
	static GComponent myComponent = new GComponent();
	
	public static void main(String[] args) {
	frame.setSize(frameWidth, frameHeight);
	final String frameTitle = "Graph Of Map";
	frame.setTitle(frameTitle);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(myComponent);
	frame.setVisible(true);
	}
	
	public void update() {
		frame.add(myComponent);
		frame.repaint();
	}
	
	public void changeComp(int[][] change) {
		myComponent.newSquares(change);
	}
}

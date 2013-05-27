import java.awt.*;
import javax.swing.*;

public class SystemFrame extends JFrame {
	
	SystemPanel SP;

	public SystemFrame(SolarSystem S) {
		setBounds(100, 100, 800, 800);
		setBackground(Color.black);
		
		getContentPane().setLayout(new BorderLayout());
		
		SP = new SystemPanel(S); //panel to display graphics
		getContentPane().add(SP, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void update(Body[] B) {
		SP.updateSystem(B); //call panel update
	}
}

class SystemPanel extends JPanel {
	
	private Vector[] coordArray;
	private double largestRadius;
    	private int planets;

	public SystemPanel(SolarSystem S) {
		setBackground(Color.black);
		setLargestRadius(S.getBodies()); //largest radius is used to define initial panel size
		setCoords(S.getBodies()); //coordinates to print
		planets = S.getNumberOfPlanets(); //number of planets to determine graphics 
	}
	public void paintComponent(Graphics g) throws NullPointerException {
		//paint background
		super.paintComponent(g);
		//loop to paint positions of each planet
		for (int i=0; i<coordArray.length; i++) {
			int x = 0;
			int y = 0;
			try {
				x = (int)(((coordArray[i].getArray()[0])/(largestRadius*3))*(getWidth())); //account for inability to return size
				y = (int)(((coordArray[i].getArray()[1])/(largestRadius*3))*(getHeight()));
			} catch (NullPointerException e) {} //ignore and continue
			Vector P = new Vector(x,y);
			int coords[] = SystemPanel.rescaleCoords(P, super.getWidth(), super.getHeight()); //rescale to fit panel
			Color C[] = new Color[]{Color.yellow, Color.red, Color.orange, Color.blue, Color.red, Color.orange}; //colour array
			int S[] = new int[]{30, 6, 12, 12, 10, 20}; //size array
			int size;
			if (i<planets) { //if not a satellite
				g.setColor(C[i]);
				size = S[i];
			} else { //satellite
				g.setColor(Color.gray);
				size = 6;
			}
			int X = coords[0]-(int)(size/2.0); //account for size of oval
			int Y = coords[1]-(int)(size/2.0);
			g.fillOval(X, Y, size, size);
		}
	}
	public void updateSystem(Body[] B) { //update panel
		setCoords(B);
		repaint();
	}
	//getters and setters
	public void setCoords(Body B[]) {
		coordArray = new Vector[B.length];
		for (int i=0; i<B.length; i++) {
			coordArray[i] = new Vector(B[i].getP().getArray());
		}
	}
	public void setLargestRadius(Body B[]) { //find from body array
		largestRadius = 0;
		for (int i=0; i<B.length; i++) {
			double radius = B[i].getP().getMagnitude();
			if (largestRadius < radius) {
				largestRadius = radius;
			}
		}
	}
	public Vector[] getCoords() {return coordArray;}
    public Dimension getPreferredSize() {
    	setSize(800, 800);
    	return getSize();
    }
    public static int[] rescaleCoords(Vector A, int h, int w) {
    	double b[] = A.getArray();
    	int c[] = new int[]{(int)((b[0] + w/2)), (int)((h/2 - b[1]))};
    	return c;
    }
}

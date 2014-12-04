import java.lang.Math;
import java.io.*;

public class Body {
    	public static String period_output_file = "PeriodOut.data";
	private String name;
	private double mass, radius, initAngle, angle, angVel, period, pCounter, closest;
	private int closestTime;
	private Vector position, velocity, acceleration[], initialVel; //acceleration has to store the next last and present values
	private Force force;
	private Energy energy;
	//constructors
	public Body() {}
	public Body(String N, Vector p, Vector v, Vector a[], Force f, Energy e, double m, double r) {
		name = N;
		position = p;
		velocity = v;
		acceleration = a;
		mass = m;
		radius = r;
		force = f;
		energy = e;
		initAngle = Math.atan((p.getArray()[1])/(p.getArray()[0]));
		initialVel = v;
		angle = 0;
		angVel = 0;
		period = 0;
		pCounter = 0;
		closest = 0;
		closestTime = 0;
	}
	//static methods
	public static double getTotalEnergy(Body B[]) {
		double sum = 0;
		for (int i=0; i<B.length; i++) {
			sum += B[i].getE().getTotal();
		}
		return sum;
	}
	public static void setPeriods(Body B[], double iteration, int choice, Algorithm A) throws IOException {
		for (int i=0; i<B.length; i++) {
			if (Math.abs(B[i].getAngle() - B[i].getInitA()) < B[i].getAV()*A.getT()/2) {
				B[i].setPer(B[i].getPcounter() + (Math.abs(B[i].getAngle() - B[i].getInitA())/(B[i].getAV()*A.getT()/2)));
				if (choice == 0) { //only print to console
					System.out.println(B[i].getName() + " period: " + B[i].getPer()/(24.0));
				}
				B[i].setPcounter(0); //reset counter for next period
			} else { //no angle match
				B[i].setPcounter(B[i].getPcounter() + 1); //iterate counter
			}
		}
	}
	public static void setClose(Body B[], int iteration, int body) { //set record distance to chosen planet for each body
		int planet = body; //mars
		if (iteration == 1) {
			for (int i=0; i<B.length; i++) { //only satellites
				Vector A = B[i].getP();
				Vector M = B[planet].getP(); //mars
				Vector S = Vector.sub(A, M);
				double mag = S.getMagnitude();
				B[i].setClosest(mag);
			}
		} else {
			for (int i=0; i<B.length; i++) { //only satellites
				Vector A = B[i].getP();
				Vector M = B[planet].getP(); //mars
				Vector S = Vector.sub(A, M);
				double mag = S.getMagnitude();
				if (mag < B[i].getClosest()) {
					B[i].setClosest(mag);
					B[i].setClosestTime(iteration);
				}
			}
		}
	}
	//setters
	public void setP(Vector P) {position = P;}
	public void setV(Vector V) {velocity = V;}
	public void setA(Vector A[]) {acceleration = A;}
	public void setF(Force F) {force = F;}
	public void setE(Energy E) {energy = E;}
	public void setName(String N) {name = N;}
	public void setAngle(double a) {angle = a;}
	public void setAV(double A) {angVel = A;}
	public void setPer(double p) {period = p;}
	public void setInitA(double a) {initAngle = a;}
	public void setPcounter(double p) {pCounter = p;}
	public void setClosest(double c) {closest = c;}
	public void setClosestTime(int t) {closestTime = t;}
	public void setInitV(Vector V) {initialVel = V;}
	//getters
	public double getM() {return mass;}
	public double getR() {return radius;}
	public Vector getP() {return position;}
	public Vector getV() {return velocity;}
	public Vector[] getA() {return acceleration;}
	public Force getF() {return force;}
	public Energy getE() {return energy;}
	public String getName() {return name;}
	public double getAngle() {return angle;}
	public double getAV() {return angVel;}
	public double getPer() {return period;}
	public double getInitA() {return initAngle;}
	public double getPcounter() {return pCounter;}
	public double getClosest() {return closest;}
	public int getClosestTime() {return closestTime;}
	public Vector getInitV() {return initialVel;}
}

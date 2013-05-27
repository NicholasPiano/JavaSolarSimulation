
public class Energy {
	private double potential, kinetic;
	//energy object: store potential and kinetic
	public Energy(double p, double k) {
		potential = p;
		kinetic = k;
	}
	//instance methods
	public double getTotal() {
		double total = potential + kinetic;
		return total;
	}
	//getters and setters
	public void setPot(Body A) {potential = getPot(A);}
	public void setKin(Body A) {kinetic = getKin(A);}
	public double getPot(Body A) {return potential;}
	public double getKin(Body A) {return kinetic;}
}


public class Force {
	private static double G = 6.67384E-11;
	private Vector force;
	
	public Force(Vector f) {
		force = f;
	}
	public static Body[] findForce(Body B[]) {
		//finds the acceleration vector of each body with respect to each other body (vectors are in the coordinate system of top left hand corner of the screen)	
		Body C[] = B;
		for (int i=0; i<B.length; i++) {
			Body A = B[i];
			Vector F = new Vector(0,0);
			for (int j=0; j<B.length; j++) {
				double f = -G*B[j].getM()*A.getM()*(1.0/((Vector.distance(A.getP(), B[j].getP()))*(Vector.distance(A.getP(), B[j].getP())))); //Newton force law
				Vector P = Vector.sub(A.getP(), B[j].getP());
				P.unit(); //unit vector of the vector joining two bodies
				P.scalarMult(f); //turn into force vector
				Vector temp = Vector.addS(F, P);
				F = temp;
			}
			A.setF(new Force(F));
			C[i] = A;
		}
		return C;
	}
	public Vector getForce() {return force;}
	public void setForce(Vector F) {force = F;}
}

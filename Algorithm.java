
import java.lang.Math;

public class Algorithm {
	private double timeStep;
	public Algorithm(double t) {timeStep = t;}

	public static Body[] run(Body A[], Algorithm X) {
		Body B[] = A;
		//calculate the new position of each body in the coordinate system of the centre of mass
		//position
		Body C[] = Algorithm.findPosition(B, X);
		//force
		Body D[] = Force.findForce(C);
		//get control input
		Body D0[] = Control.updateForce(D);
		//acceleration
		Body E[] = Algorithm.findAcceleration(D0);
		//velocity
		Body F[] = Algorithm.findVelocity(E, X);
		//energy
		Body G[] = Algorithm.findEnergy(F);
		return G;
	}
	public static Body[] findAcceleration(Body B[]) {
		//finds the acceleration vector of each body with respect to each other body (vectors are in the coordinate system of top left hand corner of the screen)
		Body C[] = B;
		for (int i=0; i<B.length; i++) {
			Body A = B[i];
			Vector D[] = new Vector[3];
			D[0] = A.getA()[1];
			D[1] = A.getA()[2];
			Vector F = A.getF().getForce(); //force previously calculated
			F.scalarMult(1.0/(A.getM())); //acceleration as force / mass
			D[2] = F;
			A.setA(D); 
			C[i] = A;
		}
		return C;
	}
	public static Body[] findVelocity(Body B[], Algorithm X) {
		//finds the acceleration vector of each body with respect to each other body (vectors are in the coordinate system of top left hand corner of the screen)
		Body C[] = B;
		for (int i=0; i<B.length; i++) {
			Body A = B[i];
			//algorithm: v(t+delta) = v(t) + (delta/6)*(2*a(t + delta) + 5*a(t) - a(t - delta));
			int len = A.getV().getArray().length;
			double temp_array[] = new double[len];
			double v_array[] = A.getV().getArray();
			double a_array1[] = A.getA()[0].getArray();
			double a_array2[] = A.getA()[1].getArray();
			double a_array3[] = A.getA()[2].getArray();
			for (int j=0; j<len; j++) {
				temp_array[j] = v_array[j] + (X.getT()/6.0)*(2*a_array3[j] + 5*a_array2[j] - a_array1[j]);
			}
			A.setV(new Vector(temp_array));
			//angular velocity
			A.setAV((A.getV().getMagnitude())/(A.getP().getMagnitude()));
			//return
			C[i] = A;
		}
		return C;
	}
	public static Body[] findPosition(Body B[], Algorithm X) {
		//finds the acceleration vector of each body with respect to each other body (vectors are in the coordinate system of top left hand corner of the screen)
		Body C[] = B;
		for (int i=0; i<B.length; i++) {
			Body A = B[i];
			//algorithm: x(t + delta) = x(t) + v(t + delta)*delta + (1/6)*delta*delta*(4*a(t) - a(t - delta))
			int len = A.getP().getArray().length;
			double temp_array[] = new double[len];
			double p_array[] = A.getP().getArray();
			double v_array[] = A.getV().getArray();
			double a_array1[] = A.getA()[2].getArray();
			double a_array2[] = A.getA()[1].getArray();
			for (int j=0; j<len; j++) {
				temp_array[j] = p_array[j] + (v_array[j])*X.getT() + (1.0/6.0)*X.getT()*X.getT()*(4*a_array1[j] - a_array2[j]);
			}
			A.setP(new Vector(temp_array));
			//angle
			double y = A.getP().getArray()[1];
			double x = A.getP().getArray()[0];
			A.setAngle(Math.atan2(y, x));
			//return
			C[i] = A;
		}
		return C;
	}
	public static Body[] findEnergy(Body B[]) {
		//finds the kinetic and potential energies of each body
		Body C[] = B;
		for (int i=0; i<B.length; i++) {
			Body A = B[i];
			//potential
			Vector F = A.getF().getForce();
			Vector P = A.getP();	
			double pot = F.getMagnitude()*P.getMagnitude();
			//kinetic
			Vector V = A.getV();
			double m = A.getM();
			double kin = 0.5*(V.getMagnitude()*V.getMagnitude())*m;
			//return
			A.setE(new Energy(pot,kin));
			C[i] = A;
		}
		return C;
	}
	public void setT(double t) {timeStep = t;} //property of algorithm object
	public double getT() {return timeStep;}
}

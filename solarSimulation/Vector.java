import java.lang.Math;

public class Vector {
	private double vArray[];
	
	//constructor
	public Vector(double x, double y, double z) { //constructor for three 
		vArray = new double[]{x, y, z};
	}
	public Vector(double[] A) { //constructor for array input
		vArray = A;
	}
	public Vector(double x, double y) { //constructor for two
		vArray = new double[]{x, y};
	}
	//instance methods
	public double getMagnitude() { //get scalar magnitude of vector
		double sum = 0;
		for (int i=0; i<vArray.length; i++) { //go through each element
			sum += vArray[i]*vArray[i];
		}
		sum = Math.sqrt(sum);
		//System.out.println("" + sum + "");
		return sum;
	}
	public void addI(Vector B) { //instance method to add A and B
		for (int i = 0; i<vArray.length; i++) {
			vArray[i] += B.getArray()[i];
			//System.out.println("v" + vArray[i] + "");
		}
	}
	public void scalarMult(double a) { //multiply by a scalar
		for (int i=0; i<vArray.length; i++) {
			if (vArray[i] != 0) {
				vArray[i] *= a;
			}
		}
	}
	public void unit() { //get unit vector in the direction of vector A
		Vector B = new Vector(vArray);
		double magnitude = B.getMagnitude();
		if (magnitude != 0) {
			B.scalarMult(1.0/magnitude);
			vArray = B.getArray();
		}
	}
	//static methods
	public static Vector addS(Vector A, Vector B) { //static method to add A to B
		int len = A.getArray().length;
		double sums[] = new double[len];
		for (int i = 0; i<len; i++) {
			sums[i] = A.getArray()[i] + B.getArray()[i];
		}
		return new Vector(sums);
	}
	public static Vector sub(Vector A, Vector B) { //subtract vector B from vector A
		int len = A.getArray().length;
		double diffs[] = new double[len];
		for (int i = 0; i<len; i++) {
			diffs[i] = A.getArray()[i] - B.getArray()[i];
		}
		return new Vector(diffs);
	}
	public static double distance(Vector A, Vector B) { //get scalar distance between two bodies
		return Vector.sub(A, B).getMagnitude();
	}
	public static Vector dotS(Vector A, Vector B) { //dot product of A and B
		int len = A.getArray().length;
		double products[] = new double[len];
		for (int i = 0; i<len; i++) {
			products[i] = A.getArray()[i]*B.getArray()[i];
		}
		return new Vector(products);
	}
	public static void printComponents(Vector A) {
		System.out.print("{");
		for (int i=0; i<A.getArray().length; i++) {
			System.out.print("" + A.getArray()[i] + "");
			if (i != A.getArray().length-1) {
				System.out.print(",");
			}
		}
		System.out.println("}");
	}
	//setters
	public void setArray(double A[]) {vArray = A;}
	
	//getters
	public double[] getArray() {return vArray;}
}
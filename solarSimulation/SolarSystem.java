import java.io.*;
import java.util.*;
import java.lang.Math;

public class SolarSystem {
	Console con = System.console();

	private Body Bodies[];
	private int numberOfBodies, recordTime, numberOfPlanets, fileNumber;
	private String recordName;
	private double recordDistance, launchTime;
	private Vector VelocityArray[], satelliteEarthLaunchPosition;

	public SolarSystem(int numberOfBodies) {
		this.numberOfBodies = numberOfBodies;
		recordDistance = 1.0E+13; //far outside the range of the simulation to begin with (arbitrarily large)
	}
	public SolarSystem() {
		recordDistance = 1.0E+13;
	}
	//instance methods
	public void setBodiesFromFile(String fileName) throws FileNotFoundException, IOException {
		//initialise array of bodies
		Body B[] = new Body[numberOfBodies];
		//open file
		FileReader input = new FileReader(new File(fileName));
		BufferedReader in = new BufferedReader(input);
		//read in each line and scan through to pick out values
		int i = 0;
		while (i < numberOfBodies) {
			String line = in.readLine();
			Vector P, V, A[];
			double mass, radius;
			Scanner scan = new Scanner(line);
			String name = scan.next().trim();
			if (name.equals("sun")) {
				P = new Vector(0.0, 0.0); //units: AUs
				V = new Vector(scan.nextDouble(), scan.nextDouble());
				A = new Vector[]{new Vector(0,0), new Vector(0,0), new Vector(0,0)};
				mass = scan.nextDouble();
				radius = scan.nextDouble();
			} else {
				P = new Vector(scan.nextDouble(), scan.nextDouble());
				P = Vector.sub(P, B[0].getP()); //set position vector with respect to the centre of the sun
				V = new Vector(scan.nextDouble(), scan.nextDouble());
				A = new Vector[]{new Vector(0,0), new Vector(0,0), new Vector(0,0)};
				mass = scan.nextDouble();
				radius = scan.nextDouble();
			}
			Body C = new Body(name, P, V, A, new Force(new Vector(0,0)), new Energy(0,0), mass, radius);
			double angle = Math.atan2(V.getArray()[0], V.getArray()[1]);
			System.out.println(name + "\t" + angle);
			B[i] = C;
			i++;
		}
		//put all bodies into an array of bodies
		//set solar system Bodies object 
		Bodies = B;
		//close file
		in.close();
	}
	public void update(Algorithm A) {
		Body B[] = getBodies();
		setBodies(Algorithm.run(B, A));
	}
	public void keep(int i, Algorithm A) {
		//get all initial satellite speeds from first iteration and store in variables
		int numsats = numberOfBodies - numberOfPlanets;	
		satelliteEarthLaunchPosition = Bodies[3].getP();
		if (i == 1) {
			VelocityArray = new Vector[numsats]; //twenty satellites
			for (int n=numberOfPlanets; n<numberOfBodies; n++) {
				VelocityArray[n - numberOfPlanets] = Bodies[n].getV();
			}
		} else if (i > 1 && i < (launchTime*365*24*3600.0/A.getT())) { //keep satellites next to earth
			for (int n=numberOfPlanets; n<numberOfBodies; n++) {
				Bodies[n].setP(Vector.addS(satelliteEarthLaunchPosition, new Vector(0, 2*n + 35000000))); //approximately geostationary
				Bodies[n].setV(Bodies[3].getV());
			}
		} else if (i > (launchTime*365*24*3600.0/A.getT() - 1000) && i < (launchTime*365*24*3600.0/A.getT() + 1000)) { //when system gets to two years in hours
			for (int n=numberOfPlanets; n<numberOfBodies; n++) {
				Bodies[n].setV(Vector.addS(VelocityArray[n - numberOfPlanets], Bodies[3].getV()));
			}
		}
	}
	public void getRecord(int iteration, int choice, int body) {
		Body.setClose(Bodies, iteration, body);
		for (int j=0; j<Bodies.length; j++) {
			if (Bodies[j].getClosest() < recordDistance && j != body) { //do not include earth, jupiter, or mars in record
				setRecordDistance(Bodies[j].getClosest());
				setRecordName(Bodies[j].getName());
				setRecordTime(iteration);
				if (choice == 0) {
					System.out.println(getRecordName() + " time: " + (getRecordTime()/24.0/365.0 - launchTime) + " distance: " + getRecordDistance());
				}
			}
		}
	}
	public void setPeriods(int i, int choice, Algorithm A) throws IOException {
		Body.setPeriods(Bodies, (double)i, choice, A);
	}
	public double getTotalEnergy() {
		double E = Body.getTotalEnergy(Bodies);
		return E;
	}
	//setters
	public void setBodies(Body bodies[]) {Bodies = bodies;}
	public void setNumberOfBodies(int n) {numberOfBodies = n;}
	public void setNumberOfPlanets(int n) {numberOfPlanets = n;}
	public void setVelocityArray(Vector V[]) {VelocityArray = V;}
	public void setSatelliteEarthLaunchPosition(Vector P) {satelliteEarthLaunchPosition = P;}
	public void setRecordDistance(double d) {recordDistance = d;}
	public void setRecordTime(int t) {recordTime = t;}
	public void setRecordName(String s) {recordName = s;}
	public void setLaunchTime(double t) {launchTime = t;}
	//getters
	public Body[] getBodies() {return Bodies;}
	public int getNumberOfBodies() {return numberOfBodies;}
	public int getNumberOfPlanets() {return numberOfPlanets;}
	public Vector[] getVelocityArray() {return VelocityArray;}
	public Vector getSatelliteEarthLaunchPosition() {return satelliteEarthLaunchPosition;}
	public double getRecordDistance() {return recordDistance;}
	public int getRecordTime() {return recordTime;}
	public String getRecordName() {return recordName;}
	public double getLaunchTime() {return launchTime;}
}

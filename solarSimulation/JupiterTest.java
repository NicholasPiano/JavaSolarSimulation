import java.io.*;

public class JupiterTest {
	public static void main(String[] args) throws IOException {
		double file_number = Double.parseDouble(args[0].trim());
		int numbodies = Integer.parseInt(args[1].trim());
		int file_num = (int)file_number;
		String input_file = "./JupiterDataIn/dataIn" + file_num + ".data";
		String output_file = "./JupiterDataOut/dataOut" + file_num + ".data";
		String output_slingshot_file = "./JupiterDataOut/dataOutSlingshot" + file_num + ".data";
		//define positions of bodies with respect to the top left hand corner of the screen.
		Algorithm A = new Algorithm(60);
		SolarSystem Sol = new SolarSystem();
		String name = "Jupiter";
		Sol.setNumberOfBodies(numbodies);
		Sol.setNumberOfPlanets(6);
		Sol.setLaunchTime(3);
		//input values from files and re-scale coordinates to be from the centre of the sun
		Sol.setBodiesFromFile(input_file);
		//frame
		SystemFrame F = new SystemFrame(Sol);
		F.pack();
		F.setVisible(true);
		//iterate bodies
		PrintWriter out = new PrintWriter(new FileWriter(output_file));
		PrintWriter out_slingshot = new PrintWriter(new FileWriter(output_slingshot_file));
		for (int i = 1; i<13879200; i++) { //7 years
			Sol.keep(i, A); //only release satellites after three years
			Sol.update(A); //iterate
			Sol.getRecord(i, 1, 5); //reset record holding satellite if new one is found
			F.update(Sol.getBodies());
			//get slingshot trajectory
			//System.out.println(i + " ");
			for (Body B: Sol.getBodies()) {
				double mag = Vector.sub(B.getP(), Sol.getBodies()[5].getP()).getMagnitude();
				if (B.getClosest() < 5E+9 && !(B.getName().equals("jupiter")) && mag < 1E+9) {
					System.out.println(B.getName() + "\t" + B.getP().getArray()[0] + "\t" + B.getP().getArray()[1]);
					out_slingshot.println(B.getName() + "\t" + B.getP().getArray()[0] + "\t" + B.getP().getArray()[1]);
				}
			}
		}
		//find winning satellite
		double winner_x = 0;
		double winner_y = 0;
		String winner = Sol.getRecordName();
		for (Body B : Sol.getBodies()) {
			if (B.getName().equals(winner)) {
				winner_x = B.getInitV().getArray()[0];
				winner_y = B.getInitV().getArray()[1];
			}
		}
		System.out.println(winner + "\t" + Sol.getRecordDistance() + "\t" + (((Sol.getRecordTime()*A.getT()/24.0)/365.0/3600.0) - 3) + "\t" + winner_x + "\t" + winner_y);
		out.println(winner + "\t" + Sol.getRecordDistance() + "\t" + (((Sol.getRecordTime()*A.getT()/24.0)/365.0/3600.0) - 3) + "\t" + winner_x + "\t" + winner_y);
		//print details to a file
		out.close();
	}
}

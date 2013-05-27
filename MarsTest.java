import java.io.*;
import java.lang.Math;

public class MarsTest {
	public static void main(String[] args) throws IOException {
	    System.out.println(args[0]);
		double file_number = Double.parseDouble(args[0].trim());
		int file_num = (int)file_number;
		//String input_file = "SolarSystemData.data";
		String input_file = "./MarsDataIn/dataIn" + file_num + ".data";
		String output_file = "./MarsDataOut/dataOutAngle" + file_num + ".data";
		//define positions of bodies with respect to the top left hand corner of the screen.
		Algorithm A = new Algorithm(24*3600);
		SolarSystem Sol = new SolarSystem();
		String name = "Mars";
		Sol.setNumberOfBodies(366);
		Sol.setNumberOfPlanets(5);
		Sol.setLaunchTime(2);
		//input values from files and re-scale coordinates to be from the centre of the sun
		Sol.setBodiesFromFile(input_file);
		//frame
		SystemFrame F = new SystemFrame(Sol);
		F.pack();
		F.setVisible(true);
		//iterate bodies
		for (int i = 1; i<132412; i++) { //10 years
			Sol.keep(i, A); //only release satellites after two years
			Sol.update(A); //iterate
			Sol.getRecord(i, 0, 4); //reset record holding satellite if new one is found
			F.update(Sol.getBodies());
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
		}
		//print details to a file
		PrintWriter out = new PrintWriter(new FileWriter(output_file));
		for (int j=0; j<Sol.getBodies().length; j++) {
			Vector V = Sol.getBodies()[j].getInitV();
			double angle = Math.atan2(V.getArray()[0], V.getArray()[1]);
			out.println(angle + "\t" + Sol.getBodies()[j].getClosest());
		}
		out.close();
	}
}

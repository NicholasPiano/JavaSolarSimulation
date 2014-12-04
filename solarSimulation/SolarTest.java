import java.io.*;

public class SolarTest {
    public static String file = "SolarSystemData.data";
    public static String energy_output_file = "EnergyOut.data";
	
    public static void main(String[] args) throws IOException {
	//define positions of bodies with respect to the top left hand corner of the screen.
    	Algorithm A = new Algorithm(3600);
	SolarSystem Sol = new SolarSystem();
	Sol.setNumberOfBodies(6); 
	Sol.setNumberOfPlanets(6);
	//input values from files and re-scale coordinates to be from the centre of the sun
	Sol.setBodiesFromFile(file);
	//create frame
	SystemFrame F = new SystemFrame(Sol);
	F.pack();
	F.setVisible(true);
	//iterate bodies
	PrintWriter out_energy = new PrintWriter(new FileWriter(energy_output_file));
	for (int i = 1; i<876000; i++) { //1000 years
	    Sol.update(A); //update positions with Beeman Algorithm in 'Algorithm'
	    Sol.setPeriods(i,0, A); //set Periods in 'Body'
	    if (i % 10 == 0) { //every ten hours
		out_energy.println(i + "\t" + Sol.getTotalEnergy()); //print energy to file
	    }
	    F.update(Sol.getBodies()); //update panel
	    try {
		Thread.sleep(1); //for nice fun viewing
	    } catch (InterruptedException e) {}
	}
	out_energy.close(); //don't forget  
    }
}

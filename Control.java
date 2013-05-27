
public class Control {
	//event handling for user input
	//used by Test class
	private int target;
	public Control(Body B[], int target, String controlArray) {
		this.target = target;
	}
	public Body[] updateForce(Body B[]) {
		Body C[] = B;
		for (int i=0; i<C.length; i++) {
			if (i == target) {
				//get current heading
				//get right turn
				//get left turn
				//get forward
				//set values accordingly
			}
		}
		return C;
	}
}

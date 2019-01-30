package commandline;

public class Card {

	int cardID;
	String description;
	int size;
	int speed;
	int range;
	int firepower;
	int cargo;
	private static int InitialID = 1;

	public Card(String description, int size, int speed, int range, int firepower, int cargo) {
			cardID = InitialID++;
			this.description = description;
			this.size = size;
			this.speed = speed;
			this.range = range;
			this.firepower = firepower;
			this.cargo = cargo;
		}

	public Card(String line) {
			String[] lineData = line.split(" ");
			int size = Integer.parseInt(lineData[1]);
			int speed = Integer.parseInt(lineData[2]);
			int range = Integer.parseInt(lineData[3]);
			int firepower = Integer.parseInt(lineData[4]);
			int cargo = Integer.parseInt(lineData[5]);
			cardID = InitialID++;
			this.description = lineData[0];
			this.size = size;
			this.speed = speed;
			this.range = range;
			this.firepower = firepower;
			this.cargo = cargo;
		}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getFirepower() {
		return firepower;
	}

	public void setFirepower(int firepower) {
		this.firepower = firepower;
	}

	public int getCargo() {
		return cargo;
	}

	public void setCargo(int cargo) {
		this.cargo = cargo;
	}

	@Override
	public String toString() {
		return "ID: " + cardID + " " + description + ":\r\n" + 
				"   > size:" + size + " \r\n" + 
				"   > speed:" + speed + " \r\n" + 
				"   > range:" + range +" \r\n" + 
				"   > firepower:" + firepower + " \r\n" + 
				"   > cargo:" + cargo;
	}

	// To reset InitialID.
	public static void resetID() {
		InitialID = 1;
	}
}

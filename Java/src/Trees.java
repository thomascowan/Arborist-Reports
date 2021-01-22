
public class Trees {
	
	double ID;
	double cID;
	String type;
	String colour;
	String Location;
	double number;
	
	Trees(){
		this.ID = 0;
		this.cID = 0;
		this.type = "";
		this.number = 0;
		this.colour = "";
		this.Location = "";
	}
	
	Trees (double i, double cl, String t, double n, String c, String l){
		this.ID = i;
		this.cID = cl;
		this.type = t;
		this.colour = c;
		this.Location = l;
		this.number = n;
	}
	
	public void print() {
		System.out.println("tree: " + ID + "\t"   + cID + "\t"   + type + "\t"  + colour + "\t"  + Location + "\t"  + number);
	}
}

import java.util.Date;

public class Client {

	double ID;
	String firstName;
	String surname;
	String address;
	String email;
	String phone;
	String title;
	String suburb;
	String state;
	int postcode;
	String inspectionDate;
	
	Client(){
		this.ID = 0;
		this.title = "";
		this.firstName = "";
		this.surname = "";
		this.address = "";
		this.suburb = "";
		this.state = "";
		this.postcode = 0;
		this.phone = "";
		this.inspectionDate = "";
	}
	
//	Client(double d, String n, String s, String a, String e, String p){
//		this.ID = d;
//		this.firstName = n;
//		this.surname = s;
//		this.address = a;
//		this.email = e;
//		this.phone = p;
//	}
	
	public void print() {
		System.out.println("Client: " + ID + "\t" + firstName + "\t"  + phone + "\t"  + surname + "\t"  + address + "\t"  + email + "\t" );
	}

}

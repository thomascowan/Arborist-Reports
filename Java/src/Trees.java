
public class Trees {
	
	int ID;
	int clientID;
	String species;
	String commonName;
	String location;
	Double DBH;
	Double DAB;
	Double height;
	Double canopySpread;
	String SULE;
	Double TPZ;
	Double SRZ;
	Double TPZM2;
	Double SRZM2;
	String DTD;
	Double clientEncroach;
	Double RecEncroach;
	String HAS;
	String Recommendation;
	String intrusionPresent;
	Double intrusionAmount;
	String ageClass;
	String intrusionLocation;
	
	Trees(){
		this.ID = 0;
		this.clientID = 0;
		this.species = "";
		this.commonName = "";
		this.location = "";
		this.DBH = 0.0;
		this.DAB = 0.0;
		this.height = 0.0;
		this.canopySpread = 0.0;
		this.SULE = "";
		this.TPZ = 0.0;
		this.SRZ = 0.0;
		this.TPZM2 = 0.0;
		this.SRZM2 = 0.0;
		this.DTD = "";
		this.clientEncroach = 0.0;
		this.RecEncroach = 0.0;
		this.HAS = "";
		this.Recommendation = "";
		this.intrusionPresent = "";
		this.intrusionLocation = "";
		this.intrusionAmount = 0.0;
		this.ageClass = "";
	}
	
	public void print() {
		System.out.println("TreeID:\t" + this.ID);
		System.out.print("\tclientID:\t" + this.clientID);
		System.out.print("\tspecies:\t" + this.species);
		System.out.print("\tcommonName:\t" + this.commonName);
		System.out.print("\tlocation:\t" + this.location);
		System.out.print("\tDBH:\t" + this.DBH);
		System.out.print("\tDAB:\t" + this.DAB);
		System.out.print("\theight:\t" + this.height);
		System.out.print("\tcanopySpread:\t" + this.canopySpread);
		System.out.print("\tSULE:\t" + this.SULE);
		System.out.print("\tTPZ:\t" + this.TPZ);
		System.out.print("\tSRZ:\t" + this.SRZ);
		System.out.print("\tTPZM2:\t" + this.TPZM2);
		System.out.print("\tSRZM2:\t" + this.SRZM2 );
		System.out.print("\tDTD:\t" + this.DTD );
		System.out.print("\tclientEncroach:\t" + this.clientEncroach);
		System.out.print("\tRecEncroach:\t" + this.RecEncroach );
		System.out.print("\tHAS:\t" + this.HAS);
		System.out.print("\tRecommendation:\t" + this.Recommendation);
		System.out.print("\tintrusionPresent:\t" + this.intrusionPresent);
		System.out.print("\tintrusionAmount:\t" + this.intrusionAmount);
		System.out.print("\tintrusionLocation:\t" + this.intrusionLocation);
		System.out.print("\tageClass:\t" + this.ageClass);
	}
}

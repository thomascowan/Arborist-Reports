import java.awt.Desktop;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;  
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.imageio.ImageIO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;  
import java.util.Date;  



public class PDFGenerator {

	static boolean debug = false;
	static Client client = new Client();
	static ArrayList<Trees> trees = new ArrayList<Trees>();
	static PDDocument pdf = null;
	static final String[] numNames = {
			"zero","one","two","three","four","five","six","seven","eight","nine","ten",
			"eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen", 
			"twenty", "twenty one", "twenty two", "twenty three", "twenty four", "twenty five", "twenty six", "twenty seven", "twenty eight", "twenty nine", 
			"thirty", "thirty one", "thirty two", "thirty three", "thirty four", "thirty five", "thirty six", "thirty seven", "thirty eight", "thirty nine", 
			"fourty", "fourty one", "fourty two", "fourty three", "fourty four", "fourty five", "fourty six", "fourty seven", "fourty eight", "fourty nine", "fourty ", "fourty ", "fourty ", 
			"fifty", "fifty one", "fifty two", "fifty three", "fifty four", "fifty five", "fifty six", "fifty seven", "fifty eight", "fifty nine", 
			"sixty", "sixty one", "sixty two", "sixty three", "sixty four", "sixty five", "sixty six", "sixty seven", "sixty eight", "sixty nine", 
			"seventy", "seventy one", "seventy two", "seventy three", "seventy four", "seventy five", "seventy six", "seventy seven", "seventy eight", "seventy nine", 
			"eighty", "eighty one", "eighty two", "eighty three", "eighty four", "eighty five", "eighty six", "eighty seven", "eighty eight", "eighty nine", 
			"ninety", "ninety one", "ninety two", "ninety three", "ninety four", "ninety five", "ninety six", "ninety seven", "ninety eight", "ninety nine"};
	
	public static void main(String[] args) {
		int clientID = -1;
		if(args.length != 0) clientID = new Integer(args[0]); 
		loadXLSM(clientID);
		generateTreePNG();
		pdf = PDFCreator.genDoc(client, trees);
//		createPDF();
//		setPDFProperties();
		openPDF();
	}
 

	public static String getDate(String d) {
		Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    String strDate= formatter.format(date);
	    String month = "";

		if(d == null || d == "") d = strDate;
	    switch(d.substring(3, 5)) {
	    case "01":
	    	month = "January";
	    	break;
	    case "02":
	    	month = "February";
	    	break;
	    case "03":
	    	month = "March";
	    	break;
	    case "04":
	    	month = "April";
	    	break;
	    case "05":
	    	month = "May";
	    	break;
	    case "06":
	    	month = "June";
	    	break;
	    case "07":
	    	month = "July";
	    	break;
	    case "08":
	    	month = "August";
	    	break;
	    case "09":
	    	month = "September";
	    	break;
	    case "10":
	    	month = "October";
	    	break;
	    case "11":
	    	month = "November";
	    	break;
	    case "12":
	    	month = "December";
	    	break;
	    }
	    return Integer.parseInt(d.substring(0,2)) + " " + month + " " + d.substring(6);
	    
	}
	
	private static void openPDF() {
		Desktop desktop = Desktop.getDesktop();
		File file = new File("../pdfs/Arborist Report - " + client.ID + " - " + client.firstName + " " + client.surname + ".pdf");
		try {
			desktop.open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadXLSM(int clientID) {
		try {
			File f = new File("../Aborist Reports.xlsm");
			FileInputStream fis = new FileInputStream(f);
			XSSFWorkbook wb = new XSSFWorkbook(fis);    

			//Reads the most recent input to the excel file to load
			XSSFSheet clientSheet = wb.getSheetAt(0); 
			Row cRow = null;
			if(clientID == -1) cRow = clientSheet.getRow(clientSheet.getLastRowNum());
			else cRow = clientSheet.getRow(clientID + 1);
			Iterator<Cell> cItr = cRow.iterator();
			ArrayList<Cell> cd = new ArrayList<Cell>();
			while(cItr.hasNext()) {
				Cell c = cItr.next();
				cd.add(c);
			}
			//Stores the current client information into the Client class object
			client.ID = cd.get(0).getNumericCellValue();
			client.title = cd.get(1).getStringCellValue();
			client.firstName = cd.get(2).getStringCellValue();
			client.surname = cd.get(3).getStringCellValue();
			client.address = cd.get(4).getStringCellValue();
			client.suburb = cd.get(5).getStringCellValue();
			client.state = cd.get(6).getStringCellValue();
			client.postcode = (int) cd.get(7).getNumericCellValue();
			client.phone = cd.get(8).getStringCellValue();
			client.inspectionDate = cd.get(9).getStringCellValue();
			
			//Stores trees from client into tree arraylist
		    ArrayList<Cell> tempTree = new ArrayList<Cell>();
			XSSFSheet treeSheet = wb.getSheetAt(1); 
			Iterator<Row> tRow = treeSheet.iterator();
			tRow.next();
			while(tRow.hasNext()) {
				Row row = tRow.next();
				Iterator<Cell> tItr = row.iterator();
				while(tItr.hasNext()) {
					Cell c = tItr.next();
					tempTree.add(c);
				}
				Trees t = new Trees();			
				t.ID = (int) tempTree.get(0).getNumericCellValue();
				System.out.println(t.ID);
				t.clientID = (int) tempTree.get(1).getNumericCellValue();				
				t.species = tempTree.get(2).getStringCellValue();
				t.commonName = tempTree.get(3).getStringCellValue();
				t.location = tempTree.get(4).getStringCellValue();
				t.DBH = tempTree.get(5).getNumericCellValue();
				t.DAB = tempTree.get(6).getNumericCellValue();
				t.height = tempTree.get(7).getNumericCellValue();
				t.canopySpread = tempTree.get(8).getNumericCellValue();
				t.SULE = tempTree.get(9).getStringCellValue();
				t.TPZ = tempTree.get(10).getNumericCellValue();
				t.SRZ = tempTree.get(11).getNumericCellValue();
				BigDecimal bd = new BigDecimal(Double.toString(tempTree.get(12).getNumericCellValue()));
				t.TPZM2 = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
				bd = new BigDecimal(Double.toString(tempTree.get(13).getNumericCellValue()));
				t.SRZM2 = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
				t.DTD = tempTree.get(14).getStringCellValue();
				t.clientEncroach = tempTree.get(15).getNumericCellValue();
				t.RecEncroach = tempTree.get(16).getNumericCellValue();
				t.HAS = tempTree.get(17).getStringCellValue();
				t.Recommendation = tempTree.get(18).getStringCellValue();
				t.intrusionPresent = tempTree.get(19).getStringCellValue();
				t.intrusionLocation = tempTree.get(20).getStringCellValue();
				t.intrusionAmount = tempTree.get(21).getNumericCellValue();
				t.ageClass = tempTree.get(22).getStringCellValue();
				if(t.clientID==client.ID) trees.add(t);
				tempTree.clear();
			}
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(debug) {
			client.print();
			for(Trees t : trees) {
				t.print();
			}
		}
		
	}
	
	private static void generateTreePNG(){
		int width = 300;
		int height = 300;
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		File newImageDir = new File("../images/" + client.ID + " - " + client.firstName + " " + client.surname);
		newImageDir.mkdir();

		for(int i = 0; i < trees.size(); i++) {


	        Graphics2D g2d = bufferedImage.createGraphics();
	        			
		  //Drawing tree stuff
			int TPZTopLeftw = width/10;
			int TPZTopLefth = height/10;
			int SRZTopLeftw = (int) (width/2 - trees.get(i).SRZ*8*width/20/trees.get(i).TPZ);
			int SRZTopLefth = (int) (height/2-trees.get(i).SRZ*8*height/20/trees.get(i).TPZ);
			int trunkTopLeftw = (int) (width/2-trees.get(i).DBH/1000*8*width/20/trees.get(i).TPZ);
			int trunkTopLefth = (int) (height/2-trees.get(i).DBH/1000*8*height/20/trees.get(i).TPZ);
			
			int TPZRadius = 0;
			int SRZRadius = 0;
			int trunkRadius = 0;
			if(width < height) {
				TPZRadius = 8*width/10;
				SRZRadius = (int) (trees.get(i).SRZ*((8*width/10)/trees.get(i).TPZ));
				trunkRadius = (int) (trees.get(i).DBH/1000*((8*width/10)/trees.get(i).TPZ));
			}else {
				TPZRadius = 8*height/10;
				SRZRadius = (int) (trees.get(i).SRZ*((8*height/10)/trees.get(i).TPZ));
				trunkRadius = (int) (trees.get(i).DBH/1000*((8*height/10)/trees.get(i).TPZ));
			}

	      //draw tree TPZ
	        g2d.setColor(Color.green); //TODO find green colour
	        g2d.fillOval(TPZTopLeftw, TPZTopLefth, TPZRadius, TPZRadius);
	        

	      //draw SRZ of tree
	        g2d.setColor(Color.MAGENTA);//TODO find brown colour
	        g2d.fillOval(SRZTopLeftw, SRZTopLefth, SRZRadius, SRZRadius);
	        
		  //little trunk in middle  
			g2d.setColor(Color.red);
	        g2d.fillOval(trunkTopLeftw, trunkTopLefth, trunkRadius, trunkRadius);
	        
	      //drawing grid
	        g2d.setColor(Color.LIGHT_GRAY);
			for(int j = 0; j < trees.get(i).canopySpread*2; j++) {
				g2d.drawLine(width/2+(int)((8*width/10)/trees.get(i).TPZ/2*j), 0, width/2+(int)((8*width/10)/trees.get(i).TPZ/2*j), height);
				g2d.drawLine(width/2-(int)((8*width/10)/trees.get(i).TPZ/2*j), 0, width/2-(int)((8*width/10)/trees.get(i).TPZ/2*j), height);
				g2d.drawLine(0,height/2+(int)((8*width/10)/trees.get(i).TPZ/2*j), width, height/2+(int)((8*width/10)/trees.get(i).TPZ/2*j));
				g2d.drawLine(0,height/2-(int)((8*width/10)/trees.get(i).TPZ/2*j), width, height/2-(int)((8*width/10)/trees.get(i).TPZ/2*j));
			}
			
		  //draw radius of TPZ
		    g2d.setColor(Color.blue);
		    g2d.drawLine(width/2, height/2, width/2-TPZRadius/2, height/2);
	        
	      //Sets background to white
	        g2d.setColor(Color.white);
	        Area outter = new Area(new Rectangle(0,0,width,height));
	        Ellipse2D.Double inner = new Ellipse2D.Double(width/10,height/10,8*width/10,8*height/10);
	        outter.subtract(new Area(inner));
	        g2d.fill(outter);  
	        
	      
	        
		  //Draw outlines
		    g2d.setColor(Color.BLUE);
		    g2d.drawOval(SRZTopLeftw, SRZTopLefth, SRZRadius, SRZRadius);
		    g2d.drawOval(TPZTopLeftw, TPZTopLefth, TPZRadius, TPZRadius);	        
	        
		  //drawing outside circles
	        for(double j = 0; j < 12; j=j+1.0) {
	        	int x = (int) (width/2-TPZRadius/2*Math.sin(3.289*j/(2*Math.PI))-5);
	        	int y = (int) (height/2-TPZRadius/2*Math.cos(3.289*j/(2*Math.PI))-5);
			    g2d.setColor(Color.red);
		        g2d.fillOval(x, y, 10, 10);
	        }
		    g2d.setColor(Color.blue);
	        g2d.fillOval(width/10-5, height/2-6, 10, 10);
	        
		    
	      //Annotations
		    g2d.setColor(Color.black);
	        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 15*width/400));
			g2d.drawString("R", width/10+5*width/250, height/2-5*height/250);
			if(trees.get(i).SRZ/trees.get(i).TPZ > 0.6)
				g2d.drawString(trees.get(i).TPZ + "m", SRZTopLeftw+20*width/250, height/2-5*height/250);
			else
				g2d.drawString(trees.get(i).TPZ + "m", SRZTopLeftw-20*width/250, height/2-5*height/250);
	        
			int ruler = (int) (trees.get(i).TPZ+2);
			if(ruler%2!=0) ruler++;
			
		    g2d.setColor(Color.blue);
			for(int j = 0; j < ruler; j++) {
		        if(j > ruler - 2) {
					g2d.fillOval(width/2-8 + (int)((8*height/10)/trees.get(i).TPZ/2*j), 19*height/20-4,8,8);
					g2d.fillOval(width/2-8 - (int)((8*height/10)/trees.get(i).TPZ/2*j), 19*height/20-4,8,8);
		        	g2d.drawLine(width/2-4 + (int)((8*height/10)/trees.get(i).TPZ/2*j), 19*height/20, width/2-4 - (int)((8*height/10)/trees.get(i).TPZ/2*j), 19*height/20);
		        } else {
					g2d.drawLine(width/2-4+(int)((8*height/10)/trees.get(i).TPZ/2*j), 19*height/20-4,width/2-4+(int)((8*height/10)/trees.get(i).TPZ/2*j),19*height/20+4);
					g2d.drawLine(width/2-4-(int)((8*height/10)/trees.get(i).TPZ/2*j), 19*height/20-4,width/2-4-(int)((8*height/10)/trees.get(i).TPZ/2*j),19*height/20+4);
		        }
			}
		    g2d.setColor(Color.black);
			g2d.drawString(new Integer((ruler-1)*2).toString() +"m", 3*width/4, 19*height/20-10);

			//draw encroachment zone if there
	        if(trees.get(i).intrusionPresent.equals("True")) {
	        	int dist = (int) (trees.get(i).intrusionAmount*(8*width/10)/trees.get(i).TPZ/2 + trunkRadius/2);
	            
	        	switch(trees.get(i).intrusionLocation) {
		        	case("North"):
		        		g2d.setColor(new Color(255,255,0,59));
		        		g2d.fillRect(0, 0, width, height/2-dist);
		        		g2d.setColor(new Color(155,155,0));
		        		g2d.drawLine(0, height/2-dist, width, height/2-dist);
		        		break;
		        	case("East"):
		        		g2d.setColor(new Color(255,255,0,59));
		        		g2d.fillRect(width/2+dist, 0, width, height);
		        		g2d.setColor(new Color(155,155,0));
		        		g2d.drawLine(width/2+dist, 0, width/2+dist, height);
		        		break;
		        	case("South"):
		        		g2d.setColor(new Color(255,255,0,59));
		        		g2d.fillRect(0, height/2+dist, width, height);
		        		g2d.setColor(new Color(155,155,0));
		        		g2d.drawLine(0, height/2+dist, width, height/2+dist);
		        		break;
		        	case("West"):
		        		g2d.setColor(new Color(255,255,0,59));
		        		g2d.fillRect(0, 0, width/2-dist, height);
		        		g2d.setColor(new Color(155,155,0));
		        		g2d.drawLine(width/2-dist, 0, width/2-dist, height);
		        		break;
	        	}
	        }
			
	      
	 
	        // Disposes of this graphics context and releases any system resources that it is using. 
	 
	        // Save as PNG
	        File file = new File("../images/" + client.ID + " - " + client.firstName + " " + client.surname + "/T" + trees.get(i).ID + ".png");
	        try {
				ImageIO.write(bufferedImage, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}

	        g2d.dispose();
	        
		}
	}
	
	private static void setPDFProperties() {
		PDDocumentInformation pdd = pdf.getDocumentInformation();
		pdd.setAuthor("Elizabeth Cowan");
		pdd.setCreator("Elizabeth Cowan"); 
	}
}

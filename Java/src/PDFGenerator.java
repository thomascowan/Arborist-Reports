import java.io.File;  
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 




public class PDFGenerator {

	static boolean debug = true;
	static Client client = new Client();
	static ArrayList<Trees> trees = new ArrayList<Trees>();
	
	public static void main(String[] args) {

		loadXLSM();
		
	}

	public static void createPDF() {
		
	}
	
	private static void loadXLSM() {
		try {
			File f = new File("../Aborist Reports.xlsm");
			FileInputStream fis = new FileInputStream(f);
			XSSFWorkbook wb = new XSSFWorkbook(fis);    

			//Reads the most recent input to the excel file to load
			XSSFSheet clientSheet = wb.getSheetAt(0); 
			Row cRow = clientSheet.getRow(clientSheet.getLastRowNum());
			Iterator<Cell> cItr = cRow.iterator();
			ArrayList<Cell> cd = new ArrayList<Cell>();
			while(cItr.hasNext()) {
				Cell c = cItr.next();
				cd.add(c);
			}
			//Stores the current client information into the Client class object
			client.ID = cd.get(0).getNumericCellValue();
			client.firstName = cd.get(1).getStringCellValue();
			client.surname = cd.get(2).getStringCellValue();
			client.address = cd.get(3).getStringCellValue();
			client.email = cd.get(4).getStringCellValue();
			client.phone = cd.get(5).getStringCellValue();
			
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
				t.ID = tempTree.get(0).getNumericCellValue();
				t.cID = tempTree.get(1).getNumericCellValue();
				t.type = tempTree.get(2).getStringCellValue();
				t.number = tempTree.get(3).getNumericCellValue();
				t.colour = tempTree.get(4).getStringCellValue();
				t.Location = tempTree.get(5).getStringCellValue();
				if(t.cID==client.ID) trees.add(t);
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

}

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.line.LineStyle;

public class PDFCreator {

	static Client client;
	static ArrayList<Trees> trees;
	static PDDocument document;
	static ArrayList<PDPage> pages = new ArrayList<PDPage>();
	static ArrayList<PDPage> ps = new ArrayList<PDPage>();


	public static PDDocument genDoc(Client c, ArrayList<Trees> t) {
		client = c;
		trees = t;

		//load template document
		File file = new File("../pdfs/Arborist Report - 0 - template.pdf"); 
		try {
			document = PDDocument.load(file);
			//Add all pages of template to array list
			for(int i = 0; i < document.getNumberOfPages(); i++) {
				pages.add(document.getPage(i));
			}

			genPage0();
			genPage1();
			genPage2();
			genPage3();
			genPage4();
			genPage5();
			genPage7();
			genPage11();

			for(int i = 0; i < ps.size(); i++) {
				pages.add(i+6,ps.get(i));
			}

			PDDocument temp = new PDDocument();
			for(PDPage p : pages) temp.addPage(p);
			document = temp;

			document.save("../pdfs/Arborist Report - " + client.ID + " - " + client.firstName + " " + client.surname + ".pdf");
			document.close();

		} catch (IOException e) {
			e.printStackTrace();
		} 

		return document;
	}

	@SuppressWarnings("deprecation")
	private static void genPage0() throws IOException{ //Title page
		String clientName = client.title + " " + client.firstName + " " + client.surname;
		String clientAddress = client.address;
		String clientLocation = client.suburb + " " + client.state+ " " + client.postcode;

		PDPageContentStream TPS = new PDPageContentStream(document, pages.get(0), true, true, false); //Title page stream

		TPS.beginText();
		TPS.setNonStrokingColor(Color.BLACK);
		TPS.setFont(PDType1Font.TIMES_ROMAN, 11);
		TPS.newLineAtOffset(181, 312);
		TPS.showText(clientName);     
		TPS.newLineAtOffset(0, -14);
		TPS.showText(clientAddress);     
		TPS.newLineAtOffset(0, -14);
		TPS.showText(clientLocation);       
		TPS.endText();

		//Adding the date to the page
		PDFont font = PDType1Font.TIMES_ROMAN;
		int fontSize = 14;
		String date = "       " + PDFGenerator.getDate(null);
		float titleWidth = font.getStringWidth(date) / 1000 * fontSize;
		float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

		//TODO adjust centering of date on title page
		TPS.beginText();  
		TPS.setFont(font, fontSize);
		TPS.newLineAtOffset((pages.get(0).getMediaBox().getWidth() - titleWidth) / 2 , pages.get(0).getMediaBox().getHeight() - 264 - titleHeight);
		TPS.showText(date);       
		TPS.endText();  
		TPS.close();
	}

	@SuppressWarnings("deprecation")
	private static void genPage1() throws IOException{ //overview

		//Page 1 - Aboricultural impact assessment report
		//		String pg2para1 = "Evolution Arbor and Consulting was engaged by " + clientName+ " (the client) to produce this\r\n" + 
		//				"Arboricultural Impact Assessment Report for " + numNames[trees.size()] + " (" + trees.size() + ") trees in relation to a proposed attached duel\r\n" + 
		//				"occupancy at " + clientAddress + ", " + clientLocation + ".";

		String clientName = client.title + " " + client.firstName + " " + client.surname;
		String clientAddress = client.address;
		String clientLocation = client.suburb + " " + client.state+ " " + client.postcode;
		String pg1para11 = "Evolution Arbor and Consulting was engaged by " + clientName+ " (the client) to produce this";
		String pg1para12 = "Arboricultural Impact Assessment Report for " + PDFGenerator.numNames[trees.size()] + " (" + trees.size() + ") trees in relation to a proposed attached duel"; 
		String pg1para13 = "occupancy at " + clientAddress + ", " + clientLocation + ".";

		PDPageContentStream CSpg1 = new PDPageContentStream(document, pages.get(1), true, true, false);
		CSpg1.beginText();
		CSpg1.setFont(PDType1Font.TIMES_ROMAN, 11);
		CSpg1.newLineAtOffset(72, 718);
		CSpg1.showText(pg1para11);   
		CSpg1.newLineAtOffset(0, -14);
		CSpg1.showText(pg1para12);   
		CSpg1.newLineAtOffset(0, -14);
		CSpg1.showText(pg1para13);           
		CSpg1.endText();
		CSpg1.close();

		//TODO implement council information
	}

	@SuppressWarnings("deprecation")
	private static void genPage2() throws IOException{ //page with map on it

		//page 2 - observations
		String clientAddress = client.address;
		String clientLocation = client.suburb + " " + client.state+ " " + client.postcode;

		String pg2para11 = "The subject site is located at " + clientAddress + ", " + clientLocation + " (Figure 1). The site is";
		String pg2para12 = "located within Parramatta City Council LGA. As a result, all trees assessed within this site are protected";
		String pg2para13 = "under the councils Tree Preservation Order (TPO). Parramatta City Council is the consenting authority";
		String pg2para14 = "for all works regarding trees within this site, no works are to be conducted without the appropriate";
		String pg2para15 = "written consent from the council.";

		PDPageContentStream CSpg2 = new PDPageContentStream(document, pages.get(2), true, true, false); 
		CSpg2.setNonStrokingColor(Color.BLACK);
		CSpg2.beginText();
		CSpg2.setFont(PDType1Font.TIMES_ROMAN, 11);
		CSpg2.newLineAtOffset(72, 733);
		CSpg2.showText(pg2para11);   
		CSpg2.newLineAtOffset(0, -14);
		CSpg2.showText(pg2para12);   
		CSpg2.newLineAtOffset(0, -14);
		CSpg2.showText(pg2para13); 
		CSpg2.newLineAtOffset(0, -14);
		CSpg2.showText(pg2para14); 
		CSpg2.newLineAtOffset(0, -14);
		CSpg2.showText(pg2para15);           
		CSpg2.endText();
		CSpg2.close();

		//TODO 4 dotpoints on observations page to drop down?
	}

	@SuppressWarnings("deprecation")
	private static void genPage3() throws IOException{ //Method page
		//page 3 - Method

		String pg3para11 = "A Visual Tree Inspection (VTA) was conducted on the " + PDFGenerator.getDate(client.inspectionDate) + ", by doing so we have";
		PDPageContentStream CSpg3 = new PDPageContentStream(document, pages.get(3), true, true, false); 
		CSpg3.setNonStrokingColor(Color.BLACK);
		CSpg3.beginText();
		CSpg3.setFont(PDType1Font.TIMES_ROMAN, 11);
		//TODO check alignment
		CSpg3.newLineAtOffset(72, 745);
		CSpg3.showText(pg3para11);          
		CSpg3.endText();
		CSpg3.close();
	}

	private static void genPage4() throws IOException{ //table page
		//page 4 - tree identification and observations
		String numOfTrees = PDFGenerator.numNames[trees.size()].substring(0,1).toUpperCase() + PDFGenerator.numNames[trees.size()].substring(1);
		String pg4para11 = numOfTrees + " (" + trees.size() + ") trees were assessed at the time of the inspection:";
		@SuppressWarnings("deprecation")
		PDPageContentStream CSpg4 = new PDPageContentStream(document, pages.get(4), true, true, false); 
		CSpg4.setNonStrokingColor(Color.BLACK);
		CSpg4.beginText();
		CSpg4.setFont(PDType1Font.TIMES_ROMAN, 11);
		CSpg4.newLineAtOffset(72, 745);
		CSpg4.showText(pg4para11);          
		CSpg4.endText();
		CSpg4.close();

		PDFont fontPlain = PDType1Font.TIMES_ROMAN;
		//Variables to define table positioning
		float margin = 72;
		float yStartNewPage = pages.get(4).getMediaBox().getHeight() - (2 * margin);
		float tableWidth = pages.get(4).getMediaBox().getWidth() - (2 * margin);
		boolean drawContent = true;
		float bottomMargin = 70;
		float yPosition = 710;

		BaseTable table = new BaseTable(yPosition, yStartNewPage,
				bottomMargin, tableWidth, margin, document, pages.get(4), true, drawContent);

		//Header Row
		Row<PDPage> headerRow = table.createRow(13.5f);
		//Cell 1
		Cell<PDPage> cell = headerRow.createCell(12.4f, "Number");        
		cell.setFontSize(11);     
		cell.setFont(fontPlain);
		cell.setHeight(cell.getTextHeight()+7f);
		cell.setAlign(HorizontalAlignment.LEFT);
		cell.setValign(VerticalAlignment.MIDDLE);
		//Cell 2
		cell = headerRow.createCell(47f, "Botanical Name");        
		cell.setFontSize(11); 
		cell.setFont(fontPlain);
		cell.setHeight(cell.getTextHeight()+7f);
		cell.setAlign(HorizontalAlignment.LEFT);
		cell.setValign(VerticalAlignment.MIDDLE);
		//Cell 3
		cell = headerRow.createCell(40.55f, "Common Name");        
		cell.setFontSize(11); 
		cell.setFont(fontPlain);
		cell.setHeight(cell.getTextHeight()+7f);
		cell.setAlign(HorizontalAlignment.LEFT);
		cell.setValign(VerticalAlignment.MIDDLE);

		table.addHeaderRow(headerRow);

		ArrayList<Row<PDPage>> row = new ArrayList<Row<PDPage>>();
		for(int i = 0; i < trees.size(); i++) {
			row.add(table.createRow(15f));
			//Cell 1
			cell = row.get(i).createCell(12.4f, "T" + trees.get(i).ID);        
			cell.setFontSize(11);     
			cell.setFont(fontPlain);
			cell.setHeight(cell.getTextHeight()+7f);
			cell.setAlign(HorizontalAlignment.LEFT);
			cell.setValign(VerticalAlignment.MIDDLE);
			//Cell 2
			cell = row.get(i).createCell(47f, trees.get(i).species);        
			cell.setFontSize(11); 
			cell.setFont(fontPlain);
			cell.setHeight(cell.getTextHeight()+7f);
			cell.setAlign(HorizontalAlignment.LEFT);
			cell.setValign(VerticalAlignment.MIDDLE);
			//Cell 3
			cell = row.get(i).createCell(40.55f, trees.get(i).commonName);        
			cell.setFontSize(11); 
			cell.setFont(fontPlain);
			cell.setHeight(cell.getTextHeight()+7f);
			cell.setAlign(HorizontalAlignment.LEFT);
			cell.setValign(VerticalAlignment.MIDDLE);
			
			table.addHeaderRow(row.get(i));
		}

		table.draw();
		CSpg4.close();
	}

	private static void genPage5() throws IOException{
		//page 5 - adding tree drawings and brief descriptions
		PDPageContentStream CSpg5 = new PDPageContentStream(document, pages.get(5), true, true, false); 
		CSpg5.setNonStrokingColor(Color.BLACK);
		int currY = 750;
		for(int i = 0; i < trees.size(); i++) {
			String displayText1 = "T" + trees.get(i).ID + " has Tree Protection Zone (TPZ) of " + trees.get(i).TPZ + "m (" + trees.get(i).TPZM2 + "m²) and a Structural Root Zone of" + trees.get(i).SRZ + "m (" + trees.get(i).SRZM2 + "m²).";

			CSpg5.beginText();
			CSpg5.setFont(PDType1Font.TIMES_ROMAN, 11);
			CSpg5.newLineAtOffset(72, currY);
			CSpg5.showText(displayText1);  
			CSpg5.endText();

			currY -= 300;
			PDImageXObject pdImage = PDImageXObject.createFromFile("../images/" + client.ID + " - " + client.firstName + " " + client.surname 
					+ "/T" + trees.get(i).ID + ".png", document);
			int imgCenter = (int) ((pages.get(0).getMediaBox().getWidth() - pdImage.getWidth()) / 2);
			CSpg5.drawImage(pdImage, imgCenter,currY);

			currY -= 50;

			if(currY < 300 & i+1 < trees.size()) {
				currY = 750;
				CSpg5.close();
				ps.add(new PDPage(PDRectangle.A4));
				CSpg5 = new PDPageContentStream(document, ps.get(ps.size()-1), true, true, false);
				PDImageXObject logo = PDImageXObject.createFromFile("resources/arborConsult.jpg", document);
				CSpg5.drawImage(logo, 500,750);
			}

		}

		CSpg5.close();
	}

	private static void genPage6() throws IOException{ // empty

	}

	private static void genPage7() throws IOException{
		//page 7 - Recommendations
		String p9para1 = "The proposed development has been considered in relation to the " + PDFGenerator.numNames[trees.size()]+ " (" + trees.size() + ") trees assessed in this ";
		String p9para2 = "report. The following recommendations are being made to ensure the proposed development is viable. ";

		PDPageContentStream CSpg9 = new PDPageContentStream(document, pages.get(7), true, true, false); 
		CSpg9.setNonStrokingColor(Color.BLACK);
		CSpg9.beginText();
		CSpg9.setFont(PDType1Font.TIMES_ROMAN, 11);
		CSpg9.newLineAtOffset(72, 735);
		CSpg9.showText(p9para1);   
		CSpg9.newLineAtOffset(0, -14);
		CSpg9.showText(p9para2);           
		CSpg9.endText();
		CSpg9.close();



		PDFont fontPlain = PDType1Font.TIMES_ROMAN;
		//Variables to define table positioning
		float margin = 72;
		float yStartNewPage = pages.get(7).getMediaBox().getHeight() - (2 * margin);
		float tableWidth = pages.get(7).getMediaBox().getWidth() - (2 * margin);
		boolean drawContent = true;
		float bottomMargin = 70;
		float yPosition = 710;

		BaseTable table = new BaseTable(yPosition, yStartNewPage,
				bottomMargin, tableWidth, margin, document, pages.get(7), true, drawContent);

		ArrayList<Row<PDPage>> row = new ArrayList<Row<PDPage>>();

		for(int i = 0; i < trees.size(); i++) {
			row.add(table.createRow(15f));
			row.get(i).setLineSpacing(1.3f);
			//Cell 1
			Cell<PDPage> cell = row.get(i).createCell(12.4f, "T" + trees.get(i).ID);        
			cell.setFontSize(11);     
			cell.setFont(fontPlain);
			cell.setHeight(cell.getTextHeight()+7f);
			cell.setAlign(HorizontalAlignment.LEFT);
			cell.setValign(VerticalAlignment.MIDDLE);
			//Cell 2
			cell = row.get(i).createCell(87.6f, trees.get(i).Recommendation);    
			cell.setFontSize(11); 
			cell.setFont(fontPlain);
			cell.setHeight(cell.getTextHeight()+7f);
			cell.setAlign(HorizontalAlignment.LEFT);
			cell.setValign(VerticalAlignment.MIDDLE);

			table.addHeaderRow(row.get(i));
		}

		table.draw();
	}
	
	private static void genCell(Cell<PDPage> cell, String content, PDFont font, ArrayList<Row<PDPage>> row, float size) {
		cell = row.get(row.size()-1).createCell(size, content,HorizontalAlignment.LEFT,VerticalAlignment.MIDDLE);    
		cell.setFontSize(11);     
		cell.setFont(font);
		cell.setHeight(cell.getTextHeight()+7f);
	}
	
//	private static void newPage(BaseTable table, ArrayList<PDPage> ps11, float margin, 
//			float yStartNewPage, float tableWidth, boolean drawContent, float bottomMargin, float yPosition) throws IOException{
//		table.draw();
//		ps11.add(new PDPage(PDRectangle.A4));
//		table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps.size()-1), true, drawContent);
//
//	}
	
	
	private static void genPage11() throws IOException{ //empty


		PDFont fontPlain = PDType1Font.TIMES_ROMAN;
		PDFont fontBold = PDType1Font.TIMES_BOLD;
		//Variables to define table positioning
		float margin = 50;
		float yStartNewPage = pages.get(4).getMediaBox().getHeight() - (2 * margin);
		float tableWidth = pages.get(4).getMediaBox().getWidth() - (2 * margin);
		boolean drawContent = true;
		float bottomMargin = 70;
		float yPosition = 750;

		ArrayList<PDPage> ps11 = new ArrayList<PDPage>();
		ps11.add(new PDPage(PDRectangle.A4));
		BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(0), true, drawContent);		
		ArrayList<Row<PDPage>> row = new ArrayList<Row<PDPage>>();
		
		for(int i = 0; i < trees.size()/2; i++) {
			//Row 1
			row.add(table.createRow(13.5f));
			Cell<PDPage> cell = null;
			genCell(cell, "", fontBold, row, 20f);
			genCell(cell, "T" + trees.get(i*2).ID, fontBold, row, 40f);
			genCell(cell, "T" + trees.get(i*2+1).ID, fontBold, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Species", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).species, fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).species, fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Common Name", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).commonName, fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).commonName, fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Location", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).location, fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).location, fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "DBH (mm)", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).DBH + "mm", fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).DBH + "mm", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "DAB (mm)", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).DAB + "mm", fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).DAB + "mm", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Height (m)", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).height + "m", fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).height + "m", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Canopy Spread (m)", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).canopySpread + "m", fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).canopySpread + "m", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "SULE", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).SULE, fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).SULE, fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Age Class", fontPlain, row, 20f);
			genCell(cell, "", fontPlain, row, 40f);
			genCell(cell, "", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "TPZ (m) Radius", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).TPZ + "m (R)", fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).TPZ + "m (R)", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "SRZ (m) Radius", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).SRZ + "m (R)", fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).SRZ + "m (R)", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			//Cell 2 //TODO add squared symbol
			row.add(table.createRow(13.5f));
			genCell(cell, "TPZ M2", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).TPZM2 + "m2", fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).TPZM2 + "m2", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "SRZ M2", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).SRZM2 + "m2", fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).SRZM2 + "m2", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Distance to development (approximate locations)", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).DTD, fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).DTD, fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Proposed Encroachment per client's plan", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).clientEncroach + "%", fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).clientEncroach + "%", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Proposed Encroachment per our recommendations", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).RecEncroach + "%", fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).RecEncroach + "%", fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Health and Structure", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).HAS, fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).HAS, fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}

			//new row
			row.add(table.createRow(13.5f));
			genCell(cell, "Recommendations", fontPlain, row, 20f);
			genCell(cell, trees.get(i*2).Recommendation, fontPlain, row, 40f);
			genCell(cell, trees.get(i*2+1).Recommendation, fontPlain, row, 40f);
			table.addHeaderRow(row.get(i));	
			
			if(table.getHeaderAndDataHeight() > 750) {
				table.draw();
				ps11.add(new PDPage(PDRectangle.A4));
				table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, ps11.get(ps11.size()-1), true, drawContent);
			}			

		}
		
		String SULEkey1 = "SULE key: Long (L) 40+, Medium (M) 15-30, Short (S) 5-10, Remove (R) Under 5, Too small (TS)."; 
		String SULEkey2 = "Age class key: Young (Y), Semi mature (SM), Mature (M), Over mature (OM) or Dead (D)";
		PDPageContentStream CSpg11 = new PDPageContentStream(document, ps11.get(ps11.size()-1), true, true, false);
		CSpg11.setNonStrokingColor(Color.BLACK);
		CSpg11.beginText();
		CSpg11.setFont(PDType1Font.TIMES_ROMAN, 11);
		CSpg11.newLineAtOffset(72, 80);
		CSpg11.showText(SULEkey1);   
		CSpg11.newLineAtOffset(0, -14);
		CSpg11.showText(SULEkey2);           
		CSpg11.endText();

		table.draw();
		CSpg11.close();

		pages.remove(11);
		for(int i = 0; i < ps11.size(); i++) pages.add(11+i,ps11.get(i));
	}
















}

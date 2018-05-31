package com.cyberiansoft.test.ios10_client.utils;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;

public class PDFReader {
	
	public static String getPDFText(File pdfdocument) {		
		String pdftext = "";
		try{
		    PDDocument document = null; 
		    document = PDDocument.load(pdfdocument);
		    document.getClass();
		    if( !document.isEncrypted() ){
		        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		        stripper.setSortByPosition( true );
		        PDFTextStripper Tstripper = new PDFTextStripper();
		        pdftext = Tstripper.getText(document);
		        System.out.println("Text:"+pdftext);
		    }
		    }catch(Exception e){
		        e.printStackTrace();
		    }
		return pdftext;
	}

}

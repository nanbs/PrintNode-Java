package com.jasci.printnode.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubmittingAPrintJob {

	static Logger log = LoggerFactory.getLogger(SubmittingAPrintJob.class.getName());

	/**
	 * @Description: MAIN method - Call printZPLFormat to test String
	 * @param str
	 * @throws IOException
	 */
	public static void main(String args[]) throws Exception{
		
		
		log.debug("Inside main Method" + args.toString() );
		
	    //String str = "https:YOURFILE.pdf";  //File stored online
		//printPDFFormat(str);

		String str = "^XA^FO100,100^BY3^B2N,150,Y,N,N^FD123456^FS^XZ";
		int printerId = 174495; 
		
		//printZPLFormat(str);  
		
		printZPLFormatByStrAndPrinterId(str, printerId);

	}
	
	
	
	/**
	 * @Description: Convert ZPL string format into Base64 and create print job
	 * @param str
	 * @return 
	 * @throws IOException
	 */
	public static boolean printZPLFormat(String str) throws Exception{
		
		
		log.debug("Inside printZPLFormat 1: " + str);
		boolean success = false;


		try {
			
			//Firstly, we'll create a new Auth object.
			Auth myAuth = new Auth();
			//We're going to set our credentials for future use with Auth.setApiKey.
			myAuth.setApiKey("YourAPIkey");
			log.debug("API key " + Arrays.toString(myAuth.getCredentials()));
			//We then make an APIClient, which saves our authorization to use with requests.
			APIClient aClient = new APIClient(myAuth);
			log.debug("APIClient " + aClient.getWhoami());

			//If we're going to submit a printjob, we need a printer. We'll get one from our list of printers.
			Printer[] myPrinters = aClient.getPrinters("");
			log.debug("PRINTERS : " + myPrinters.toString());
			Printer myPrinterForPrintJob = myPrinters[11];   //Printer ID sequence under Printers on PrintNode account
			
			log.debug("MY PRINTER : " + myPrinterForPrintJob.getId());
			log.debug("qty: " + myPrinterForPrintJob.getQty());
			log.debug("Printer toString: " + myPrinterForPrintJob.toString());


			//Create File to print
		   //File src = new File("C:\\YOURFILE.txt");
		   
			//Create File to print
	        //String str = FileUtils.readFileToString(src);
							
			 //encode data using BASE64
							String encoded = DatatypeConverter.printBase64Binary(str.getBytes());
							//System.out.println("encoded value is \t" + encoded);
							log.debug("encoded value is \t" + encoded);

			 // Decode data 
				//String decoded = new String(DatatypeConverter.parseBase64Binary(encoded));
				//System.out.println("decoded value is \t" + decoded);

				//System.out.println("original value is \t" + str);
				
			//We now need to create a PrintJobJson object so we can put use it in a request.
			//This takes 5 arguments:
			//printerId,title,contentType,content and source.
			PrintJobJson myPrintJobCreation = new PrintJobJson(myPrinterForPrintJob.getId(),"PrintNode-Java","raw_base64", encoded, "PrintNode"); 
			
			log.debug("myPrintJobCreation : " + myPrinterForPrintJob.getState() );
			log.debug("QTY : " + myPrintJobCreation.getQty()) ;
			log.debug("PRINTER ID " +  myPrinterForPrintJob.getId() );
			
			//We'll then make the request by using APIClient.createPrintJob
			int myPrintJobId = aClient.createPrintJob(myPrintJobCreation);
			log.debug("myPrintJobId: " + myPrintJobId);
			//We can then look at the information of the PrintJob by using the id we just got on creation
			PrintJob myPrintJob = aClient.getPrintJobs(Integer.toString(myPrintJobId))[0];
			log.debug("myPrintJob result: " + myPrintJob);
			      
			} catch (Exception ObjectJasciException) {	
				ObjectJasciException.printStackTrace();
				log.error(".printZPLFormat() throws exception: " + ObjectJasciException.getMessage());
				throw ObjectJasciException;
			}
		return true;			

	}
	
	
	
	/**
	 * @Description:  Convert ZPL string format into Base64 and create print job (String str, int printerId,)
	 * @param str
	 * @param printer
	 * @param computer
	 * @return
	 * @throws Exception
	 */
	public static boolean printZPLFormatByStrAndPrinterId(String str, int printerId) throws Exception{

		log.debug("Inside printZPLFormat 2: " + str);

		try {
			
			//Firstly, we'll create a new Auth object.
			Auth myAuth = new Auth();
			//We're going to set our credentials for future use with Auth.setApiKey.
			myAuth.setApiKey("YourAPIkey");
			log.debug("API key " + Arrays.toString(myAuth.getCredentials()));
			//We then make an APIClient, which saves our authorization to use with requests.
			APIClient aClient = new APIClient(myAuth);
			log.debug("APIClient " + aClient.getWhoami());=
			
			 //encode data using BASE64
							String encoded = DatatypeConverter.printBase64Binary(str.getBytes());
							//System.out.println("encoded value is \t" + encoded);
							log.debug("encoded value is \t" + encoded);

			//We now need to create a PrintJobJson object so we can put use it in a request.
			//This takes 5 arguments:
			//printerId,title,contentType,content and source.
			PrintJobJson myPrintJobCreation = new PrintJobJson(printerId,"PrintNode-Java","raw_base64",encoded, "PrintNode"); 

			
			log.debug("printerid : " + printerId );
			log.debug("QTY : " + myPrintJobCreation.getQty()) ;
			
			//We'll then make the request by using APIClient.createPrintJob
			int myPrintJobId = aClient.createPrintJob(myPrintJobCreation);
			log.debug("myPrintJobId: " + myPrintJobId);
			//We can then look at the information of the PrintJob by using the id we just got on creation
			PrintJob myPrintJob = aClient.getPrintJobs(Integer.toString(myPrintJobId))[0];
			log.debug("myPrintJob result: " + myPrintJob);
			      
			} catch (Exception ObjectJasciException) {	
				ObjectJasciException.printStackTrace();
				log.error(".printZPLFormat() throws exception: " + ObjectJasciException.getMessage());
				throw ObjectJasciException;
			}
		return true;			

	}
	
	
	/**
	 * @Description: Convert PDF string format into Base64 and create print job
	 * @param str
	 * @throws Exception 
	 */
	public static void printPDFFormat(String str) throws Exception{		
		//final Logger log = LoggerFactory.getLogger(SubmittingAPrintJob.class.getName());
		

		//Firstly, we'll create a new Auth object.
		Auth myAuth = new Auth();

		//We're going to set our credentials for future use with Auth.setApiKey.
		myAuth.setApiKey("ae92b2898e68ec215b3735d3860e2e0795661ca8");

		//We then make an APIClient, which saves our authorization to use with requests.
		APIClient aClient = new APIClient(myAuth);

		//If we're going to submit a printjob, we need a printer. We'll get one from our list of printers.
		Printer[] myPrinters = aClient.getPrinters("");
		log.debug("PRINTERS : " + myPrinters.toString());
		Printer myPrinterForPrintJob = myPrinters[15];  // ID sequence for Printer under Printers on PrintNode account 
		log.debug("MY PRINTER : " + myPrinterForPrintJob);

		try {
			//We now need to create a PrintJobJson object so we can put use it in a request.
			//This takes 5 arguments:
			//printerId,title,contentType,content and source.

			   			    
			 //encode data using BASE64
			    //String encoded = DatatypeConverter.printBase64Binary(str.getBytes());
				//log.debug("encoded value is \t" + encoded);
			
			//PrintJobJson myPrintJobCreation = new PrintJobJson(myPrinterForPrintJob.getId(),"PrintNode-Java","pdf_base64", encoded, "PrintNode", 1); 
			PrintJobJson myPrintJobCreation = new PrintJobJson(myPrinterForPrintJob.getId(),"PrintNode-Java","pdf_uri", str, "PrintNode"); //use for online URL

			log.debug("myPrintJobCreation : " + myPrinterForPrintJob.getState() );
			//log.debug("COPIES " +  myPrinterForPrintJob.getCapabilities().getCopies());
			log.debug("PRINTER ID " +  myPrinterForPrintJob.getId() );
			
			//We'll then make the request by using APIClient.createPrintJob
			int myPrintJobId = aClient.createPrintJob(myPrintJobCreation);
			log.debug("myPrintJobId: " + myPrintJobId);
			//We can then look at the information of the PrintJob by using the id we just got on creation
			PrintJob myPrintJob = aClient.getPrintJobs(Integer.toString(myPrintJobId))[0];
			log.debug("myPrintJob result: " + myPrintJob);
		
		} catch (Exception ObjectJasciException) {	
			ObjectJasciException.printStackTrace();
			log.error(".printPDFFormat() throws exception: " + ObjectJasciException);
		}		

	}
	

	
}
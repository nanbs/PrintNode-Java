package com.jasci.printnode.api;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReadingScale {

	static Logger log = LoggerFactory.getLogger(ReadingScale.class.getName());

	/**
	 * @Description: Main method 
	 * @param str
	 * @throws IOException
	 */
	public static void main(String args[]) throws Exception{
		
		
		log.debug("Inside main method" + args.toString() );
		
		getWeight(39686);
	}
	
	/**
	 * @Description: Get weight from scale
	 * @param str
	 * @return 
	 * @return 
	 * @throws Exception 
	 * @throws IOException
	 */

	public static double getWeight(int computerId) throws Exception{
		
		HashMap<String, BigInteger> measurement= null; 
		BigInteger measurementValue = null; 	   
		double weight = 0; 
	    double nineZeros = 1000000000; 

		log.debug("Inside getWeight: " + computerId);

		try {
			
			//Firstly, we'll create a new Auth object.
			Auth myAuth = new Auth();
			//We're going to set our credentials for future use with Auth.setApiKey.
			myAuth.setApiKey("EnterYourAPIKEY");
			log.debug("API key " + Arrays.toString(myAuth.getCredentials()));
			//We then make an APIClient, which saves our authorization to use with requests.
			APIClient aClient = new APIClient(myAuth);
			log.debug("APIClient " + aClient.getWhoami());

			//If we're going to get weight, we need a scale. We'll get one using Computer Id.
			Scale myScales = null;
			try {
				myScales = aClient.getScales(computerId)[0];
			} catch (Exception ObjectJasciException) {
				ObjectJasciException.printStackTrace();
				log.error("can't find scale: " + ObjectJasciException.getMessage());
				ObjectJasciException.printStackTrace();
				throw ObjectJasciException;
			}
			log.debug("SCALES : " + myScales.toString());
				//get Weight only if a scale is found
				if (myScales != null){
						
						//Original value from scale as a Hash Map (key, value)
			        	measurement = myScales.getMeasurement();
			        	//Get value only from hash map as Big Integer
						measurementValue = (BigInteger) measurement.values().toArray()[0];
						//Convert measurementValue into Double
						Double value = measurementValue.doubleValue();
						//Divide value into 1000000000 to get decimal
						weight = value/nineZeros;
					
					System.out.println("MEASUREMENT: " + measurement);
					System.out.println("MEASUREMENT VALUE: " + measurementValue);
					System.out.println("WEIGHT: " + weight);
				}
			      
			} catch (Exception ObjectJasciException) {	
				ObjectJasciException.printStackTrace();
				log.error(".getWeightFromScale() throws exception: " + ObjectJasciException.getMessage());
				throw ObjectJasciException;
			}
		return weight;			

	}
	
}
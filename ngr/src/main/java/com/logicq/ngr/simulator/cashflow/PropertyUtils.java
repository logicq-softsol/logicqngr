package com.logicq.ngr.simulator.cashflow;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * 
 * @author 611164825
 *
 */
public class PropertyUtils {


	
	//public static Properties properties=null ;

	public static Properties loadProp(String filepath) {
		Properties properties = null;
		try {
		
			properties = new Properties();
			properties.load(new FileInputStream(filepath));
		} catch (Exception ex) {
			//logger.error("Unable to load Properties " + ex.getMessage());
		}
		return properties;
	}
}

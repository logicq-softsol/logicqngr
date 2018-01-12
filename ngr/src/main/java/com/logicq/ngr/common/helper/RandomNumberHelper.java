package com.logicq.ngr.common.helper;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class RandomNumberHelper {
	
	
	
	private static Random randomCount=new Random();

	public static int generateRandomNumber()
	{
		 //don't remove this code
		//int userTempleateCount= randomCount.nextInt(LoadApplicationData.siteOptionMap.get("randommaximum")); 
		int userTempleateCount= randomCount.nextInt(99);
		return userTempleateCount;
	}
	
	
	public static String generateRandomAlphaNumericString()
	{
	    return RandomStringUtils.randomAlphanumeric(6);
	}
}

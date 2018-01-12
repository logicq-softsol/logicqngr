package com.logicq.ngr.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.client.druid.DruidRestClient;
import com.logicq.ngr.common.constant.DateConstant;

public class ObjectFactory {

	public static DruidRestClient<?> getDruidRestClient() {
		return new DruidRestClient();
	}
	
	public static ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	public static DateFormat getDateFormatForDisplay(){
		return new SimpleDateFormat(DateConstant.dateFormatForDisplay);
	}
	
	public static SimpleDateFormat getSimpleDateFormat(){
		return new SimpleDateFormat(DateConstant.dateFormat);
	} 
}
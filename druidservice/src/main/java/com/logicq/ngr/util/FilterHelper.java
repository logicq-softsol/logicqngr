package com.logicq.ngr.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.logicq.ngr.model.common.Rule;
import com.logicq.ngr.model.common.Rules;
import com.logicq.ngr.model.druid.common.Fields;
import com.logicq.ngr.model.druid.common.Filter;

public class FilterHelper {
	public static Map<String, String> expressionsMap = new HashMap<String, String>();
	static {
		expressionsMap.put("AND", "and");
		expressionsMap.put("OR", "or");
		expressionsMap.put(">", "greaterThan");
		expressionsMap.put("equal", "selector");
		expressionsMap.put("<", "lessThan");
		expressionsMap.put("!", "not");
		expressionsMap.put(">=", "greaterThanEqual");
		expressionsMap.put("<=", "LessThanEqual");
		expressionsMap.put("in", "in");

	}
	
	
	private static FilterHelper instance=null;
	
	private  FilterHelper(){
	}
	
	/**
	 * 
	 * @return
	 */
	public static FilterHelper getInstance(){
		if(null==instance){
		synchronized (FilterHelper.class) {
			if(null==instance){
				instance= new FilterHelper();
			}
		 }
		}
		return instance;
	}
		
	private static void replaceFilterTypeForDruidQuery(List<Rule> p_rules, List<Fields> p_fields) {
		if (null != p_rules && !p_rules.isEmpty()) {
			p_rules.forEach((rule) -> {
				Fields field = new Fields();
				if (null != rule.getCondition() && !rule.getCondition().isEmpty()) {
					if (null != p_fields) {
						field.setType(expressionsMap.get(rule.getCondition()));
						p_fields.add(field);
					}
				} else {
					field.setDimension(rule.getField());
					field.setType(expressionsMap.get(rule.getOperator()));
					field.setValue(rule.getValue());
					field.setDimension(rule.getId());
					p_fields.add(field);
				}
				List<Rule> l_rules = rule.getRules();
				if (null != l_rules && !l_rules.isEmpty()) {
					replaceFilterTypeForDruidQuery(rule.getRules(), field.getFields());
				}
			});
		}
	}
	
	public static Filter getFilter(Rules p_rules) {
		Filter filter = null;
		if (null != p_rules && null != p_rules.getRules() && !p_rules.getRules().isEmpty()) {
			filter = new Filter();
			filter.setType(expressionsMap.get(p_rules.getCondition()));
			List<Fields> fields = new ArrayList<>();
			List<Rule> l_rules = p_rules.getRules();
			if (null != l_rules && !l_rules.isEmpty()) {
				replaceFilterTypeForDruidQuery(l_rules, fields);
			}
			filter.setFields(fields);
		}
		return filter;
	}
			
}
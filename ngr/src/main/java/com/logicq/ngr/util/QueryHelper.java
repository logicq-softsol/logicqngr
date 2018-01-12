package com.logicq.ngr.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.logicq.ngr.common.helper.DruidHelper;
import com.logicq.ngr.controllers.LoadApplicationData;
import com.logicq.ngr.helper.ReportHelper;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.common.Configuration;
import com.logicq.ngr.model.common.Rule;
import com.logicq.ngr.model.common.Rules;
import com.logicq.ngr.model.druid.common.Fields;
import com.logicq.ngr.model.druid.common.Filter;
import com.logicq.ngr.model.druid.request.ReportRequest;

@Component
public class QueryHelper {
	@Autowired
	DruidHelper druidHelper;
	
	public static Map<String, String> mysqlOperatorsMap = new HashMap<String, String>();
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

		mysqlOperatorsMap.put("OR", "OR");
		mysqlOperatorsMap.put("AND", "AND");
		mysqlOperatorsMap.put(">", ">");
		mysqlOperatorsMap.put(">=", ">=");
		mysqlOperatorsMap.put("IN", "IN");
		mysqlOperatorsMap.put("equal", "=");
		mysqlOperatorsMap.put("between", "between");

	}

	public Filter getFilter(Rules p_rules) {
		Filter filter = new Filter();
		if (null != p_rules) {
			filter.setType(LoadApplicationData.operatorsMap.get(p_rules.getCondition()).getDruidType());
			List<Fields> fields = new ArrayList<>();
			List<Rule> l_rules = p_rules.getRules();
			if (null != l_rules && !l_rules.isEmpty()) {
				replaceFilterTypeForDruidQuery(l_rules, fields);
			}
			filter.setFields(fields);
		}
		return filter;
	}

	private void replaceFilterTypeForDruidQuery(List<Rule> p_rules, List<Fields> p_fields) {
		if (null != p_rules && !p_rules.isEmpty()) {
			p_rules.forEach((rule) -> {
				Fields field = new Fields();
				if (!StringUtils.isEmpty(rule.getCondition())) {
					if (null != p_fields) {
						field.setType(LoadApplicationData.operatorsMap.get(rule.getCondition()).getDruidType());
						p_fields.add(field);
					}
				} else {
					field.setDimension(rule.getField());
					field.setType(LoadApplicationData.operatorsMap.get(rule.getOperator()).getDruidType());
					field.setValue(rule.getValue());
					p_fields.add(field);
				}
				List<Rule> l_rules = rule.getRules();
				if (null != l_rules && !l_rules.isEmpty()) {
					replaceFilterTypeForDruidQuery(rule.getRules(), field.getFields());
				}
			});
		}
	}

	
	public String prepareHqlQueryForReportRequest(ReportRequest reportRequest, Class claz) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Configuration configuration = reportRequest.getConfiguration();
		Rules p_filters = reportRequest.getFilter();
		StringBuilder specificColumnsQuery = new StringBuilder(" Select  ");
		List<Attribute> listOfColumns = configuration.getProperties();
		if (null != listOfColumns && !listOfColumns.isEmpty()) {
			String columns = "";
			for (int i = 0; i < listOfColumns.size(); i++) {
				Attribute attribute = listOfColumns.get(i);
				if (i < listOfColumns.size() - 1) {
					columns = columns + "al." + attribute.getName() + " as " + attribute.getName() + ",";
				} else {
					columns = columns + "al." + attribute.getName() + " as " + attribute.getName();
				}
			}
			specificColumnsQuery.append(columns);
		}
		StringBuilder selectQuery = new StringBuilder(" from " + claz.getName() + " al ");
		StringBuilder whereQuery = new StringBuilder(" where ");
		if (null != p_filters && null != p_filters.getRules() && !p_filters.getRules().isEmpty()) {
			String p_filterCondition = p_filters.getCondition();
			List<Rule> p_filterList = p_filters.getRules();
			if (!p_filterList.isEmpty())
				prepareQuery(whereQuery, p_filterCondition, p_filterList);
			selectQuery.append(whereQuery);
			if (!ReportHelper.isEmpty(reportRequest.getIntervals())) {
				String intervals = druidHelper.generateIntervalString(reportRequest.getIntervals());
				String[] inputDatesplit = intervals.split("/");
				String fromDate = formatter.format(sdf.parse(inputDatesplit[0]));
				String toDate = formatter.format(sdf.parse(inputDatesplit[1]));
				String betweenQuery = " AND (al.timeStamp BETWEEN '" + fromDate + "' AND '" + toDate + "')";
				selectQuery.append(betweenQuery);
			}
		} else {
			if (!ReportHelper.isEmpty(reportRequest.getIntervals())) {
				String intervals = druidHelper.generateIntervalString(reportRequest.getIntervals());
				String[] inputDatesplit = intervals.split("/");
				Date d = sdf.parse(inputDatesplit[0]);
				String fromDate = formatter.format(d);
				Date d2 = sdf.parse(inputDatesplit[1]);
				String toDate = formatter.format(d2);
				String betweenQuery = " al.timeStamp BETWEEN '" + fromDate + "' AND '" + toDate + "'";
				selectQuery.append(whereQuery);
				selectQuery.append(betweenQuery);
			}
		}
		specificColumnsQuery.append(selectQuery);
		return specificColumnsQuery.toString();

	}

	private static void prepareQuery(StringBuilder whereQuery, String p_filterCondition, List<Rule> p_filterList) {
		for (int i = 0; i < p_filterList.size(); i++) {
			Rule l_filter = p_filterList.get(i);
			if (null != l_filter.getRules() && !l_filter.getRules().isEmpty()) {
				if (l_filter.getRules().size() > 1) {
					whereQuery.append(" ( ");
				}

				prepareQuery(whereQuery, l_filter.getCondition(), l_filter.getRules());
				if (l_filter.getRules().size() > 1) {
					whereQuery.append(" ) ");
				}
				if (i < p_filterList.size() - 1) {
					whereQuery.append(" " + p_filterCondition);
				}

			} else {
				if (!l_filter.getId().isEmpty()) {
					whereQuery.append(" al.").append(l_filter.getId()).append(" ")
							.append(LoadApplicationData.operatorsMap.get(l_filter.getOperator()).getMysqlType())
							.append(" ").append("'").append(l_filter.getValue()).append("'");
					if (i < p_filterList.size() - 1) {
						whereQuery.append(" " + p_filterCondition);
					}
				}

			}
		}
	}


}
package com.logicq.ngr.constant;

public enum ReportType {
	DYNAMIC_REPORT("dynamicreport"),DASH_BOARD("dashboardreport"),ALRAM("event"), GRAPH("Graph"),TOPN("TopN"),GROUP_BY("groupBy"),REPORT("report"),NORMAL("normal"),REALTIME("realtime"),DEFAULT("default");
	private final String value;

	private ReportType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static ReportType getEnum(String value) {
		for (ReportType v : values())
			if (v.getValue().equals(value))
				return v;
		throw new IllegalArgumentException();
	}
}
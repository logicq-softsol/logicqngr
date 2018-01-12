package com.logicq.ngr.model.druid.request;

import java.io.Serializable;
import java.util.List;

import com.logicq.ngr.constant.Constants;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.common.Having;
import com.logicq.ngr.model.common.LimitSpec;

public class DruidGroupByRequest  extends DruidRequest implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5381320580477449046L;
	private String[] dimensions;
	private LimitSpec limitSpec;
	private Having having;
	
	public String[] getDimensions() {
		return dimensions;
	}

	public LimitSpec getLimitSpec() {
		return limitSpec;
	}
	
	public Having getHaving() {
		return having;
	}

	private DruidGroupByRequest(DruidBaseRequestBuilder baseRequestBuilder, DruidRequestBuilder druidRequestBuilder,
			GroupByRequestBuilder groupByRequestBuilder) {
		super(baseRequestBuilder, druidRequestBuilder);
		this.dimensions = groupByRequestBuilder.dimensions;
		this.limitSpec = groupByRequestBuilder.limitSpec;
		this.having = groupByRequestBuilder.having;

	}

	public static class GroupByRequestBuilder {
		private String[] dimensions;
		private LimitSpec limitSpec;
		private Having having;
		private DruidRequestBuilder druidRequestBuilder;
		private DruidBaseRequestBuilder baseRequestBuilder;

		public GroupByRequestBuilder(DruidBaseRequestBuilder baseRequestBuilder, ReportRequest reportrequest) {
			//Build dimensions
			String[] dimensions = null;
			if (Constants.TABLE.equalsIgnoreCase(reportrequest.getConfiguration().getType())) {
				List<Attribute> properties = reportrequest.getConfiguration().getProperties();
				if (null != properties && !properties.isEmpty()) {
					dimensions = new String[properties.size()];
					for (int i = 0; i < properties.size(); i++) {
						dimensions[i] = properties.get(i).getId();
					}
				}
			} else {
				List<Attribute> legends = reportrequest.getConfiguration().getLegends();
				if (null != legends && !legends.isEmpty()) {
					dimensions = new String[legends.size()];
					for (int i = 0; i < legends.size(); i++) {
						dimensions[i] = legends.get(i).getId();
					}
				}
			}
			
			this.baseRequestBuilder = baseRequestBuilder;
			this.druidRequestBuilder = new DruidRequestBuilder(baseRequestBuilder, reportrequest);
			this.dimensions = dimensions;
			this.limitSpec = null;
			this.having = null;
		}

		public DruidGroupByRequest build() {
			return new DruidGroupByRequest(baseRequestBuilder, druidRequestBuilder, this);
		}
	}



}

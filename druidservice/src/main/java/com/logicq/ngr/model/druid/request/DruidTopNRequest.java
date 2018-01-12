package com.logicq.ngr.model.druid.request;

import java.io.Serializable;
import java.util.List;

import com.logicq.ngr.model.common.Attribute;

public class DruidTopNRequest extends DruidRequest implements Serializable {

	private String dimension;
	private int threshold;
	private String metric;

	public String getDimension() {
		return dimension;
	}

	public int getThreshold() {
		return threshold;
	}

	public String getMetric() {
		return metric;
	}

	private DruidTopNRequest(DruidBaseRequestBuilder baseRequestBuilder,DruidRequestBuilder druidRequestBuilder, TopNRequestBuilder topnRequestBuilder) {
		super(baseRequestBuilder,druidRequestBuilder);
		this.dimension = topnRequestBuilder.dimension;
		this.threshold = topnRequestBuilder.threshold;
		this.metric = topnRequestBuilder.metric;
	}

	public static class TopNRequestBuilder {
		private String dimension;
		private int threshold;
		private String metric;
		private DruidRequestBuilder druidRequestBuilder;
		private DruidBaseRequestBuilder baseRequestBuilder;

		public TopNRequestBuilder(String metric, DruidBaseRequestBuilder baseRequestBuilder,
				ReportRequest reportrequest) {
			List<Attribute> legends = reportrequest.getConfiguration().getLegends();
			if (null != legends && !legends.isEmpty()) {
				for (Attribute legend : legends) {
					this.dimension = legend.getId();
					break;
				}
			}
			this.baseRequestBuilder = baseRequestBuilder;
			this.druidRequestBuilder = new DruidRequestBuilder(baseRequestBuilder, reportrequest);
			this.threshold = reportrequest.getPagination().getPagesize();
			this.metric = metric;
		}

		public DruidTopNRequest build() {
			return new DruidTopNRequest(baseRequestBuilder,druidRequestBuilder, this);
		}
	}
	
}
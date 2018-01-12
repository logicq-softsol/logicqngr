package com.logicq.ngr.model.druid.request;

import java.util.List;

import com.logicq.ngr.constant.Constants;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.common.Metric;
import com.logicq.ngr.model.druid.common.PagingSpec;

public class DruidSelectRequest extends DruidRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2972429363998388520L;

	private String[] dimensions;
	private String[] metrics;
	private PagingSpec pagingSpec;

	protected DruidSelectRequest(DruidBaseRequestBuilder baseRequestBuilder, DruidRequestBuilder druidRequestBuilder,
			SelectRequestBuilder selectRequestBuilder) {
		super(baseRequestBuilder, druidRequestBuilder);
		this.dimensions = selectRequestBuilder.dimensions;
		this.metrics = selectRequestBuilder.metrics;
		this.pagingSpec = selectRequestBuilder.pagingSpec;
	}

	public String[] getDimensions() {
		return dimensions;
	}

	public String[] getMetrics() {
		return metrics;
	}

	public PagingSpec getPagingSpec() {
		return pagingSpec;
	}

	public static class SelectRequestBuilder {
		private String[] dimensions;
		private String[] metrics;
		private PagingSpec pagingSpec;
		private DruidRequestBuilder druidRequestBuilder;
		private DruidBaseRequestBuilder baseRequestBuilder;

		public SelectRequestBuilder(DruidBaseRequestBuilder baseRequestBuilder, ReportRequest reportRequest) {
			// Build dimensions
			String[] dimensions = null;
			if (Constants.TABLE.equalsIgnoreCase(reportRequest.getConfiguration().getType())
					|| Constants.TABLE_REAL_TIME.equalsIgnoreCase(reportRequest.getConfiguration().getType())) {
				List<Attribute> properties = reportRequest.getConfiguration().getProperties();
				if (null != properties && !properties.isEmpty()) {
					dimensions = new String[properties.size()];
					for (int i = 0; i < properties.size(); i++) {
						dimensions[i] = properties.get(i).getId();
					}
				}
			} else {
				List<Attribute> legends = reportRequest.getConfiguration().getLegends();
				if (null != legends && !legends.isEmpty()) {
					dimensions = new String[legends.size()];
					for (int i = 0; i < legends.size(); i++) {
						dimensions[i] = legends.get(i).getId();
					}
				}
			}

			List<Metric> metrics = reportRequest.getConfiguration().getMetrics();
			if (null != metrics && !metrics.isEmpty()) {
				String[] druidMetrics = new String[metrics.size()];
				for (int i = 0; i < metrics.size(); i++) {
					druidMetrics[i] = metrics.get(i).getName();
				}
				this.metrics = druidMetrics;
			}

			this.baseRequestBuilder = baseRequestBuilder;
			this.druidRequestBuilder = new DruidRequestBuilder(baseRequestBuilder, reportRequest);
			this.dimensions = dimensions;

			/*
			 * this.pagingSpec = new
			 * PagingSpecBuilder(reportrequest.getPagination().
			 * getPagingIdentifiers(),
			 * reportrequest.getPagination().getPagesize(),
			 * reportrequest.getPagination().getPagenumber()).build();
			 */
			PagingSpec pagingSpec = reportRequest.getPagination().getPagingSpec();
			if (null == pagingSpec) {
				pagingSpec = new PagingSpec();
				reportRequest.getPagination().setPagingSpec(pagingSpec);
			}
			pagingSpec.setThreshold(reportRequest.getPagination().getPagesize());
			this.pagingSpec = pagingSpec;
		}

		public DruidSelectRequest build() {
			return new DruidSelectRequest(baseRequestBuilder, druidRequestBuilder, this);
		}
	}

}

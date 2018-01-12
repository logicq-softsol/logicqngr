package com.logicq.ngr.model.druid.request;

import java.util.ArrayList;
import java.util.List;

import com.logicq.ngr.constant.AggregationEnum;
import com.logicq.ngr.constant.Constants;
import com.logicq.ngr.model.common.Metric;
import com.logicq.ngr.model.druid.common.Aggregations;

public class DruidTimeseriesRequest extends DruidRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 452705996758437819L;

	protected DruidTimeseriesRequest(DruidBaseRequestBuilder druidBaseRequestBuilder,
			DruidRequestBuilder druidRequestBuilder) {
		super(druidBaseRequestBuilder, druidRequestBuilder);
	}

	public static class TimeseriesRequestBuilder {
		private DruidRequestBuilder druidRequestBuilder;
		private DruidBaseRequestBuilder druidBaseRequestBuilder;
		private List<Aggregations> timeseriesAggregations = new ArrayList<>();

		public TimeseriesRequestBuilder(DruidBaseRequestBuilder druidBaseRequestBuilder, ReportRequest reportrequest) {
			this.druidBaseRequestBuilder = druidBaseRequestBuilder;
			this.druidRequestBuilder = new DruidRequestBuilder(druidBaseRequestBuilder, reportrequest);

			if (null != reportrequest && null != reportrequest.getConfiguration()
					&& null != reportrequest.getConfiguration().getMetrics()) {
				List<Metric> metrics = reportrequest.getConfiguration().getMetrics();
				metrics.forEach((metric) -> {
					if (AggregationEnum.AVERAGE.getValue().equalsIgnoreCase(metric.getAggregationType())) {
						Aggregations minAggregation = new Aggregations.AggregationsBuilder(Constants.DoubleMin,
								metric.getName() + Constants.UnderScore + Constants.MIN, metric.getName()).build();
						Aggregations maxAggregation = new Aggregations.AggregationsBuilder(Constants.DoubleMax,
								metric.getName() + Constants.UnderScore + Constants.MAX, metric.getName()).build();
						timeseriesAggregations.add(minAggregation);
						timeseriesAggregations.add(maxAggregation);
					}
				});
			}
		}

		public DruidTimeseriesRequest build() {
			DruidTimeseriesRequest druidTimeseriesRequest = new DruidTimeseriesRequest(druidBaseRequestBuilder,
					druidRequestBuilder);
			if (!this.timeseriesAggregations.isEmpty())
				druidTimeseriesRequest.getAggregations().addAll(this.timeseriesAggregations);

			return druidTimeseriesRequest;
		}
	}

}